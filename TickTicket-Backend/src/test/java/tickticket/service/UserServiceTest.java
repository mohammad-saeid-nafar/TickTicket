package tickticket.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import tickticket.dao.EventTypeRepository;
import tickticket.dao.ProfileRepository;
import tickticket.dao.UserRepository;
import tickticket.dto.UserDTO;
import tickticket.model.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
	@Mock
	private UserRepository userRepository;

	@Mock
	private ProfileRepository profileRepository;

    @Mock
	private EventTypeRepository eventTypeRepository;

    @InjectMocks
	private UserService userService;

	private static final UUID USER_ID = UUID.randomUUID();
	private static final String USER_USERNAME = "TestUser";
	private static final String USER_PASSWORD = "TestPassword123";

	private static final String PROFILE_FIRSTNAME = "Bob";
	private static final String PROFILE_LASTNAME = "Fisher";
	private static final String PROFILE_ADDRESS ="1000, MEMORY LANE";
	private static final String PROFILE_EMAIL = "TestUser@mail.com";
	private static final String PROFILE_PHONE = "5141234567";
    private static final String PROFILE_PICTURE = "img1.png";
	private static final LocalDate PROFILE_DATE_OF_BIRTH = LocalDate.parse("2000-01-02");

	private static final String PROFILE2_FIRSTNAME = "Gary";
	private static final String PROFILE2_LASTNAME = "Jimmy";
	private static final String PROFILE2_ADDRESS = "222, 5th Ave";
	private static final String PROFILE2_EMAIL = "garyjimmy@mail.com";
	private static final String PROFILE2_PHONE = "012344567";
	private static final String PROFILE2_PICTURE = "img1.png";
	private static final LocalDate PROFILE2_DATE_OF_BIRTH = LocalDate.parse("2001-04-30");

    private static final String EVENT_TYPE_NAME = "Test Event Type";
    private static final String EVENT_TYPE_DESCRIPTION = "Testing the user service";
    private static final int EVENT_TYPE_AGE_REQUIREMENT = 13;

	private static final String EVENT_TYPE2_NAME = "Pop";
	private static final String EVENT_TYPE2_DESCRIPTION = "Pop is a kind of music";
	private static final int EVENT_TYPE2_AGE_REQUIREMENT = 13;

	@BeforeEach
	public void setMockOutput() {

		lenient().when(eventTypeRepository.findEventTypeByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(EVENT_TYPE_NAME)) {
				EventType eventType = new EventType();
				eventType.setName(EVENT_TYPE_NAME);
				eventType.setDescription(EVENT_TYPE_DESCRIPTION);
				eventType.setAgeRequirement(EVENT_TYPE_AGE_REQUIREMENT);
				return Optional.of(eventType);
			}else if(invocation.getArgument(0).equals(EVENT_TYPE2_NAME)){
				EventType eventType = new EventType();
				eventType.setName(EVENT_TYPE2_NAME);
				eventType.setDescription(EVENT_TYPE2_DESCRIPTION);
				eventType.setAgeRequirement(EVENT_TYPE2_AGE_REQUIREMENT);
				return Optional.of(eventType);
			}
			else{
				return Optional.empty();
			}
		});

		lenient().when(profileRepository.findProfileByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PROFILE_EMAIL)) {
				EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE_NAME).orElse(null);
				List<EventType> interests = new ArrayList<>();
				interests.add(eventType);

				Profile profile = new Profile();
				profile.setFirstName(PROFILE_FIRSTNAME);
				profile.setLastName(PROFILE_LASTNAME);
				profile.setAddress(PROFILE_ADDRESS);
				profile.setEmail(PROFILE_EMAIL);
				profile.setPhoneNumber(PROFILE_PHONE);
				profile.setProfilePicture(PROFILE_PICTURE);
				profile.setDateOfBirth(PROFILE_DATE_OF_BIRTH);
				profile.setInterests(interests);

				return Optional.of(profile);
			}else if(invocation.getArgument(0).equals(PROFILE2_EMAIL)){
				EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
				List<EventType> interests = new ArrayList<>();
				interests.add(eventType);

				Profile profile = new Profile();
				profile.setFirstName(PROFILE2_FIRSTNAME);
				profile.setLastName(PROFILE2_LASTNAME);
				profile.setAddress(PROFILE2_ADDRESS);
				profile.setEmail(PROFILE2_EMAIL);
				profile.setPhoneNumber(PROFILE2_PHONE);
				profile.setProfilePicture(PROFILE2_PICTURE);
				profile.setDateOfBirth(PROFILE2_DATE_OF_BIRTH);
				profile.setInterests(interests);

				return Optional.of(profile);
			}
			else {
				return Optional.empty();
			}
		});

		lenient().when(userRepository.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(USER_USERNAME)) {
               Profile profile = profileRepository.findProfileByEmail(PROFILE_EMAIL).orElse(null);
                User user = new User();
				user.setId(USER_ID);
                user.setUsername(USER_USERNAME);
                user.setPassword(USER_PASSWORD);
                user.setCreated(LocalDate.now());
                user.setProfile(profile);
				return Optional.of(user);
			}else {
				return Optional.empty();
			}
		});

		lenient().when(userRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(USER_ID)) {
				return userRepository.findUserByUsername(USER_USERNAME);
			}else {
				return Optional.empty();
			}
		});
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);

		lenient().when(userRepository.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(profileRepository.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
		lenient().doNothing().when(userRepository).delete(any(User.class));
	}


	@Test
	public void testCreateUser() {

		String username = "garyjimmy";
		String password = "Password123";
		User user = null;

		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		userDTO.setPassword(password);

		try {
			user = userService.createUser(userDTO);
		}catch(IllegalArgumentException e) {
			fail();
		}

		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertEquals(password, user.getPassword());

//		assertNotNull(user.getProfile());
//		assertEquals(PROFILE2_FIRSTNAME, user.getProfile().getFirstName());
//		assertEquals(PROFILE2_LASTNAME, user.getProfile().getLastName());
//		assertEquals(PROFILE2_ADDRESS, user.getProfile().getAddress());
//		assertEquals(PROFILE2_EMAIL, user.getProfile().getEmail());
//		assertEquals(PROFILE2_PHONE, user.getProfile().getPhoneNumber());
//		assertEquals(PROFILE2_PICTURE, user.getProfile().getProfilePicture());
//        assertEquals(PROFILE2_DATE_OF_BIRTH,user.getProfile().getDateOfBirth());
//        assertEquals(EVENT_TYPE2_NAME, user.getProfile().getInterests().get(0).getName());
//        assertEquals(EVENT_TYPE2_DESCRIPTION, user.getProfile().getInterests().get(0).getDescription());
//        assertEquals(EVENT_TYPE2_AGE_REQUIREMENT, user.getProfile().getInterests().get(0).getAgeRequirement());

	}
	
	@Test
	public void testCreateUserTakenUsername(){
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USER_USERNAME);
		userDTO.setPassword(USER_PASSWORD);

		try {
			userService.createUser(userDTO);
		}catch(IllegalArgumentException e) {
			assertEquals("Username is already taken", e.getMessage());
		}
	}
	
    @Test
	public void testCreateUserErrorBlankUsername() {
		String username ="";
		String password = "Password123";

		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		userDTO.setPassword(password);
		try {
			userService.createUser(userDTO);
		}catch (IllegalArgumentException e) {
			assertEquals("Username cannot be blank", e.getMessage());
		}
	}

    @Test
	public void testCreateUserErrorBlankPassword() {
		String username ="GarryJimmy";
		String password = "";
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		userDTO.setPassword(password);
		try {
			userService.createUser(userDTO);
		}catch (IllegalArgumentException e) {
			assertEquals("Password cannot be blank", e.getMessage());
		}
	}

	@Test
	public void invalidPasswordUpperCase(){
		String username = "GaryJimmy";
		String password = "password123";
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		userDTO.setPassword(password);
		try {
			userService.createUser(userDTO);
		}catch(IllegalArgumentException e) {
			assertEquals("Password must contain at least one uppercase character", e.getMessage());
		}
	}


	@Test
	public void invalidPasswordLowerCase(){
		String username = "GaryJimmy";
		String password = "PASSWORD123";
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		userDTO.setPassword(password);
		try {
			userService.createUser(userDTO);
		}catch(IllegalArgumentException e) {
			assertEquals("Password must contain at least one lowercase character", e.getMessage());
		}
	}

	@Test
	public void invalidPasswordNumeric(){
		String username = "GaryJimmy";
		String password = "Password";
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		userDTO.setPassword(password);
		try {
			userService.createUser(userDTO);
		}catch(IllegalArgumentException e) {
			assertEquals("Password must contain at least one numeric character", e.getMessage());
		}
	}

	@Test
	public void invalidPasswordLengthUnder8(){
		String username = "GaryJimmy";
		String password = "Pass1";
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		userDTO.setPassword(password);
		try {
			userService.createUser(userDTO);
		}catch(IllegalArgumentException e) {
			assertEquals("Password must have at least 8 characters", e.getMessage());
		}
	}


	@Test
	public void invalidPasswordLengthOver20(){
		String username = "GaryJimmy";
		String password = "Password1234567890123456789";
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		userDTO.setPassword(password);
		try {
			userService.createUser(userDTO);
		}catch(IllegalArgumentException e) {
			assertEquals("Password must not have more than 20 characters", e.getMessage());
		}
	}
    
    @Test
	public void editUserPassword() {
		User user = null;
		try {
            user = userService.editUserPassword(USER_ID,USER_PASSWORD, "Newpassword123");
		}catch(IllegalArgumentException e) {
			fail();
		}
		assertNotNull(user);
		assertEquals(USER_ID, user.getId());
		assertEquals(USER_USERNAME, user.getUsername());
		assertEquals("Newpassword123", user.getPassword());
	}
	
	@Test
	public void editUserPasswordInvalidUpperCase() {
		try {
			userService.editUserPassword(USER_ID, USER_PASSWORD, "newpassword123");
		}catch(IllegalArgumentException e) {
			assertEquals("Password must contain at least one uppercase character", e.getMessage());
		}
	}
	
    @Test
	public void testUpdateOldPasswordIncorrect() {
		try {
			userService.editUserPassword(USER_ID, "wrong password", USER_PASSWORD);
		}catch(IllegalArgumentException e) {
			assertEquals("Old password entered is incorrect", e.getMessage());
		}
	}

	@Test
	public void editUserPasswordNullPassword() {
		try {
			userService.editUserPassword(USER_ID, USER_PASSWORD, null);
		}catch(IllegalArgumentException e) {
			assertEquals("New password cannot be blank.", e.getMessage());
		}
	}
	
	@Test
	public void deleteUserSuccess() {
		boolean success = false;
		try {
			success = userService.deleteUser(USER_ID);
		}catch(IllegalArgumentException e) {
			fail();
		}
		assertTrue(success);
	}
	
	
	@Test
	public void deleteUserUserNotFound() {
		UUID id = UUID.randomUUID();
		try {
			userService.deleteUser(id);
		}catch(IllegalArgumentException e) {
			assertEquals("User " + id + " not found.", e.getMessage());
		}
	}

	@Test
	public void testFindUser(){
		User user = null;
		try {
			user = userService.getUser(USER_ID);
		}catch(IllegalArgumentException e) {
			fail();
		}
		assertNotNull(user);
		assertEquals(user.getId(), USER_ID);
		assertEquals(user.getUsername(), USER_USERNAME);
		assertEquals(user.getPassword(), USER_PASSWORD);
	}

}
