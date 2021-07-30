package cinema.entities.response;

import cinema.entities.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReturnTickedResponse {

    @JsonProperty("returned_ticket")
    private final Seat seat;

    public ReturnTickedResponse(Seat seat) {
        this.seat = seat;
    }

    public Seat getSeat() {
        return seat;
    }
}
