package tickticket.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tickticket.dao.TicketRepository;
import tickticket.dto.TicketDTO;
import tickticket.model.Event;
import tickticket.model.Ticket;
import tickticket.model.User;

@Service
@AllArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventService eventService;
    private final UserService userService;

    public Ticket createTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setBookingDate(LocalDateTime.now());

        String eventName = ticketDTO.getEvent().getName();
        Event event = eventService.getEventByName(eventName);

        if (event == null) {
            throw new RuntimeException("Event not found");
        }
        ticket.setEvent(event);

        String username = ticketDTO.getUser().getUsername();
        User user = userService.getUser(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }
        ticket.setUser(user);

        return ticketRepository.save(ticket);
    }

    public Ticket getTicket(UUID id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Ticket " + id + " not found"));
    }

    public void deleteTicket(UUID id) {
        ticketRepository.deleteById(id);
    }

    public boolean existsByEventAndUser(UUID eventId, UUID userId) {
        return ticketRepository.existsByEventAndUser(eventService.getEventById(eventId), userService.getUser(userId));
    }

    public Ticket getTicketByEventAndUser(UUID eventId, UUID userId) {
        return ticketRepository.findTicketByEventAndUser(eventService.getEventById(eventId), userService.getUser(userId))
                .orElseThrow(() ->
                        new IllegalArgumentException("Ticket for event " + eventId + " and user " + userId + " not found"));
    }

    public List<Ticket> getTicketsByUser(UUID userId) {
        return ticketRepository.findTicketsByUser(userService.getUser(userId));
    }

    public List<Ticket> getTicketsByEvent(UUID eventId) {
        return ticketRepository.findTicketsByEvent(eventService.getEventById(eventId));
    }

    public List<Ticket> getAllTickets() {
        return (List<Ticket>) ticketRepository.findAll();
    }
}
