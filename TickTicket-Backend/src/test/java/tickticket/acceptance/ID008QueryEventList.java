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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ID008QueryEventList {

//    @Mock
//    private TicketRepository ticketRepository;
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private EventService eventService;
//
//    private static final UUID EVENT1_ID = UUID.randomUUID();
//    private static final String EVENT1_NAME = "Wine and Cheese";
//    private static final String EVENT1_DESCRIPTION = "Graduation Wine and Cheese";
//    private static final int EVENT1_CAPACITY = 80;
//    private static final double EVENT1_COST = 20;
//    private static final String EVENT1_ADDRESS = "2620 rue Stanley";
//    private static final String EVENT1_EMAIL = "aly1@gmail.com";
//    private static final String EVENT1_PHONE_NUMBER = "514-888-88889";
//    private static final LocalDateTime EVENT1_START = LocalDateTime.of(2022, 9, 15, 12, 0);
//    private static final LocalDateTime EVENT1_END = LocalDateTime.of(2022, 9, 15, 23, 59);
//
//    private static final UUID EVENT2_ID = UUID.randomUUID();
//    private static final String EVENT2_NAME = "OSM Concert";
//    private static final String EVENT2_DESCRIPTION = "Classical Mucisc Concert";
//    private static final int EVENT2_CAPACITY = 450;
//    private static final double EVENT2_COST = 20;
//    private static final String EVENT2_ADDRESS = "2620 rue Stanley";
//    private static final String EVENT2_EMAIL = "aly1@gmail.com";
//    private static final String EVENT2_PHONE_NUMBER = "514-888-8888";
//    private static final LocalDateTime EVENT2_START = LocalDateTime.of(2022, 10, 15, 12, 0);
//    private static final LocalDateTime EVENT2_END = LocalDateTime.of(2022, 10, 15, 23, 59);
//
//    private static final UUID EVENT3_ID = UUID.randomUUID();
//    private static final String EVENT3_NAME = "Graduation Ceremony";
//    private static final String EVENT3_DESCRIPTION = "Graduation Ceremony for 2024 classe";
//    private static final int EVENT3_CAPACITY = 130;
//    private static final double EVENT3_COST = 80;
//    private static final String EVENT3_ADDRESS = "McGill Lower Field";
//    private static final String EVENT3_EMAIL = "aly2@gmail.com";
//    private static final String EVENT3_PHONE_NUMBER = "514-777-7777";
//    private static final LocalDateTime EVENT3_START = LocalDateTime.of(2022, 9, 15, 12, 0);
//    private static final LocalDateTime EVENT3_END = LocalDateTime.of(2022, 9, 15, 23, 59);
//
//    private static final UUID USER1_ID = UUID.randomUUID();
//    private static final String USER1_USERNAME = "aly1";
//    private static final String USER1_PASSWORD = "Aly1235!";
//    private static final LocalDate USER1_CREATED = LocalDate.of(2022, 10, 1);
//
//    private static final UUID USER2_ID = UUID.randomUUID();
//    private static final String USER2_USERNAME = "aly2";
//    private static final String USER2_PASSWORD = "Aly1233!";
//    private static final LocalDate USER2_CREATED = LocalDate.of(2022, 10, 3);
//
//    @Test
//    public void getListEvents1(){
//        LocalDateTime dateTime = LocalDateTime.of(2022,10,10,12,0);
//        List<Event> events = new ArrayList<>();
//        events = eventService.getAllEvents();
//        assertEquals(0, events.size());
//    }
//
//    @BeforeEach
//    public void setMockOutput() {
//
//        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
//            if(invocation.getArgument(0).equals(USER2_ID)){
//                User user1 = new User();
//                user1.setId(USER1_ID);
//                user1.setUsername(USER1_USERNAME);
//                user1.setPassword(USER1_PASSWORD);
//                user1.setCreated(USER1_CREATED);
//
//                return organizer;
//            }else if(invocation.getArgument(0).equals(USER2_ID)){
//                User user2 = new User();
//                user2.setId(USER2_ID);
//                user2.setUsername(USER2_USERNAME);
//                user2.setPassword(USER2_PASSWORD);
//                user2.setCreated(USER2_CREATED);
//
//                return user;
//            }else{
//                return null;
//            }
//
//        });
//
//        lenient().when(ticketRepository.findTicketsByUser(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
//
//            User organizer = userService.getUser(ORGANIZER_ID);
//            User user = userService.getUser(USER_ID);
//
//            EventSchedule eventSchedule1 = new EventSchedule();
//            eventSchedule1.setStartDateTime(EVENT1_START);
//            eventSchedule1.setEndDateTime(EVENT1_END);
//
//            EventSchedule eventSchedule2 = new EventSchedule();
//            eventSchedule2.setStartDateTime(EVENT2_START);
//            eventSchedule2.setEndDateTime(EVENT2_END);
//
//            Event event1 = new Event();
//            event1.setId(EVENT1_ID);
//            event1.setName(EVENT1_NAME);
//            event1.setDescription(EVENT1_DESCRIPTION);
//            event1.setAddress(EVENT1_ADDRESS);
//            event1.setEmail(EVENT1_EMAIL);
//            event1.setPhoneNumber(EVENT1_PHONE_NUMBER);
//            event1.setCapacity(EVENT1_CAPACITY);
//            event1.setCost(EVENT1_COST);
//            event1.setOrganizer(organizer);
//            event1.setEventSchedule(eventSchedule1);
//
//            Event event2 = new Event();
//            event2.setId(EVENT2_ID);
//            event2.setName(EVENT2_NAME);
//            event2.setDescription(EVENT2_DESCRIPTION);
//            event2.setAddress(EVENT2_ADDRESS);
//            event2.setEmail(EVENT2_EMAIL);
//            event2.setPhoneNumber(EVENT2_PHONE_NUMBER);
//            event2.setCapacity(EVENT2_CAPACITY);
//            event2.setCost(EVENT2_COST);
//            event2.setOrganizer(organizer);
//            event2.setEventSchedule(eventSchedule2);
//
//            Ticket ticket1 = new Ticket();
//            ticket1.setUser(user);
//            ticket1.setEvent(event1);
//            ticket1.setBookingDate(BOOKING_DATE);
//
//            Ticket ticket2 = new Ticket();
//            ticket2.setUser(user);
//            ticket2.setEvent(event2);
//            ticket2.setBookingDate(BOOKING_DATE);
//
//            List<Ticket> tickets = new ArrayList<>();
//            tickets.add(ticket1);
//            tickets.add(ticket2);
//
//            return tickets;
//
//        });
//    }
//
//
//    @Test
//    public void getListEvents2(){
//        LocalDateTime dateTime = LocalDateTime.of(2022,10,10,12,0);
//        List<Event> events = new ArrayList<>();
//        events = eventService.getAllEvents();
//        assertEquals(1, events.size());
//        assertEquals(EVENT1_ID, events.get(0).getId());
//    }
//
//    @Test
//    public void getListEvents3(){
//        LocalDateTime dateTime = LocalDateTime.of(2022,10,10,12,0);
//        List<Event> events = new ArrayList<>();
//        events = eventService.getAllEvents();
//        assertEquals(3, events.size());
//        assertEquals(EVENT1_ID, events.get(0).getId());
//        assertEquals(EVENT2_ID, events.get(1).getId());
//        ssertEquals(EVENT3_ID, events.get(2).getId());
//    }

}
