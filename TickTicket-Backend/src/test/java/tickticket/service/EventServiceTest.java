package tickticket.service;

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
import tickticket.model.Event;
import tickticket.model.EventSchedule;
import tickticket.model.EventType;
import tickticket.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventScheduleRepository eventScheduleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

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

    private static final String EVENT_TYPE1_NAME = "Party";
    private static final String EVENT_TYPE1_DESCRIPTION = "Fun & Dancing";
    private static final int EVENT_TYPE1_AGE = 18;

    private static final String EVENT_TYPE2_NAME = "Concert";
    private static final String EVENT_TYPE2_DESCRIPTION = "Nice music";
    private static final int EVENT_TYPE2_AGE = 13;


    @BeforeEach
    public void setMockOutput() {

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
    public void testCreateEvent(){
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        Event event = null;

        try{
            event = eventService.createEvent(EVENT_NAME, EVENT_DESCRIPTION, EVENT_CAPACITY, EVENT_COST, EVENT_ADDRESS, EVENT_EMAIL, EVENT_PHONE_NUMBER, organizer, eventTypes, EVENT_START, EVENT_END);
        }catch(Exception e){
            fail();
        }

        assertNotNull(event);
        assertEquals(event.getName(), EVENT_NAME);
        assertEquals(event.getDescription(), EVENT_DESCRIPTION);
        assertEquals(event.getCapacity(), EVENT_CAPACITY);
        assertEquals(event.getCost(), EVENT_COST);
        assertEquals(event.getAddress(), EVENT_ADDRESS);
        assertEquals(event.getEmail(), EVENT_EMAIL);
        assertEquals(event.getPhoneNumber(), EVENT_PHONE_NUMBER);

        assertEquals(event.getOrganizer().getId(), ORGANIZER_ID);
        assertEquals(event.getOrganizer().getUsername(), ORGANIZER_USERNAME);
        assertEquals(event.getOrganizer().getPassword(), ORGANIZER_PASSWORD);
        assertEquals(event.getOrganizer().getCreated(), ORGANIZER_CREATED);

        assertEquals(event.getEventTypes().size(), 2);

        assertEquals(event.getEventTypes().get(0).getName(), EVENT_TYPE1_NAME);
        assertEquals(event.getEventTypes().get(0).getDescription(), EVENT_TYPE1_DESCRIPTION);
        assertEquals(event.getEventTypes().get(0).getAgeRequirement(), EVENT_TYPE1_AGE);

        assertEquals(event.getEventTypes().get(1).getName(), EVENT_TYPE2_NAME);
        assertEquals(event.getEventTypes().get(1).getDescription(), EVENT_TYPE2_DESCRIPTION);
        assertEquals(event.getEventTypes().get(1).getAgeRequirement(), EVENT_TYPE2_AGE);

    }


    @Test
    public void testCreateEventNullName() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        try {
            eventService.createEvent(null, EVENT_DESCRIPTION, EVENT_CAPACITY, EVENT_COST, EVENT_ADDRESS, EVENT_EMAIL, EVENT_PHONE_NUMBER, organizer, eventTypes, EVENT_START, EVENT_END);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Name cannot be blank");
        }

    }

    @Test
    public void testCreateEventBlankName() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        try {
            eventService.createEvent("", EVENT_DESCRIPTION, EVENT_CAPACITY, EVENT_COST, EVENT_ADDRESS, EVENT_EMAIL, EVENT_PHONE_NUMBER, organizer, eventTypes, EVENT_START, EVENT_END);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Name cannot be blank");
        }

    }

    @Test
    public void testCreateEventNullAddress() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        try {
            eventService.createEvent(EVENT_NAME, EVENT_DESCRIPTION, EVENT_CAPACITY, EVENT_COST, null, EVENT_EMAIL, EVENT_PHONE_NUMBER, organizer, eventTypes, EVENT_START, EVENT_END);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Address cannot be blank");
        }

    }

    @Test
    public void testCreateEventBlankAddress() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        try {
            eventService.createEvent(EVENT_NAME, EVENT_DESCRIPTION, EVENT_CAPACITY, EVENT_COST, "", EVENT_EMAIL, EVENT_PHONE_NUMBER, organizer, eventTypes, EVENT_START, EVENT_END);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Address cannot be blank");
        }

    }

    @Test
    public void testCreateEventNullEmail() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);


        try {
            eventService.createEvent(EVENT_NAME, EVENT_DESCRIPTION, EVENT_CAPACITY, EVENT_COST, EVENT_ADDRESS, null, EVENT_PHONE_NUMBER, organizer, eventTypes, EVENT_START, EVENT_END);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Email cannot be blank");
        }

    }

    @Test
    public void testCreateEventBlankEmail() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        try {
            eventService.createEvent(EVENT_NAME, EVENT_DESCRIPTION, EVENT_CAPACITY, EVENT_COST, EVENT_ADDRESS, "", EVENT_PHONE_NUMBER, organizer, eventTypes, EVENT_START, EVENT_END);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Email cannot be blank");
        }

    }

    @Test
    public void testCreateEventNullPhoneNumber() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        try {
            eventService.createEvent(EVENT_NAME, EVENT_DESCRIPTION, EVENT_CAPACITY, EVENT_COST, EVENT_ADDRESS, EVENT_EMAIL, null, organizer, eventTypes, EVENT_START, EVENT_END);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Phone number cannot be blank");
        }

    }

    @Test
    public void testCreateEventBlankPhoneNumber() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        try {
            eventService.createEvent(EVENT_NAME, EVENT_DESCRIPTION, EVENT_CAPACITY, EVENT_COST, EVENT_ADDRESS, EVENT_EMAIL, "", organizer, eventTypes, EVENT_START, EVENT_END);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Phone number cannot be blank");
        }

    }

    @Test
    public void testCreateEventZeroCapacity() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        try {
            eventService.createEvent(EVENT_NAME, EVENT_DESCRIPTION, 0, EVENT_COST, EVENT_ADDRESS, EVENT_EMAIL, EVENT_PHONE_NUMBER, organizer, eventTypes, EVENT_START, EVENT_END);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Capacity cannot be blank or 0");
        }

    }

    @Test
    public void testCreateEventNullCost() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        try {
            eventService.createEvent(EVENT_NAME, EVENT_DESCRIPTION, EVENT_CAPACITY, null, EVENT_ADDRESS, EVENT_EMAIL, EVENT_PHONE_NUMBER, organizer, eventTypes, EVENT_START, EVENT_END);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Cost cannot be blank");
        }

    }

    @Test
    public void testCreateEventNullStart() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        try {
            eventService.createEvent(EVENT_NAME, EVENT_DESCRIPTION, EVENT_CAPACITY, EVENT_COST, EVENT_ADDRESS, EVENT_EMAIL, EVENT_PHONE_NUMBER, organizer, eventTypes, null, EVENT_END);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Start of event schedule cannot be blank");
        }

    }

    @Test
    public void testCreateEventNullEnd() {
        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);
        EventType eventType2 = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);
        eventTypes.add(eventType2);

        try {
            eventService.createEvent(EVENT_NAME, EVENT_DESCRIPTION, EVENT_CAPACITY, EVENT_COST, EVENT_ADDRESS, EVENT_EMAIL, EVENT_PHONE_NUMBER, organizer, eventTypes, EVENT_START, null);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "End of event schedule cannot be blank");
        }

    }

    @Test
    public void testUpdateEvent() {
        User newOrganizer = new User();
        newOrganizer.setId(UUID.randomUUID());
        newOrganizer.setUsername("New organizer");
        newOrganizer.setPassword("NewPass123");
        newOrganizer.setCreated(LocalDate.of(2022, 10, 2));

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);

        List<EventType> newEventTypes = new ArrayList<>();
        newEventTypes.add(eventType1);

        Event event = null;
        String newName = "New name";
        String newDescription = "New description";
        String newAddress = "123 New Ave";
        String newEmail = "newemail@mail.ca";
        String newPhoneNumber = "987654321";
        int newCapacity = 200;
        double newCost = 200.0;

        try {
            event = eventService.updateEvent(EVENT_ID, newName, newDescription, newCapacity, newCost, newAddress, newEmail, newPhoneNumber, newOrganizer, newEventTypes);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(event);
        assertEquals(event.getId(), EVENT_ID);
        assertEquals(event.getName(), newName);
        assertEquals(event.getDescription(), newDescription);
        assertEquals(event.getCapacity(), newCapacity);
        assertEquals(event.getCost(), newCost);
        assertEquals(event.getAddress(), newAddress);
        assertEquals(event.getEmail(), newEmail);
        assertEquals(event.getPhoneNumber(), newPhoneNumber);

        assertEquals(event.getOrganizer().getId(), newOrganizer.getId());
        assertEquals(event.getOrganizer().getUsername(), newOrganizer.getUsername());
        assertEquals(event.getOrganizer().getPassword(), newOrganizer.getPassword());
        assertEquals(event.getOrganizer().getCreated(), newOrganizer.getCreated());

        assertEquals(event.getEventTypes().size(), 1);

        assertEquals(event.getEventTypes().get(0).getName(), EVENT_TYPE1_NAME);
        assertEquals(event.getEventTypes().get(0).getDescription(), EVENT_TYPE1_DESCRIPTION);
        assertEquals(event.getEventTypes().get(0).getAgeRequirement(), EVENT_TYPE1_AGE);
    }

    @Test
    public void testUpdateEventNotFound() {
        User newOrganizer = new User();
        newOrganizer.setId(UUID.randomUUID());
        newOrganizer.setUsername("New organizer");
        newOrganizer.setPassword("NewPass123");
        newOrganizer.setCreated(LocalDate.of(2022, 10, 2));

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);

        List<EventType> newEventTypes = new ArrayList<>();
        newEventTypes.add(eventType1);

        String newName = "New name";
        String newDescription = "New description";
        String newAddress = "123 New Ave";
        String newEmail = "newemail@mail.ca";
        String newPhoneNumber = "987654321";
        int newCapacity = 200;
        double newCost = 200.0;
        UUID id = UUID.randomUUID();

        try {
            eventService.updateEvent(id, newName, newDescription, newCapacity, newCost, newAddress, newEmail, newPhoneNumber, newOrganizer, newEventTypes);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Event " + id + " not found");
        }

    }

    @Test
    public void testDeleteEvent(){
        boolean success = false;
        try{
            success = eventService.deleteEvent(EVENT_ID);
        }catch (Exception e){
            fail();
        }
        assertTrue(success);
    }

    @Test
    public void testDeleteEventNotFound(){
        UUID id = UUID.randomUUID();
        try{
            eventService.deleteEvent(id);
        }catch (Exception e){
            assertEquals(e.getMessage(), "Event " + id + " not found");
        }
    }

    @Test
    public void testGetEventsByName() {

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
    public void testGetAllEventsFromType() {

        EventType eventType1 = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null);

        List<EventType> eventTypes = new ArrayList<>();
        eventTypes.add(eventType1);

        List<Event> events = new ArrayList<>();
        try {
            events = eventService.getAllEventsFromType(eventTypes);
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
    public void testGetAllEventsFromOrganizer() {

        User organizer = userRepository.findUserByUsername(ORGANIZER_USERNAME).orElse(null);
        List<Event> events = new ArrayList<>();
        try {
            events = eventService.getAllEventsFromOrganizer(organizer);
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
