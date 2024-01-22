package org.dbnoobs.noobdb.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {
    public List<Token> lex(String input){
        List<Token> tokens = new ArrayList<>();
        if(input == null || input.isEmpty()){
            return tokens;
        }
        List<LexerInterface> lexers = Arrays.asList(this::lexKeyword, this::lexSymbol, this::lexString, this::lexNumeric, this::lexIdentifier);
        Cursor cursor = new Cursor();
        while(cursor.position < input.length()){
            boolean tokenFound = false;
            for(LexerInterface lexer: lexers){
                Token token = lexer.apply(input, cursor);
                if(token != null){
                    tokens.add(token);
                    tokenFound = true;
                    break;
                }
            }
            // TODO: improve the error handling here
            if(!tokenFound){
                System.out.println("Couldn't parse token starting from position: "+ cursor.position);
                break;
            }
        }

        return tokens;
    }

    public Token lexKeyword(String input, Cursor cursor){
        if(input == null || cursor == null || cursor.position >= input.length()){
            return null;
        }
        for(String keyword: Keyword.keywords){
            if(keyword.equalsIgnoreCase(input.substring(cursor.position, cursor.position + keyword.length()))){

            }
        }        return null;
    }

    public Token lexSymbol(String input, Cursor cursor){
        return null;
    }

    public Token lexString(String input, Cursor cursor){
        return null;
    }

    public Token lexNumeric(String input, Cursor cursor){
        return null;
    }

    public Token lexIdentifier(String input, Cursor cursor){
        return null;
    }

}
