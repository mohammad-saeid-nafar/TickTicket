package tickticket.controller;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tickticket.dto.TicketDTO;
import tickticket.model.Ticket;
import tickticket.service.TicketService;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = ticketService.createTicket(ticketDTO);
        return ResponseEntity.ok(TicketDTO.from(ticket));
    }

    @GetMapping
    public ResponseEntity<List<TicketDTO>> viewAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        List<TicketDTO> ticketDTOS = tickets.stream()
                .map(TicketDTO::from)
                .toList();
        return ResponseEntity.ok(ticketDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> viewTicket(@PathVariable UUID id) {
        Ticket ticket = ticketService.getTicket(id);
        return ResponseEntity.ok(TicketDTO.from(ticket));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<TicketDTO>> viewTicketsByUser(@PathVariable UUID id) {
        List<Ticket> tickets = ticketService.getTicketsByUser(id);
        List<TicketDTO> ticketDTOS = tickets.stream()
                .map(TicketDTO::from)
                .toList();
        return ResponseEntity.ok(ticketDTOS);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<List<TicketDTO>> viewTicketsByEvent(@PathVariable UUID id) {
        List<Ticket> tickets = ticketService.getTicketsByEvent(id);
        List<TicketDTO> ticketDTOS = tickets.stream()
                .map(TicketDTO::from)
                .toList();
        return ResponseEntity.ok(ticketDTOS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/event/{eventId}/user/{userId}")
    public ResponseEntity<Void> deleteTicketByEventAndUser(@PathVariable UUID eventId, @PathVariable UUID userId) {
        ticketService.deleteTicketByEventAndUser(eventId, userId);
        return ResponseEntity.ok().build();
    }

}
