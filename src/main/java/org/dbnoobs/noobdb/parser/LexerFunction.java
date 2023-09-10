package org.dbnoobs.noobdb.parser;

import org.dbnoobs.noobdb.tokens.Cursor;
import org.dbnoobs.noobdb.tokens.Token;

@FunctionalInterface
public interface LexerFunction {
    Token apply(String input, Cursor cursor);
}
