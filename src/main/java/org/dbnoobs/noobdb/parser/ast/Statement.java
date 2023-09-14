package org.dbnoobs.noobdb.parser.ast;

public class Statement {
    private SelectStatement selectStatement;
    private CreateTableStatement createTableStatement;
    private InsertStatement insertStatement;
    private ASTType type;

    public Statement(){

    }

    public Statement(SelectStatement selectStatement, CreateTableStatement createTableStatement, InsertStatement insertStatement, ASTType type) {
        this.selectStatement = selectStatement;
        this.createTableStatement = createTableStatement;
        this.insertStatement = insertStatement;
        this.type = type;
    }

    public SelectStatement getSelectStatement() {
        return selectStatement;
    }

    public CreateTableStatement getCreateTableStatement() {
        return createTableStatement;
    }

    public InsertStatement getInsertStatement() {
        return insertStatement;
    }

    public ASTType getType() {
        return type;
    }

    public void setSelectStatement(SelectStatement selectStatement) {
        this.selectStatement = selectStatement;
    }

    public void setCreateTableStatement(CreateTableStatement createTableStatement) {
        this.createTableStatement = createTableStatement;
    }

    public void setInsertStatement(InsertStatement insertStatement) {
        this.insertStatement = insertStatement;
    }

    public void setType(ASTType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "selectStatement=" + selectStatement +
                ", createTableStatement=" + createTableStatement +
                ", insertStatement=" + insertStatement +
                ", type=" + type +
                '}';
    }
}
