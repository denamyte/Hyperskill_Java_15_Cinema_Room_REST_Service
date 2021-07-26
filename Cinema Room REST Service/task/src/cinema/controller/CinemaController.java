package cinema.controller;

import cinema.entities.AvailableSeats;
import cinema.entities.Seat;
import cinema.entities.request.PurchaseSeatBody;
import cinema.storage.CinemaStorage;
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
    public AvailableSeats getSeats() {
        return storage.getAvailableSeats();
    }

    @PostMapping("/purchase")
    public ResponseEntity<Seat> purchaseSeat(@RequestBody PurchaseSeatBody body) {
        try {
            return new ResponseEntity<>(storage.purchaseSeat(body.row, body.column), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(Collections.singletonMap("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
