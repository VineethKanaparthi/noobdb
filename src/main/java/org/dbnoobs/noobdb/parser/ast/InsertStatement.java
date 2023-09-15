package org.dbnoobs.noobdb.parser.ast;

import org.dbnoobs.noobdb.parser.tokens.Token;

import java.util.List;
import java.util.Objects;

public class InsertStatement {
    private Token table;
    private List<Expression> values;

    public InsertStatement(Token table, List<Expression> values) {
        this.table = table;
        this.values = values;
    }

    public Token getTable() {
        return table;
    }

    public void setTable(Token table) {
        this.table = table;
    }

    public List<Expression> getValues() {
        return values;
    }

    public void setValues(List<Expression> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "InsertStatement{" +
                "table=" + table +
                ", values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsertStatement that = (InsertStatement) o;
        return Objects.equals(table, that.table) && Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, values);
    }
}
