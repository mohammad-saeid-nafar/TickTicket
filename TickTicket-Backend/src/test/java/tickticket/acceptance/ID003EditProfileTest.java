package tickticket.acceptance;

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

import tickticket.controller.Conversion;
import tickticket.dao.*;
import tickticket.service.*;
import tickticket.model.*;
import tickticket.dto.*;
@ExtendWith(MockitoExtension.class)
public class ID003EditProfileTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

    @Mock
    private EventTypeService eventTypeService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

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

    private ProfileDTO profile2DTO;

    @BeforeEach
    public void setMockOutput() {
        profile2DTO = new ProfileDTO();
        profile2DTO.setUsername("TestUser2");
        profile2DTO.setFirstName(PROFILE2_FIRSTNAME);
        profile2DTO.setLastName(PROFILE2_LASTNAME);
        profile2DTO.setEmail(PROFILE2_EMAIL);
        profile2DTO.setPhoneNumber(PROFILE2_PHONE);
        profile2DTO.setAddress(PROFILE2_ADDRESS);
        profile2DTO.setProfilePicture(PROFILE2_PICTURE);
        profile2DTO.setDateOfBirth(PROFILE2_DATE_OF_BIRTH);

        lenient().when(userService.getUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            System.out.println("Mocking getUserByUsername");
            User user = new User();
            user.setUsername("TestUser");
            user.setId(UUID.randomUUID());
            System.out.println("User: " + user);
            return user;
        });

        lenient().when(userRepository.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            System.out.println("Mocking getUserByUsername");
            User user = new User();
            user.setUsername("TestUser");
            user.setId(UUID.randomUUID());
            System.out.println("User: " + user);
            return Optional.of(user);
        });

        lenient().when(eventTypeRepository.findEventTypeByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            System.out.println("Mocking findEventTypeByName");
            if(invocation.getArgument(0).equals(EVENT_TYPE_NAME)) {
                EventType eventType = new EventType();
                eventType.setId(UUID.randomUUID());
                eventType.setName(EVENT_TYPE_NAME);
                eventType.setDescription(EVENT_TYPE_DESCRIPTION);
                eventType.setAgeRequirement(EVENT_TYPE_AGE_REQUIREMENT);
                return Optional.of(eventType);
            }else if(invocation.getArgument(0).equals(EVENT_TYPE2_NAME)){
                System.out.println("here");
                EventType eventType = new EventType();
                eventType.setId(UUID.randomUUID());
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

        lenient().when(eventTypeService.getAllEventTypes(any())).thenAnswer((InvocationOnMock invocation) -> {
            System.out.println("Mocking getAllEventTypes");
            EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE2_NAME).orElse(null);
            List<EventType> eventTypes = new ArrayList<>();
            eventTypes.add(eventType);
            return eventTypes;
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);

        lenient().when(profileRepository.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void updateProfile() {

        String newPhoneNumber = "4389935031";

        Optional<Profile> profileOption = profileRepository.findById(PROFILE_ID);
        Profile profile = profileOption.get();
        ProfileDTO profileDTO = Conversion.convertToDTO(profile);

        profileDTO.setPhoneNumber(newPhoneNumber);
        Profile updatedProfile = profileService.updateProfile(profileDTO);

        assertEquals(profile.getFirstName(), updatedProfile.getFirstName());
        assertEquals(profile.getLastName(), updatedProfile.getLastName());
        assertEquals(profile.getAddress(), updatedProfile.getAddress());
        assertEquals(profile.getEmail(), updatedProfile.getEmail());
        assertEquals(profile.getDateOfBirth(), updatedProfile.getDateOfBirth());
        assertEquals(profile.getProfilePicture(), updatedProfile.getProfilePicture());
        assertEquals(newPhoneNumber, updatedProfile.getPhoneNumber());

    }

    @Test
    public void updateProfile2() {

        String newPhoneNumber = "4389935031";
        String newFirstName = "TestName";
        String newLastName = "TestLast";
        String newAddress = "TestAddress";
        String newProfilePicture = "img2.png";

        Optional<Profile> profileOption = profileRepository.findById(PROFILE_ID);
        Profile profile = profileOption.get();
        ProfileDTO profileDTO = Conversion.convertToDTO(profile);

        profileDTO.setPhoneNumber(newPhoneNumber);
        profileDTO.setFirstName(newFirstName);
        profileDTO.setAddress(newAddress);
        profileDTO.setLastName(newLastName);
        profileDTO.setProfilePicture(newProfilePicture);
        Profile updatedProfile = profileService.updateProfile(profileDTO);

        assertEquals(newFirstName, updatedProfile.getFirstName());
        assertEquals(newLastName, updatedProfile.getLastName());
        assertEquals(newAddress, updatedProfile.getAddress());
        assertEquals(newProfilePicture, updatedProfile.getProfilePicture());
        assertEquals(newPhoneNumber, updatedProfile.getPhoneNumber());
    }

    @Test
    public void updateProfileFail() {

        String error = "";

        Optional<Profile> profileOption = profileRepository.findById(PROFILE_ID);
        Profile profile = profileOption.get();
        ProfileDTO profileDTO = Conversion.convertToDTO(profile);
        profileDTO.setFirstName("");
        try {
            Profile updatedProfile = profileService.updateProfile(profileDTO);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("The first name cannot be empty", error);


        profileOption = profileRepository.findById(PROFILE_ID);
        profile = profileOption.get();
        profileDTO = Conversion.convertToDTO(profile);
        profileDTO.setLastName("");
        try {
            profileService.updateProfile(profileDTO);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("The last name cannot be empty", error);


        profileOption = profileRepository.findById(PROFILE_ID);
        profile = profileOption.get();
        profileDTO = Conversion.convertToDTO(profile);
        profileDTO.setAddress("");
        try {
            profileService.updateProfile(profileDTO);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("The address cannot be empty", error);


        profileOption = profileRepository.findById(PROFILE_ID);
        profile = profileOption.get();
        profileDTO = Conversion.convertToDTO(profile);
        profileDTO.setPhoneNumber("");
        try {
            profileService.updateProfile(profileDTO);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("The phone number cannot be empty", error);

    }
}
