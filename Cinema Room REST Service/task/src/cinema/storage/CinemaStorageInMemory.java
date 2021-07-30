package cinema.storage;

import cinema.entities.response.AvailableSeatsResponse;
import cinema.entities.Seat;
import cinema.entities.response.PurchasedTicketResponse;
import cinema.entities.response.ReturnTickedResponse;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class CinemaStorageInMemory implements CinemaStorage {

    private static final int ROW_COUNT = 9;
    private static final int COL_COUNT = 9;
    private final Map<String, Seat> seatMap;
    private final Map<String, Seat> tokenToSeatMap = new HashMap<>();

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
    public AvailableSeatsResponse getAvailableSeats() {
        return new AvailableSeatsResponse()
                .setRows(ROW_COUNT)
                .setColumns(COL_COUNT)
                .setSeats(getAvailableSeatsCollection());
    }

    @Override
    public PurchasedTicketResponse purchaseTicket(int row, int column) {
        Seat seat = getSeatFromStorage(row, column);
        checkSeatIsAvailable(seat);
        seat.purchase();
        String token = UUID.randomUUID().toString();
        tokenToSeatMap.put(token, seat);
        return new PurchasedTicketResponse(token, seat);
    }

    @Override
    public ReturnTickedResponse returnTicket(String token) {
        Seat seat = tokenToSeatMap.remove(token);
        if (seat == null) {
            throw new TicketBadRequestException("Wrong token!");
        }
        makeSeatAvailable(seat.getRow(), seat.getColumn());
        return new ReturnTickedResponse(seat);
    }

    private Seat getSeatFromStorage(int row, int column) {
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

    private void makeSeatAvailable(int row, int column) {
        getSeatFromStorage(row, column).makeAvailable();
    }
}
