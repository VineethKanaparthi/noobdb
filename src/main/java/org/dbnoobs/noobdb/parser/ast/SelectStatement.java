package org.dbnoobs.noobdb.parser.ast;

import org.dbnoobs.noobdb.parser.tokens.Token;

import java.util.List;

public class SelectStatement {
    private Token from;
    private List<Expression> items;

    public SelectStatement() {
    }

    public SelectStatement(Token from, List<Expression> items) {
        this.from = from;
        this.items = items;
    }

    public Token getFrom() {
        return from;
    }

    public void setFrom(Token from) {
        this.from = from;
    }

    public List<Expression> getItems() {
        return items;
    }

    public void setItems(List<Expression> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SelectStatement{" +
                "from=" + from +
                ", items=" + items +
                '}';
    }
}
