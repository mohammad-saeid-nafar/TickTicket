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

import javax.validation.constraints.Email;


@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

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

    @BeforeEach
    public void setMockOutput() {
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

        lenient().when(profileRepo.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateProfile() {
        assertEquals(0, profileService.getAllProfiles().size());

        Profile profile = null;
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
        } catch(IllegalArgumentException e) {
            fail();
        }

        assertNotNull(profile);
        assertEquals(firstName, profile.getFirstName());
        assertEquals(lastName, profile.getLastName());
        assertEquals(address, profile.getAddress());
        assertEquals(email, profile.getEmail());
        assertEquals(phoneNumber, profile.getPhoneNumber());
        assertEquals(profilePicture, profile.getProfilePicture());
        assertEquals(dateOfBirth,profile.getDateOfBirth());
        assertEquals(eventTypeName, profile.getInterests().get(0).getName());
        assertEquals(eventTypeDescription, profile.getInterests().get(0).getDescription());
        assertEquals(ageRequirement, profile.getInterests().get(0).getAgeRequirement());
    }

    @Test
    public void testCreateProfileTakenEmail() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, lastName, address, PROFILE_EMAIL, phoneNumber, profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "User with email entered already exists.");
    }

    @Test
    public void testCreateProfileInvalidFirstName() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile("", lastName, address, PROFILE_EMAIL, phoneNumber, profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "First name cannot be blank.");
    }

    @Test
    public void testCreateProfileInvalidLastName() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, "", address, PROFILE_EMAIL, phoneNumber, profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "Last name cannot be blank.");
    }

    @Test
    public void testCreateProfileInvalidAddress() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, lastName, "", PROFILE_EMAIL, phoneNumber, profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "Address cannot be blank.");
    }

    @Test
    public void testCreateProfileInvalidEmail() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, lastName, address, "aadeew", phoneNumber, profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "Invalid email.");
    }

    @Test
    public void testCreateProfileInvalidEmail2() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, lastName, address, "", phoneNumber, profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "Email cannot be blank.");
    }

    @Test
    public void testCreateProfileInvalidPhone() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, lastName, address, email, "", profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "Phone number cannot be blank.");
    }

    @Test
    public void testCreateProfileInvalidDateOfBirth() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, null, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "Date of birth cannot be blank.");
    }

    @Test
    public void testDeleteProfile() {
        boolean deleted = profileService.deleteByEmail(PROFILE_EMAIL);
        assertTrue(deleted);
        assertTrue(profileRepo.existsByEmail(PROFILE_EMAIL) == false);
    }

    @Test
    public void testDeleteProfileNotFound() {
        boolean deleted = profileService.deleteByEmail(email);
        assertTrue(deleted == false);
    }

    @Test
    public void testGetProfile() {
        Profile profile = profileService.getProfileByEmail(PROFILE_EMAIL);
        assertNotNull(profile);
        assertEquals(PROFILE_FIRSTNAME, profile.getFirstName());
        assertEquals(PROFILE_LASTNAME, profile.getLastName());
        assertEquals(PROFILE_ADDRESS, profile.getAddress());
        assertEquals(PROFILE_EMAIL, profile.getEmail());
        assertEquals(PROFILE_PHONE, profile.getPhoneNumber());
        assertEquals(PROFILE_PICTURE, profile.getProfilePicture());
        assertEquals(PROFILE_DATEOFBIRTH,profile.getDateOfBirth().toString());
    }

    @Test
    public void testGetProfileNotFound() {
        Profile profile = profileService.getProfileByEmail(email);
        assertNull(profile);
    }

    @Test
    public void testUpdateProfile() {
        assertEquals(0, profileService.getAllProfiles().size());

        Profile profile = null;
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.updateProfile(firstName, lastName, address, PROFILE_EMAIL, phoneNumber, profilePicture, dateOfBirth, interests);
        } catch(IllegalArgumentException e) {
            fail();
        }

        assertNotNull(profile);
        assertEquals(firstName, profile.getFirstName());
        assertEquals(lastName, profile.getLastName());
        assertEquals(address, profile.getAddress());
        assertEquals(PROFILE_EMAIL, profile.getEmail());
        assertEquals(phoneNumber, profile.getPhoneNumber());
        assertEquals(profilePicture, profile.getProfilePicture());
        assertEquals(dateOfBirth,profile.getDateOfBirth());
    }

    @Test
    public void testUpdateProfileInvalidFirstName() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.updateProfile("", lastName, address, PROFILE_EMAIL, phoneNumber, profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "First name cannot be blank.");
    }

    @Test
    public void testUpdateProfileInvalidLastName() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, "", address, PROFILE_EMAIL, phoneNumber, profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "Last name cannot be blank.");
    }

    @Test
    public void testUpdateProfileInvalidAddress() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, lastName, "", PROFILE_EMAIL, phoneNumber, profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "Address cannot be blank.");
    }

    @Test
    public void testUpdateProfileInvalidEmail() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, lastName, address, "aadeew", phoneNumber, profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "Invalid email.");
    }
    
    @Test
    public void testUpdateProfileInvalidPhone() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, lastName, address, email, "", profilePicture, dateOfBirth, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "Phone number cannot be blank.");
    }

    @Test
    public void testUpdateProfileInvalidDateOfBirth() {
        Profile profile = null;
        String error = "";
        try {
            eventType = eventTypeService.createEventType(eventTypeName, eventTypeDescription, ageRequirement);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);

            profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, null, interests);

        } catch(IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(profile);
        assertEquals(error, "Date of birth cannot be blank.");
    }

}
