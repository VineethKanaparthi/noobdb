package org.dbnoobs.noobdb.memorybackend;

import org.dbnoobs.noobdb.memorybackend.exceptions.InvalidDataTypeException;
import org.dbnoobs.noobdb.memorybackend.exceptions.MissingValuesException;
import org.dbnoobs.noobdb.memorybackend.exceptions.TableDoesNotExistException;
import org.dbnoobs.noobdb.parser.ast.*;
import org.dbnoobs.noobdb.parser.tokens.Token;
import org.dbnoobs.noobdb.parser.tokens.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MemoryBackend implements Backend {
    private Map<String, Table> tables;

    public MemoryBackend() {
    }

    public MemoryBackend(Map<String, Table> tables) {
        this.tables = tables;
    }

    @Override
    public void createTable(CreateTableStatement createTableStatement) {
        if(createTableStatement == null || createTableStatement.getName() == null
                || createTableStatement.getName().getValue() == null
                || createTableStatement.getColumnDefinitions() == null){
            throw new RuntimeException("Expected table name, column definitions in create table statement but not found");
        }
        Table t = new Table();
        String tableName = createTableStatement.getName().getValue();
        this.tables.put(tableName, t);
        for(ColumnDefinition columnDefinition: createTableStatement.getColumnDefinitions()){
            if(columnDefinition == null || columnDefinition.getName() == null
                    || columnDefinition.getName().getValue() == null
                    || columnDefinition.getDatatype() == null || columnDefinition.getDatatype().getValue() == null) {
                throw new RuntimeException("Column name and datatype cannot be null");
            }
            t.getColumns().add(columnDefinition.getName().getValue());
            if("int".equals(columnDefinition.getDatatype().getValue())){
                t.getColumnTypes().add(ColumnType.INT);
            }else if("text".equals(columnDefinition.getDatatype().getValue())){
                t.getColumnTypes().add(ColumnType.TEXT);
            }else{
                throw new InvalidDataTypeException();
            }
        }
    }

    @Override
    public void insert(InsertStatement insertStatement) {
        if(insertStatement == null || insertStatement.getTable() == null || insertStatement.getTable().getValue() == null
        || insertStatement.getValues() == null ){
            throw new RuntimeException("Insert statement should have table name and values");
        }
        String tableName = insertStatement.getTable().getValue();
        if(!this.tables.containsKey(tableName) || this.tables.get(tableName)==null){
            throw new TableDoesNotExistException();
        }
        Table t = this.tables.get(tableName);
        if(insertStatement.getValues().size() != t.getColumns().size()){
            throw new MissingValuesException();
        }
        List<MemoryCell> row = new ArrayList<>();
        for(Expression value: insertStatement.getValues()){
            if(value != null && value.getLiteral() != null && value.getLiteral().getValue() != null){
                row.add(tokenToCell(value.getLiteral()));
            }else{
                throw new RuntimeException("Expected value but not found");
            }
        }
        t.getRows().add(row);
    }

    @Override
    public Results select(SelectStatement selectStatement) {
        return null;
    }

    private MemoryCell tokenToCell(Token token){
        if(token == null || token.getValue() == null){
            return null;
        }
        if(TokenType.NUMERIC.equals(token.getTokenType())){
            return new MemoryCell(Integer.parseInt(token.getValue()));
        }else if(TokenType.STRING.equals(token.getTokenType())){
            return new MemoryCell(token.getValue());
        }else{
            return null;
        }
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public void setTables(Map<String, Table> tables) {
        this.tables = tables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoryBackend that = (MemoryBackend) o;
        return Objects.equals(tables, that.tables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tables);
    }

    @Override
    public String toString() {
        return "MemoryBackend{" +
                "tables=" + tables +
                '}';
    }
}
