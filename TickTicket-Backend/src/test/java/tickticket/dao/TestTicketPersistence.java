package tickticket.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tickticket.model.Event;
import tickticket.model.EventSchedule;
import tickticket.model.EventType;
import tickticket.model.Profile;
import tickticket.model.Ticket;
import tickticket.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTicketPersistence {

    EntityManager entityManager;

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

    @BeforeEach
	public void clearDatabase() {
        ticketRepository.deleteAll();
        eventRepository.deleteAll();
		userRepository.deleteAll();
		profileRepository.deleteAll();
        eventTypeRepository.deleteAll();
        eventScheduleRepository.deleteAll();
	}

    @Test
    public void testPersistAndLoadTicket(){
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
        testProfile.setDateOfBirth(LocalDate.of(2000,2,22));
        testProfile.setInterests(eventTypeList);

        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setCreated(LocalDate.of(2022,10,16));
        testUser.setProfile(testProfile);

        EventSchedule testEventSchedule = new EventSchedule();
        testEventSchedule.setStartDateTime(LocalDateTime.of(2022,12,5,17,00));
        testEventSchedule.setEndDateTime(LocalDateTime.of(2022,12,5,22,00));
        testEventSchedule.setRecurrent(false);

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
        LocalDateTime bookingDate = LocalDateTime.of(2022,10,16,15,28);
        testTicket.setBookingDate(bookingDate);
        testTicket.setEvent(testEvent);
        testTicket.setUser(testUser);

        eventTypeRepository.save(testEventType);
        profileRepository.save(testProfile);
        userRepository.save(testUser);
        eventScheduleRepository.save(testEventSchedule);
        eventRepository.save(testEvent);
        ticketRepository.save(testTicket);

        testTicket = null;

        boolean exists = ticketRepository.existsByEventAndUser(testEvent,testUser);
        testTicket = ticketRepository.findTicketsByUser(testUser).get(0);
        
        assertNotNull(testTicket);
        assertEquals(exists, true);
        assertEquals(bookingDate, testTicket.getBookingDate());

        assertEquals(testUser.getUsername(),testTicket.getUser().getUsername());
        assertEquals(testUser.getPassword(),testTicket.getUser().getPassword());

        assertEquals(testUser.getProfile().getFirstName(),testTicket.getUser().getProfile().getFirstName());
        assertEquals(testUser.getProfile().getLastName(),testTicket.getUser().getProfile().getLastName());
        assertEquals(testUser.getProfile().getEmail(),testTicket.getUser().getProfile().getEmail());
        assertEquals(testUser.getProfile().getDateOfBirth(),testTicket.getUser().getProfile().getDateOfBirth());
        assertEquals(testUser.getProfile().getPhoneNumber(),testTicket.getUser().getProfile().getPhoneNumber());
        assertEquals(testUser.getProfile().getAddress(),testTicket.getUser().getProfile().getAddress());
        assertEquals(testUser.getProfile().getProfilePicture(),testTicket.getUser().getProfile().getProfilePicture());
    

        assertEquals(testEvent.getName(),testTicket.getEvent().getName());
        assertEquals(testEvent.getCapacity(),testTicket.getEvent().getCapacity());
        assertEquals(testEvent.getDescription(),testTicket.getEvent().getDescription());
        assertEquals(testEvent.getCost(),testTicket.getEvent().getCost());
        assertEquals(testEvent.getAddress(),testTicket.getEvent().getAddress());
        assertEquals(testEvent.getEmail(),testTicket.getEvent().getEmail());
        assertEquals(testEvent.getPhoneNumber(),testTicket.getEvent().getPhoneNumber());

        assertEquals(testEventSchedule.getStartDateTime(),testTicket.getEvent().getEventSchedule().getStartDateTime());
        


    
    
    }
}
