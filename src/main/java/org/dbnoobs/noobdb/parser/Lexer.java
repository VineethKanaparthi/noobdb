package org.dbnoobs.noobdb.parser;

import org.dbnoobs.noobdb.tokens.Cursor;
import org.dbnoobs.noobdb.tokens.Location;
import org.dbnoobs.noobdb.tokens.Token;
import org.dbnoobs.noobdb.tokens.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {
    public List<Token> lex(String sql){
        List<Token> tokens = new ArrayList<>();
        Cursor cursor = new Cursor(0, new Location(0, 0));
        int i = 0, n = sql.length();
        while(cursor.getPointer() < n){
            List<LexerFunction> lexers = Arrays.asList(this::lexKeyword, this::lexSymbol, this::lexString, this::lexNumeric, this::lexIdentifier);
            boolean tokenFound = false;
            for(LexerFunction lexer : lexers){
                Token token = lexer.apply(sql, cursor);
                if(token != null){
                    tokens.add(token);
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

    private Token lexKeyword(String input, Cursor cursor){
        return null;
    }

    private Token lexSymbol(String input, Cursor cursor){
        return null;
    }

    private Token lexString(String input, Cursor cursor){
        return null;
    }

    private Token lexNumeric(String sql, Cursor inputCursor){
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
        inputCursor = cursor;
        return token;
    }

    private Token lexIdentifier(String input, Cursor cursor){
        return null;
    }

}
