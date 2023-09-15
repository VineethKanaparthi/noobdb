package org.dbnoobs.noobdb.parser.ast;

import org.dbnoobs.noobdb.parser.tokens.Token;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expression that = (Expression) o;
        return Objects.equals(literal, that.literal) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(literal, type);
    }
}
