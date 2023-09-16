package org.dbnoobs.noobdb.memorybackend;

import java.util.Objects;

public class Column {
    private ColumnType type;
    private String name;

    public Column() {
    }

    public Column(ColumnType type, String name) {
        this.type = type;
        this.name = name;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return type == column.type && Objects.equals(name, column.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

    @Override
    public String toString() {
        return "Column{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
