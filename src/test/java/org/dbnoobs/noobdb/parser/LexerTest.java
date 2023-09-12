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

    @Test
    void lexSymbol(){
        Cursor cursor = new Cursor(1, new Location(1, 1));
        Token actualLeftParan = lexer.lexSymbol(" (", cursor);
        Assertions.assertEquals("(", actualLeftParan.getValue());
        Assertions.assertEquals(TokenType.SYMBOL, actualLeftParan.getTokenType());
        Assertions.assertEquals(new Location(1, 1), actualLeftParan.getLocation());
        Assertions.assertEquals(2, cursor.getPointer());
        Assertions.assertEquals(1, cursor.getLocation().getLine());
        Assertions.assertEquals(2, cursor.getLocation().getCol());

        cursor = new Cursor(1, new Location(1, 1));
        Token actualRightParan = lexer.lexSymbol(" )) ", cursor);
        Assertions.assertEquals(")", actualRightParan.getValue());
        Assertions.assertEquals(TokenType.SYMBOL, actualRightParan.getTokenType());
        Assertions.assertEquals(new Location(1, 1), actualRightParan.getLocation());
        Assertions.assertEquals(2, cursor.getPointer());
        Assertions.assertEquals(1, cursor.getLocation().getLine());
        Assertions.assertEquals(2, cursor.getLocation().getCol());
    }

    @Test
    void lexKeyword(){
        Cursor cursor = new Cursor(1, new Location(1, 1));
        Token intToken = lexer.lexKeyword(" iNt", cursor);
        Assertions.assertEquals("int", intToken.getValue());
        Assertions.assertEquals(new Location(1, 1), intToken.getLocation());
        Assertions.assertEquals(TokenType.KEYWORD, intToken.getTokenType());
        Assertions.assertEquals(4, cursor.getPointer());
        Assertions.assertEquals(1, cursor.getLocation().getLine());
        Assertions.assertEquals(4, cursor.getLocation().getCol());

        cursor = new Cursor(1, new Location(1, 1));
        Token intoToken = lexer.lexKeyword(" iNtox", cursor);
        Assertions.assertEquals("into", intoToken.getValue());
        Assertions.assertEquals(new Location(1, 1), intoToken.getLocation());
        Assertions.assertEquals(TokenType.KEYWORD, intoToken.getTokenType());
        Assertions.assertEquals(5, cursor.getPointer());
        Assertions.assertEquals(1, cursor.getLocation().getLine());
        Assertions.assertEquals(5, cursor.getLocation().getCol());
    }
}