package tickticket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import tickticket.model.*;
import tickticket.service.EventService;
import tickticket.service.EventTypeService;
import tickticket.service.ReviewService;
import tickticket.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ID029CreateReviewTests {

    @Mock
    private UserService userService;

    @Mock
    private EventTypeService eventTypeService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private ReviewService reviewService;

    private static final UUID USER1_ID = UUID.randomUUID();
    private static final String USER1_USERNAME = "aly1";
    private static final String USER1_PASSWORD = "Aly1235!";
    private static final LocalDate USER1_CREATED = LocalDate.of(2022, 10, 1);

    private static final UUID USER2_ID = UUID.randomUUID();
    private static final String USER2_USERNAME = "aly2";
    private static final String USER2_PASSWORD = "Aly1233!";
    private static final LocalDate USER2_CREATED = LocalDate.of(2022, 10, 3);

    private static final UUID USER3_ID = UUID.randomUUID();
    private static final String USER3_USERNAME = "aly3";
    private static final String USER3_PASSWORD = "Aly1233!";
    private static final LocalDate USER3_CREATED = LocalDate.of(2022, 10, 3);

    private static final UUID EVENT1_ID = UUID.randomUUID();
    private static final String EVENT1_NAME = "Wine and Cheese";
    private static final String EVENT1_DESCRIPTION = "Graduation Wine and Cheese";
    private static final int EVENT1_CAPACITY = 80;
    private static final double EVENT1_COST = 20;
    private static final String EVENT1_ADDRESS = "2620 rue Stanley";
    private static final String EVENT1_EMAIL = "aly1@gmail.com";
    private static final String EVENT1_PHONE_NUMBER = "514-888-8888";
    private static final LocalDateTime EVENT1_START = LocalDateTime.of(2022, 10, 5, 12, 0);
    private static final LocalDateTime EVENT1_END = LocalDateTime.of(2022, 10, 5, 21, 0);

    private static final UUID EVENT2_ID = UUID.randomUUID();
    private static final String EVENT2_NAME = "Pop";
    private static final String EVENT2_DESCRIPTION = "Pop concert";
    private static final int EVENT2_CAPACITY = 150;
    private static final double EVENT2_COST = 20;
    private static final String EVENT2_ADDRESS = "321 Ave";
    private static final String EVENT2_EMAIL = "pop@mail.ca";
    private static final String EVENT2_PHONE_NUMBER = "514-888-8888";
    private static final LocalDateTime EVENT2_START = LocalDateTime.of(2022, 12, 5, 12, 0);
    private static final LocalDateTime EVENT2_END = LocalDateTime.of(2022, 12, 5, 21, 0);

    private static final UUID EVENT_TYPE1_ID = UUID.randomUUID();
    private static final String EVENT_TYPE1_NAME = "Food";

    private static final UUID EVENT_TYPE2_ID = UUID.randomUUID();
    private static final String EVENT_TYPE2_NAME = "Music";

    private static final LocalDateTime TICKET_BOOKING_DATE_TIME = LocalDateTime.of(2022,10,10,15,0);

    @BeforeEach
    public void setMockOutput() {

        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(USER1_ID)){
                User user = new User();
                user.setId(USER1_ID);
                user.setUsername(USER1_USERNAME);
                user.setPassword(USER1_PASSWORD);
                user.setCreated(USER1_CREATED);
                return user;
            }else if(invocation.getArgument(0).equals(USER2_ID)){
                User user = new User();
                user.setId(USER2_ID);
                user.setUsername(USER2_USERNAME);
                user.setPassword(USER2_PASSWORD);
                user.setCreated(USER2_CREATED);
                return user;
            }else if (invocation.getArgument(0).equals(USER3_ID)){
                User user = new User();
                user.setId(USER3_ID);
                user.setUsername(USER3_USERNAME);
                user.setPassword(USER3_PASSWORD);
                user.setCreated(USER3_CREATED);
                return user;
            }else{
                return null;
            }

        });

        lenient().when(eventTypeService.getEventType(any(UUID.class))).thenAnswer((InvocationOnMock invocation) ->{
            if(invocation.getArgument((0)).equals(EVENT_TYPE1_ID)){
                EventType eventType = new EventType();
                eventType.setId(EVENT_TYPE1_ID);
                eventType.setName(EVENT_TYPE1_NAME);
                return eventType;
            }else if(invocation.getArgument((0)).equals(EVENT_TYPE2_ID)){
                EventType eventType = new EventType();
                eventType.setId(EVENT_TYPE2_ID);
                eventType.setName(EVENT_TYPE2_NAME);
                return eventType;
            }else{
                return null;
            }
        });

        lenient().when(eventService.getEvent(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(EVENT1_ID)) {
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

            }else if(invocation.getArgument(0).equals(EVENT2_ID)) {

            }else{
                return null;
            }

            });
    }
}
