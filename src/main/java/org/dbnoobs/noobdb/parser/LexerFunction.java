package org.dbnoobs.noobdb.parser;

import org.dbnoobs.noobdb.parser.tokens.Cursor;
import org.dbnoobs.noobdb.parser.tokens.Token;

@FunctionalInterface
public interface LexerFunction {
    Token apply(String input, Cursor cursor);
}
