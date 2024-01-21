package org.dbnoobs.noobdb.parser.prattparsing2;

import java.util.*;

class Token {
    enum TokenType { NUMBER, PLUS, MINUS, MULTIPLY, LPAREN, RPAREN, EOF }
    TokenType type;
    String lexeme;

    public Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }
}

class Lexer {
    private String input;
    private int position = 0;

    public Lexer(String input) {
        this.input = input;
    }

    private void skipWhitespace() {
        while (position < input.length() && Character.isWhitespace(input.charAt(position))) {
            position++;
        }
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (position < input.length()) {
            skipWhitespace();
            char currentChar = input.charAt(position);

            if (Character.isDigit(currentChar)) {
                StringBuilder lexemeBuilder = new StringBuilder();
                while (position < input.length() && Character.isDigit(input.charAt(position))) {
                    lexemeBuilder.append(input.charAt(position));
                    position++;
                }
                tokens.add(new Token(Token.TokenType.NUMBER, lexemeBuilder.toString()));
            } else {
                switch (currentChar) {
                    case '+':
                        tokens.add(new Token(Token.TokenType.PLUS, "+"));
                        break;
                    case '-':
                        tokens.add(new Token(Token.TokenType.MINUS, "-"));
                        break;
                    case '*':
                        tokens.add(new Token(Token.TokenType.MULTIPLY, "*"));
                        break;
                    case '(':
                        tokens.add(new Token(Token.TokenType.LPAREN, "("));
                        break;
                    case ')':
                        tokens.add(new Token(Token.TokenType.RPAREN, ")"));
                        break;
                    default:
                        throw new RuntimeException("Invalid character: " + currentChar);
                }
                position++;
            }
        }
        tokens.add(new Token(Token.TokenType.EOF, ""));
        return tokens;
    }
}

class PrattParser {
    private List<Token> tokens;
    private int position = 0;
    private Token currentToken;

    public PrattParser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentToken = tokens.get(position);
    }

    public int parse() {
        return parseExpression(0);
    }

    Token getNextToken(){
        this.position++;
        if(position < tokens.size()){
            return tokens.get(position);
        }
        return new Token(Token.TokenType.EOF, "");
    }

    private int parseExpression(int precedence) {
        int left = parseTerm();
        while (precedence < getOperatorPrecedence(currentToken)) {
            Token operator = currentToken;
            currentToken = getNextToken();
            int right = parseExpression(getOperatorPrecedence(operator) + 1);
            left = applyOperator(left, operator, right);
        }
        return left;
    }

    private int parseTerm() {
        int left = parseFactor();

        while (currentToken.type == Token.TokenType.MULTIPLY) {
            Token operator = currentToken;
            currentToken = getNextToken();
            int right = parseFactor();
            left = applyOperator(left, operator, right);
        }

        return left;
    }

    private int parseFactor() {
        if (currentToken.type == Token.TokenType.NUMBER) {
            int value = Integer.parseInt(currentToken.lexeme);
            currentToken = getNextToken();
            return value;
        } else if (currentToken.type == Token.TokenType.MINUS) {
            currentToken = getNextToken();
            return -parseFactor();
        } else if (currentToken.type == Token.TokenType.LPAREN) {
            currentToken = getNextToken();
            int result = parseExpression(0);
            if (currentToken.type != Token.TokenType.RPAREN) {
                throw new RuntimeException("Expected ')'");
            }
            currentToken = getNextToken();
            return result;
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken.lexeme);
        }
    }

    private int getOperatorPrecedence(Token token) {
        switch (token.type) {
            case PLUS:
                return 1;
            case MINUS:
                return 1;
            case MULTIPLY:
                return 2;
            default:
                return 0;
        }
    }

    private int applyOperator(int left, Token operator, int right) {
        switch (operator.type) {
            case PLUS:
                return left + right;
            case MINUS:
                return left - right;
            case MULTIPLY:
                return left * right;
            default:
                throw new RuntimeException("Invalid operator: " + operator.lexeme);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String input = "3 + 5 * (2 - 1)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        PrattParser parser = new PrattParser(tokens);

        try {
            int result = parser.parse();
            System.out.println("Result: " + result);
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
