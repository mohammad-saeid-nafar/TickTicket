package tickticket.controller;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tickticket.dto.TicketDTO;
import tickticket.model.Ticket;
import tickticket.service.TicketService;

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

    @GetMapping("/{id}/users")
    public ResponseEntity<List<TicketDTO>> viewTicketsByUser(@PathVariable UUID id) {
        List<Ticket> tickets = ticketService.getTicketsByUser(id);
        List<TicketDTO> ticketDTOS = tickets.stream()
                .map(TicketDTO::from)
                .toList();
        return ResponseEntity.ok(ticketDTOS);
    }

    @GetMapping("/{id}/events")
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

}
