package org.dbnoobs.noobdb.parser;

import java.util.Arrays;
import java.util.List;

public class Keyword {
    public static final String INSERT = "insert";
    public static final String SELECT = "select";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String TABLE = "table";
    public static final String FROM = "from";
    public static final String INTO = "into";
    public static final String VALUES = "values";
    public static final String INT = "int";
    public static final String TEXT = "text";

    protected static final List<String> keywords = Arrays.asList(INSERT, SELECT, CREATE, UPDATE, DELETE, TABLE,
            FROM, INTO, VALUES, INT, TEXT);

}
