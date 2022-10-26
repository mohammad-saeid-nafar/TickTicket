package tickticket.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tickticket.dao.TicketRepository;
import tickticket.dto.EventDTO;
import tickticket.dto.TicketDTO;
import tickticket.dto.UserDTO;
import tickticket.model.Event;
import tickticket.model.Ticket;
import tickticket.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TicketService ticketService;

    private static final Event event1 = new Event();
    private static final EventDTO event1DTO = new EventDTO();

    private static final Event event2 = new Event();
    private static final EventDTO event2DTO = new EventDTO();

    private static final User user1 = new User();
    private static final UserDTO user1DTO = new UserDTO();

    private static final User user2 = new User();
    private static final UserDTO user2DTO = new UserDTO();

    private static final TicketDTO ticket1DTO = new TicketDTO();
    private static final Ticket ticket1 = new Ticket();

    private static final TicketDTO ticket2DTO = new TicketDTO();
    private static final Ticket ticket2 = new Ticket();

    private static final TicketDTO ticket3DTO = new TicketDTO();
    private static final Ticket ticket3 = new Ticket();

    private static final TicketDTO ticket4DTO = new TicketDTO();
    private static final Ticket ticket4 = new Ticket();

    @BeforeAll
    public static void init() {
        event1.setId(UUID.randomUUID());
        event1.setName("event1");
        event1DTO.setName("event1");

        event2.setId(UUID.randomUUID());
        event2.setName("event2");
        event2DTO.setName("event2");

        user1.setId(UUID.randomUUID());
        user1.setUsername("user1");
        user1DTO.setUsername("user1");

        user2.setId(UUID.randomUUID());
        user2.setUsername("user2");
        user2DTO.setUsername("user2");

        ticket1DTO.setId(UUID.randomUUID())
                .setEvent(event1DTO)
                .setUser(user1DTO);
        ticket1.setId(ticket1DTO.getId());
        ticket1.setEvent(event1);
        ticket1.setUser(user1);

        ticket2DTO.setId(UUID.randomUUID())
                .setEvent(event1DTO)
                .setUser(user2DTO);
        ticket2.setId(ticket2DTO.getId());
        ticket2.setEvent(event1);
        ticket2.setUser(user2);

        ticket3DTO.setId(UUID.randomUUID())
                .setEvent(event2DTO)
                .setUser(user1DTO);
        ticket3.setId(ticket3DTO.getId());
        ticket3.setEvent(event2);
        ticket3.setUser(user1);

        ticket4DTO.setId(UUID.randomUUID())
                .setEvent(event2DTO)
                .setUser(user2DTO);
        ticket4.setId(ticket4DTO.getId());
        ticket4.setEvent(event2);
        ticket4.setUser(user2);
    }

    @Test
    public void testCreateTicket() {
        when(eventService.getEventById(any())).thenReturn(event1);
        when(userService.getUser(any(String.class))).thenReturn(user1);
        when(ticketRepository.save(any())).thenReturn(ticket1);

        Ticket result = ticketService.createTicket(ticket1DTO);

        assertEquals(ticket1DTO.getId(), result.getId());
        assertEquals(ticket1DTO.getEvent().getName(), result.getEvent().getName());
        assertEquals(ticket1DTO.getUser().getUsername(), result.getUser().getUsername());
    }

    @Test
    public void testCreateTicketWithEventNotFound() {
        when(eventService.getEventById(any())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ticketService.createTicket(ticket1DTO));

        assertEquals("Event not found", exception.getMessage());
    }

    @Test
    public void testCreateTicketWithUserNotFound() {
        when(eventService.getEventById(any())).thenReturn(event1);
        when(userService.getUser(any(String.class))).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ticketService.createTicket(ticket1DTO));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testGetTicket() {
        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticket1));

        Ticket result = ticketService.getTicket(ticket1DTO.getId());

        assertEquals(ticket1DTO.getId(), result.getId());
        assertEquals(ticket1DTO.getEvent().getName(), result.getEvent().getName());
        assertEquals(ticket1DTO.getUser().getUsername(), result.getUser().getUsername());
    }

    @Test
    public void testGetTicketWithTicketNotFound() {
        when(ticketRepository.findById(any())).thenReturn(Optional.empty());

        UUID id = ticket1DTO.getId();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> ticketService.getTicket(id));

        assertEquals("Ticket " + id + " not found", exception.getMessage());
    }

    @Test
    public void testGetTicketByEventAndUser() {
        when(ticketRepository.findTicketByEventAndUser(any(), any())).thenReturn(Optional.of(ticket1));
        when(ticketRepository.existsByEventAndUser(any(), any())).thenReturn(true);

        Ticket result = ticketService.getTicketByEventAndUser(event1.getId(), user1.getId());
        boolean exists = ticketService.existsByEventAndUser(event1.getId(), user1.getId());

        assertTrue(exists);
        assertEquals(ticket1DTO.getId(), result.getId());
        assertEquals(ticket1DTO.getEvent().getName(), result.getEvent().getName());
        assertEquals(ticket1DTO.getUser().getUsername(), result.getUser().getUsername());
    }

    @Test
    public void testGetTicketByEventAndUserWithTicketNotFound() {
        when(ticketRepository.findTicketByEventAndUser(any(), any())).thenReturn(Optional.empty());
        when(ticketRepository.existsByEventAndUser(any(), any())).thenReturn(false);

        UUID eventId = event1.getId();
        UUID userId = user1.getId();
        boolean exists = ticketService.existsByEventAndUser(eventId, userId);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> ticketService.getTicketByEventAndUser(eventId, userId));

        assertFalse(exists);
        assertEquals("Ticket for event " + eventId + " and user " + userId + " not found", exception.getMessage());
    }

    @Test
    public void testGetTicketsByEvent() {
        when(ticketRepository.findTicketsByEvent(any())).thenReturn(Arrays.asList(ticket1, ticket2));

        List<Ticket> result = ticketService.getTicketsByEvent(event1.getId());

        assertEquals(2, result.size());
        assertEquals(ticket1DTO.getId(), result.get(0).getId());
        assertEquals(ticket1DTO.getEvent().getName(), result.get(0).getEvent().getName());
        assertEquals(ticket1DTO.getUser().getUsername(), result.get(0).getUser().getUsername());
        assertEquals(ticket2DTO.getId(), result.get(1).getId());
        assertEquals(ticket2DTO.getEvent().getName(), result.get(1).getEvent().getName());
        assertEquals(ticket2DTO.getUser().getUsername(), result.get(1).getUser().getUsername());
    }

    @Test
    public void testGetTicketsByEventWithTicketsNotFound() {
        when(ticketRepository.findTicketsByEvent(any())).thenReturn(Collections.emptyList());

        List<Ticket> result = ticketService.getTicketsByEvent(event1.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetTicketsByUser() {
        when(ticketRepository.findTicketsByUser(any())).thenReturn(Arrays.asList(ticket3, ticket4));

        List<Ticket> result = ticketService.getTicketsByUser(user1.getId());

        assertEquals(2, result.size());
        assertEquals(ticket3DTO.getId(), result.get(0).getId());
        assertEquals(ticket3DTO.getEvent().getName(), result.get(0).getEvent().getName());
        assertEquals(ticket3DTO.getUser().getUsername(), result.get(0).getUser().getUsername());
        assertEquals(ticket4DTO.getId(), result.get(1).getId());
        assertEquals(ticket4DTO.getEvent().getName(), result.get(1).getEvent().getName());
        assertEquals(ticket4DTO.getUser().getUsername(), result.get(1).getUser().getUsername());
    }

    @Test
    public void testGetTicketsByUserWithTicketsNotFound() {
        when(ticketRepository.findTicketsByUser(any())).thenReturn(Collections.emptyList());

        List<Ticket> result = ticketService.getTicketsByUser(user1.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllTickets() {
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket1, ticket2, ticket3, ticket4));

        List<Ticket> result = ticketService.getAllTickets();

        assertEquals(4, result.size());
        assertEquals(ticket1DTO.getId(), result.get(0).getId());
        assertEquals(ticket1DTO.getEvent().getName(), result.get(0).getEvent().getName());
        assertEquals(ticket1DTO.getUser().getUsername(), result.get(0).getUser().getUsername());
        assertEquals(ticket2DTO.getId(), result.get(1).getId());
        assertEquals(ticket2DTO.getEvent().getName(), result.get(1).getEvent().getName());
        assertEquals(ticket2DTO.getUser().getUsername(), result.get(1).getUser().getUsername());
        assertEquals(ticket3DTO.getId(), result.get(2).getId());
        assertEquals(ticket3DTO.getEvent().getName(), result.get(2).getEvent().getName());
        assertEquals(ticket3DTO.getUser().getUsername(), result.get(2).getUser().getUsername());
        assertEquals(ticket4DTO.getId(), result.get(3).getId());
        assertEquals(ticket4DTO.getEvent().getName(), result.get(3).getEvent().getName());
        assertEquals(ticket4DTO.getUser().getUsername(), result.get(3).getUser().getUsername());
    }
}
