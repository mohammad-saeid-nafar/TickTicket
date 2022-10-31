package tickticket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import tickticket.dao.TicketRepository;
import tickticket.model.Event;
import tickticket.model.EventSchedule;
import tickticket.model.Ticket;
import tickticket.model.User;
import tickticket.service.EventService;
import tickticket.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ID011ShowPastEventsUserTests {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private EventService eventService;

    private static final UUID EVENT1_ID = UUID.randomUUID();
    private static final String EVENT1_NAME = "Jazz";
    private static final String EVENT1_DESCRIPTION = "Jazz concert";
    private static final int EVENT1_CAPACITY = 20;
    private static final double EVENT1_COST = 25;
    private static final String EVENT1_ADDRESS = "123 Ave";
    private static final String EVENT1_EMAIL = "jazz@mail.ca";
    private static final String EVENT1_PHONE_NUMBER = "123456789";
    private static final LocalDateTime EVENT1_START = LocalDateTime.of(2022, 9, 15, 12, 0);
    private static final LocalDateTime EVENT1_END = LocalDateTime.of(2022, 9, 15, 23, 59);

    private static final UUID EVENT2_ID = UUID.randomUUID();
    private static final String EVENT2_NAME = "event name";
    private static final String EVENT2_DESCRIPTION = "event description";
    private static final int EVENT2_CAPACITY = 100;
    private static final double EVENT2_COST = 150.0;
    private static final String EVENT2_ADDRESS = "123 test ave";
    private static final String EVENT2_EMAIL = "testevent@mail.ca";
    private static final String EVENT2_PHONE_NUMBER = "12345678";
    private static final LocalDateTime EVENT2_START = LocalDateTime.of(2022, 10, 15, 12, 0);
    private static final LocalDateTime EVENT2_END = LocalDateTime.of(2022, 10, 15, 23, 59);

    private static final UUID ORGANIZER_ID = UUID.randomUUID();
    private static final String ORGANIZER_USERNAME = "Organizer";
    private static final String ORGANIZER_PASSWORD = "Password123";
    private static final LocalDate ORGANIZER_CREATED = LocalDate.of(2022, 9, 1);

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_USERNAME = "user1";
    private static final String USER_PASSWORD = "Password1";
    private static final LocalDate USER_CREATED = LocalDate.of(2022, 1, 1);

    private static final LocalDateTime BOOKING_DATE = LocalDateTime.of(2022, 9, 1, 12, 0);

    @BeforeEach
    public void setMockOutput() {

        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(ORGANIZER_ID)){
                User organizer = new User();
                organizer.setId(ORGANIZER_ID);
                organizer.setUsername(ORGANIZER_USERNAME);
                organizer.setPassword(ORGANIZER_PASSWORD);
                organizer.setCreated(ORGANIZER_CREATED);

                return organizer;
            }else if(invocation.getArgument(0).equals(USER_ID)){
                User user = new User();
                user.setId(USER_ID);
                user.setUsername(USER_USERNAME);
                user.setPassword(USER_PASSWORD);
                user.setCreated(USER_CREATED);

                return user;
            }else{
                return null;
            }

        });

        lenient().when(ticketRepository.findTicketsByUser(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {

            User organizer = userService.getUser(ORGANIZER_ID);
            User user = userService.getUser(USER_ID);

            EventSchedule eventSchedule1 = new EventSchedule();
            eventSchedule1.setStartDateTime(EVENT1_START);
            eventSchedule1.setEndDateTime(EVENT1_END);

            EventSchedule eventSchedule2 = new EventSchedule();
            eventSchedule2.setStartDateTime(EVENT2_START);
            eventSchedule2.setEndDateTime(EVENT2_END);

            Event event1 = new Event();
            event1.setId(EVENT1_ID);
            event1.setName(EVENT1_NAME);
            event1.setDescription(EVENT1_DESCRIPTION);
            event1.setAddress(EVENT1_ADDRESS);
            event1.setEmail(EVENT1_EMAIL);
            event1.setPhoneNumber(EVENT1_PHONE_NUMBER);
            event1.setCapacity(EVENT1_CAPACITY);
            event1.setCost(EVENT1_COST);
            event1.setOrganizer(organizer);
            event1.setEventSchedule(eventSchedule1);

            Event event2 = new Event();
            event2.setId(EVENT2_ID);
            event2.setName(EVENT2_NAME);
            event2.setDescription(EVENT2_DESCRIPTION);
            event2.setAddress(EVENT2_ADDRESS);
            event2.setEmail(EVENT2_EMAIL);
            event2.setPhoneNumber(EVENT2_PHONE_NUMBER);
            event2.setCapacity(EVENT2_CAPACITY);
            event2.setCost(EVENT2_COST);
            event2.setOrganizer(organizer);
            event2.setEventSchedule(eventSchedule2);

            Ticket ticket1 = new Ticket();
            ticket1.setUser(user);
            ticket1.setEvent(event1);
            ticket1.setBookingDate(BOOKING_DATE);

            Ticket ticket2 = new Ticket();
            ticket2.setUser(user);
            ticket2.setEvent(event2);
            ticket2.setBookingDate(BOOKING_DATE);

            List<Ticket> tickets = new ArrayList<>();
            tickets.add(ticket1);
            tickets.add(ticket2);

            return tickets;

        });
    }

    @Test
    public void getUserPastEvents1(){
        LocalDateTime dateTime = LocalDateTime.of(2022,10,16,12,0);
        List<Event> events = new ArrayList<>();
        try{
            events = eventService.getUserPastEvents(USER_ID, dateTime);
        }catch (IllegalArgumentException e){
            fail();
        }
        assertEquals(2, events.size());
        assertEquals(EVENT1_ID, events.get(0).getId());
        assertEquals(EVENT2_ID, events.get(1).getId());
    }

    @Test
    public void getUserPastEvents2(){
        LocalDateTime dateTime = LocalDateTime.of(2022, 10, 10, 12, 0);
        List<Event> events = new ArrayList<>();
        try{
            events = eventService.getUserPastEvents(USER_ID, dateTime);
        }catch (IllegalArgumentException e){
            fail();
        }
        assertEquals(1, events.size());
        assertEquals(EVENT1_ID, events.get(0).getId());
    }

    @Test
    public void getUserPastEventsFail(){
        LocalDateTime dateTime = LocalDateTime.of(2022, 9, 1, 12, 0);
        try{
            eventService.getUserPastEvents(USER_ID, dateTime);
        }catch (IllegalArgumentException e){
            assertEquals("You do not have any past events.", e.getMessage());
        }
    }

}
