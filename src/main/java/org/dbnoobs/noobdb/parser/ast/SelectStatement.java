package org.dbnoobs.noobdb.parser.ast;

import org.dbnoobs.noobdb.parser.tokens.Token;

import java.util.List;
import java.util.Objects;

public class SelectStatement {
    private Token from;
    private List<SelectItem> items;

    public SelectStatement() {
    }

    public SelectStatement(Token from, List<SelectItem> items) {
        this.from = from;
        this.items = items;
    }

    public Token getFrom() {
        return from;
    }

    public void setFrom(Token from) {
        this.from = from;
    }

    public List<SelectItem> getItems() {
        return items;
    }

    public void setItems(List<SelectItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SelectStatement{" +
                "from=" + from +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectStatement that = (SelectStatement) o;
        return Objects.equals(from, that.from) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, items);
    }
}
