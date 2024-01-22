package org.dbnoobs.noobdb.parser;

@FunctionalInterface
public interface LexerInterface {
    public Token apply(String input, Cursor cursor);
}
