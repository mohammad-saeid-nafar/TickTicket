package tickticket.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import tickticket.controller.Conversion;
import tickticket.model.Event;
import tickticket.model.Ticket;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TicketDTO {
    private UUID id;
    private LocalDateTime bookingDate;
    private UserDTO user;
    private EventDTO event;

    public static TicketDTO from(Ticket ticket) {

        Event event = ticket.getEvent();
        EventDTO eventDTO = new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getCapacity(),
                event.getCost(),
                event.getAddress(),
                event.getEmail(),
                event.getPhoneNumber(),
                Conversion.convertToDTO(event.getOrganizer()),
                Conversion.convertToDTO(event.getEventSchedule()),
                event.getEventTypes().stream().map(Conversion::convertToDTO).toList()
        );

        return new TicketDTO()
                .setId(ticket.getId())
                .setBookingDate(ticket.getBookingDate())
                .setUser(Conversion.convertToDTO(ticket.getUser()))
                .setEvent(eventDTO);
    }
}
