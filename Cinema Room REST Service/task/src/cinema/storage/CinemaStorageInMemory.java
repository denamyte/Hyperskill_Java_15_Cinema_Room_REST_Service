package cinema.storage;

import cinema.entities.Seat;
import cinema.entities.response.*;
import cinema.storage.exception.TicketBadRequestException;
import cinema.storage.exception.UnathorizedException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class CinemaStorageInMemory implements CinemaStorage {

    private static final int ROW_COUNT = 9;
    private static final int COL_COUNT = 9;
    private final Map<String, Seat> seatMap;
    // Token to Seat map
    private final Map<String, Seat> purchasedSeatsMap = new HashMap<>();

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
        purchasedSeatsMap.put(token, seat);
        return new PurchasedTicketResponse(token, seat);
    }

    @Override
    public ReturnTicketResponse returnTicket(String token) {
        Seat seat = purchasedSeatsMap.remove(token);
        if (seat == null) {
            throw new TicketBadRequestException("Wrong token!");
        }
        makeSeatAvailable(seat.getRow(), seat.getColumn());
        return new ReturnTicketResponse(seat);
    }

    @Override
    public StatisticsResponse statistics(String password) {
        checkPassword(password);
        return new StatisticsResponse()
                .setIncome(purchasedSeatsMap.values().stream().mapToInt(Seat::getPrice).sum())
                .setAvailableSeats(seatMap.size() - purchasedSeatsMap.size())
                .setPurchasedTickets(purchasedSeatsMap.size());
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

    private void checkPassword(String password) {
        if (!"super_secret".equals(password)) {
            throw new UnathorizedException("The password is wrong!");
        }
    }
}
