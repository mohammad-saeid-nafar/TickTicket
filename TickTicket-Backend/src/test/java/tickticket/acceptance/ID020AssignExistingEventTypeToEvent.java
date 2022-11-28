package tickticket.acceptance;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.stubbing.Answer;
import tickticket.controller.Conversion;
import tickticket.dao.*;
import tickticket.dto.*;
import tickticket.model.*;
import tickticket.service.*;

@ExtendWith(MockitoExtension.class)
public class ID020AssignExistingEventTypeToEvent {

    @Mock
    private EventRepository eventRepository;

//    @Mock
//    private EventTypeRepository eventTypeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventTypeService eventTypeService;

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

    // Data for organizer profile
    private static final UUID ORGANIZER_PROFILE_ID = UUID.randomUUID();
    private static final String ORGANIZER_FIRST_NAME = "John";
    private static final String ORGANIZER_LAST_NAME = "Doe";
    private static final String ORGANIZER_EMAIL = "johndoe@gmail.com";
    private static final String ORGANIZER_PHONE_NUMBER = "4385663241";
    private static final String ORGANIZER_ADDRESS = "120 Street 1";
    private static final String ORGANIZER_PROFILE_PICTURE = "img1.jpg";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1990, 2, 22);

    // Data for user profile
    private static final UUID USER_PROFILE_ID = UUID.randomUUID();
    private static final String USER_FIRST_NAME = "Bruce";
    private static final String USER_LAST_NAME = "Jil";
    private static final String USER_EMAIL = "bruce_j@gmail.com";
    private static final String USER_PHONE_NUMBER = "4388665551";
    private static final String USER_ADDRESS = "11 Street 17";
    private static final String USER_PROFILE_PICTURE = "img2.jpg";
    private static final LocalDate USER_DATE_OF_BIRTH = LocalDate.of(2000, 4, 30);

    // Data for event type
    private static final UUID EVENT_TYPE1_ID = UUID.randomUUID();
    private static final String EVENT_TYPE1_NAME = "Pop Music";
    private static final String EVENT_TYPE1_DESCRIPTION = "Music";
    private static final int EVENT_TYPE1_AGE_REQUIREMENT = 0;

//    private static final UUID EVENT_TYPE2_ID = UUID.randomUUID();
//    private static final String EVENT_TYPE2_NAME = "Party";
//    private static final String EVENT_TYPE2_DESCRIPTION = "Big Crowd";
//    private static final int EVENT_TYPE2_AGE_REQUIREMENT = 18;

    // Data for Event
    private static final UUID EVENT_ID = UUID.randomUUID();
    private static final String EVENT_NAME = "Justin Bieber Tour";
    private static final String EVENT_DESCRIPTION = "Music Tour";
    private static final int EVENT_CAPACITY = 600;
    private static final double EVENT_COST = 250.00;
    private static final String EVENT_ADDRESS = "True Square";
    private static final String EVENT_EMAIL = ORGANIZER_EMAIL;
    private static final String EVENT_PHONE_NUMBER = ORGANIZER_PHONE_NUMBER;

    // Data for Event Schedule
    private static final LocalDateTime EVENT_START = LocalDateTime.of(2022, 10, 15, 19,0);
    private static final LocalDateTime EVENT_END = LocalDateTime.of(2022, 10, 15, 22, 0);

    @BeforeEach
    public void setMockOutput() {
        lenient().when(eventTypeService.getEventType(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_TYPE1_ID)) {

                EventType eventType1 = new EventType();
                eventType1.setId(EVENT_TYPE1_ID);
                eventType1.setName(EVENT_TYPE1_NAME);
                eventType1.setDescription(EVENT_TYPE1_DESCRIPTION);
                eventType1.setAgeRequirement(EVENT_TYPE1_AGE_REQUIREMENT);

                return eventType1;

            }
            else {
                return null;
            }
        });

        lenient().when(eventTypeService.getAllEventTypes(any())).thenAnswer((InvocationOnMock invocation) -> Collections.singletonList(eventTypeService.getEventType(EVENT_TYPE1_ID)));
        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ORGANIZER_ID)) {
                Profile organizerProfile = new Profile();
                organizerProfile.setId(ORGANIZER_PROFILE_ID);
                organizerProfile.setFirstName(ORGANIZER_FIRST_NAME);
                organizerProfile.setLastName(ORGANIZER_LAST_NAME);
                organizerProfile.setEmail(ORGANIZER_EMAIL);
                organizerProfile.setPhoneNumber(ORGANIZER_PHONE_NUMBER);
                organizerProfile.setAddress(ORGANIZER_ADDRESS);
                organizerProfile.setProfilePicture(ORGANIZER_PROFILE_PICTURE);
                organizerProfile.setDateOfBirth(DATE_OF_BIRTH);
                organizerProfile.setInterests(new ArrayList<>());

                User organizer = new User();
                organizer.setId(ORGANIZER_ID);
                organizer.setUsername(ORGANIZER_USERNAME);
                organizer.setPassword(ORGANIZER_PASSWORD);
                organizer.setCreated(ORGANIZER_CREATED);
                organizer.setProfile(organizerProfile);

                return organizer;
            } else if (invocation.getArgument(0).equals(USER_ID)) {
                Profile userProfile = new Profile();
                userProfile.setId(USER_PROFILE_ID);
                userProfile.setFirstName(USER_FIRST_NAME);
                userProfile.setLastName(USER_LAST_NAME);
                userProfile.setEmail(USER_EMAIL);
                userProfile.setPhoneNumber(USER_PHONE_NUMBER);
                userProfile.setAddress(USER_ADDRESS);
                userProfile.setProfilePicture(USER_PROFILE_PICTURE);
                userProfile.setDateOfBirth(USER_DATE_OF_BIRTH);
                userProfile.setInterests(new ArrayList<>());

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

        lenient().when(eventRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_ID)) {
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
                event.setOrganizer(userService.getUser(USER_ID));
                event.setEventSchedule(eventSchedule);
                event.setEventTypes(Collections.singletonList(eventTypeService.getEventType(EVENT_TYPE1_ID)));
//                return event;
                return Optional.of(event);
            }
            else {
                return Optional.empty();
            }
        });

        lenient().when(eventRepository.findEventsByEventTypesIn(any())).thenAnswer((InvocationOnMock invocation) -> {

            if (((List<EventType>)invocation.getArgument(0)).get(0).getId().equals(EVENT_TYPE1_ID)) {

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
                event.setEventTypes(Collections.singletonList(eventTypeService.getEventType(EVENT_TYPE1_ID)));

                return Collections.singletonList(event);
            }else{
                return new ArrayList<>();
            }
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(eventRepository.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);    }


    @Test
    public void testAssignEventTypeSuccess(){
        EventDTO eventDTO = Conversion.convertToDTO(eventRepository.findById(EVENT_ID).get());
        List<UUID> evTypes = new ArrayList<>();
        evTypes.add(EVENT_TYPE1_ID);
        eventDTO.setEventTypeIds(evTypes);
        try{
            Event event = eventService.updateEvent(eventDTO);
            assertEquals(EVENT_TYPE1_ID, event.getEventTypes().get(0).getId());

        }catch (Exception e){
            fail();
        }
    }
}
