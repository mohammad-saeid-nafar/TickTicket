package tickticket.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tickticket.model.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestReviewPersistence {

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
    public void testPersistAndLoadReview(){

        // EventType
        EventType testEventType = new EventType();
        testEventType.setName("Test Type");
        testEventType.setDescription("Persistence Test!");
        testEventType.setAgeRequirement(13);
        List<EventType> eventTypeList = new ArrayList<>();
        eventTypeList.add(testEventType);

        // Profile
        Profile testProfile = new Profile();
        testProfile.setFirstName("TestName");
        testProfile.setLastName("TestLastName");
        testProfile.setAddress("Test Address");
        testProfile.setEmail("testemail@test.com");
        testProfile.setPhoneNumber("(123)456-7890");
        testProfile.setProfilePicture("img1.jpg");
        testProfile.setDateOfBirth(LocalDate.of(2000,2,22));
        testProfile.setInterests(eventTypeList);

        // User
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setCreated(LocalDate.of(2022,10,16));
        testUser.setProfile(testProfile);

        // EventSchedule
        EventSchedule testEventSchedule = new EventSchedule();
        testEventSchedule.setStartDateTime(LocalDateTime.of(2022,12,5,17,0));
        testEventSchedule.setEndDateTime(LocalDateTime.of(2022,12,5,22,0));

        // Event
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

        // Review
        Review testReview = new Review();
        String title = "TestTitle";
        String description = "Spectacular!";
        int rating = 5;
        testReview.setTitle(title);
        testReview.setDescription(description);
        testReview.setRating(rating);
        testReview.setUser(testUser);
        testReview.setEvent(testEvent);

        // SAVE
        eventTypeRepository.save(testEventType);
        userRepository.save(testUser);
        eventRepository.save(testEvent);
        reviewRepository.save(testReview);

        boolean exists = reviewRepository.existsByEventAndUser(testEvent,testUser);
        testReview = reviewRepository.findReviewsByUser(testUser).get(0);

        assertNotNull(testReview);
        assertTrue(exists);
        assertEquals(title, testReview.getTitle()); // CHECK TITLE
        assertEquals(description, testReview.getDescription()); // CHECK DESCRIPTION
        assertEquals(rating, testReview.getRating());  // CHECK RATING

        assertEquals(testUser.getUsername(),testReview.getUser().getUsername()); // CHECK USERNAME
        assertEquals(testUser.getPassword(),testReview.getUser().getPassword()); // CHECK PASSWORD

        assertEquals(testUser.getProfile().getFirstName(),testReview.getUser().getProfile().getFirstName());
        assertEquals(testUser.getProfile().getLastName(),testReview.getUser().getProfile().getLastName());
        assertEquals(testUser.getProfile().getEmail(),testReview.getUser().getProfile().getEmail());
        assertEquals(testUser.getProfile().getDateOfBirth(),testReview.getUser().getProfile().getDateOfBirth());
        assertEquals(testUser.getProfile().getPhoneNumber(),testReview.getUser().getProfile().getPhoneNumber());
        assertEquals(testUser.getProfile().getAddress(),testReview.getUser().getProfile().getAddress());
        assertEquals(testUser.getProfile().getProfilePicture(),testReview.getUser().getProfile().getProfilePicture());

        assertEquals(testEvent.getName(),testReview.getEvent().getName());
        assertEquals(testEvent.getCapacity(),testReview.getEvent().getCapacity());
        assertEquals(testEvent.getDescription(),testReview.getEvent().getDescription());
        assertEquals(testEvent.getCost(),testReview.getEvent().getCost());
        assertEquals(testEvent.getAddress(),testReview.getEvent().getAddress());
        assertEquals(testEvent.getEmail(),testReview.getEvent().getEmail());
        assertEquals(testEvent.getPhoneNumber(),testReview.getEvent().getPhoneNumber());

        assertEquals(testEventSchedule.getStartDateTime(),testReview.getEvent().getEventSchedule().getStartDateTime());

    }
}