package cinema.storage;

import cinema.entities.AvailableSeats;
import cinema.entities.Seat;

public interface CinemaStorage {

    AvailableSeats getAvailableSeats();

    Seat purchaseSeat(int row, int column);
}
