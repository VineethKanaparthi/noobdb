package org.dbnoobs.noobdb.tokens;

public class Location {
    private int line;
    private int col;

    public Location(int line, int col) {
        this.line = line;
        this.col = col;
    }

    public Location(Location location){
        this.col = location.getCol();
        this.line = location.getLine();
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

}
