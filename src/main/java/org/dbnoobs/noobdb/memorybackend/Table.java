package org.dbnoobs.noobdb.memorybackend;

import java.util.List;
import java.util.Objects;

public class Table {
    private List<String> columns;
    private List<ColumnType> columnTypes;
    private List<List<MemoryCell>> rows;

    public Table() {
    }

    public Table(List<String> columns, List<ColumnType> columnTypes, List<List<MemoryCell>> rows) {
        this.columns = columns;
        this.columnTypes = columnTypes;
        this.rows = rows;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<ColumnType> getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(List<ColumnType> columnTypes) {
        this.columnTypes = columnTypes;
    }

    public List<List<MemoryCell>> getRows() {
        return rows;
    }

    public void setRows(List<List<MemoryCell>> rows) {
        this.rows = rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Objects.equals(columns, table.columns) && Objects.equals(columnTypes, table.columnTypes) && Objects.equals(rows, table.rows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columns, columnTypes, rows);
    }

    @Override
    public String toString() {
        return "Table{" +
                "columns=" + columns +
                ", columnTypes=" + columnTypes +
                ", rows=" + rows +
                '}';
    }
}
