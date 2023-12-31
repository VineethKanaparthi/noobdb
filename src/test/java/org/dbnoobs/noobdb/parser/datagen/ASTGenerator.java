package org.dbnoobs.noobdb.parser.datagen;

import org.dbnoobs.noobdb.parser.ast.*;
import org.dbnoobs.noobdb.parser.tokens.Location;
import org.dbnoobs.noobdb.parser.tokens.Token;
import org.dbnoobs.noobdb.parser.tokens.TokenType;

import java.util.Arrays;
import java.util.List;

public class ASTGenerator {
    public static AST getSelectStarAST(){
        return new AST(
                List.of(
                        new Statement(
                                new SelectStatement(
                                        null,
                                        Arrays.asList(
                                                new SelectItem(null, true, null),
                                                new SelectItem(
                                                        new Expression(
                                                                new Token(
                                                                        "exclusive",
                                                                        TokenType.IDENTIFIER,
                                                                        new Location(1, 10)
                                                                ),
                                                                ExpressionType.LITERAL
                                                        ),
                                                        false,
                                                        null
                                                )
                                        )
                                ),
                                null,
                                null,
                                ASTType.SELECT
                        )
                )
        );
    }

    public static AST getSimpleSelectItemsAST(){
        return new AST(
                List.of(
                        new Statement(
                                new SelectStatement(
                                        new Token(
                                                "users",
                                                TokenType.IDENTIFIER,
                                                new Location(1, 33)
                                        ),
                                        Arrays.asList(
                                                new SelectItem(
                                                        new Expression(
                                                                new Token(
                                                                        "id",
                                                                        TokenType.IDENTIFIER,
                                                                        new Location(1, 7)
                                                                ),
                                                                ExpressionType.LITERAL
                                                        ),
                                                        false,
                                                        null
                                                ),
                                                new SelectItem(
                                                        new Expression(
                                                                new Token(
                                                                        "name",
                                                                        TokenType.IDENTIFIER,
                                                                        new Location(1, 11)
                                                                ),
                                                                ExpressionType.LITERAL
                                                        ),
                                                        false,
                                                        new Token(
                                                                "fullname",
                                                                TokenType.IDENTIFIER,
                                                                new Location(1, 19)
                                                        )
                                                )
                                        )
                                ),
                                null,
                                null,
                                ASTType.SELECT
                        )
                )
        );
    }

    public static AST insertStatement(){
        return new AST(
                List.of(
                        new Statement(
                                null,
                                null,
                                new InsertStatement(
                                        new Token(
                                                "users",
                                                TokenType.IDENTIFIER,
                                                new Location(1, 12)
                                        ),
                                        Arrays.asList(
                                                new Expression(
                                                        new Token(
                                                                "105",
                                                                TokenType.NUMERIC,
                                                                new Location(1, 26)
                                                        ),
                                                        ExpressionType.LITERAL
                                                ),
                                                new Expression(
                                                        new Token(
                                                                "233",
                                                                TokenType.NUMERIC,
                                                                new Location(1, 31)
                                                        ),
                                                        ExpressionType.LITERAL
                                                )
                                        )
                                ),
                                ASTType.INSERT
                        )
                )
        );
    }

    public static AST createStatement() {
        return new AST(
                List.of(
                        new Statement(
                                null,
                                new CreateTableStatement(
                                        new Token(
                                                "users",
                                                TokenType.IDENTIFIER,
                                                new Location(1, 13)
                                        ),
                                        Arrays.asList(
                                                new ColumnDefinition(
                                                        new Token(
                                                                "id",
                                                                TokenType.IDENTIFIER,
                                                                new Location(1, 20)
                                                        ),
                                                        new Token(
                                                                "int",
                                                                TokenType.KEYWORD,
                                                                new Location(1, 23)
                                                        )
                                                ),
                                                new ColumnDefinition(
                                                        new Token(
                                                                "name",
                                                                TokenType.IDENTIFIER,
                                                                new Location(1, 28)
                                                        ),
                                                        new Token(
                                                                "text",
                                                                TokenType.KEYWORD,
                                                                new Location(1, 33)
                                                        )
                                                )
                                        )
                                ),
                                null,
                                ASTType.CREATE
                        )
                )
        );
    }

}
