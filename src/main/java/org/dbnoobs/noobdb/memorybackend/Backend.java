package org.dbnoobs.noobdb.memorybackend;

import org.dbnoobs.noobdb.parser.ast.CreateTableStatement;
import org.dbnoobs.noobdb.parser.ast.InsertStatement;
import org.dbnoobs.noobdb.parser.ast.SelectStatement;

public interface Backend {
    public void createTable(CreateTableStatement createTableStatement);

    public void insert(InsertStatement insertStatement);

    public Results select(SelectStatement selectStatement);
}
