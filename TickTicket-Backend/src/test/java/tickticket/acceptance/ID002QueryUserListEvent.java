package tickticket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import tickticket.dao.EventRepository;
import tickticket.dao.TicketRepository;
import tickticket.model.Event;
import tickticket.model.Ticket;
import tickticket.model.User;
import tickticket.service.EventService;
import tickticket.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ID002QueryUserListEvent {

    @Mock
    private UserService userService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EventRepository eventRepository;

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

    private static final UUID EVENT2_ID = UUID.randomUUID();
    private static final String EVENT2_NAME = "Pop";
    private static final String EVENT2_DESCRIPTION = "Pop concert";
    private static final int EVENT2_CAPACITY = 200;
    private static final double EVENT2_COST = 50;
    private static final String EVENT2_ADDRESS = "321 Ave";
    private static final String EVENT2_EMAIL = "pop@mail.ca";
    private static final String EVENT2_PHONE_NUMBER = "987654321";

    private static final UUID EVENT3_ID = UUID.randomUUID();
    private static final String EVENT3_NAME = "Rock";
    private static final String EVENT3_DESCRIPTION = "Rock concert";
    private static final int EVENT3_CAPACITY = 150;
    private static final double EVENT3_COST = 75;
    private static final String EVENT3_ADDRESS = "321 Ave";
    private static final String EVENT3_EMAIL = "pop@mail.ca";
    private static final String EVENT3_PHONE_NUMBER = "987654321";

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_USERNAME = "user1";
    private static final String USER_PASSWORD = "Password1";
    private static final LocalDate USER_CREATED = LocalDate.of(2022, 1, 1);

    private static final UUID USER_ID2 = UUID.randomUUID();
    private static final String USER_USERNAME2 = "user2";
    private static final String USER_PASSWORD2 = "Password2";
    private static final LocalDate USER_CREATED2 = LocalDate.of(2022, 1, 1);

    private static final UUID USER_ID3 = UUID.randomUUID();
    private static final String USER_USERNAME3 = "user3";
    private static final String USER_PASSWORD3 = "Password3";
    private static final LocalDate USER_CREATED3 = LocalDate.of(2022, 1, 1);

    private static final UUID USER_ID4 = UUID.randomUUID();
    private static final String USER_USERNAME4 = "user4";
    private static final String USER_PASSWORD4 = "Password4";
    private static final LocalDate USER_CREATED4 = LocalDate.of(2022, 1, 1);

    private static final LocalDateTime BOOKING_DATE = LocalDateTime.of(2022, 9, 1, 12, 0);

    @BeforeEach
    public void setMockOutput() {

        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USER_ID)){
                User user1 = new User();
                user1.setId(USER_ID);
                user1.setUsername(USER_USERNAME);
                user1.setPassword(USER_PASSWORD);
                user1.setCreated(USER_CREATED);
                return user1;
            }else if(invocation.getArgument(0).equals(USER_ID2)){
                User user2 = new User();
                user2.setId(USER_ID2);
                user2.setUsername(USER_USERNAME2);
                user2.setPassword(USER_PASSWORD2);
                user2.setCreated(USER_CREATED2);
                return user2;
            }else if(invocation.getArgument(0).equals(USER_ID3)){
                User user3 = new User();
                user3.setId(USER_ID3);
                user3.setUsername(USER_USERNAME3);
                user3.setPassword(USER_PASSWORD3);
                user3.setCreated(USER_CREATED3);
                return user3;
            }else if(invocation.getArgument(0).equals(USER_ID4)){
                User user4 = new User();
                user4.setId(USER_ID4);
                user4.setUsername(USER_USERNAME4);
                user4.setPassword(USER_PASSWORD4);
                user4.setCreated(USER_CREATED4);
                return user4;
            }else{
                return null;
            }

        });

        lenient().when(eventRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            User user4 = userService.getUser(USER_ID4);
            if(invocation.getArgument(0).equals(EVENT1_ID)){
                Event event1 = new Event();
                event1.setId(EVENT1_ID);
                event1.setName(EVENT1_NAME);
                event1.setDescription(EVENT1_DESCRIPTION);
                event1.setAddress(EVENT1_ADDRESS);
                event1.setEmail(EVENT1_EMAIL);
                event1.setPhoneNumber(EVENT1_PHONE_NUMBER);
                event1.setCapacity(EVENT1_CAPACITY);
                event1.setCost(EVENT1_COST);
                event1.setOrganizer(user4);
                return Optional.of(event1);
            }
            else if(invocation.getArgument(0).equals(EVENT2_ID)){
                Event event2 = new Event();
                event2.setId(EVENT2_ID);
                event2.setName(EVENT2_NAME);
                event2.setDescription(EVENT2_DESCRIPTION);
                event2.setAddress(EVENT2_ADDRESS);
                event2.setEmail(EVENT2_EMAIL);
                event2.setPhoneNumber(EVENT2_PHONE_NUMBER);
                event2.setCapacity(EVENT2_CAPACITY);
                event2.setCost(EVENT2_COST);
                event2.setOrganizer(user4);
                return Optional.of(event2);
            }
            else if(invocation.getArgument(0).equals(EVENT3_ID)){
                Event event3 = new Event();
                event3.setId(EVENT3_ID);
                event3.setName(EVENT3_NAME);
                event3.setDescription(EVENT3_DESCRIPTION);
                event3.setAddress(EVENT3_ADDRESS);
                event3.setEmail(EVENT3_EMAIL);
                event3.setPhoneNumber(EVENT3_PHONE_NUMBER);
                event3.setCapacity(EVENT3_CAPACITY);
                event3.setCost(EVENT3_COST);
                event3.setOrganizer(user4);
                return Optional.of(event3);
            }
            else{
                return null;
            }
        });

        lenient().when(ticketRepository.findTicketsByEvent(any(Event.class))).thenAnswer((InvocationOnMock invocation) -> {
            User user1 = userService.getUser(USER_ID);
            User user2 = userService.getUser(USER_ID2);
            Event event2 = eventService.getEvent(EVENT2_ID);

            List<Ticket> tickets = new ArrayList<>();
            Ticket ticket1 = new Ticket();
            ticket1.setUser(user1);
            ticket1.setEvent(event2);
            ticket1.setBookingDate(BOOKING_DATE);

            Ticket ticket2 = new Ticket();
            ticket2.setUser(user2);
            ticket2.setEvent(event2);
            ticket2.setBookingDate(BOOKING_DATE);

            tickets.add(ticket1);
            tickets.add(ticket2);
            return tickets;
        });
    }

    @Test
    public void testQueryUserListEvent1(){
        List<UUID> userIds = new ArrayList<>();
        try{
            userIds = eventService.queryUserListEvent(USER_ID4, EVENT2_ID);
        }catch (IllegalArgumentException e) {
            fail();
        }
        assertEquals(userIds.size(), 2);
    }

    @Test
    public void testQueryUserListEvent2(){
        String error = "";
        try{
            eventService.queryUserListEvent(USER_ID, EVENT2_ID);
        }catch (IllegalArgumentException e) {
            error=e.getMessage();
        }
        assertEquals(error, "You are not the organizer of this event.");
    }

}
