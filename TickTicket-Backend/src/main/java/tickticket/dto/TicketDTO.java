package tickticket.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import tickticket.model.Ticket;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TicketDTO {
    private UUID id;
    private LocalDateTime bookingDate;
    private UUID userId;
    private UUID eventId;

    public static TicketDTO from(Ticket ticket) {
        return new TicketDTO()
                .setId(ticket.getId())
                .setBookingDate(ticket.getBookingDate())
                .setUserId(ticket.getUser().getId())
                .setEventId(ticket.getEvent().getId());
    }
}
