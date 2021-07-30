package cinema.controller;

import cinema.entities.request.PurchaseSeatRequest;
import cinema.entities.request.ReturnTicketRequest;
import cinema.entities.response.AvailableSeatsResponse;
import cinema.entities.response.PurchasedTicketResponse;
import cinema.entities.response.ReturnTickedResponse;
import cinema.storage.CinemaStorage;
import cinema.storage.TicketBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class CinemaController {

    private final CinemaStorage storage;

    public CinemaController(CinemaStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/seats")
    public AvailableSeatsResponse getSeats() {
        return storage.getAvailableSeats();
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchasedTicketResponse> purchaseTicket(@RequestBody PurchaseSeatRequest body) {
        try {
            return new ResponseEntity<>(storage.purchaseTicket(body.row, body.column), HttpStatus.OK);
        } catch (TicketBadRequestException e) {
            return getBadRequestEntity(e);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnTickedResponse> returnTicket(@RequestBody ReturnTicketRequest body) {
        try {
            return new ResponseEntity<>(storage.returnTicket(body.token), HttpStatus.OK);
        } catch (TicketBadRequestException e) {
            return getBadRequestEntity(e);
        }
    }

    private <T> ResponseEntity<T> getBadRequestEntity(Exception e) {
        return new ResponseEntity(Collections.singletonMap("error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
