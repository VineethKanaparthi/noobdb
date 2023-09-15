package org.dbnoobs.noobdb.parser.ast;

import org.dbnoobs.noobdb.parser.tokens.Token;

import java.util.List;
import java.util.Objects;

public class CreateTableStatement {
    private Token name;
    private List<ColumnDefinition> columnDefinitions;

    public CreateTableStatement(Token name, List<ColumnDefinition> columnDefinitions) {
        this.name = name;
        this.columnDefinitions = columnDefinitions;
    }

    public Token getName() {
        return name;
    }

    public void setName(Token name) {
        this.name = name;
    }

    public List<ColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void setColumnDefinitions(List<ColumnDefinition> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    @Override
    public String toString() {
        return "CreateTableStatement{" +
                "name=" + name +
                ", columnDefinitions=" + columnDefinitions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateTableStatement that = (CreateTableStatement) o;
        return Objects.equals(name, that.name) && Objects.equals(columnDefinitions, that.columnDefinitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, columnDefinitions);
    }
}
