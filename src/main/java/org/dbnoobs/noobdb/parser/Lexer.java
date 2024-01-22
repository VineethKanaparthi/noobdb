package org.dbnoobs.noobdb.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {



    public List<Token> lex(String input){
        Cursor cursor = new Cursor();
        List<Token> tokens = new ArrayList<>();
        if(input == null || input.isEmpty()){
            return tokens;
        }
        List<LexerInterface> lexers = Arrays.asList(this::lexWhitespace, this::lexSymbol, this::lexString, this::lexNumeric, this::lexIdentifier);
        while(cursor.getPosition() < input.length()){
            boolean tokenFound = false;
            for(LexerInterface lexer: lexers){
                Token token = lexer.apply(input, cursor);
                if(token != null){
                    if(!TokenType.WHITESPACE.equals(token.getTokenType())) {
                        tokens.add(token);
                    }
                    tokenFound = true;
                    break;
                }
            }
            // TODO: improve the error handling here
            if(!tokenFound){
                System.out.println("Couldn't parse token starting from position: "+ cursor.getPosition() + " at line number: " + cursor.getLocation().getLineNumber() + " at column: " + cursor.getLocation().getPosition());
                break;
            }
        }

        return tokens;
    }

    // TODO: test \t single character or double
    public Token lexWhitespace(String input, Cursor cursor){
        int startPosition = cursor.getPosition();
        int startLine = cursor.getLocation().getLineNumber();
        if(input == null || startPosition >= input.length()){
            return null;
        }
        char ch = input.charAt(startPosition);
        if(ch == ' ' || ch == '\t'){
            cursor.incrementPosition(1);
            return new Token(String.valueOf(ch), TokenType.WHITESPACE, new Location(startLine, startPosition));
        }else if(ch == '\n'){
            cursor.incrementLine();
            return new Token(String.valueOf(ch), TokenType.WHITESPACE, new Location(startLine, startPosition));
        }
        return null;
    }
    public Token lexSymbol(String input, Cursor cursor){
        int startPosition = cursor.getPosition();
        int startLine = cursor.getLocation().getLineNumber();
        if(input == null || startPosition >= input.length()){
            return null;
        }
        String match = null;
        for(String symbol : Symbol.symbols){
            if(startPosition + symbol.length() <= input.length()){
                String tokenValue = input.substring(startPosition, startPosition + symbol.length());
                if(symbol.equalsIgnoreCase(tokenValue) && (match == null || match.length() < symbol.length())){
                    match = tokenValue;
                }
            }
        }
        if(match != null){
            cursor.incrementPosition(match.length());
            return new Token(match, TokenType.SYMBOL, new Location(startLine, startPosition));
        }
        return null;
    }

    public Token lexString(String input, Cursor cursor){
        return lexCharacterDelimited(input, cursor,'\'');
    }
    
    public Token lexCharacterDelimited(String input, Cursor cursor, char delimiter){
        int startPosition = cursor.getPosition();
        int currPosition = cursor.getPosition();
        int startLine = cursor.getLocation().getLineNumber();
        if(input == null || startPosition >= input.length()){
            return null;
        }
        char ch = input.charAt(currPosition);
        if(ch != delimiter){
            return null;
        }
        currPosition++;
        while(currPosition < input.length()){
            ch = input.charAt(currPosition); currPosition++;
            if(ch == delimiter){
                if(currPosition < input.length() && input.charAt(currPosition) == delimiter){
                    currPosition++;
                    continue;
                }
                String tokenValue = input.substring(startPosition+1, currPosition-1);
                cursor.incrementPosition(tokenValue.length()+2);
                return new Token(tokenValue, TokenType.STRING, new Location(startLine, startPosition));
            }
        }
        return null;
    }

    /**
     * Numeric constants are accepted in these general forms:
     *
     * digits
     * digits.[digits][e[+-]digits]
     * [digits].digits[e[+-]digits]
     * digitse[+-]digits
     * where digits is one or more decimal digits (0 through 9).
     * At least one digit must be before or after the decimal point, if one is used.
     * At least one digit must follow the exponent marker (e), if one is present.
     * There cannot be any spaces or other characters embedded in the constant, except for underscores,
     * which can be used for visual grouping as described below.
     *
     * Note that any leading plus or minus sign is not actually considered part of the constant;
     * it is an operator applied to the constant.
     *
     * These are some examples of valid numeric constants:
     *
     *
     * 42
     * 3.5
     * 4.
     * .001
     * 5e2
     * 1.925e-3
     * @param input
     * @return
     */
    public Token lexNumeric(String input, Cursor cursor){
        int startPosition = cursor.getPosition();
        int currPosition = cursor.getPosition();
        int startLine = cursor.getLocation().getLineNumber();
        if(input == null || currPosition >= input.length()){
            return null;
        }
        boolean isAtLeastOneDigitPresent = false;
        boolean isPeriodPresent = false;
        char ch = input.charAt(currPosition);
        if(isPlusOrMinus(ch)) {
            currPosition++;
        }
        while(currPosition < input.length()){
            ch = input.charAt(currPosition);
            if(Character.isDigit(ch)){
                isAtLeastOneDigitPresent = true;
                currPosition++;
            }else if(ch == '.'){
                if(isPeriodPresent){
                    return null;
                }
                isPeriodPresent = true;
                currPosition++;
            }else if(ch == 'e'){
                currPosition++;
                // must have digit followed after e
                if(currPosition < input.length()){
                    // skip +-
                    if(isPlusOrMinus(input.charAt(currPosition))){
                        currPosition++;
                    }
                    if(currPosition < input.length() && Character.isDigit(input.charAt(currPosition))){
                        currPosition++;
                    }else{
                        return null;
                    }
                }else {
                    return null;
                }
            }else{
                break;
            }
        }

        if(isAtLeastOneDigitPresent){
            String tokenValue = input.substring(startPosition, currPosition);
            cursor.incrementPosition(tokenValue.length());
            return new Token(tokenValue, TokenType.NUMERIC, new Location(startLine, startPosition));
        }
        return null;
    }

    private static boolean isPlusOrMinus(char ch) {
        return ch == '+' || ch == '-';
    }

    /**
     * Identifiers must begin with a-z/A-Z or an underscore,
     * subsequent characters can be letters, underscores or digits
     * Note:
     * note that postgres standard allows diacritical marks and non latin letters,
     * but we are limiting our scope here
     * @param input - sql
     * @return Token of type identifier or keyword
     */
    public Token lexIdentifier(String input, Cursor cursor){
        int startPosition = cursor.getPosition();
        int currPosition = cursor.getPosition();
        int startLine = cursor.getLocation().getLineNumber();
        if(input == null || currPosition >= input.length()){
            return null;
        }
        char ch = input.charAt(currPosition);
        if(isAnEnglishAlphabetOrAnUnderScore(ch)){
            currPosition++;
            while(currPosition < input.length()){
                ch = input.charAt(currPosition);
                if(!(Character.isDigit(ch) || isAnEnglishAlphabetOrAnUnderScore(ch))){
                    break;
                }
                currPosition++;
            }
            String tokenValue = input.substring(startPosition, currPosition);
            cursor.incrementPosition(tokenValue.length());

            // check if token is a keyword
            for(String keyword: Keyword.keywords){
                if(keyword.equalsIgnoreCase(tokenValue)){
                    return new Token(tokenValue.toLowerCase(), TokenType.KEYWORD, new Location(startLine, startPosition));
                }
            }
            return new Token(tokenValue, TokenType.IDENTIFIER, new Location(startLine, startPosition));
        }
        return null;
    }

    private static boolean isAnEnglishAlphabetOrAnUnderScore(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_';
    }

}
