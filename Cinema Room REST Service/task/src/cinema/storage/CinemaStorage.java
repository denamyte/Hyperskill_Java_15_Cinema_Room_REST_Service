package cinema.storage;

import cinema.entities.response.AvailableSeatsResponse;
import cinema.entities.response.PurchasedTicketResponse;
import cinema.entities.response.ReturnTickedResponse;

public interface CinemaStorage {

    AvailableSeatsResponse getAvailableSeats();

    PurchasedTicketResponse purchaseTicket(int row, int column);

    ReturnTickedResponse returnTicket(String token);
}
