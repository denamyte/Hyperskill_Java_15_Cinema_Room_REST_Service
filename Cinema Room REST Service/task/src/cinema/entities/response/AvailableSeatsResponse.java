package cinema.entities.response;

import cinema.entities.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Collections;

@Data
@Accessors(chain = true)
public class AvailableSeatsResponse {
    @JsonProperty("total_rows")
    private int rows;
    @JsonProperty("total_columns")
    private int columns;
    @JsonProperty("available_seats")
    private Collection<Seat> seats = Collections.emptyList();
}
