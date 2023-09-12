package org.dbnoobs.noobdb.tokens;

import java.util.Objects;

public class Token {
    private String value;
    private TokenType tokenType;

    private Location location;

    public Token(String value, TokenType tokenType, Location location) {
        this.value = value;
        this.tokenType = tokenType;
        this.location = location;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(value, token.value) && tokenType == token.tokenType && Objects.equals(location, token.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, tokenType, location);
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                ", tokenType=" + tokenType +
                ", location=" + location +
                '}';
    }
}

