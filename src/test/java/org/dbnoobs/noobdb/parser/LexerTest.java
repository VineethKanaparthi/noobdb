package org.dbnoobs.noobdb.parser;

import org.dbnoobs.noobdb.parser.tokens.Cursor;
import org.dbnoobs.noobdb.parser.tokens.Location;
import org.dbnoobs.noobdb.parser.tokens.Token;
import org.dbnoobs.noobdb.parser.tokens.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

// TODO: Add more comprehensive tests
class LexerTest {
    private final Lexer lexer = new Lexer();

    @Test
    void lex() {
        System.out.println(lexer.lex("select a"));
        System.out.println(lexer.lex("select 1"));
        System.out.println(lexer.lex("select true"));
        System.out.println(lexer.lex("select 'foo' || 'bar';"));
        System.out.println(lexer.lex("CREATE TABLE u (id INT, name TEXT)"));
        System.out.println(lexer.lex("insert into users Values (105, 233)"));
        System.out.println(lexer.lex("SELECT id FROM users;"));
    }

    @Test
    void lexString() {
        String source = "'This is a string with ''escaped'' apostrophes.'";
        Cursor ic = new Cursor();
        Token token = lexer.lexString(source, ic);
        Assertions.assertEquals("This is a string with ''escaped'' apostrophes.", token.getValue());
        Assertions.assertEquals(TokenType.STRING, token.getTokenType());
        Assertions.assertEquals(new Location(), token.getLocation());
        Assertions.assertEquals(new Location(1, 48), ic.getLocation());
        Assertions.assertEquals(48, ic.getPointer());
    }

    @Test
    void lexCharacterDelimited() {
        String source = "   \"This is a string with \"\"escaped\"\" double quotes.\"";
        Cursor ic = new Cursor(3, new Location(1, 3));
        Token token = lexer.lexCharacterDelimited(source, ic, '\"');
        Assertions.assertEquals("This is a string with \"\"escaped\"\" double quotes.", token.getValue());
        Assertions.assertEquals(TokenType.STRING, token.getTokenType());
        Assertions.assertEquals(new Location(1, 3), token.getLocation());
        Assertions.assertEquals(new Location(1, 53), ic.getLocation());
        Assertions.assertEquals(53, ic.getPointer());
    }

    @Test
    void lexNumeric() {
        Map<String, Token> tests = new HashMap<>() {{
            // valid values
            put("105", new Token("105", TokenType.NUMERIC, new Location(1, 0)));
            put("105 ", new Token("105", TokenType.NUMERIC, new Location(1, 0)));
            put("123.", new Token("123.", TokenType.NUMERIC, new Location(1, 0)));
            put("123.145", new Token("123.145", TokenType.NUMERIC, new Location(1, 0)));
            put("1e5", new Token("1e5", TokenType.NUMERIC, new Location(1, 0)));
            put("1.e21", new Token("1.e21", TokenType.NUMERIC, new Location(1, 0)));
            put("1.e-1", new Token("1.e-1", TokenType.NUMERIC, new Location(1, 0)));
            put("1.e+2", new Token("1.e+2", TokenType.NUMERIC, new Location(1, 0)));
            put("1e-1", new Token("1e-1", TokenType.NUMERIC, new Location(1, 0)));
            put("1.", new Token("1.", TokenType.NUMERIC, new Location(1, 0)));
            put(".1", new Token(".1", TokenType.NUMERIC, new Location(1, 0)));
            // invalid values
            put("e4", null);
            put("1..", null);
            put("1ee4", null);
            put(" 1", null);
        }};

        tests.forEach((input, expected)->{
            Cursor cursor = new Cursor();
            Assertions.assertEquals(expected, lexer.lexNumeric(input, cursor));
            if(expected == null){
                Assertions.assertEquals(new Location(), cursor.getLocation());
                Assertions.assertEquals(0, cursor.getPointer());
            }
        });
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

    @Test
    void lexIdentifier(){
        Cursor cursor = new Cursor(1, new Location(1, 1));
        Token intToken = lexer.lexIdentifier(" \"identifier1\"", cursor);
        Assertions.assertEquals("identifier1", intToken.getValue());
        Assertions.assertEquals(new Location(1, 1), intToken.getLocation());
        Assertions.assertEquals(TokenType.IDENTIFIER, intToken.getTokenType());
        Assertions.assertEquals(14, cursor.getPointer());
        Assertions.assertEquals(1, cursor.getLocation().getLine());
        Assertions.assertEquals(14, cursor.getLocation().getCol());

        cursor = new Cursor(1, new Location(1, 1));
        Token intoToken = lexer.lexIdentifier(" identifier1", cursor);
        Assertions.assertEquals("identifier1", intoToken.getValue());
        Assertions.assertEquals(new Location(1, 1), intoToken.getLocation());
        Assertions.assertEquals(TokenType.IDENTIFIER, intoToken.getTokenType());
        Assertions.assertEquals(12, cursor.getPointer());
        Assertions.assertEquals(1, cursor.getLocation().getLine());
        Assertions.assertEquals(12, cursor.getLocation().getCol());
    }
}