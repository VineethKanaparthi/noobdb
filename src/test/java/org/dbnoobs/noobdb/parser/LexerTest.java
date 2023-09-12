package org.dbnoobs.noobdb.parser;

import org.dbnoobs.noobdb.tokens.Cursor;
import org.dbnoobs.noobdb.tokens.Location;
import org.dbnoobs.noobdb.tokens.Token;
import org.dbnoobs.noobdb.tokens.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LexerTest {
    private final Lexer lexer = new Lexer();

    @Test
    void lex() {
    }

    @Test
    void lexString() {
        String source = "'This is a string with ''escaped'' apostrophes.'";
        Cursor ic = new Cursor();
        Token token = lexer.lexString(source, ic);
        Assertions.assertEquals("This is a string with ''escaped'' apostrophes.", token.getValue());
        Assertions.assertEquals(TokenType.STRING, token.getTokenType());
        Assertions.assertEquals(new Location(), token.getLocation());
        Assertions.assertEquals(new Location(1, 47), ic.getLocation());
        Assertions.assertEquals(47, ic.getPointer());
    }

    @Test
    void lexCharacterDelimited() {
        String source = "   \"This is a string with \"\"escaped\"\" double quotes.\"";
        Cursor ic = new Cursor(3, new Location(1, 3));
        Token token = lexer.lexCharacterDelimited(source, ic, '\"');
        Assertions.assertEquals("This is a string with \"\"escaped\"\" double quotes.", token.getValue());
        Assertions.assertEquals(TokenType.STRING, token.getTokenType());
        Assertions.assertEquals(new Location(1, 3), token.getLocation());
        Assertions.assertEquals(new Location(1, 52), ic.getLocation());
        Assertions.assertEquals(52, ic.getPointer());
    }

    @Test
    void lexNumeric() {
    }
}