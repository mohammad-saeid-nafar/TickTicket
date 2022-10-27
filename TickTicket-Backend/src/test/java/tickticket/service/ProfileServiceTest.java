package tickticket.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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
import tickticket.model.*;


@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

    @InjectMocks
    private ProfileService profileService;

    private static final UUID PROFILE_ID = UUID.randomUUID();
    private static final String PROFILE_FIRSTNAME ="Bob";
    private static final String PROFILE_LASTNAME ="Fisher";
    private static final String PROFILE_ADDRESS ="1000, MEMORY LANE";
    private static final String PROFILE_EMAIL ="TestUser@mail.com";
    private static final String PROFILE_PHONE ="5141234567";
    private static final String PROFILE_PICTURE = "img1.png";
    private static final LocalDate PROFILE_DATE_OF_BIRTH = LocalDate.parse("2000-01-02");

    private static final String PROFILE2_FIRSTNAME = "Gary";
    private static final String PROFILE2_LASTNAME = "Jimmy";
    private static final String PROFILE2_EMAIL = "garyjimmy@mail.com";
    private static final String PROFILE2_PHONE = "012344567";
    private static final String PROFILE2_ADDRESS = "222, 5th Ave";
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
                profile.setId(PROFILE_ID);
                profile.setFirstName(PROFILE_FIRSTNAME);
                profile.setLastName(PROFILE_LASTNAME);
                profile.setAddress(PROFILE_ADDRESS);
                profile.setEmail(PROFILE_EMAIL);
                profile.setPhoneNumber(PROFILE_PHONE);
                profile.setProfilePicture(PROFILE_PICTURE);
                profile.setDateOfBirth(PROFILE_DATE_OF_BIRTH);
                profile.setInterests(interests);

                return Optional.of(profile);
            }
            else {
                return Optional.empty();
            }
        });

        lenient().when(profileRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(PROFILE_ID)) {
                return profileRepository.findProfileByEmail(PROFILE_EMAIL);
            }
            else {
                return Optional.empty();
            }
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);

        lenient().when(profileRepository.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateProfile() {
        Profile profile = null;
        try {
            EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);
            profile = profileService.createProfile(PROFILE2_FIRSTNAME, PROFILE2_LASTNAME, PROFILE2_ADDRESS, PROFILE2_EMAIL, PROFILE2_PHONE, PROFILE2_PICTURE, PROFILE2_DATE_OF_BIRTH, interests);
        } catch(IllegalArgumentException e) {
            fail();
        }

        assertNotNull(profile);
        assertEquals(PROFILE2_FIRSTNAME, profile.getFirstName());
        assertEquals(PROFILE2_LASTNAME, profile.getLastName());
        assertEquals(PROFILE2_ADDRESS, profile.getAddress());
        assertEquals(PROFILE2_EMAIL, profile.getEmail());
        assertEquals(PROFILE2_PHONE, profile.getPhoneNumber());
        assertEquals(PROFILE2_PICTURE, profile.getProfilePicture());
        assertEquals(PROFILE2_DATE_OF_BIRTH,profile.getDateOfBirth());
        assertEquals(EVENT_TYPE2_NAME, profile.getInterests().get(0).getName());
        assertEquals(EVENT_TYPE2_DESCRIPTION, profile.getInterests().get(0).getDescription());
        assertEquals(EVENT_TYPE2_AGE_REQUIREMENT, profile.getInterests().get(0).getAgeRequirement());
    }

    @Test
    public void testCreateProfileTakenEmail() {
        try {
            EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);
            profileService.createProfile(PROFILE2_FIRSTNAME, PROFILE2_LASTNAME, PROFILE2_ADDRESS, PROFILE_EMAIL, PROFILE2_PHONE, PROFILE2_PICTURE, PROFILE2_DATE_OF_BIRTH, interests);
        } catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), "User with email entered already exists.");
        }
    }

    @Test
    public void testCreateProfileInvalidFirstName() {
        try {
            EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);
            profileService.createProfile("", PROFILE2_LASTNAME, PROFILE2_ADDRESS, PROFILE_EMAIL, PROFILE2_PHONE, PROFILE2_PICTURE, PROFILE2_DATE_OF_BIRTH, interests);
        } catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), "First name cannot be blank.");
        }
    }

    @Test
    public void testCreateProfileInvalidLastName() {
        try {
            EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);
            profileService.createProfile(PROFILE2_FIRSTNAME, "", PROFILE2_ADDRESS, PROFILE_EMAIL, PROFILE2_PHONE, PROFILE2_PICTURE, PROFILE2_DATE_OF_BIRTH, interests);
        } catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Last name cannot be blank.");
        }
    }

    @Test
    public void testCreateProfileInvalidAddress() {
        try {
            EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);
            profileService.createProfile(PROFILE2_FIRSTNAME, PROFILE2_LASTNAME, "", PROFILE_EMAIL, PROFILE2_PHONE, PROFILE2_PICTURE, PROFILE2_DATE_OF_BIRTH, interests);
        } catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Address cannot be blank.");
        }
    }

    @Test
    public void testCreateProfileInvalidEmail() {
        try {
            EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);
            profileService.createProfile(PROFILE2_FIRSTNAME, PROFILE2_LASTNAME, PROFILE2_ADDRESS, "aadeew", PROFILE2_PHONE, PROFILE2_PICTURE, PROFILE2_DATE_OF_BIRTH, interests);
        } catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Invalid email.");
        }
    }

    @Test
    public void testCreateProfileInvalidEmail2() {
        try {
            EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);
            profileService.createProfile(PROFILE2_FIRSTNAME, PROFILE2_LASTNAME, PROFILE2_ADDRESS, "", PROFILE2_PHONE, PROFILE2_PICTURE, PROFILE2_DATE_OF_BIRTH, interests);
        } catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Email cannot be blank.");
        }
    }

    @Test
    public void testCreateProfileInvalidPhone() {
        try {
            EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);
            profileService.createProfile(PROFILE2_FIRSTNAME, PROFILE2_LASTNAME, PROFILE2_ADDRESS, PROFILE2_EMAIL, "", PROFILE2_PICTURE, PROFILE2_DATE_OF_BIRTH, interests);
        } catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Phone number cannot be blank.");
        }
    }

    @Test
    public void testCreateProfileInvalidDateOfBirth() {
        try {
            EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);
            profileService.createProfile(PROFILE2_FIRSTNAME, PROFILE2_LASTNAME, PROFILE2_ADDRESS, PROFILE2_EMAIL, PROFILE2_PHONE, PROFILE2_PICTURE, null, interests);
        } catch(IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Date of birth cannot be blank.");
        }
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
        assertEquals(PROFILE_DATE_OF_BIRTH,profile.getDateOfBirth());
    }

    @Test
    public void testGetProfileNotFound() {
        try{
            profileService.getProfileByEmail(PROFILE2_EMAIL);
        }catch(IllegalArgumentException e){
            assertEquals(e.getMessage(), "Profile "+PROFILE2_EMAIL+" not found");
        }
    }

    @Test
    public void testUpdateProfile() {
        Profile profile = null;
        try {
            EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
            List<EventType> interests = new ArrayList<>();
            interests.add(eventType);
            profile = profileService.updateProfile(PROFILE_ID, PROFILE2_FIRSTNAME, PROFILE2_LASTNAME, PROFILE2_ADDRESS, PROFILE_EMAIL, PROFILE2_PHONE, PROFILE2_PICTURE, PROFILE2_DATE_OF_BIRTH, interests);
        } catch(IllegalArgumentException e) {
            fail();
        }

        assertNotNull(profile);
        assertEquals(PROFILE2_FIRSTNAME, profile.getFirstName());
        assertEquals(PROFILE2_LASTNAME, profile.getLastName());
        assertEquals(PROFILE2_ADDRESS, profile.getAddress());
        assertEquals(PROFILE_EMAIL, profile.getEmail());
        assertEquals(PROFILE2_PHONE, profile.getPhoneNumber());
        assertEquals(PROFILE2_PICTURE, profile.getProfilePicture());
        assertEquals(PROFILE2_DATE_OF_BIRTH,profile.getDateOfBirth());
    }

}
