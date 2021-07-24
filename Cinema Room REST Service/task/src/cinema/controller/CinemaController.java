package cinema.controller;

import cinema.entities.AvailableSeats;
import cinema.storage.CinemaStorage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaController {

    private final CinemaStorage storage;

    public CinemaController(CinemaStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/seats")
    AvailableSeats getSeats() {
        return storage.getAvailableSeats();
    }
}
