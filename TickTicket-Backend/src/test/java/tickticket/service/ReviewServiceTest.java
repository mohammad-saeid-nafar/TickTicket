package tickticket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import tickticket.dao.EventRepository;
import tickticket.dao.ReviewRepository;
import tickticket.dao.UserRepository;
import tickticket.model.Event;
import tickticket.model.Review;
import tickticket.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    // Review1
    private static final UUID reviewID1 = UUID.randomUUID();
    private static final String title1 = "First Review";
    private static final int rating1 = 5;
    private static final String reviwDescription1 = "Best experience";

    // Review2
    private static final UUID reviewID2 = UUID.randomUUID();
    private static final String title2 = "Second Review";
    private static final int rating2 = 4;
    private static final String reviwDescription2 = "Worst experience";

    // User
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_USERNAME = "Organizer";
    private static final String USER_PASSWORD = "Password123";
    private static final LocalDate USER_CREATED = LocalDate.of(2022, 10, 1);

    // Event
    private static final String EVENT_NAME = "event name";
    private static final String EVENT_DESCRIPTION = "event description";
    private static final int EVENT_CAPACITY = 100;
    private static final double EVENT_COST = 150.0;
    private static final String EVENT_ADDRESS = "123 test ave";
    private static final String EVENT_EMAIL = "testevent@mail.ca";
    private static final String EVENT_PHONE_NUMBER = "12345678";


    @BeforeEach
    public void setMockOutput() {

        lenient().when(reviewRepository.existsByEventAndUser(any(Event.class), any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (((Event) invocation.getArgument(0)).getName().equals(EVENT_NAME) && ((User) invocation.getArgument(1)).getId().equals(USER_ID)) {

                User user = new User();
                user.setId(USER_ID);
                user.setUsername(USER_USERNAME);
                user.setPassword(USER_PASSWORD);
                user.setCreated(USER_CREATED);

                Event event = new Event();
                event.setName(EVENT_NAME);
                event.setDescription(EVENT_DESCRIPTION);
                event.setAddress(EVENT_ADDRESS);
                event.setEmail(EVENT_EMAIL);
                event.setPhoneNumber(EVENT_PHONE_NUMBER);
                event.setCapacity(EVENT_CAPACITY);
                event.setCost(EVENT_COST);

                Review review1 = new Review();
                review1.setId(reviewID1);
                review1.setTitle(title1);
                review1.setDescription(reviwDescription1);
                review1.setRating(rating1);
                review1.setUser(user);
                review1.setEvent(event);

                return true;
            }
            else {
                return false;
            }
        });
        
        lenient().when(reviewRepository.findReviewsByUser(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (((User) invocation.getArgument(0)).getId().equals(USER_ID)) {
                User user = userRepository.findUserByUsername(USER_USERNAME);
                Event event = eventRepository.findEventsByName(EVENT_NAME);

                Review review1 = new Review();
                review1.setId(reviewID1);
                review1.setTitle(title1);
                review1.setDescription(reviwDescription1);
                review1.setRating(rating1);
                review1.setUser(user);
                review1.setEvent(event);

                List<Review> reviews = new ArrayList<>();
                reviews.add(review1);

                return reviews;

            }else{
                return null;
            }
        });

        lenient().when(reviewRepository.findReviewsByEvent(any(Event.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (((Event) invocation.getArgument(0)).getName().equals(EVENT_NAME)) {
                User user = userRepository.findUserByUsername(USER_USERNAME);
                Event event = eventRepository.findEventsByName(EVENT_NAME);

                Review review1 = new Review();
                review1.setId(reviewID1);
                review1.setTitle(title1);
                review1.setDescription(reviwDescription1);
                review1.setRating(rating1);
                review1.setUser(user);
                review1.setEvent(event);
                
                List<Review> reviews = new ArrayList<>();
                reviews.add(review1);

                return reviews;

            }else{
                return null;
            }
        });

    }

}