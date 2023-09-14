package org.dbnoobs.noobdb.parser.ast;

import org.dbnoobs.noobdb.parser.tokens.Token;

public class ColumnDefinition {
    private Token name;
    private Token datatype;

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
}
