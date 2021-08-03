package cinema.entities.response;

import cinema.entities.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReturnTicketResponse {
    @JsonProperty("returned_ticket")
    private final Seat seat;
}
