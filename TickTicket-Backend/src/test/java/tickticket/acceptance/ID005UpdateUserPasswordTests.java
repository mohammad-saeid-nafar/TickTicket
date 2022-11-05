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
import tickticket.dao.EventTypeRepository;
import tickticket.dao.ProfileRepository;
import tickticket.dao.UserRepository;
import tickticket.dto.UserDTO;
import tickticket.model.*;
import tickticket.service.UserService;


@ExtendWith(MockitoExtension.class)
public class ID005UpdateUserPasswordTests {

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
    public void updateUserPassword() {

        String newPassword = "NewPassword1";

        Optional<User> userOption = userRepository.findById(USER_ID);
        User user = userOption.get();
        UserDTO userDTO = Conversion.convertToDTO(user);

        userDTO.setPassword(newPassword);
        User updatedUser = userService.editUserPassword(USER_ID, USER_PASSWORD, newPassword);

        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(user.getCreated(), updatedUser.getCreated());
        assertEquals(newPassword, updatedUser.getPassword());

    }

    @Test
    public void updateProfileFail1() {

        String error = "";

        Optional<User> userOption = userRepository.findById(USER_ID);
        User user = userOption.get();
        UserDTO userDTO = Conversion.convertToDTO(user);
        try {
            User updatedUser = userService.editUserPassword(USER_ID, "hsdyd", USER_PASSWORD);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("Old password entered is incorrect", error);
    }

    @Test
    public void updateProfileFail2() {

        String error = "";

        Optional<User> userOption = userRepository.findById(USER_ID);
        User user = userOption.get();
        UserDTO userDTO = Conversion.convertToDTO(user);
        try {
            User updatedUser = userService.editUserPassword(USER_ID, USER_PASSWORD, "");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals("New password cannot be blank.", error);
    }

}
