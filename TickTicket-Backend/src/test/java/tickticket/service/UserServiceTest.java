package tickticket.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import tickticket.dao.EventTypeRepository;
import tickticket.dao.ProfileRepository;
import tickticket.dao.UserRepository;
import tickticket.model.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
	@Mock
	private UserRepository userRepository;

	@Mock
	private ProfileRepository profileRepo;

    @Mock
	private EventTypeRepository eventTypeRepository;

    @InjectMocks
	private UserService userService;

	@InjectMocks
	private ProfileService profileService;

    @InjectMocks
    private EventTypeService eventTypeService;

	private static final String USER_USERNAME ="TestUser";
	private static final String USER_PASSWORD ="TestPassword123";

	private static final String PROFILE_FIRSTNAME ="Bob";
	private static final String PROFILE_LASTNAME ="Fisher";
	private static final String PROFILE_ADDRESS ="1000, MEMORY LANE";
	private static final String PROFILE_EMAIL ="TestUser@mail.com";
	private static final String PROFILE_PHONE ="5141234567";
    private static final String PROFILE_PICTURE = "img1.png";
	private static final String PROFILE_DATEOFBIRTH ="2000-01-02";

    private static final String EVENTTYPE_NAME = "Test Event Type";
    private static final String EVENTTYPE_DESCRIPTION = "Testing the user service";
    private static final int EVENTTYPE_AGEREQUIREMENT = 13;

	@BeforeEach
	public void setMockOutput() {

		lenient().when(userRepository.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(USER_USERNAME)) {
                EventType eventType = new EventType();
                eventType.setName(EVENTTYPE_NAME);
                eventType.setDescription(EVENTTYPE_DESCRIPTION);
                eventType.setAgeRequirement(EVENTTYPE_AGEREQUIREMENT);
                List<EventType> interests = new ArrayList<>();

				Profile profile = new Profile();
				profile.setFirstName(PROFILE_FIRSTNAME);
				profile.setLastName(PROFILE_LASTNAME);
				profile.setAddress(PROFILE_ADDRESS);
				profile.setEmail(PROFILE_EMAIL);
				profile.setPhoneNumber(PROFILE_PHONE);
				profile.setProfilePicture(PROFILE_PICTURE);
                profile.setDateOfBirth(LocalDate.parse(PROFILE_DATEOFBIRTH));
                profile.setInterests(interests);

                User user = new User();
                user.setUsername(USER_USERNAME);
                user.setPassword(USER_PASSWORD);
                user.setCreated(LocalDate.now());
                user.setProfile(profile);

				return user;

			}else {
				return null;
			}
		});
		
		lenient().when(profileRepo.findProfileByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PROFILE_EMAIL)) {

				EventType eventType = new EventType();
                eventType.setName(EVENTTYPE_NAME);
                eventType.setDescription(EVENTTYPE_DESCRIPTION);
                eventType.setAgeRequirement(EVENTTYPE_AGEREQUIREMENT);
                List<EventType> interests = new ArrayList<>();

				Profile profile = new Profile();
				profile.setFirstName(PROFILE_FIRSTNAME);
				profile.setLastName(PROFILE_LASTNAME);
				profile.setAddress(PROFILE_ADDRESS);
				profile.setEmail(PROFILE_EMAIL);
				profile.setPhoneNumber(PROFILE_PHONE);
				profile.setProfilePicture(PROFILE_PICTURE);
                profile.setDateOfBirth(LocalDate.parse(PROFILE_DATEOFBIRTH));
                profile.setInterests(interests);

				return profile;
			} else {
				return null;
			}
		});
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(userRepository.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(profileRepo.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(eventTypeRepository.save(any(EventType.class))).thenAnswer(returnParameterAsAnswer);
	}

	
	


	@Test
	public void testCreateUser() {
		assertEquals(0, userService.getAllUsers().size());

        String eventTypeName = "Pop";
        String eventTypeDescription = "Pop is a kind of music";
        int ageRequirement = 13;
        EventType eventType = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String profilePicture = "img1.png";
        LocalDate dateOfBirth = LocalDate.parse("2001-04-30");
		Profile profile = null;

		String username = "garyjimmy";
		String password = "Password123";
		User user = null;

		try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

			profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
			user = userService.createUser(username, password, profile);
		}catch(IllegalArgumentException e) {
			fail();
		}

		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertEquals(password, user.getPassword());

		assertNotNull(user.getProfile());
		assertEquals(firstName, user.getProfile().getFirstName());
		assertEquals(lastName, user.getProfile().getLastName());
		assertEquals(address, user.getProfile().getAddress());
		assertEquals(email, user.getProfile().getEmail());
		assertEquals(phoneNumber, user.getProfile().getPhoneNumber());
		assertEquals(profilePicture, user.getProfile().getProfilePicture());
        assertEquals(dateOfBirth,user.getProfile().getDateOfBirth());
        assertEquals(eventTypeName, user.getProfile().getInterests().get(0).getName());
        assertEquals(eventTypeDescription, user.getProfile().getInterests().get(0).getDescription());
        assertEquals(ageRequirement, user.getProfile().getInterests().get(0).getAgeRequirement());

	}
	
	@Test
	public void testCreateUserTakenUsername(){

		assertEquals(0, userService.getAllUsers().size());

		String eventTypeName = "Pop";
        String eventTypeDescription = "Pop is a kind of music";
        int ageRequirement = 13;
        EventType eventType = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String profilePicture = "img1.png";
        LocalDate dateOfBirth = LocalDate.parse("2001-04-30");
		Profile profile = null;

		String username = USER_USERNAME;
		String password = "Password123";
		User user = null;
		String error = "";
		try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

			profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
			user = userService.createUser(username, password, profile);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(user);
		assertEquals(error, "Username is already taken");

	}
	

    @Test
    public void testFindUser(){
		assertEquals(0, userService.getAllUsers().size());
        User user = null;
		try {
			user = userService.getUser(USER_USERNAME);
		}catch(IllegalArgumentException e) {
			fail();
		}
		assertNotNull(user);
		assertEquals(user.getUsername(), USER_USERNAME);
		assertEquals(user.getPassword(), USER_PASSWORD);
    }
	
    @Test
	public void testCreateUserErrorBlankUsername() {
		assertEquals(0, userService.getAllUsers().size());  
        String eventTypeName = "Pop";
        String eventTypeDescription = "Pop is a kind of music";
        int ageRequirement = 13;
        EventType eventType = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String profilePicture = "img1.png";
        LocalDate dateOfBirth = LocalDate.parse("2001-04-30");
		Profile profile = null;

		String username ="";
		String password = "Password123";
		User user = null;
		String error = "";
		try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

			profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
			user = userService.createUser(username, password, profile);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		assertEquals("Username cannot be blank",error);
	}

    @Test
	public void testCreateUserErrorBlankPassword() {
		assertEquals(0, userService.getAllUsers().size());  
        String eventTypeName = "Pop";
        String eventTypeDescription = "Pop is a kind of music";
        int ageRequirement = 13;
        EventType eventType = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String profilePicture = "img1.png";
        LocalDate dateOfBirth = LocalDate.parse("2001-04-30");
		Profile profile = null;

		String username ="GarryJimmy";
		String password = "";
		User user = null;
		String error = "";
		try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

			profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
			user = userService.createUser(username, password, profile);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(user);
		assertEquals("Password cannot be blank",error);
	}
    
    @Test
	public void editUserPassword() {
		assertEquals(0, userService.getAllUsers().size());
		User user = null;
		
		try {
            user = userService.editUserPassword(USER_USERNAME, "Newpassword123");
		}catch(IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(user);
		assertEquals("Newpassword123", user.getPassword());
		assertEquals(USER_USERNAME, user.getUsername());
		
	}
	
	@Test
	public void editUserPasswordInvalidUpperCase() {
		assertEquals(0, userService.getAllUsers().size());
		User user = null;
		String error = null;
		
		try {
			user = userService.editUserPassword(USER_USERNAME, "newpassword123");
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(user);
		assertEquals(error, "Password must contain at least one uppercase character");
		
	}
	
    @Test
	public void testUpdateSamePassword() {
		User user = null;

		try {
			user = userService.editUserPassword(USER_USERNAME, USER_PASSWORD);
		}catch(IllegalArgumentException e) {
			fail();
		}
		assertNotNull(user);
		assertEquals(USER_USERNAME,user.getUsername());
		assertEquals(USER_PASSWORD,user.getPassword());
	}

	@Test
	public void editUserPasswordNullPassword() {
		assertEquals(0, userService.getAllUsers().size());
		User user = null;
		String error = null;
		
		try {
			user = userService.editUserPassword(USER_USERNAME, null);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(user);
		assertEquals(error, "New password cannot be blank.");
		
	}
	
	@Test
	public void deleteUserSuccess() {
		boolean success = false;
		try {
			success = userService.deleteUser(USER_USERNAME);
		}catch(IllegalArgumentException e) {
			fail();
		}
		assertTrue(success);
	}
	
	
	@Test
	public void deleteUserUserNotFound() {
		String error = null;
		try {
			userService.deleteUser("Fake username");
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "User not found.");
	}
	

	@Test
	public void invalidPasswordUpperCase(){

		assertEquals(0, userService.getAllUsers().size());

		String eventTypeName = "Pop";
        String eventTypeDescription = "Pop is a kind of music";
        int ageRequirement = 13;
        EventType eventType = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String profilePicture = "img1.png";
        LocalDate dateOfBirth = LocalDate.parse("2001-04-30");
		Profile profile = null;

		String username = "GaryJimmy";
		String password = "password123";
		User user = null;
		String error = "";
		try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

			profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
			user = userService.createUser(username, password, profile);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(user);
		assertEquals(error, "Password must contain at least one uppercase character");

	}


	@Test
	public void invalidPasswordLowerCase(){

		assertEquals(0, userService.getAllUsers().size());

		String eventTypeName = "Pop";
        String eventTypeDescription = "Pop is a kind of music";
        int ageRequirement = 13;
        EventType eventType = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String profilePicture = "img1.png";
        LocalDate dateOfBirth = LocalDate.parse("2001-04-30");
		Profile profile = null;


		String username = "GaryJimmy";
		String password = "PASSWORD123";
		User user = null;
		String error = "";
		try {
			eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

			profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
			user = userService.createUser(username, password, profile);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(user);
		assertEquals(error, "Password must contain at least one lowercase character");

	}

	@Test
	public void invalidPasswordNumeric(){

		assertEquals(0, userService.getAllUsers().size());

		String eventTypeName = "Pop";
        String eventTypeDescription = "Pop is a kind of music";
        int ageRequirement = 13;
        EventType eventType = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String profilePicture = "img1.png";
        LocalDate dateOfBirth = LocalDate.parse("2001-04-30");
		Profile profile = null;

		String username = "GaryJimmy";
		String password = "Password";
		User user = null;
		String error = "";
		try {
			eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

			profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
			user = userService.createUser(username, password, profile);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(user);
		assertEquals(error, "Password must contain at least one numeric character");

	}

	@Test
	public void invalidPasswordLengthUnder8(){

		assertEquals(0, userService.getAllUsers().size());

		String eventTypeName = "Pop";
        String eventTypeDescription = "Pop is a kind of music";
        int ageRequirement = 13;
        EventType eventType = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String profilePicture = "img1.png";
        LocalDate dateOfBirth = LocalDate.parse("2001-04-30");
		Profile profile = null;

		String username = "GaryJimmy";
		String password = "Pass1";
		User user = null;
		String error = "";
		try {
			eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

			profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
			user = userService.createUser(username, password, profile);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(user);
		assertEquals(error, "Password must have at least 8 characters");

	}


	@Test
	public void invalidPasswordLengthOver20(){

		assertEquals(0, userService.getAllUsers().size());

		
		String eventTypeName = "Pop";
        String eventTypeDescription = "Pop is a kind of music";
        int ageRequirement = 13;
        EventType eventType = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String profilePicture = "img1.png";
        LocalDate dateOfBirth = LocalDate.parse("2001-04-30");
		Profile profile = null;
		String username = "GaryJimmy";
		String password = "Password1234567890123456789";
		User user = null;
		String error = "";
		try {
			eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

			profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
			user = userService.createUser(username, password, profile);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(user);
		assertEquals(error, "Password must not have more than 20 characters");

	}

}
