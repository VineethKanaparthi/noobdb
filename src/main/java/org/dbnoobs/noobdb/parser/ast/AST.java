package org.dbnoobs.noobdb.parser.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AST {
    private List<Statement> statements;

    public AST(){
        this.statements = new ArrayList<>();
    }
    public AST(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void appendStatement(Statement statement){
        this.statements.add(statement);
    }

    @Override
    public String toString() {
        return "AST{" +
                "statements=" + statements +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AST ast = (AST) o;
        return Objects.equals(statements, ast.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements);
    }
}
