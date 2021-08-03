package cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Seat {

    private final int row;
    private final int column;
    private final int price;
    @JsonIgnore
    private boolean purchased;

    public void purchase() {
        this.purchased = true;
    }

    public void makeAvailable() {
        this.purchased = false;
    }

    @JsonIgnore
    public String getKey() {
        return getKey(row, column);
    }

    public static String getKey(int row, int column) {
        return row + "_" + column;
    }
}
