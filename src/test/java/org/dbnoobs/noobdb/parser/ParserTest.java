package org.dbnoobs.noobdb.parser;

import org.dbnoobs.noobdb.parser.datagen.ASTGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parseSelect() {
        Parser parser = new Parser();
        Assertions.assertEquals(ASTGenerator.getSelectStarAST(), parser.parse("select *, exclusive"));
        Assertions.assertEquals(ASTGenerator.getSimpleSelectItemsAST(), parser.parse("select id, name as fullname from users"));
    }

    @Test
    void parseInsert() {
        Parser parser = new Parser();
        Assertions.assertEquals(ASTGenerator.insertStatement(), parser.parse("INSERT INTO users VALUES (105, 233)"));
   }

   @Test
    void parseCreate(){
        Parser parser = new Parser();
       Assertions.assertEquals(ASTGenerator.createStatement(), parser.parse("CREATE TABLE users (id INT, name TEXT)"));
   }
}