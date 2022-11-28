package tickticket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import tickticket.dao.EventRepository;
import tickticket.dao.EventScheduleRepository;
import tickticket.dao.TicketRepository;
import tickticket.dto.TicketDTO;
import tickticket.model.*;
import tickticket.service.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ID028CancelTicket {

    @Mock
    private EventTypeService eventTypeService;

    @Mock
    private EventScheduleRepository eventScheduleRepository;

    @Mock
    private UserService userService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventService eventService;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private static final UUID USER1_ID = UUID.randomUUID();
    private static final String USER1_USERNAME = "johnDoe";
    private static final String USER1_PASSWORD = "myP@assword1!";
    private static final LocalDate USER1_CREATED = LocalDate.of(2022, 07, 12);

    private static final UUID EVENT1_ID = UUID.randomUUID();
    private static final String EVENT1_NAME = "Justin Bieber Tour";
    private static final String EVENT1_DESCRIPTION = "Music Tour";
    private static final int EVENT1_CAPACITY = 600;
    private static final double EVENT1_COST = 250;
    private static final String EVENT1_ADDRESS = "True Square";
    private static final String EVENT1_EMAIL = "johndoe@gmail.comm";
    private static final String EVENT1_PHONE_NUMBER = "438 566 3241";
    private static final LocalDateTime EVENT1_START = LocalDateTime.of(2022, 10, 15, 19, 0);
    private static final LocalDateTime EVENT1_END = LocalDateTime.of(2022, 10, 15, 22, 0);

    private static final UUID EVENT_TYPE1_ID = UUID.randomUUID();
    private static final String EVENT_TYPE1_NAME = "Pop Music";

    @BeforeEach
    public void setMockOutput() {

        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
                User user = new User();
                user.setId(USER1_ID);
                user.setUsername(USER1_USERNAME);
                user.setPassword(USER1_PASSWORD);
                user.setCreated(USER1_CREATED);
                return user;
        });

        lenient().when(eventTypeService.getEventType(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
                EventType eventType = new EventType();
                eventType.setId(EVENT_TYPE1_ID);
                eventType.setName(EVENT_TYPE1_NAME);
                return eventType;
        });

        lenient().when(eventService.getEvent(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
                EventSchedule eventSchedule = new EventSchedule();
                eventSchedule.setStartDateTime(EVENT1_START);
                eventSchedule.setEndDateTime(EVENT1_END);
                Event event = new Event();
                event.setId(EVENT1_ID);
                event.setName(EVENT1_NAME);
                event.setDescription(EVENT1_DESCRIPTION);
                event.setAddress(EVENT1_ADDRESS);
                event.setEmail(EVENT1_EMAIL);
                event.setPhoneNumber(EVENT1_PHONE_NUMBER);
                event.setCapacity(EVENT1_CAPACITY);
                event.setCost(EVENT1_COST);
                event.setOrganizer(userService.getUser(USER1_ID));
                event.setEventSchedule(eventSchedule);
                event.setEventTypes(Collections.singletonList(eventTypeService.getEventType(EVENT_TYPE1_ID)));
                return event;
        });

        lenient().when(ticketRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        });

    }

    @Test
    public void deleteTicketSuccessful(){

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setEventId(EVENT1_ID);
        ticketDTO.setUserId(USER1_ID);
        Ticket ticket = new Ticket();

        try{
            ticket = ticketService.createTicket(ticketDTO);
            ticketService.deleteTicket(ticket.getId());
        }catch (IllegalArgumentException e){
            fail();
        }
        List test = new ArrayList<>();
        assertEquals(ticketRepository.findAll(), test);
    }
}
