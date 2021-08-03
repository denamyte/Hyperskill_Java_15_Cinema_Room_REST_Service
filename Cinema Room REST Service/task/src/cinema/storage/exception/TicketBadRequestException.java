package cinema.storage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TicketBadRequestException extends RuntimeException {
    public TicketBadRequestException(String message) {
        super(message);
    }
}
