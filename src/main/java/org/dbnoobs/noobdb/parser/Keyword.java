package org.dbnoobs.noobdb.parser;

import java.util.Arrays;
import java.util.List;

public class Keyword {
    public static final String INSERT = "insert";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    public static final List<String> keywords = Arrays.asList(INSERT, CREATE, UPDATE, DELETE);

}
