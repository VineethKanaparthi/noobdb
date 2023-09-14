package org.dbnoobs.noobdb.parser.ast;

public class Cursor {
    private int index;

    public Cursor(int index) {
        this.index = index;
    }

    public Cursor() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void increment(){
        this.index++;
    }
}
