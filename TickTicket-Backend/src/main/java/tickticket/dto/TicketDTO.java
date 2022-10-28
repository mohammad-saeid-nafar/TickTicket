package tickticket.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import tickticket.controller.Conversion;
import tickticket.model.Event;
import tickticket.model.EventSchedule;
import tickticket.model.EventType;
import tickticket.model.Ticket;
import tickticket.model.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TicketDTO {
    private UUID id;
    private LocalDateTime bookingDate;

    private UUID userId;
    private UserDTO user;

    private UUID eventId;
    private EventDTO event;

    public static TicketDTO from(Ticket ticket) {

        Event event = ticket.getEvent();
        User organizer = event.getOrganizer();
        EventSchedule eventSchedule = event.getEventSchedule();
        List<EventType> eventTypes = event.getEventTypes();
        EventDTO eventDTO = new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getCapacity(),
                event.getCost(),
                event.getAddress(),
                event.getEmail(),
                event.getPhoneNumber(),
                Conversion.convertToDTO(organizer),
                Conversion.convertToDTO(eventSchedule),
                eventTypes.stream()
                        .map(Conversion::convertToDTO)
                        .toList()
        );
        eventDTO.setOrganizerId(organizer.getId());
        eventDTO.setEventScheduleId(eventSchedule.getId());
        eventDTO.setEventTypeIds(eventTypes.stream()
                .map(EventType::getId)
                .toList());
        eventDTO.setStart(eventSchedule.getStartDateTime());
        eventDTO.setEnd(eventSchedule.getEndDateTime());

        return new TicketDTO()
                .setId(ticket.getId())
                .setBookingDate(ticket.getBookingDate())
                .setUserId(ticket.getUser().getId())
                .setUser(Conversion.convertToDTO(ticket.getUser()))
                .setEventId(event.getId())
                .setEvent(eventDTO);
    }
}
