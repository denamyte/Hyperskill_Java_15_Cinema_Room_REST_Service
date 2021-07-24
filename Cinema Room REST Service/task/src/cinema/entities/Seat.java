package cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {

    private final int row;
    private final int column;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @JsonIgnore
    public String getKey() {
        return row + "_" + column;
    }
}
