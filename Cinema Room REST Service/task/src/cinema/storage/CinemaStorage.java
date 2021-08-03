package cinema.storage;

import cinema.entities.response.*;

public interface CinemaStorage {

    AvailableSeatsResponse getAvailableSeats();

    PurchasedTicketResponse purchaseTicket(int row, int column);

    ReturnTicketResponse returnTicket(String token);

    StatisticsResponse statistics(String password);
}
