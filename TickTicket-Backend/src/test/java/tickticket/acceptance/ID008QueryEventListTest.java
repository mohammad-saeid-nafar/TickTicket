package tickticket.acceptance;

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
import tickticket.dao.EventRepository;
import tickticket.dao.EventScheduleRepository;
import tickticket.dao.EventTypeRepository;
import tickticket.dao.UserRepository;
import tickticket.dto.EventDTO;
import tickticket.model.Event;
import tickticket.model.EventSchedule;
import tickticket.model.EventType;
import tickticket.model.User;
import tickticket.service.EventService;
import tickticket.service.EventTypeService;
import tickticket.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ID008QueryEventListTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventScheduleRepository eventScheduleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private EventTypeRepository eventTypeRepository;

    @Mock
    private EventTypeService eventTypeService;

    @InjectMocks
    private EventService eventService;

    private static final UUID EVENT_ID = UUID.randomUUID();
    private static final String EVENT_NAME = "event name";
    private static final String EVENT_DESCRIPTION = "event description";
    private static final int EVENT_CAPACITY = 100;
    private static final double EVENT_COST = 150.0;
    private static final String EVENT_ADDRESS = "123 test ave";
    private static final String EVENT_EMAIL = "testevent@mail.ca";
    private static final String EVENT_PHONE_NUMBER = "12345678";

    private static final LocalDateTime EVENT_START = LocalDateTime.of(2022, 10, 2, 12, 0);
    private static final LocalDateTime EVENT_END = LocalDateTime.of(2022, 10, 2, 23, 59);

    private static final UUID ORGANIZER_ID = UUID.randomUUID();
    private static final String ORGANIZER_USERNAME = "Organizer";
    private static final String ORGANIZER_PASSWORD = "Password123";
    private static final LocalDate ORGANIZER_CREATED = LocalDate.of(2022, 10, 1);

    private static final UUID EVENT_TYPE_ID = UUID.randomUUID();
    private static final String EVENT_TYPE1_NAME = "Party";
    private static final String EVENT_TYPE1_DESCRIPTION = "Fun & Dancing";
    private static final int EVENT_TYPE1_AGE = 18;

    private static final UUID EVENT_TYPE2_ID = UUID.randomUUID();
    private static final String EVENT_TYPE2_NAME = "Concert";
    private static final String EVENT_TYPE2_DESCRIPTION = "Nice music";
    private static final int EVENT_TYPE2_AGE = 13;

    private EventDTO eventDTO;


    @BeforeEach
    public void setMockOutput() {
        eventDTO = new EventDTO();
        eventDTO.setId(EVENT_ID);
        eventDTO.setName(EVENT_NAME);
        eventDTO.setDescription(EVENT_DESCRIPTION);
        eventDTO.setCapacity(EVENT_CAPACITY);
        eventDTO.setCost(EVENT_COST);
        eventDTO.setAddress(EVENT_ADDRESS);
        eventDTO.setEmail(EVENT_EMAIL);
        eventDTO.setPhoneNumber(EVENT_PHONE_NUMBER);
        eventDTO.setOrganizerId(ORGANIZER_ID);
        eventDTO.setEventTypeIds(List.of(EVENT_TYPE_ID, EVENT_TYPE2_ID));
        eventDTO.setStart(EVENT_START);
        eventDTO.setEnd(EVENT_END);

        lenient().when(eventTypeRepository.findEventTypeByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_TYPE1_NAME)) {

                EventType eventType1 = new EventType();
                eventType1.setName(EVENT_TYPE1_NAME);
                eventType1.setDescription(EVENT_TYPE1_DESCRIPTION);
                eventType1.setAgeRequirement(EVENT_TYPE1_AGE);

                return Optional.of(eventType1);

            }else if(invocation.getArgument(0).equals(EVENT_TYPE2_NAME)) {

                EventType eventType2 = new EventType();
                eventType2.setName(EVENT_TYPE2_NAME);
                eventType2.setDescription(EVENT_TYPE2_DESCRIPTION);
                eventType2.setAgeRequirement(EVENT_TYPE2_AGE);

                return Optional.of(eventType2);

            }
            else {
                return Optional.empty();
            }
        });

        lenient().when(eventTypeService.getAllEventTypes(any(List.class))).thenAnswer((InvocationOnMock invocation) -> {
            List<EventType> eventTypes = new ArrayList<>();
            EventType eventType1 = new EventType();
            eventType1.setId(EVENT_TYPE_ID);
            eventType1.setName(EVENT_TYPE1_NAME);
            eventType1.setDescription(EVENT_TYPE1_DESCRIPTION);
            eventType1.setAgeRequirement(EVENT_TYPE1_AGE);
            eventTypes.add(eventType1);

            List<UUID> ls = invocation.getArgument(0);
            if (ls.size() < 2) {
                return eventTypes;
            }

            EventType eventType2 = new EventType();
            eventType2.setId(EVENT_TYPE2_ID);
            eventType2.setName(EVENT_TYPE2_NAME);
            eventType2.setDescription(EVENT_TYPE2_DESCRIPTION);
            eventType2.setAgeRequirement(EVENT_TYPE2_AGE);

            eventTypes.add(eventType2);

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
            }
            else {
                return Optional.empty();
            }
        });

        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ORGANIZER_ID)) {

                User organizer = new User();
                organizer.setId(ORGANIZER_ID);
                organizer.setUsername(ORGANIZER_USERNAME);
                organizer.setPassword(ORGANIZER_PASSWORD);
                organizer.setCreated(ORGANIZER_CREATED);

                return organizer;
            }
            else {
                User organizer = new User();
                organizer.setId(invocation.getArgument(0));
                organizer.setUsername("New organizer");
                organizer.setPassword("NewPass123");
                organizer.setCreated(LocalDate.now());
                return organizer;
            }
        });

        lenient().when(eventRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_ID)) {

                User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

                EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
                EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

                List<EventType> eventTypes = new ArrayList<>();
                eventTypes.add(eventType1);
                eventTypes.add(eventType2);

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
                event.setEventTypes(eventTypes);
                event.setEventSchedule(eventSchedule);

                return Optional.of(event);

            } else {
                return Optional.empty();
            }
        });

        lenient().when(eventRepository.findEventsByEventTypesIn(any())).thenAnswer((InvocationOnMock invocation) -> {
            Event event = eventRepository.findById(EVENT_ID).orElse(null);
            List<Event> events = new ArrayList<>();
            events.add(event);
            return events;
        });

        lenient().when(eventRepository.findEventsByOrganizer(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
            Event event = eventRepository.findById(EVENT_ID).orElse(null);
            List<Event> events = new ArrayList<>();
            events.add(event);
            return events;
        });

        lenient().when(eventRepository.findEventsByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            Event event = eventRepository.findById(EVENT_ID).orElse(null);
            List<Event> events = new ArrayList<>();
            events.add(event);
            return events;
        });

        lenient().when(eventRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            Event event = eventRepository.findById(EVENT_ID).orElse(null);
            List<Event> events = new ArrayList<>();
            events.add(event);
            return events;
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(eventRepository.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(eventScheduleRepository.save(any(EventSchedule.class))).thenAnswer(returnParameterAsAnswer);
        lenient().doNothing().when(eventRepository).delete(any(Event.class));
    }

    @Test
    public void testGetEventByName() {

        List<Event> events = new ArrayList<>();
        try {
            events = eventService.getEventsByName(EVENT_NAME);
        } catch (Exception e) {
            fail();
        }
        assertEquals(events.size(), 1);
        assertEquals(events.get(0).getId(), EVENT_ID);
        assertEquals(events.get(0).getName(), EVENT_NAME);
        assertEquals(events.get(0).getDescription(), EVENT_DESCRIPTION);
        assertEquals(events.get(0).getCapacity(), EVENT_CAPACITY);
        assertEquals(events.get(0).getCost(), EVENT_COST);
        assertEquals(events.get(0).getAddress(), EVENT_ADDRESS);
        assertEquals(events.get(0).getEmail(), EVENT_EMAIL);
        assertEquals(events.get(0).getPhoneNumber(), EVENT_PHONE_NUMBER);

        assertEquals(events.get(0).getOrganizer().getId(), ORGANIZER_ID);
        assertEquals(events.get(0).getOrganizer().getUsername(), ORGANIZER_USERNAME);
        assertEquals(events.get(0).getOrganizer().getPassword(), ORGANIZER_PASSWORD);
        assertEquals(events.get(0).getOrganizer().getCreated(), ORGANIZER_CREATED);

        assertEquals(events.get(0).getEventTypes().size(), 2);

        assertEquals(events.get(0).getEventTypes().get(0).getName(), EVENT_TYPE1_NAME);
        assertEquals(events.get(0).getEventTypes().get(0).getDescription(), EVENT_TYPE1_DESCRIPTION);
        assertEquals(events.get(0).getEventTypes().get(0).getAgeRequirement(), EVENT_TYPE1_AGE);

    }

    @Test
    public void testGetAllEvents() {

        List<Event> events = new ArrayList<>();
        try {
            events = eventService.getAllEvents();
        } catch (Exception e) {
            fail();
        }

        assertEquals(events.size(), 1);
        assertEquals(events.get(0).getId(), EVENT_ID);
        assertEquals(events.get(0).getName(), EVENT_NAME);
        assertEquals(events.get(0).getDescription(), EVENT_DESCRIPTION);
        assertEquals(events.get(0).getCapacity(), EVENT_CAPACITY);
        assertEquals(events.get(0).getCost(), EVENT_COST);
        assertEquals(events.get(0).getAddress(), EVENT_ADDRESS);
        assertEquals(events.get(0).getEmail(), EVENT_EMAIL);
        assertEquals(events.get(0).getPhoneNumber(), EVENT_PHONE_NUMBER);

        assertEquals(events.get(0).getOrganizer().getId(), ORGANIZER_ID);
        assertEquals(events.get(0).getOrganizer().getUsername(), ORGANIZER_USERNAME);
        assertEquals(events.get(0).getOrganizer().getPassword(), ORGANIZER_PASSWORD);
        assertEquals(events.get(0).getOrganizer().getCreated(), ORGANIZER_CREATED);

        assertEquals(events.get(0).getEventTypes().size(), 2);

        assertEquals(events.get(0).getEventTypes().get(0).getName(), EVENT_TYPE1_NAME);
        assertEquals(events.get(0).getEventTypes().get(0).getDescription(), EVENT_TYPE1_DESCRIPTION);
        assertEquals(events.get(0).getEventTypes().get(0).getAgeRequirement(), EVENT_TYPE1_AGE);

    }
}
