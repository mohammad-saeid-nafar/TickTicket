
package tickticket.acceptance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import tickticket.controller.Conversion;
import tickticket.dao.*;
import tickticket.dto.EventDTO;
import tickticket.dto.EventTypeDTO;
import tickticket.model.*;
import tickticket.service.*;

@ExtendWith(MockitoExtension.class)
public class ID006AddNewEventTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private EventTypeService eventTypeService;

    @InjectMocks
    private EventService eventService;

    // Data for organizer
    private User organizer;
    private static final UUID ORGANIZER_ID = UUID.randomUUID();
    private static final String ORGANIZER_USERNAME = "johnDoe";
    private static final String ORGANIZER_PASSWORD = "myP@assword1";
    private static final LocalDate ORGANIZER_CREATED = LocalDate.of(2022, 7, 12);

    // Data for user
    private User user;
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_USERNAME = "bruceJ2";
    private static final String USER_PASSWORD = "BrUcE_@214";
    private static final LocalDate USER_CREATED = LocalDate.of(2022, 10, 3);

    // Data for organizer profile
    private Profile organizerProfile;
    private static final UUID ORGANIZER_PROFILE_ID = UUID.randomUUID();
    private static final String ORGANIZER_FIRST_NAME = "John";
    private static final String ORGANIZER_LAST_NAME = "Doe";
    private static final String ORGANIZER_EMAIL = "johndoe@gmail.com";
    private static final String ORGANIZER_PHONE_NUMBER = "4385663241";
    private static final String ORGANIZER_ADDRESS = "120 Street 1";
    private static final String ORGANIZER_PROFILE_PICTURE = "img1.jpg";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1990, 2, 22);

    // Data for user profile
    private Profile userProfile;
    private static final UUID USER_PROFILE_ID = UUID.randomUUID();
    private static final String USER_FIRST_NAME = "Bruce";
    private static final String USER_LAST_NAME = "Jil";
    private static final String USER_EMAIL = "bruce_j@gmail.com";
    private static final String USER_PHONE_NUMBER = "4388665551";
    private static final String USER_ADDRESS = "11 Street 17";
    private static final String USER_PROFILE_PICTURE = "img2.jpg";
    private static final LocalDate USER_DATE_OF_BIRTH = LocalDate.of(2000, 4, 30);

    // Data for event type
    private EventType eventType;
    private static final UUID EVENT_TYPE_ID = UUID.randomUUID();
    private static final String EVENT_TYPE_NAME = "Pop Music";
    private static final String EVENT_TYPE_DESCRIPTION = "Music";

    // Data for Event
    private Event event;
    private static final UUID EVENT_ID = UUID.randomUUID();
    private static final String EVENT_NAME = "Justin Bieber Tour";
    private static final String EVENT_DESCRIPTION = "Music Tour";
    private static final int EVENT_CAPACITY = 600;
    private static final double EVENT_COST = 250.00;
    private static final String EVENT_ADDRESS = "True Square";
    private static final String EVENT_EMAIL = ORGANIZER_EMAIL;
    private static final String EVENT_PHONE_NUMBER = ORGANIZER_PHONE_NUMBER;

    // Data for Event Schedule
    private EventSchedule eventSchedule;
    private static final LocalDateTime EVENT_START = LocalDateTime.of(2022, 10, 15, 19,0);
    private static final LocalDateTime EVENT_END = LocalDateTime.of(2022, 10, 15, 22, 0);

    @BeforeEach
    public void setMockOutput() {

        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ORGANIZER_ID)) {
                organizerProfile = new Profile();
                organizerProfile.setId(ORGANIZER_PROFILE_ID);
                organizerProfile.setFirstName(ORGANIZER_FIRST_NAME);
                organizerProfile.setLastName(ORGANIZER_LAST_NAME);
                organizerProfile.setEmail(ORGANIZER_EMAIL);
                organizerProfile.setPhoneNumber(ORGANIZER_PHONE_NUMBER);
                organizerProfile.setAddress(ORGANIZER_ADDRESS);
                organizerProfile.setProfilePicture(ORGANIZER_PROFILE_PICTURE);
                organizerProfile.setDateOfBirth(DATE_OF_BIRTH);
                organizerProfile.setInterests(new ArrayList<>());

                organizer = new User();
                organizer.setId(ORGANIZER_ID);
                organizer.setUsername(ORGANIZER_USERNAME);
                organizer.setPassword(ORGANIZER_PASSWORD);
                organizer.setCreated(ORGANIZER_CREATED);
                organizer.setProfile(organizerProfile);

                return organizer;
            } else if (invocation.getArgument(0).equals(USER_ID)) {
                userProfile = new Profile();
                userProfile.setId(USER_PROFILE_ID);
                userProfile.setFirstName(USER_FIRST_NAME);
                userProfile.setLastName(USER_LAST_NAME);
                userProfile.setEmail(USER_EMAIL);
                userProfile.setPhoneNumber(USER_PHONE_NUMBER);
                userProfile.setAddress(USER_ADDRESS);
                userProfile.setProfilePicture(USER_PROFILE_PICTURE);
                userProfile.setDateOfBirth(USER_DATE_OF_BIRTH);
                userProfile.setInterests(new ArrayList<>());

                user = new User();
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
            organizer = userService.getUser(ORGANIZER_ID);

            eventSchedule = new EventSchedule();
            eventSchedule.setStartDateTime(EVENT_START);
            eventSchedule.setEndDateTime(EVENT_END);

            eventType = new EventType();
            eventType.setId(EVENT_TYPE_ID);
            eventType.setName(EVENT_TYPE_NAME);
            eventType.setDescription(EVENT_TYPE_DESCRIPTION);
            List<EventType> eventTypes = new ArrayList<>();
            eventTypes.add(eventType);

            event = new Event();
            event.setId(EVENT_ID);
            event.setName(EVENT_NAME);
            event.setDescription(EVENT_DESCRIPTION);
            event.setAddress(EVENT_ADDRESS);
            event.setEmail(EVENT_EMAIL);
            event.setPhoneNumber(EVENT_PHONE_NUMBER);
            event.setCapacity(EVENT_CAPACITY);
            event.setCost(EVENT_COST);
            event.setOrganizer(user);
            event.setEventSchedule(eventSchedule);
            event.setEventTypes(eventTypes);

            return Optional.of(event);
        });

        lenient().when(eventRepository.findEventsByEventTypesIn(any())).thenAnswer((InvocationOnMock invocation) -> {
            Event event = eventRepository.findById(EVENT_ID).orElse(null);
            List<Event> events = new ArrayList<>();
            events.add(event);
            return events;
        });

        lenient().when(eventRepository.findEventsByOrganizer(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ORGANIZER_ID)) {
                Event event = eventRepository.findById(EVENT_ID).orElse(null);
                List<Event> events = new ArrayList<>();
                events.add(event);
                return events;
            } else {
                return null;
            }
        });

        lenient().when(eventRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<Event> events1 = new ArrayList<>();
            Event event = eventRepository.findById(EVENT_ID).orElse(null);
            events1.add(event);
            return events1;
        });

        lenient().when(eventRepository.findEventsByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            Event event = eventRepository.findById(EVENT_ID).orElse(null);
            List<Event> events = new ArrayList<>();
            events.add(event);
            return events;
        });

        lenient().when(eventTypeRepository.findEventTypeByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_TYPE_NAME)) {

                EventType eventType1 = new EventType();
                eventType1.setName(EVENT_TYPE_NAME);
                eventType1.setDescription(EVENT_TYPE_DESCRIPTION);

                return Optional.of(eventType1);

            } else {
                return Optional.empty();
            }
        });

        lenient().when(eventTypeService.getAllEventTypes(any())).thenAnswer((InvocationOnMock invocation) -> {
            List<EventType> eventTypes = new ArrayList<>();
            EventType eventType1 = new EventType();
            eventType1.setId(EVENT_TYPE_ID);
            eventType1.setName(EVENT_TYPE_NAME);
            eventType1.setDescription(EVENT_TYPE_DESCRIPTION);
            eventTypes.add(eventType1);

            return eventTypes;
        });

        lenient().when(userRepository.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ORGANIZER_USERNAME)) {

                User organizer = new User();
                organizer.setId(ORGANIZER_ID);
                organizer.setUsername(ORGANIZER_USERNAME);
                organizer.setPassword(ORGANIZER_PASSWORD);
                organizer.setCreated(ORGANIZER_CREATED);

                return Optional.of(organizer);
            } else if (invocation.getArgument(0).equals(USER_USERNAME)) {

                User user = new User();
                user.setId(USER_ID);
                user.setUsername(USER_USERNAME);
                user.setPassword(USER_PASSWORD);
                user.setCreated(USER_CREATED);

                return Optional.of(organizer);
            } else {
                return Optional.empty();
            }
        });

        lenient().when(userRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER_ID)) {
                return userRepository.findUserByUsername(USER_USERNAME);
            } else if (invocation.getArgument(0).equals(ORGANIZER_ID)) {
                return userRepository.findUserByUsername(ORGANIZER_USERNAME);
            } else {
                return Optional.empty();
            }
        });

        lenient().when(eventTypeRepository.findEventTypeByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_TYPE_NAME)) {
                eventType = new EventType();
                eventType.setId(EVENT_TYPE_ID);
                eventType.setName(EVENT_TYPE_NAME);
                eventType.setDescription(EVENT_TYPE_DESCRIPTION);

                return Optional.of(eventType);
            } else {
                return Optional.empty();
            }
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(eventRepository.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(eventRepository.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
        lenient().doNothing().when(eventRepository).delete(any(Event.class));

    }

    @Test
    public void addNewEventSuccess() {
        organizer = userService.getUser(ORGANIZER_ID);

        EventDTO newEvent = new EventDTO();
        newEvent.setId(UUID.randomUUID());
        newEvent.setName("Taylor Swift Tour");
        newEvent.setDescription(EVENT_DESCRIPTION);
        newEvent.setAddress("Bell Center");
        newEvent.setEmail(EVENT_EMAIL);
        newEvent.setPhoneNumber(EVENT_PHONE_NUMBER);
        newEvent.setCapacity(560);
        newEvent.setCost(330.0);
        newEvent.setOrganizer(Conversion.convertToDTO(organizer));
        newEvent.setOrganizerId(ORGANIZER_ID);
        List<EventTypeDTO> eventTypes = new ArrayList<>();
        eventTypes.add(Conversion.convertToDTO(eventTypeRepository.findEventTypeByName(EVENT_TYPE_NAME).orElse(null)));
        newEvent.setEventTypes(eventTypes);
        newEvent.setStart(LocalDateTime.of(2022,12,1,20,0));
        newEvent.setEnd(LocalDateTime.of(2022,12,1,22,0));

        Event event = null;

        try {
            event = eventService.createEvent(newEvent);
        } catch (IllegalArgumentException e) {
            fail("Should not throw exception");
        }

        assertNotNull(event);
        assertEquals(event.getName(), "Taylor Swift Tour");
        assertEquals(event.getEventSchedule().getStartDateTime(), LocalDateTime.of(2022, 12, 1, 20, 0));
        assertEquals(event.getEventSchedule().getEndDateTime(), LocalDateTime.of(2022, 12, 1, 22, 0));
        assertEquals(event.getEventTypes().get(0).getName(), EVENT_TYPE_NAME);
        assertEquals(event.getCapacity(), 560);
        assertEquals(event.getCost(), 330);
        assertEquals(event.getAddress(), "Bell Center");
        assertEquals(event.getOrganizer().getUsername(), ORGANIZER_USERNAME);
    }

    @Test
    public void addNewEventSuccess2() {
        organizer = userService.getUser(ORGANIZER_ID);


        EventDTO newEvent = new EventDTO();
        newEvent.setId(UUID.randomUUID());
        newEvent.setName("Taylor Swift Tour");
        newEvent.setDescription(EVENT_DESCRIPTION);
        newEvent.setAddress("Bell Center");
        newEvent.setEmail(EVENT_EMAIL);
        newEvent.setPhoneNumber(EVENT_PHONE_NUMBER);
        newEvent.setCapacity(560);
        newEvent.setCost(330.0);
        newEvent.setOrganizer(Conversion.convertToDTO(organizer));
        newEvent.setOrganizerId(ORGANIZER_ID);
        List<EventTypeDTO> eventTypes = new ArrayList<>();
        eventTypes.add(Conversion.convertToDTO(eventTypeRepository.findEventTypeByName(EVENT_TYPE_NAME).orElse(null)));
        newEvent.setEventTypes(eventTypes);
        newEvent.setStart(EVENT_START);
        newEvent.setEnd(EVENT_END);
        Event event = null;

        try {
            event = eventService.createEvent(newEvent);
        } catch (IllegalArgumentException e) {
            fail("Should not throw exception");
        }

        assertNotNull(event);

        assertEquals(eventService.getAllEvents().size() + 1, 2);
        assertEquals(event.getName(), "Taylor Swift Tour");
        assertEquals(event.getEventSchedule().getStartDateTime(), EVENT_START);
        assertEquals(event.getEventSchedule().getEndDateTime(), EVENT_END);
        assertEquals(event.getEventTypes().get(0).getName(), EVENT_TYPE_NAME);
        assertEquals(event.getCapacity(), 560);
        assertEquals(event.getCost(), 330);
        assertEquals(event.getAddress(), "Bell Center");
        assertEquals(event.getOrganizer().getUsername(), ORGANIZER_USERNAME);
    }

    @Test
    public void addNewEventFail() {
        organizer = userService.getUser(ORGANIZER_ID);

        EventSchedule newEventSchedule = new EventSchedule();
        newEventSchedule.setStartDateTime(LocalDateTime.of(2022, 12, 1, 13, 0));
        newEventSchedule.setEndDateTime(LocalDateTime.of(2022, 12, 1, 15, 0));

        Event newEvent = new Event();
        newEvent.setId(UUID.randomUUID());
        newEvent.setName("US OPEN");
        newEvent.setDescription("Tennis Game");
        newEvent.setAddress("Court 5");
        newEvent.setEmail(EVENT_EMAIL);
        newEvent.setPhoneNumber(EVENT_PHONE_NUMBER);
        newEvent.setCapacity(300);
        newEvent.setCost(140);
        newEvent.setOrganizer(organizer);
        newEvent.setEventSchedule(newEventSchedule);

        try {

            eventService.addEventType("Sport", newEvent);

            eventService.createEvent(Conversion.convertToDTO(event));
        } catch (NullPointerException e) {
            assertEquals("Event Type doesn't exist", e.getMessage());
            assertEquals(eventService.getAllEvents().size(), 1);

        }


    }

    @Test
    public void addExistingEvent() {
        organizer = userService.getUser(ORGANIZER_ID);

        EventSchedule newEventSchedule = new EventSchedule();
        newEventSchedule.setStartDateTime(EVENT_START);
        newEventSchedule.setEndDateTime(EVENT_END);

        EventDTO newEvent = new EventDTO();
        newEvent.setId(UUID.randomUUID());
        newEvent.setName(EVENT_NAME);
        newEvent.setDescription(EVENT_DESCRIPTION);
        newEvent.setAddress(EVENT_ADDRESS);
        newEvent.setEmail(EVENT_EMAIL);
        newEvent.setPhoneNumber(EVENT_PHONE_NUMBER);
        newEvent.setCapacity(EVENT_CAPACITY);
        newEvent.setCost(EVENT_COST);
        newEvent.setOrganizer(Conversion.convertToDTO(organizer));
        List<EventTypeDTO> eventTypes = new ArrayList<>();
        eventTypes.add(Conversion.convertToDTO(eventTypeRepository.findEventTypeByName(EVENT_TYPE_NAME).orElse(null)));
        newEvent.setEventTypes(eventTypes);
        newEvent.setStart(EVENT_START);
        newEvent.setEnd(EVENT_END);


        try {
            eventService.createEvent(newEvent);
        } catch (IllegalArgumentException e) {
            assertEquals("The event already exists", e.getMessage());
            assertEquals(eventService.getAllEvents().size(), 1);

        }

    }


}
