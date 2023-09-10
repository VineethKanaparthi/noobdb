package org.dbnoobs.noobdb.tokens;

public class Cursor {
    private int pointer;
    private Location location;

    public Cursor(int pointer, Location location) {
        this.pointer = pointer;
        this.location = location;
    }

    public Cursor(Cursor cursor){
        this.pointer = cursor.getPointer();
        this.location = new Location(cursor.getLocation());
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void increment(){
        this.pointer++;
    }

}
