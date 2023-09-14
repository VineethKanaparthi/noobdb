package org.dbnoobs.noobdb.parser.ast;

import java.util.ArrayList;
import java.util.List;

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
}
