package org.dbnoobs.noobdb.memorybackend;

import org.dbnoobs.noobdb.memorybackend.exceptions.ColumnDoesNotExistException;
import org.dbnoobs.noobdb.memorybackend.exceptions.InvalidDataTypeException;
import org.dbnoobs.noobdb.memorybackend.exceptions.MissingValuesException;
import org.dbnoobs.noobdb.memorybackend.exceptions.TableDoesNotExistException;
import org.dbnoobs.noobdb.parser.ast.*;
import org.dbnoobs.noobdb.parser.tokens.Token;
import org.dbnoobs.noobdb.parser.tokens.TokenType;

import java.util.*;

public class MemoryBackend implements Backend {
    private Map<String, Table> tables;

    public MemoryBackend() {
        tables = new HashMap<>();
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
        Table t = new Table(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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
        if(selectStatement == null){
            throw new RuntimeException("Select statement cannot be null");
        }
        if(selectStatement.getFrom() != null && selectStatement.getFrom().getValue() != null){
            String tableName = selectStatement.getFrom().getValue();
            if(!this.tables.containsKey(tableName) || this.tables.get(tableName)==null){
                throw new TableDoesNotExistException();
            }
        }

        if(selectStatement.getItems() == null || selectStatement.getItems().size() == 0){
            return new Results();
        }
        String tableName = selectStatement.getFrom().getValue();
        Table t = this.tables.get(tableName);
        Results results = new Results();
        List<Column> columns = new ArrayList<>();
        List<List<Cell>> rows = new ArrayList<>();
        List<Integer> colNumbers = new ArrayList<>();
        for(SelectItem item: selectStatement.getItems()){
            if(!item.getExpression().getType().equals(ExpressionType.LITERAL)){
                System.out.println("Skipping non literal expression");
                continue;
            }
            Token token = item.getExpression().getLiteral();
            if(token.getTokenType().equals(TokenType.IDENTIFIER)){
                boolean found  = false;
                for (int j = 0; j < t.getColumns().size(); j++) {
                    if (t.getColumns().get(j).equals(token.getValue())) {
                        columns.add(new Column(t.getColumnTypes().get(j), token.getValue()));
                        colNumbers.add(j);
                        found = true;
                        break;
                    }
                }
                if(!found){
                    throw new ColumnDoesNotExistException();
                }
                continue;
            }
            throw new ColumnDoesNotExistException();
        }
        for(int i=0;i<t.getRows().size();i++){
            List<Cell> row = new ArrayList<>();
            for(int j: colNumbers){
                row.add(t.getRows().get(i).get(j));
            }
            rows.add(row);
        }
        results.setColumns(columns);
        results.setRows(rows);
        return results;
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
