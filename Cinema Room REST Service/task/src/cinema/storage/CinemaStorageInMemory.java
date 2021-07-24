package cinema.storage;

import cinema.entities.AvailableSeats;
import cinema.entities.Seat;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class CinemaStorageInMemory implements CinemaStorage {

    private static final int ROW_COUNT = 9;
    private static final int COL_COUNT = 9;
    private Map<String, Seat> availableSeats;

    {
        availableSeats = new LinkedHashMap<>();
        for (int row = 1; row <= ROW_COUNT; row++) {
            for (int col = 1; col <= COL_COUNT; col++) {
                Seat seat = new Seat(row, col);
                availableSeats.put(seat.getKey(), seat);
            }
        }
    }

    @Override
    public AvailableSeats getAvailableSeats() {
        return new AvailableSeats()
                .setRows(ROW_COUNT)
                .setColumns(COL_COUNT)
                .setSeats(availableSeats.values());
    }
}
