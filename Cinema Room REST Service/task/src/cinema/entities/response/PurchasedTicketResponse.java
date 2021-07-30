package cinema.entities.response;

import cinema.entities.Seat;

public class PurchasedTicketResponse {
    private final String token;
    private final Seat ticket;

    public PurchasedTicketResponse(String token, Seat ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public String getToken() {
        return token;
    }

    public Seat getTicket() {
        return ticket;
    }
}
