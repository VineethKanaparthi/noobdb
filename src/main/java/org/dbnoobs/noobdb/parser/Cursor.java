package org.dbnoobs.noobdb.parser;

import java.util.Objects;

public class Cursor {
    Integer position;
    Location location;

    public Cursor(){
        this.position = 0;
        this.location = new Location();
    }

    public Cursor(Integer position, Location location) {
        this.position = position;
        this.location = location;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void incrementPosition(int length){
        this.position += length;
        this.location.incrementPosition(length);
    }

    public void incrementLine(){
        this.position+=1;
        this.location.incrementLine();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cursor cursor = (Cursor) o;
        return Objects.equals(position, cursor.position) && Objects.equals(location, cursor.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, location);
    }

    @Override
    public String toString() {
        return "Cursor{" +
                "position=" + position +
                ", location=" + location +
                '}';
    }
}
