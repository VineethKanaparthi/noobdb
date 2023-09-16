package org.dbnoobs.noobdb.memorybackend;

import java.util.List;
import java.util.Objects;

public class Results {
    private List<Column> columns;

    private List<List<Cell>> rows;

    public Results() {
    }

    public Results(List<Column> columns, List<List<Cell>> rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<List<Cell>> getRows() {
        return rows;
    }

    public void setRows(List<List<Cell>> rows) {
        this.rows = rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Results results = (Results) o;
        return Objects.equals(columns, results.columns) && Objects.equals(rows, results.rows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columns, rows);
    }

    @Override
    public String toString() {
        return "Results{" +
                "columns=" + columns +
                ", rows=" + rows +
                '}';
    }
}
