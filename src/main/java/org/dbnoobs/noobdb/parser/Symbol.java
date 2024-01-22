package org.dbnoobs.noobdb.parser;

import java.util.Arrays;
import java.util.List;

public class Symbol {
    public static final String SEMICOLON = ";";
    public static final String EQUAL = "=";
    public static final String ASTRIX = "*";
    public static final String COMMA = ",";
    public static final String LEFT_PARENTHESES = "(";
    public static final String RIGHT_PARENTHESES = ")";
    public static final String AND = "&&";
    public static final String OR = "||";

    protected static final List<String> symbols = Arrays.asList(SEMICOLON, EQUAL, ASTRIX, COMMA,
            LEFT_PARENTHESES, RIGHT_PARENTHESES, AND, OR);
}
