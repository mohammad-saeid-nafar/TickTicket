package tickticket.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tickticket.model.Event;
import tickticket.model.EventSchedule;
import tickticket.model.EventType;
import tickticket.model.Profile;
import tickticket.model.Ticket;
import tickticket.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestTicketPersistence {

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
    public void testPersistAndLoadTicket() {
        EventType testEventType = new EventType();
        testEventType.setName("Test Type");
        testEventType.setDescription("Persistence Test!");
        testEventType.setAgeRequirement(13);
        List<EventType> eventTypeList = new ArrayList<>();
        eventTypeList.add(testEventType);

        Profile testProfile = new Profile();
        testProfile.setFirstName("TestName");
        testProfile.setLastName("TestLastName");
        testProfile.setAddress("Test Address");
        testProfile.setEmail("testemail@test.com");
        testProfile.setPhoneNumber("(123)456-7890");
        testProfile.setProfilePicture("img1.jpg");
        testProfile.setDateOfBirth(LocalDate.of(2000, 2, 22));
        testProfile.setInterests(eventTypeList);

        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setCreated(LocalDate.of(2022, 10, 16));
        testUser.setProfile(testProfile);

        EventSchedule testEventSchedule = new EventSchedule();
        testEventSchedule.setStartDateTime(LocalDateTime.of(2022, 12, 5, 17, 0));
        testEventSchedule.setEndDateTime(LocalDateTime.of(2022, 12, 5, 22, 0));

        Event testEvent = new Event();
        testEvent.setName("Test Event");
        testEvent.setDescription("Just a test");
        testEvent.setCapacity(200);
        testEvent.setCost(250);
        testEvent.setAddress("Test Address");
        testEvent.setEmail("Test email");
        testEvent.setPhoneNumber("(123)456-7890");
        testEvent.setEventTypes(eventTypeList);
        testEvent.setOrganizer(testUser);
        testEvent.setEventSchedule(testEventSchedule);

        Ticket testTicket = new Ticket();
        LocalDateTime bookingDate = LocalDateTime.of(2022, 10, 16, 15, 28);
        testTicket.setBookingDate(bookingDate);
        testTicket.setEvent(testEvent);
        testTicket.setUser(testUser);

        eventTypeRepository.save(testEventType);
        userRepository.save(testUser);
        eventRepository.save(testEvent);
        ticketRepository.save(testTicket);

        boolean exists = ticketRepository.existsByEventAndUser(testEvent, testUser);
        testTicket = ticketRepository.findTicketsByUser(testUser).get(0);

        Optional<Ticket> optionalTicket = ticketRepository.findTicketByEventAndUser(testEvent, testUser);
        assertTrue(optionalTicket.isPresent());

        assertNotNull(testTicket);
        assertTrue(exists);
        assertEquals(bookingDate, testTicket.getBookingDate());

        assertEquals(testUser.getUsername(), testTicket.getUser().getUsername());
        assertEquals(testUser.getPassword(), testTicket.getUser().getPassword());

        assertEquals(testUser.getProfile().getFirstName(), testTicket.getUser().getProfile().getFirstName());
        assertEquals(testUser.getProfile().getLastName(), testTicket.getUser().getProfile().getLastName());
        assertEquals(testUser.getProfile().getEmail(), testTicket.getUser().getProfile().getEmail());
        assertEquals(testUser.getProfile().getDateOfBirth(), testTicket.getUser().getProfile().getDateOfBirth());
        assertEquals(testUser.getProfile().getPhoneNumber(), testTicket.getUser().getProfile().getPhoneNumber());
        assertEquals(testUser.getProfile().getAddress(), testTicket.getUser().getProfile().getAddress());
        assertEquals(testUser.getProfile().getProfilePicture(), testTicket.getUser().getProfile().getProfilePicture());


        assertEquals(testEvent.getName(), testTicket.getEvent().getName());
        assertEquals(testEvent.getCapacity(), testTicket.getEvent().getCapacity());
        assertEquals(testEvent.getDescription(), testTicket.getEvent().getDescription());
        assertEquals(testEvent.getCost(), testTicket.getEvent().getCost());
        assertEquals(testEvent.getAddress(), testTicket.getEvent().getAddress());
        assertEquals(testEvent.getEmail(), testTicket.getEvent().getEmail());
        assertEquals(testEvent.getPhoneNumber(), testTicket.getEvent().getPhoneNumber());

        assertEquals(testEventSchedule.getStartDateTime(), testTicket.getEvent().getEventSchedule().getStartDateTime());
    }

    @Test
    public void testFindTicketsByEvent() {
        EventType testEventType = new EventType();
        testEventType.setName("Test Type");
        testEventType.setDescription("Persistence Test!");
        testEventType.setAgeRequirement(13);
        List<EventType> eventTypeList = List.of(testEventType);

        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setCreated(LocalDate.of(2022, 10, 16));

        User testUser2 = new User();
        testUser2.setUsername("testUser2");
        testUser2.setPassword("testPassword2");
        testUser2.setCreated(LocalDate.of(2022, 10, 16));

        EventSchedule testEventSchedule = new EventSchedule();
        testEventSchedule.setStartDateTime(LocalDateTime.of(2022, 12, 5, 17, 0));
        testEventSchedule.setEndDateTime(LocalDateTime.of(2022, 12, 5, 22, 0));

        Event testEvent = new Event();
        testEvent.setName("Test Event");
        testEvent.setDescription("Just a test");
        testEvent.setCapacity(200);
        testEvent.setCost(250);
        testEvent.setAddress("Test Address");
        testEvent.setEmail("Test email");
        testEvent.setPhoneNumber("(123)456-7890");
        testEvent.setEventTypes(eventTypeList);
        testEvent.setOrganizer(testUser);
        testEvent.setEventSchedule(testEventSchedule);

        Ticket testTicket1 = new Ticket();
        testTicket1.setBookingDate(LocalDateTime.of(2022, 1, 1, 12, 0));
        testTicket1.setEvent(testEvent);
        testTicket1.setUser(testUser);

        Ticket testTicket2 = new Ticket();
        testTicket2.setBookingDate(LocalDateTime.of(2022, 1, 2, 12, 0));
        testTicket2.setEvent(testEvent);
        testTicket2.setUser(testUser2);

        eventTypeRepository.save(testEventType);
        userRepository.save(testUser);
        userRepository.save(testUser2);
        eventRepository.save(testEvent);
        ticketRepository.save(testTicket1);
        ticketRepository.save(testTicket2);

        List<Ticket> tickets = ticketRepository.findTicketsByEvent(testEvent);

        assertNotNull(tickets);
        assertEquals(2, tickets.size());
        assertEquals(testTicket1.getBookingDate(), tickets.get(0).getBookingDate());
        assertEquals(testTicket2.getBookingDate(), tickets.get(1).getBookingDate());
    }
}
