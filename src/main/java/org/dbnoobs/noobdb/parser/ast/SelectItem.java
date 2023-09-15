package org.dbnoobs.noobdb.parser.ast;

import org.dbnoobs.noobdb.parser.tokens.Token;

import java.util.Objects;

public class SelectItem {
    private Expression expression;
    private boolean asterisk;
    private Token as;

    public SelectItem(Expression expression, boolean asterisk, Token as) {
        this.expression = expression;
        this.asterisk = asterisk;
        this.as = as;
    }

    public SelectItem() {
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public boolean isAsterisk() {
        return asterisk;
    }

    public void setAsterisk(boolean asterisk) {
        this.asterisk = asterisk;
    }

    public Token getAs() {
        return as;
    }

    public void setAs(Token as) {
        this.as = as;
    }

    @Override
    public String toString() {
        return "SelectItem{" +
                "expression=" + expression +
                ", asterisk=" + asterisk +
                ", as=" + as +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectItem that = (SelectItem) o;
        return asterisk == that.asterisk && Objects.equals(expression, that.expression) && Objects.equals(as, that.as);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, asterisk, as);
    }
}
