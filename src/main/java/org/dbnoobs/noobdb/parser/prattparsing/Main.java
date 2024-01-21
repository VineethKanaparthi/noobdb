package org.dbnoobs.noobdb.parser.prattparsing;

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

    public Token getNextToken() {
        if (position >= input.length()) {
            return new Token(Token.TokenType.EOF, "");
        }

        char currentChar = input.charAt(position);

        if (Character.isDigit(currentChar)) {
            StringBuilder lexemeBuilder = new StringBuilder();
            while (position < input.length() && Character.isDigit(input.charAt(position))) {
                lexemeBuilder.append(input.charAt(position));
                position++;
            }
            return new Token(Token.TokenType.NUMBER, lexemeBuilder.toString());
        }

        switch (currentChar) {
            case '+':
                position++;
                return new Token(Token.TokenType.PLUS, "+");
            case '-':
                position++;
                return new Token(Token.TokenType.MINUS, "-");
            case '*':
                position++;
                return new Token(Token.TokenType.MULTIPLY, "*");
            case '(':
                position++;
                return new Token(Token.TokenType.LPAREN, "(");
            case ')':
                position++;
                return new Token(Token.TokenType.RPAREN, ")");
            default:
                throw new RuntimeException("Invalid character: " + currentChar);
        }
    }
}

class PrattParser {
    private Lexer lexer;
    private Token currentToken;

    public PrattParser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    public int parse() {
        return parseExpression(0);
    }

    private int parseExpression(int precedence) {
        int left = parseTerm();
        while (precedence < getOperatorPrecedence(currentToken)) {
            Token operator = currentToken;
            currentToken = lexer.getNextToken();
            int right = parseExpression(getOperatorPrecedence(operator) + 1);
            left = applyOperator(left, operator, right);
        }
        return left;
    }

    private int parseTerm() {
        int left = parseFactor();

        while (currentToken.type == Token.TokenType.MULTIPLY) {
            Token operator = currentToken;
            currentToken = lexer.getNextToken();
            int right = parseFactor();
            left = applyOperator(left, operator, right);
        }

        return left;
    }

    private int parseFactor() {
        if (currentToken.type == Token.TokenType.NUMBER) {
            int value = Integer.parseInt(currentToken.lexeme);
            currentToken = lexer.getNextToken();
            return value;
        } else if (currentToken.type == Token.TokenType.MINUS) {
            currentToken = lexer.getNextToken();
            return -parseFactor();
        } else if (currentToken.type == Token.TokenType.LPAREN) {
            currentToken = lexer.getNextToken();
            int result = parseExpression(0);
            if (currentToken.type != Token.TokenType.RPAREN) {
                throw new RuntimeException("Expected ')'");
            }
            currentToken = lexer.getNextToken();
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
        PrattParser parser = new PrattParser(lexer);
        try {
            int result = parser.parse();
            System.out.println("Result: " + result);
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
