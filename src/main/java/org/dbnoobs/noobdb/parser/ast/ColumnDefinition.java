package org.dbnoobs.noobdb.parser.ast;

import org.dbnoobs.noobdb.parser.tokens.Token;

import java.util.Objects;

public class ColumnDefinition {
    private Token name;
    private Token datatype;

    public ColumnDefinition() {
    }

    public ColumnDefinition(Token name, Token datatype) {
        this.name = name;
        this.datatype = datatype;
    }

    public Token getName() {
        return name;
    }

    public void setName(Token name) {
        this.name = name;
    }

    public Token getDatatype() {
        return datatype;
    }

    public void setDatatype(Token datatype) {
        this.datatype = datatype;
    }

    @Override
    public String toString() {
        return "ColumnDefinition{" +
                "name=" + name +
                ", datatype=" + datatype +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnDefinition that = (ColumnDefinition) o;
        return Objects.equals(name, that.name) && Objects.equals(datatype, that.datatype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, datatype);
    }
}
