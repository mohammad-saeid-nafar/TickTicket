package tickticket.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tickticket.dao.EventRepository;
import tickticket.dao.TicketRepository;
import tickticket.dto.TicketDTO;
import tickticket.model.Event;
import tickticket.model.Ticket;

@Service
@AllArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserService userService;

    public Ticket createTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setBookingDate(LocalDateTime.now());

        UUID eventId = ticketDTO.getEventId();
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new IllegalArgumentException("Event " + eventId + " not found"));
        ticket.setEvent(event);

        UUID userId = ticketDTO.getUserId();
        ticket.setUser(userService.getUser(userId));

        return ticketRepository.save(ticket);
    }

    public Ticket getTicket(UUID id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Ticket " + id + " not found"));
    }

    public void deleteTicket(UUID id) {
        ticketRepository.deleteById(id);
    }

    public Ticket updateTicket(TicketDTO ticketDTO) {
        Ticket ticket = getTicket(ticketDTO.getId());

        if (ticketDTO.getEventId() != null && !ticketDTO.getEventId().equals(ticket.getEvent().getId())) {
            ticket.setEvent(eventRepository.findById(ticketDTO.getEventId()).orElseThrow(() ->
                    new IllegalArgumentException("Event " + ticketDTO.getEventId() + " not found")));
        }
        if (ticketDTO.getUserId() != null && !ticketDTO.getUserId().equals(ticket.getUser().getId())) {
            ticket.setUser(userService.getUser(ticketDTO.getUserId()));
        }

        return ticketRepository.save(ticket);
    }

    public boolean existsByEventAndUser(UUID eventId, UUID userId) {
        return ticketRepository.existsByEventAndUser(eventRepository.findById(eventId).orElseThrow(() ->
                new IllegalArgumentException("Event " + eventId + " not found")), userService.getUser(userId));
    }

    public Ticket getTicketByEventAndUser(UUID eventId, UUID userId) {
        return ticketRepository.findTicketByEventAndUser(
                eventRepository.findById(eventId).orElseThrow(() ->
                        new IllegalArgumentException("Event " + eventId + " not found")),
                userService.getUser(userId)).orElseThrow(() ->
                new IllegalArgumentException("Ticket for event " + eventId + " and user " + userId + " not found"));
    }

    public List<Ticket> getTicketsByUser(UUID userId) {
        return ticketRepository.findTicketsByUser(userService.getUser(userId));
    }

    public List<Ticket> getTicketsByEvent(UUID eventId) {
        return ticketRepository.findTicketsByEvent(eventRepository.findById(eventId).orElseThrow(() ->
                new IllegalArgumentException("Event " + eventId + " not found")));
    }

    public List<Ticket> getAllTickets() {
        return (List<Ticket>) ticketRepository.findAll();
    }

}
