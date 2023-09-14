package org.dbnoobs.noobdb.parser;

import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parse() {
//        System.out.println(new Parser().parse("select * from table"));
//        System.out.println(new Parser().parse("select user from table1"));
        System.out.println(new Parser().parse("select user from db_table;"));
    }
}