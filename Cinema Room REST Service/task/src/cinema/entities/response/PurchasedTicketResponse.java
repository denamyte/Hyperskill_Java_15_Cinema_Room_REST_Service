package cinema.entities.response;

import cinema.entities.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PurchasedTicketResponse {
    private final String token;
    private final Seat ticket;
}
