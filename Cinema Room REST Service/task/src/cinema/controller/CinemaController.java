package cinema.controller;

import cinema.entities.request.PurchaseSeatRequest;
import cinema.entities.request.ReturnTicketRequest;
import cinema.entities.response.*;
import cinema.storage.CinemaStorage;
import cinema.storage.exception.UnathorizedException;
import cinema.storage.exception.TicketBadRequestException;
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
            return getErrorEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnTicketResponse> returnTicket(@RequestBody ReturnTicketRequest body) {
        try {
            return new ResponseEntity<>(storage.returnTicket(body.token), HttpStatus.OK);
        } catch (TicketBadRequestException e) {
            return getErrorEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<StatisticsResponse> statistics(@RequestParam(defaultValue = "") String password) {
        try {
            return new ResponseEntity<>(storage.statistics(password), HttpStatus.OK);
        } catch (UnathorizedException e) {
            return getErrorEntity(e, HttpStatus.UNAUTHORIZED);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> ResponseEntity<T> getErrorEntity(Exception e, HttpStatus status) {
        return new ResponseEntity(Collections.singletonMap("error", e.getMessage()), status);
    }
}
