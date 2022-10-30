package tickticket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import tickticket.controller.Conversion;
import tickticket.dao.EventTypeRepository;
import tickticket.dao.ProfileRepository;
import tickticket.dao.TicketRepository;
import tickticket.dao.UserRepository;
import tickticket.dto.ProfileDTO;
import tickticket.dto.UserDTO;
import tickticket.model.*;
import tickticket.service.EventService;
import tickticket.service.EventTypeService;
import tickticket.service.ProfileService;
import tickticket.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

public class ID005UpdateUserPasswordTests {

    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    private static final UUID ORGANIZER_ID = UUID.randomUUID();
    private static final String ORGANIZER_USERNAME = "Organizer";
    private static final String ORGANIZER_PASSWORD = "Password123";
    private static final LocalDate ORGANIZER_CREATED = LocalDate.of(2022, 9, 1);

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_USERNAME = "user1";
    private static final String USER_PASSWORD = "Password1";
    private static final LocalDate USER_CREATED = LocalDate.of(2022, 1, 1);

    @BeforeEach
    public void setMockOutput() {

        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
                if(invocation.getArgument(0).equals(ORGANIZER_ID)){
                User organizer = new User();
                organizer.setId(ORGANIZER_ID);
                organizer.setUsername(ORGANIZER_USERNAME);
                organizer.setPassword(ORGANIZER_PASSWORD);
                organizer.setCreated(ORGANIZER_CREATED);

                return organizer;
            }else if(invocation.getArgument(0).equals(USER_ID)){
                User user = new User();
                user.setId(USER_ID);
                user.setUsername(USER_USERNAME);
                user.setPassword(USER_PASSWORD);
                user.setCreated(USER_CREATED);

                return user;
            }else{
                return null;
            }

        });
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
        assertNotEquals(user.getCreated(), updatedUser.getCreated());
        assertEquals(newPassword, updatedUser.getPassword());

    }

    @Test
    public void updateProfileFail1() {

        String error = "";

        Optional<User> userOption = userRepository.findById(USER_ID);
        User user = userOption.get();
        UserDTO userDTO = Conversion.convertToDTO(user);
        try {
            User updatedUser = userService.editUserPassword(USER_ID, USER_PASSWORD, USER_PASSWORD);
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
