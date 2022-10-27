package tickticket.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tickticket.model.Profile;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestProfilePersistence {


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
	 public void testPersistAndLoadProfile() {

	        // Profile
	        Profile testProfile = new Profile();
	        testProfile.setFirstName("TestName");
	        testProfile.setLastName("TestLastName");
	        testProfile.setAddress("Test Address");
	        testProfile.setEmail("testemail@test.com");
	        testProfile.setPhoneNumber("(123)456-7890");
	        testProfile.setProfilePicture("img1.jpg");
	        testProfile.setDateOfBirth(LocalDate.of(2000,2,22));

	        profileRepository.save(testProfile);
	        
	        assertTrue(profileRepository.existsByEmail("testemail@test.com"));
	        testProfile = profileRepository.findProfileByEmail("testemail@test.com").orElse(null);
	        
	        assertNotNull(testProfile);
	        assertEquals("testemail@test.com", testProfile.getEmail());
	        assertEquals("TestName", testProfile.getFirstName());
	        assertEquals("TestLastName", testProfile.getLastName());
	        assertEquals("Test Address", testProfile.getAddress());
	        assertEquals("(123)456-7890", testProfile.getPhoneNumber());
	        assertEquals("img1.jpg", testProfile.getProfilePicture());
	        assertEquals(LocalDate.of(2000,2,22), testProfile.getDateOfBirth());
	 }
}
