package org.dbnoobs.noobdb.parser.ast;

import org.dbnoobs.noobdb.parser.tokens.Token;

import java.util.List;

public class CreateTableStatement {
    private Token name;
    private List<Expression> columnDefinitions;

    public CreateTableStatement(Token name, List<Expression> columnDefinitions) {
        this.name = name;
        this.columnDefinitions = columnDefinitions;
    }

    public Token getName() {
        return name;
    }

    public void setName(Token name) {
        this.name = name;
    }

    public List<Expression> getColumnDefinitions() {
        return columnDefinitions;
    }

    public void setColumnDefinitions(List<Expression> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    @Override
    public String toString() {
        return "CreateTableStatement{" +
                "name=" + name +
                ", columnDefinitions=" + columnDefinitions +
                '}';
    }
}
