package org.dbnoobs.noobdb.parser;

import org.dbnoobs.noobdb.parser.datagen.ASTGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parse() {
        Parser parser = new Parser();
        Assertions.assertEquals(ASTGenerator.getSelectStarAST(), parser.parse("select *, exclusive"));
        Assertions.assertEquals(ASTGenerator.getSimpleSelectItemsAST(), parser.parse("select id, name as fullname from users"));
    }
}