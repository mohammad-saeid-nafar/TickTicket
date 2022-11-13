package tickticket.acceptance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import tickticket.dao.*;
import tickticket.model.*;
import tickticket.service.*;

@ExtendWith(MockitoExtension.class)
public class ID004DeleteAUserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    // Data for organizer
    private User user1;
    private static final UUID USER1_ID = UUID.randomUUID();
    private static final String USER1_USERNAME = "johnDoe";
    private static final String USER1_PASSWORD = "myP@assword1";
    private static final LocalDate USER1_CREATED = LocalDate.of(2022, 7, 12);

    // Data for user
    private User user2;
    private static final UUID USER2_ID = UUID.randomUUID();
    private static final String USER2_USERNAME = "bruceJ2";
    private static final String USER2_PASSWORD = "BrUcE_@214";
    private static final LocalDate USER2_CREATED = LocalDate.of(2022, 10, 3);

    // Data for organizer profile
    private Profile user1Profile;
    private static final UUID USER1_PROFILE_ID = UUID.randomUUID();
    private static final String USER1_FIRST_NAME = "John";
    private static final String USER1_LAST_NAME = "Doe";
    private static final String USER1_EMAIL = "johndoe@gmail.com";
    private static final String USER1_PHONE_NUMBER = "4385663241";
    private static final String USER1_ADDRESS = "120 Street 1";
    private static final String USER1_PROFILE_PICTURE = "img1.jpg";
    private static final LocalDate USER1_DATE_OF_BIRTH = LocalDate.of(1990, 2, 22);

    // Data for user profile
    private Profile user2Profile;
    private static final UUID USER2_PROFILE_ID = UUID.randomUUID();
    private static final String USER2_FIRST_NAME = "Bruce";
    private static final String USER2_LAST_NAME = "Jil";
    private static final String USER2_EMAIL = "bruce_j@gmail.com";
    private static final String USER2_PHONE_NUMBER = "4388665551";
    private static final String USER2_ADDRESS = "11 Street 17";
    private static final String USER2_PROFILE_PICTURE = "img2.jpg";
    private static final LocalDate USER2_DATE_OF_BIRTH = LocalDate.of(2000, 4, 30);


    @BeforeEach
    public void setMockOutput() {

        lenient().when(userRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER1_ID)) {
                user1Profile = new Profile();
                user1Profile.setId(USER1_PROFILE_ID);
                user1Profile.setFirstName(USER1_FIRST_NAME);
                user1Profile.setLastName(USER1_LAST_NAME);
                user1Profile.setEmail(USER1_EMAIL);
                user1Profile.setPhoneNumber(USER1_PHONE_NUMBER);
                user1Profile.setAddress(USER1_ADDRESS);
                user1Profile.setProfilePicture(USER1_PROFILE_PICTURE);
                user1Profile.setDateOfBirth(USER1_DATE_OF_BIRTH);
                user1Profile.setInterests(new ArrayList<>());

                user1 = new User();
                user1.setId(USER1_ID);
                user1.setUsername(USER1_USERNAME);
                user1.setPassword(USER1_PASSWORD);
                user1.setCreated(USER1_CREATED);
                user1.setProfile(user1Profile);

                return Optional.of(user1);
            } else if (invocation.getArgument(0).equals(USER2_ID)) {
                user2Profile = new Profile();
                user2Profile.setId(USER2_PROFILE_ID);
                user2Profile.setFirstName(USER2_FIRST_NAME);
                user2Profile.setLastName(USER2_LAST_NAME);
                user2Profile.setEmail(USER2_EMAIL);
                user2Profile.setPhoneNumber(USER2_PHONE_NUMBER);
                user2Profile.setAddress(USER2_ADDRESS);
                user2Profile.setProfilePicture(USER2_PROFILE_PICTURE);
                user2Profile.setDateOfBirth(USER2_DATE_OF_BIRTH);
                user2Profile.setInterests(new ArrayList<>());

                user2 = new User();
                user2.setId(USER2_ID);
                user2.setUsername(USER2_USERNAME);
                user2.setPassword(USER2_PASSWORD);
                user2.setCreated(USER2_CREATED);

                return Optional.of(user2);
            } else {
                return Optional.empty();
            }
        });

        lenient().when(userRepository.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER1_USERNAME)) {
                User user1 = userService.getUser(USER1_ID);
                return Optional.of(user1);
            } else if (invocation.getArgument(0).equals(USER2_USERNAME)) {
                User user2 = userService.getUser(USER2_ID);
                return Optional.of(user2);
            } else {
                return Optional.empty();
            }
        });

        lenient().doNothing().when(userRepository).delete(any(User.class));
    }


    @Test
    public void deleteUserSuccess() {
        boolean success = false;
        try {
            success = userService.deleteUserByUsername(USER1_USERNAME, USER1_PASSWORD);
        }catch(IllegalArgumentException e) {
            fail();
        }
        assertTrue(success);
    }

    @Test
    public void deleteUserUserNotFound() {
        String username = "lea_jillina";
        String password = "Lea12345";
        try {
            userService.deleteUserByUsername(username,password);
        }catch(IllegalArgumentException e) {
            assertEquals("User " + username + " not found.", e.getMessage());
        }
    }

    @Test
    public void deleteUserUserIncorrectPassword() {
        String password = "assword211";
        try {
            userService.deleteUserByUsername(USER1_USERNAME,password);
        }catch(IllegalArgumentException e) {
            assertEquals("The account cannot be deleted with an incorrect password", e.getMessage());
        }
    }


}