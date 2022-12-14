package tickticket.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tickticket.model.EventType;
import tickticket.model.Profile;
import tickticket.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestUserPersistence {

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
    public void testPersistAndLoadUser(){
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

        String username = "testUser";
		String password = "testPassword";
        LocalDate created = LocalDate.of(2022,10,16);

        User testUser = new User();
        testUser.setUsername(username);
        testUser.setPassword(password);
        testUser.setCreated(created);
        testUser.setProfile(testProfile);

        eventTypeRepository.save(testEventType);
        userRepository.save(testUser);

        boolean exists = userRepository.existsByUsername(username);
        testUser = userRepository.findUserByUsername(username).orElse(null);

        assertNotNull(testUser);
        assertTrue(exists);
		assertEquals(username, testUser.getUsername());
		assertEquals(password,testUser.getPassword());
        assertEquals(created,testUser.getCreated());
        assertEquals(testProfile.getFirstName(), testUser.getProfile().getFirstName());
		assertEquals(testProfile.getLastName(),testUser.getProfile().getLastName());
		assertEquals(testProfile.getAddress(),testUser.getProfile().getAddress());
		assertEquals(testProfile.getEmail(),testUser.getProfile().getEmail());
		assertEquals(testProfile.getPhoneNumber(),testUser.getProfile().getPhoneNumber());
        assertEquals(testProfile.getProfilePicture(),testUser.getProfile().getProfilePicture());
        assertEquals(testProfile.getDateOfBirth(),testUser.getProfile().getDateOfBirth());
        assertEquals(testProfile.getInterests().get(0).getId(), testUser.getProfile().getInterests().get(0).getId());
    }

}
