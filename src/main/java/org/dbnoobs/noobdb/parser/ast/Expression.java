package org.dbnoobs.noobdb.parser.ast;

import org.dbnoobs.noobdb.parser.tokens.Token;

public class Expression {
    private Token literal;
    private ExpressionType type;

    public Expression(Token literal, ExpressionType type) {
        this.literal = literal;
        this.type = type;
    }

    public Token getLiteral() {
        return literal;
    }

    public void setLiteral(Token literal) {
        this.literal = literal;
    }

    public ExpressionType getType() {
        return type;
    }

    public void setType(ExpressionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "literal=" + literal +
                ", type=" + type +
                '}';
    }
}
