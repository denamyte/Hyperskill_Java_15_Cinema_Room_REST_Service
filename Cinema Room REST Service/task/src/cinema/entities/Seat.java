package cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {

    private final int row;
    private final int column;
    private final int price;
    @JsonIgnore
    private boolean purchased;

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void purchase() {
        this.purchased = true;
    }

    @JsonIgnore
    public String getKey() {
        return getKey(row, column);
    }

    public static String getKey(int row, int column) {
        return row + "_" + column;
    }
}
