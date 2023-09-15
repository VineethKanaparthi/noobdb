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

}
