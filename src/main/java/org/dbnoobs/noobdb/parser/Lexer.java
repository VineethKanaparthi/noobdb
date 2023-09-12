package org.dbnoobs.noobdb.parser;

import org.dbnoobs.noobdb.tokens.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {
    public List<Token> lex(String sql){
        List<Token> tokens = new ArrayList<>();
        Cursor cursor = new Cursor();
        int n = sql.length();
        List<LexerFunction> lexers = Arrays.asList(this::lexKeyword, this::lexSymbol, this::lexString, this::lexNumeric, this::lexIdentifier);
        while(cursor.getPointer() < n){
            boolean tokenFound = false;
            for(LexerFunction lexer : lexers){
                Token token = lexer.apply(sql, cursor);
                if(token != null){
                    if(!TokenType.WHITESPACE.equals(token.getTokenType())){
                        tokens.add(token);
                    }
                    tokenFound = true;
                    break;
                }
            }
            String lastToken = (tokens.size() > 0)?tokens.get(tokens.size()-1).getValue(): "";
            if(!tokenFound){
                throw new RuntimeException(String.format("Unable to lex after token %s, at line: %d col: %d", lastToken, cursor.getLocation().getLine(), cursor.getLocation().getCol()));
            }
        }
        return tokens;
    }

    // TODO: Check why eaton phil used longest match instead of directly comparing strings
    public Token lexKeyword(String input, Cursor cursor){
        if(input == null || cursor.getPointer() >= input.length()){
            return null;
        }
        String match = null;
        for(String keyword : KeyWords.KEYWORDS){
            int begin = cursor.getPointer();
            int end = begin + keyword.length();
            if(end <= input.length() && keyword.equalsIgnoreCase(input.substring(begin, end)) && (match == null || keyword.length() > match.length())){
                match = keyword;
            }
        }
        if(match != null){
            Token token = new Token(match, TokenType.KEYWORD, new Location(cursor.getLocation()));
            cursor.increment(match.length());
            return token;
        }
        return null;
    }

    public Token lexSymbol(String input, Cursor inputCursor){
        if(input == null || input.isEmpty() || inputCursor.getPointer() >= input.length()){
            return null;
        }
        Cursor cursor = new Cursor(inputCursor);

        // white space syntax
        char c = input.charAt(cursor.getPointer());
        if(c == '\n'){
            inputCursor.incrementLine();
            return null;
        }else if(c == '\t' || c == ' '){
            inputCursor.increment();
            return null;
        }

        boolean matchFound = false;
        for(char symbol: Symbols.SYMBOLS){
            if(symbol == c){
                cursor.increment();
                matchFound = true;
                break;
            }
        }

        if(matchFound){
            Token token = new Token(input.substring(inputCursor.getPointer(), inputCursor.getPointer()+1), TokenType.SYMBOL, new Location(inputCursor.getLocation()));
            inputCursor.copy(cursor);
            return token;
        }
        return null;
    }

    public Token lexString(String input, Cursor cursor){
        return lexCharacterDelimited(input, cursor, '\'');
    }

    public Token lexCharacterDelimited(String input, Cursor inputCursor, char delimiter){
        if(input == null || input.isEmpty()){
            return null;
        }
        int n = input.length();
        Cursor cursor = new Cursor(inputCursor);
        if(cursor.getPointer() > n || input.charAt(cursor.getPointer()) != delimiter){
            return null;
        }
        cursor.increment();
        StringBuilder value = new StringBuilder();
        while(cursor.getPointer() < n){
            if(input.charAt(cursor.getPointer()) == delimiter){
                if(cursor.getPointer() + 1 < n && input.charAt(cursor.getPointer()) == delimiter){
                    value.append(delimiter);
                    cursor.increment();
                }else{
                    Token token = new Token(value.toString(), TokenType.STRING, new Location(inputCursor.getLocation()));
                    inputCursor.copy(cursor);
                    return token;
                }
            }
            value.append(input.charAt(cursor.getPointer()));
            cursor.increment();
        }
        return null;
    }

    public Token lexNumeric(String sql, Cursor inputCursor){
        if(sql == null || sql.isEmpty()){
            return null;
        }
        Cursor cursor = new Cursor(inputCursor);
        char firstChar = sql.charAt(cursor.getPointer());
        // skip the first character if it's a sign
        if(firstChar == '+' || firstChar == '-'){
            cursor.increment();
        }
        boolean periodExists = false;
        boolean exponentExists = false;
        boolean atLeastOneDigitExists = false;

        while(cursor.getPointer() < sql.length()){
            char c = sql.charAt(cursor.getPointer());
            boolean isDigit = Character.isDigit(c);
            boolean isExpMarker = (c=='e');
            boolean isPeriod = (c=='.');

            if(isDigit){
                atLeastOneDigitExists = true;
            }else if(isPeriod){
                if(periodExists){
                    return null;
                }
                periodExists = true;
            }else if(isExpMarker && !exponentExists){
                exponentExists = true;
                // at least one digit should be before exponent
                if(!atLeastOneDigitExists){
                    return null;
                }
                // at least 1 digit should follow after exponent
                cursor.increment();
                // if next is a sign skip it
                if(cursor.getPointer() < sql.length()){
                    char next = sql.charAt(cursor.getPointer());
                    if(next == '+' || next == '-'){
                        cursor.increment();
                    }
                }else{
                    return null;
                }
                // if next is not a digit return null
                if(cursor.getPointer() >= sql.length() || !Character.isDigit(sql.charAt(cursor.getPointer()))){
                    return null;
                }
            }else{
                break;
            }
            cursor.increment();
        }
        if(!atLeastOneDigitExists){
            return null;
        }

        Token token = new Token(sql.substring(inputCursor.getPointer(), cursor.getPointer()), TokenType.NUMERIC, new Location(inputCursor.getLocation()));
        inputCursor.copy(cursor);
        return token;
    }

    private Token lexIdentifier(String input, Cursor cursor){
        return null;
    }

}
