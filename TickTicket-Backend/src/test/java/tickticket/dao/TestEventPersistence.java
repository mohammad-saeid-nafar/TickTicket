package tickticket.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tickticket.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestEventPersistence {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventScheduleRepository eventScheduleRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @AfterEach
    public void clearDatabase() {
        reviewRepository.deleteAll();
        ticketRepository.deleteAll();
        eventRepository.deleteAll();
        userRepository.deleteAll();
        profileRepository.deleteAll();
        eventTypeRepository.deleteAll();
        eventScheduleRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadEvent(){
        EventType testEventType = new EventType();
        testEventType.setName("Test Type");
        testEventType.setDescription("Persistence Test!");
        testEventType.setAgeRequirement(13);
        List<EventType> eventTypeList = new ArrayList<>();
        eventTypeList.add(testEventType);

        User organizer = new User();
        organizer.setUsername("testUser");
        organizer.setPassword("testPassword");
        organizer.setCreated(LocalDate.of(2022,10,16));

        LocalDateTime start = LocalDateTime.of(2022, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2022, 1, 1, 23, 59);
        EventSchedule testEventSchedule = new EventSchedule();
        testEventSchedule.setStartDateTime(start);
        testEventSchedule.setEndDateTime(end);

        Event testEvent = new Event();
        testEvent.setName("Test Event");
        testEvent.setDescription("Just a test");
        testEvent.setCapacity(200);
        testEvent.setCost(250);
        testEvent.setAddress("Test Address");
        testEvent.setEmail("Test email");
        testEvent.setPhoneNumber("(123)456-7890");
        testEvent.setEventTypes(eventTypeList);
        testEvent.setOrganizer(organizer);
        testEvent.setEventSchedule(testEventSchedule);

        eventTypeRepository.save(testEventType);
        userRepository.save(organizer);
        eventRepository.save(testEvent);

        testEvent = eventRepository.findEventsByOrganizer(organizer).get(0);

        assertEquals(organizer.getUsername(),testEvent.getOrganizer().getUsername());
        assertEquals(organizer.getPassword(),testEvent.getOrganizer().getPassword());

        assertEquals(testEvent.getName(),"Test Event");
        assertEquals(testEvent.getCapacity(),200);
        assertEquals(testEvent.getDescription(),"Just a test");
        assertEquals(testEvent.getCost(),250);
        assertEquals(testEvent.getAddress(),"Test Address");
        assertEquals(testEvent.getEmail(),"Test email");
        assertEquals(testEvent.getPhoneNumber(),"(123)456-7890");

        assertEquals(testEvent.getEventSchedule().getStartDateTime(), start);
        assertEquals(testEvent.getEventSchedule().getEndDateTime(), end);

        testEvent = eventRepository.findEventsByEventTypesIn(eventTypeList).get(0);

        assertEquals(organizer.getUsername(),testEvent.getOrganizer().getUsername());
        assertEquals(organizer.getPassword(),testEvent.getOrganizer().getPassword());

        assertEquals(testEvent.getName(),"Test Event");
        assertEquals(testEvent.getCapacity(),200);
        assertEquals(testEvent.getDescription(),"Just a test");
        assertEquals(testEvent.getCost(),250);
        assertEquals(testEvent.getAddress(),"Test Address");
        assertEquals(testEvent.getEmail(),"Test email");
        assertEquals(testEvent.getPhoneNumber(),"(123)456-7890");

        assertEquals(testEvent.getEventSchedule().getStartDateTime(), start);
        assertEquals(testEvent.getEventSchedule().getEndDateTime(), end);
    }
}
