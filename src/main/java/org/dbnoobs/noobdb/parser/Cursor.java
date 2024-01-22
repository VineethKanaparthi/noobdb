package org.dbnoobs.noobdb.parser;

public class Cursor {
    Integer position;
    Location location;

    public Cursor(){
        this.position = 0;
        this.location = new Location();
    }
}
