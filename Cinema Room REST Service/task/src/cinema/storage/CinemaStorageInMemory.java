package cinema.storage;

import cinema.entities.AvailableSeats;
import cinema.entities.Seat;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class CinemaStorageInMemory implements CinemaStorage {

    private static final int ROW_COUNT = 9;
    private static final int COL_COUNT = 9;
    private Map<String, Seat> seatMap;

    {
        seatMap = new LinkedHashMap<>();
        for (int row = 1; row <= ROW_COUNT; row++) {
            int price = getPrice(row);
            for (int col = 1; col <= COL_COUNT; col++) {
                Seat seat = new Seat(row, col, price);
                seatMap.put(seat.getKey(), seat);
            }
        }
    }

    private static int getPrice(int row) {
        return row <= 4 ? 10 : 8;
    }

    @Override
    public AvailableSeats getAvailableSeats() {
        return new AvailableSeats()
                .setRows(ROW_COUNT)
                .setColumns(COL_COUNT)
                .setSeats(getAvailableSeatsCollection());
    }

    @Override
    public Seat purchaseSeat(int row, int column) {
        Seat seat = getSeat(row, column);
        checkSeatIsAvailable(seat);
        seat.purchase();
        return seat;
    }

    private Seat getSeat(int row, int column) {
        Seat seat = seatMap.get(Seat.getKey(row, column));
        if (seat == null) {
            throw new TicketBadRequestException("The number of a row or a column is out of bounds!");
        }
        return seat;
    }

    private void checkSeatIsAvailable(Seat seat) {
        if (seat.isPurchased()) {
            throw new TicketBadRequestException("The ticket has been already purchased!");
        }
    }

    private Collection<Seat> getAvailableSeatsCollection() {
        return seatMap.values()
                .stream()
                .filter(Predicate.not(Seat::isPurchased))
                .collect(Collectors.toList());
    }
}
