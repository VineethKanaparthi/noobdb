package org.dbnoobs.noobdb.parser;

import org.dbnoobs.noobdb.parser.ast.*;
import org.dbnoobs.noobdb.parser.tokens.KeyWords;
import org.dbnoobs.noobdb.parser.tokens.Symbols;
import org.dbnoobs.noobdb.parser.tokens.Token;
import org.dbnoobs.noobdb.parser.tokens.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public AST parse(String source){
        List<Token> tokens = new Lexer().lex(source);
        Token semiColonToken = tokenFromSymbol(Symbols.SEMICOLON_SYMBOL);
        if(tokens.size() > 0 && !tokens.get(tokens.size()-1).equals(semiColonToken)){
            tokens.add(semiColonToken);
        }
        AST ast = null;
        if(tokens != null && tokens.size() > 0){
            Cursor cursor = new Cursor();
            while(cursor.getIndex() < tokens.size()){
                Statement statement = parseStatement(tokens, cursor);
                ast = new AST();
                ast.appendStatement(statement);
                // expect semicolon
                boolean atLeastOneSemicolonExists = false;
                while(expectToken(tokens, cursor, tokenFromSymbol(Symbols.SEMICOLON_SYMBOL))){
                    atLeastOneSemicolonExists = true;
                    cursor.increment();
                }
                // throw exception if semicolon isnt there
                // TODO: improve exception message
                if(!atLeastOneSemicolonExists){
                    throw new RuntimeException();
                }
            }
        }
        return ast;

    }

    // does not increment cursor
    private boolean expectToken(List<Token> tokens, Cursor cursor, Token expectedToken) {
        if(tokens == null || cursor == null || expectedToken == null || cursor.getIndex() >= tokens.size()){
            // throw exception
            return false;
        }
        Token actualToken = tokens.get(cursor.getIndex());
        return (actualToken != null && expectedToken.getTokenType().equals(actualToken.getTokenType()) && expectedToken.getValue().equalsIgnoreCase(actualToken.getValue()));
    }

    private Token tokenFromSymbol(String symbol) {
        return new Token(symbol, TokenType.SYMBOL, null);
    }

    private Token tokenFromKeyword(String keyword) {
        return new Token(keyword, TokenType.KEYWORD, null);
    }

    private Statement parseStatement(List<Token> tokens, Cursor inputCursor) {
        Cursor cursor = new Cursor(inputCursor.getIndex());
        SelectStatement selectStatement = parseSelectStatement(tokens, cursor);
        if(selectStatement != null){
            Statement statement = new Statement();
            statement.setSelectStatement(selectStatement);
            statement.setType(ASTType.SELECT);
            inputCursor.setIndex(cursor.getIndex());
            return statement;
        }
//        InsertStatement insertStatement = parseInsertStatement(tokens, cursor);
//        if(insertStatement != null){
//            Statement statement = new Statement();
//            statement.setInsertStatement(insertStatement);
//            statement.setType(ASTType.INSERT);
//            inputCursor.setIndex(cursor.getIndex());
//            return statement;
//        }
//        CreateTableStatement createTableStatement = parseCreateStatement(tokens, cursor);
//        if(createTableStatement != null){
//            Statement statement = new Statement();
//            statement.setCreateTableStatement(createTableStatement);
//            statement.setType(ASTType.CREATE);
//            inputCursor.setIndex(cursor.getIndex());
//            return statement;
//        }
        throw new RuntimeException(String.format("Expected Select/Insert/Create Statement but not found at index: %d, %s", inputCursor.getIndex(), tokens.get(inputCursor.getIndex())));
    }

    private SelectStatement parseSelectStatement(List<Token> tokens, Cursor inputCursor) {
        if(tokens == null || inputCursor == null || inputCursor.getIndex() >= tokens.size()){
            return null;
        }
        Cursor cursor = new Cursor(inputCursor.getIndex());
        // expect select keyword first
        boolean isSelectKeyword = expectToken(tokens, cursor, tokenFromKeyword(KeyWords.SELECT_KEYWORD));
        if(!isSelectKeyword){
            return null;
        }
        cursor.increment();
        SelectStatement selectStatement = new SelectStatement();
        // expect atLeast one expression
        List<SelectItem> selectItems = new ArrayList<>();
        while(true){

            if(cursor.getIndex() >= tokens.size()){
                return null;
            }
            boolean isFromKeyword = expectToken(tokens, cursor, tokenFromKeyword(KeyWords.FROM_KEYWORD));
            boolean isSemicolon = expectToken(tokens, cursor, tokenFromSymbol(Symbols.SEMICOLON_SYMBOL));

            if(isFromKeyword || isSemicolon){
                break;
            }

            if(selectItems.size() > 0){
                boolean isComma = expectToken(tokens, cursor, tokenFromSymbol(Symbols.COMMA_SYMBOL));
                if(isComma){
                    cursor.increment();
                }else{
                    //TODO: improve exception handling
                    throw new RuntimeException("Expected comma but not found");
                }
            }

            SelectItem selectItem = paresSelectItem(tokens, cursor);
            if(selectItem == null){
                throw new RuntimeException("Expected expression but not found");
            }else{
                selectItems.add(selectItem);
            }
        }

        selectStatement.setItems(selectItems);
        // optionally expect from and table_name identifier
        if(expectToken(tokens, cursor, tokenFromKeyword(KeyWords.FROM_KEYWORD))){
            cursor.increment();
            Token from = parseToken(tokens, cursor, TokenType.IDENTIFIER);
            if(from == null){
                throw new RuntimeException("Expected from token but not present");
            }
            selectStatement.setFrom(from);
        }
        inputCursor.setIndex(cursor.getIndex());
        return selectStatement;
    }

    private SelectItem paresSelectItem(List<Token> tokens, Cursor inputCursor) {
        if(tokens == null || inputCursor == null || inputCursor.getIndex() >= tokens.size()){
            return null;
        }
        Cursor cursor = new Cursor(inputCursor.getIndex());
        if(expectToken(tokens, cursor, tokenFromSymbol(Symbols.ASTERISK_SYMBOL))){
            SelectItem selectItem = new SelectItem();
            selectItem.setAsterisk(true);
            cursor.increment();
            inputCursor.increment();
            return selectItem;
        }else{
            Expression expression = parseExpression(tokens, cursor);
            if(expression == null){
                return null;
            }
            SelectItem selectItem = new SelectItem();
            selectItem.setExpression(expression);
            if(expectToken(tokens, cursor, tokenFromKeyword(KeyWords.AS_KEYWORD))){
                cursor.increment();
                Token as = parseToken(tokens, cursor, TokenType.IDENTIFIER);
                if(as == null){
                    throw new RuntimeException("Expected alias after as");
                }
                selectItem.setAs(as);
            }
            inputCursor.setIndex(cursor.getIndex());
            return selectItem;
        }
    }

    // does increment cursor
    private Expression parseExpression(List<Token> tokens, Cursor inputCursor) {
        if(tokens == null || inputCursor == null || inputCursor.getIndex() >= tokens.size() ){
            return null;
        }
        Cursor cursor = new Cursor(inputCursor.getIndex());
        TokenType[] types = {TokenType.IDENTIFIER, TokenType.NUMERIC, TokenType.STRING};
        for(TokenType type: types) {
            Token token = parseToken(tokens, cursor, type);
            if (token != null) {
                inputCursor.setIndex(cursor.getIndex());
                return new Expression(token, ExpressionType.LITERAL);
            }
        }
        return null;
    }

    // TODO: does increment input cursor. should we?
    private Token parseToken(List<Token> tokens, Cursor inputCursor, TokenType type) {
        if(tokens == null || inputCursor == null || type == null || inputCursor.getIndex() >= tokens.size()){
            return null;
        }
        Token currentToken = tokens.get(inputCursor.getIndex());
        if(type.equals(currentToken.getTokenType())){
            inputCursor.increment();
            return currentToken;
        }
        return null;
    }
}
