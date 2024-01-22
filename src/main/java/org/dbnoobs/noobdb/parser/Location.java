package org.dbnoobs.noobdb.parser;

import java.util.Objects;

public class Location {

    private int lineNumber;
    private int position;
    public Location(){
        this.lineNumber = 1;
        this.position = 0;
    }

    public Location(int lineNumber, int position){
        this.lineNumber = lineNumber;
        this.position = position;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lineNumber=" + lineNumber +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return lineNumber == location.lineNumber && position == location.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, position);
    }

    public void incrementPosition(int length) {
        this.position += length;
    }

    public void incrementLine() {
        this.position = 0;
        this.lineNumber += 1;
    }
}
