package cinema.entities.response;

import cinema.entities.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class AvailableSeatsResponse {
    @JsonProperty("total_rows")
    private int rows;
    @JsonProperty("total_columns")
    private int columns;
    @JsonProperty("available_seats")
    private Collection<Seat> seats = Collections.emptyList();

    public int getRows() {
        return rows;
    }

    public AvailableSeatsResponse setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public int getColumns() {
        return columns;
    }

    public AvailableSeatsResponse setColumns(int columns) {
        this.columns = columns;
        return this;
    }

    public Collection<Seat> getSeats() {
        return new ArrayList<>(seats);
    }

    public AvailableSeatsResponse setSeats(Collection<Seat> seats) {
        this.seats = seats == null ? Collections.emptyList() : new ArrayList<>(seats);
        return this;
    }

}
