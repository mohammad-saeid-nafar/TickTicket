package tickticket.acceptance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import tickticket.controller.Conversion;
import tickticket.dao.*;
import tickticket.model.*;
import tickticket.service.*;

@ExtendWith(MockitoExtension.class)
public class ID004DeleteAUserTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private EventService eventService;

    // Data for oganizer
    private User organizer;
    private static final UUID ORGANIZER_ID = UUID.randomUUID();
    private static final String ORGANIZER_USERNAME = "johnDoe";
    private static final String ORGANIZER_PASSWORD = "myP@assword1";
    private static final LocalDate ORGANIZER_CREATED = LocalDate.of(2022, 07, 12);

    // Data for user
    private User user;
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_USERNAME = "bruceJ2";
    private static final String USER_PASSWORD = "BrUcE_@214";
    private static final LocalDate USER_CREATED = LocalDate.of(2022, 10, 03);

    // Data for organizer profile
    private Profile organizerProfile;
    private static final UUID ORGANIZER_PROFILE_ID = UUID.randomUUID();
    private static final String ORGANIZER_FIRST_NAME = "John";
    private static final String ORGANIZER_LAST_NAME = "Doe";
    private static final String ORGANIZER_EMAIL = "johndoe@gmail.com";
    private static final String ORGANIZER_PHONE_NUMBER = "4385663241";
    private static final String ORGANIZER_ADDRESS = "120 Street 1";
    private static final String ORGANIZER_PROFILE_PICTURE = "img1.jpg";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1990, 02, 22);

    // Data for user profile
    private Profile userProfile;
    private static final UUID USER_PROFILE_ID = UUID.randomUUID();
    private static final String USER_FIRST_NAME = "Bruce";
    private static final String USER_LAST_NAME = "Jil";
    private static final String USER_EMAIL = "bruce_j@gmail.com";
    private static final String USER_PHONE_NUMBER = "4388665551";
    private static final String USER_ADDRESS = "11 Street 17";
    private static final String USER_PROFILE_PICTURE = "img2.jpg";
    private static final LocalDate USER_DATE_OF_BIRTH = LocalDate.of(2000, 04, 30);

    // Data for event type
    private EventType eventType;
    private static final UUID EVENT_TYPE_ID = UUID.randomUUID();
    private static final String EVENT_TYPE_NAME = "Pop Music";
    private static final String EVENT_TYPE_DESCRIPTION = "Music";

    // Data for Event
    private Event event;
    private static final UUID EVENT_ID = UUID.randomUUID();
    private static final String EVENT_NAME = "Justin Bieber Tour";
    private static final String EVENT_DESCRIPTION = "Music Tour";
    private static final int EVENT_CAPACITY = 600;
    private static final double EVENT_COST = 250.00;
    private static final String EVENT_ADDRESS = "True Square";
    private static final String EVENT_EMAIL = ORGANIZER_EMAIL;
    private static final String EVENT_PHONE_NUMBER = ORGANIZER_PHONE_NUMBER;

    // Data for Event Schedule
    private EventSchedule eventSchedule;
    private static final UUID EVENT_SCHEDULE_ID = UUID.randomUUID();
    private static final LocalDateTime EVENT_START = LocalDateTime.of(2022, 10, 15, 19, 00);
    private static final LocalDateTime EVENT_END = LocalDateTime.of(2022, 10, 15, 22, 00);

    // Data for Ticket 1
    private Ticket ticket1;
    private static final UUID TICKET1_ID = UUID.randomUUID();
    private static final LocalDateTime BOOKING_DATE_1 = LocalDateTime.of(2022, 10, 05, 19, 00);

    // Data for Ticket 2
    private Ticket ticket2;
    private static final UUID TICKET2_ID = UUID.randomUUID();
    private static final LocalDateTime BOOKING_DATE_2 = LocalDateTime.of(2022, 10, 06, 20, 00);

    @BeforeEach
    public void setMockOutput() {

        eventType = new EventType();
        eventType.setId(EVENT_TYPE_ID);
        eventType.setName(EVENT_TYPE_NAME);
        eventType.setDescription(EVENT_TYPE_DESCRIPTION);

        lenient().when(eventTypeRepository.findEventTypeByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_TYPE_NAME)) {
                return Optional.of(eventType);
            } else {
                return Optional.empty();
            }
        });

        lenient().when(profileRepository.findProfileByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ORGANIZER_EMAIL)) {
                List<EventType> interests = new ArrayList<>();
                interests.add(eventType);

                organizerProfile = new Profile();
                organizerProfile.setId(ORGANIZER_PROFILE_ID);
                organizerProfile.setFirstName(ORGANIZER_FIRST_NAME);
                organizerProfile.setLastName(ORGANIZER_LAST_NAME);
                organizerProfile.setEmail(ORGANIZER_EMAIL);
                organizerProfile.setPhoneNumber(ORGANIZER_PHONE_NUMBER);
                organizerProfile.setAddress(ORGANIZER_ADDRESS);
                organizerProfile.setProfilePicture(ORGANIZER_PROFILE_PICTURE);
                organizerProfile.setDateOfBirth(DATE_OF_BIRTH);
                organizerProfile.setInterests(interests);

                return Optional.of(organizerProfile);

            } else if (invocation.getArgument(0).equals(USER_EMAIL)) {
                List<EventType> interests = new ArrayList<>();
                interests.add(eventType);

                userProfile = new Profile();
                userProfile.setId(USER_PROFILE_ID);
                userProfile.setFirstName(USER_FIRST_NAME);
                userProfile.setLastName(USER_LAST_NAME);
                userProfile.setEmail(USER_EMAIL);
                userProfile.setPhoneNumber(USER_PHONE_NUMBER);
                userProfile.setAddress(USER_ADDRESS);
                userProfile.setProfilePicture(USER_PROFILE_PICTURE);
                userProfile.setDateOfBirth(USER_DATE_OF_BIRTH);
                userProfile.setInterests(new ArrayList<>());

                return Optional.of(userProfile);
            } else {
                return Optional.empty();
            }
        });

        lenient().when(userRepository.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER_USERNAME)) {
                Profile profile = profileRepository.findProfileByEmail(USER_EMAIL).orElse(null);
                User user = new User();
                user.setId(USER_ID);
                user.setUsername(USER_USERNAME);
                user.setPassword(USER_PASSWORD);
                user.setCreated(LocalDate.now());
                user.setProfile(profile);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        });

        lenient().when(userRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER_ID)) {
                return userRepository.findUserByUsername(USER_USERNAME);
            } else {
                return Optional.empty();
            }
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);

        lenient().when(userRepository.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(profileRepository.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
        lenient().doNothing().when(userRepository).delete(any(User.class));
    }
}