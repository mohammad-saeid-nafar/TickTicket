package tickticket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import tickticket.dao.EventRepository;
import tickticket.dao.EventTypeRepository;
import tickticket.dao.TicketRepository;
import tickticket.model.*;
import tickticket.service.TicketService;
import tickticket.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ID025FindTicketById {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

    @Mock
    private UserService userService;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    // Data for organizer
    private static final UUID ORGANIZER_ID = UUID.randomUUID();
    private static final String ORGANIZER_USERNAME = "johnDoe";
    private static final String ORGANIZER_PASSWORD = "myP@assword1";
    private static final LocalDate ORGANIZER_CREATED = LocalDate.of(2022, 7, 12);

    // Data for user
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_USERNAME = "bruceJ2";
    private static final String USER_PASSWORD = "BrUcE_@214";
    private static final LocalDate USER_CREATED = LocalDate.of(2022, 10, 3);

    // Data for event type
    private static final UUID EVENT_TYPE_ID = UUID.randomUUID();
    private static final String EVENT_TYPE_NAME = "Pop Music";
    private static final String EVENT_TYPE_DESCRIPTION = "Music";
    private static final int EVENT_TYPE_AGE_REQUIREMENT = 0;

    // Data for Event
    private static final UUID EVENT_ID = UUID.randomUUID();
    private static final String EVENT_NAME = "Justin Bieber Tour";
    private static final String EVENT_DESCRIPTION = "Music Tour";
    private static final int EVENT_CAPACITY = 600;
    private static final double EVENT_COST = 250.00;
    private static final String EVENT_ADDRESS = "True Square";
    private static final String EVENT_EMAIL = "organizer@mail.ca";
    private static final String EVENT_PHONE_NUMBER = "123456789";

    // Data for Event Schedule
    private static final LocalDateTime EVENT_START = LocalDateTime.of(2022, 10, 15, 19,0);
    private static final LocalDateTime EVENT_END = LocalDateTime.of(2022, 10, 15, 22, 0);

    // Data for Ticket
    private static final UUID TICKET_ID = UUID.randomUUID();
    private static final LocalDateTime TICKET_BOOKING_DATE = LocalDateTime.of(2022, 10, 14, 19,0);


    @BeforeEach
    public void setMockOutput() {

        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ORGANIZER_ID)) {

                User organizer = new User();
                organizer.setId(ORGANIZER_ID);
                organizer.setUsername(ORGANIZER_USERNAME);
                organizer.setPassword(ORGANIZER_PASSWORD);
                organizer.setCreated(ORGANIZER_CREATED);

                return organizer;
            } else if (invocation.getArgument(0).equals(USER_ID)) {

                User user = new User();
                user.setId(USER_ID);
                user.setUsername(USER_USERNAME);
                user.setPassword(USER_PASSWORD);
                user.setCreated(USER_CREATED);

                return user;
            } else {
                return null;
            }

        });

        lenient().when(eventTypeRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_TYPE_ID)) {

                EventType eventType1 = new EventType();
                eventType1.setId(EVENT_TYPE_ID);
                eventType1.setName(EVENT_TYPE_NAME);
                eventType1.setDescription(EVENT_TYPE_DESCRIPTION);
                eventType1.setAgeRequirement(EVENT_TYPE_AGE_REQUIREMENT);

                return Optional.of(eventType1);

            } else {
                return Optional.empty();
            }
        });

        lenient().when(eventRepository.findById(any())).thenAnswer((InvocationOnMock invocation) -> {

                User organizer = userService.getUser(ORGANIZER_ID);

                EventSchedule eventSchedule = new EventSchedule();
                eventSchedule.setStartDateTime(EVENT_START);
                eventSchedule.setEndDateTime(EVENT_END);

                Event event = new Event();
                event.setId(EVENT_ID);
                event.setName(EVENT_NAME);
                event.setDescription(EVENT_DESCRIPTION);
                event.setAddress(EVENT_ADDRESS);
                event.setEmail(EVENT_EMAIL);
                event.setPhoneNumber(EVENT_PHONE_NUMBER);
                event.setCapacity(EVENT_CAPACITY);
                event.setCost(EVENT_COST);
                event.setOrganizer(organizer);
                event.setEventSchedule(eventSchedule);
                event.setEventTypes(Collections.singletonList(eventTypeRepository.findById(EVENT_TYPE_ID).get()));

                return Optional.of(event);
        });

        lenient().when(ticketRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(TICKET_ID)) {
                Ticket ticket = new Ticket();
                ticket.setId(TICKET_ID);
                ticket.setEvent(eventRepository.findById(EVENT_ID).get());
                ticket.setUser(userService.getUser(USER_ID));
                ticket.setBookingDate(TICKET_BOOKING_DATE);
                return Optional.of(ticket);
            }else{
                return Optional.empty();
            }
        });
    }

    @Test
    public void testFindTicketByIdSuccess(){
        try{
            Ticket ticket = ticketService.getTicket(TICKET_ID);
            assertEquals(TICKET_ID, ticket.getId());
            assertEquals(TICKET_BOOKING_DATE, ticket.getBookingDate());
            assertEquals(EVENT_ID, ticket.getEvent().getId());
            assertEquals(USER_ID, ticket.getUser().getId());
        }catch (IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void testFindTicketByIdNotFound(){
        UUID id = UUID.randomUUID();
        try{
            ticketService.getTicket(id);
        }catch (IllegalArgumentException e){
            assertEquals("Ticket " + id + " not found", e.getMessage());
        }
    }

}
