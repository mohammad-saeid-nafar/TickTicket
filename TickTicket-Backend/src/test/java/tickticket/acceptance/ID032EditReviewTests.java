package tickticket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import tickticket.controller.Conversion;
import tickticket.dao.ReviewRepository;
import tickticket.dto.ReviewDTO;
import tickticket.model.*;
import tickticket.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ID032EditReviewTests {

    @Mock
    private UserService userService;

    @Mock
    private EventTypeService eventTypeService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private EventService eventService;

    @InjectMocks
    private ReviewService reviewService;

    private static final UUID USER1_ID = UUID.randomUUID();
    private static final String USER1_USERNAME = "aly1";
    private static final String USER1_PASSWORD = "Aly1235!";
    private static final LocalDate USER1_CREATED = LocalDate.of(2022, 10, 1);

    private static final UUID USER2_ID = UUID.randomUUID();
    private static final String USER2_USERNAME = "aly2";
    private static final String USER2_PASSWORD = "Aly1233!";
    private static final LocalDate USER2_CREATED = LocalDate.of(2022, 10, 3);

    private static final UUID USER3_ID = UUID.randomUUID();
    private static final String USER3_USERNAME = "aly3";
    private static final String USER3_PASSWORD = "Aly1233!";
    private static final LocalDate USER3_CREATED = LocalDate.of(2022, 10, 3);

    private static final UUID EVENT1_ID = UUID.randomUUID();
    private static final String EVENT1_NAME = "Wine and Cheese";
    private static final String EVENT1_DESCRIPTION = "Graduation Wine and Cheese";
    private static final int EVENT1_CAPACITY = 80;
    private static final double EVENT1_COST = 20;
    private static final String EVENT1_ADDRESS = "2620 rue Stanley";
    private static final String EVENT1_EMAIL = "aly1@gmail.com";
    private static final String EVENT1_PHONE_NUMBER = "514-888-8888";
    private static final LocalDateTime EVENT1_START = LocalDateTime.of(2022, 10, 5, 12, 0);
    private static final LocalDateTime EVENT1_END = LocalDateTime.of(2022, 10, 5, 21, 0);

    private static final UUID EVENT2_ID = UUID.randomUUID();
    private static final String EVENT2_NAME = "Pop";
    private static final String EVENT2_DESCRIPTION = "Pop concert";
    private static final int EVENT2_CAPACITY = 150;
    private static final double EVENT2_COST = 20;
    private static final String EVENT2_ADDRESS = "321 Ave";
    private static final String EVENT2_EMAIL = "pop@mail.ca";
    private static final String EVENT2_PHONE_NUMBER = "514-888-8888";
    private static final LocalDateTime EVENT2_START = LocalDateTime.of(2022, 12, 5, 12, 0);
    private static final LocalDateTime EVENT2_END = LocalDateTime.of(2022, 12, 5, 21, 0);

    private static final UUID EVENT_TYPE1_ID = UUID.randomUUID();
    private static final String EVENT_TYPE1_NAME = "Food";

    private static final UUID EVENT_TYPE2_ID = UUID.randomUUID();
    private static final String EVENT_TYPE2_NAME = "Music";

    private static final UUID REVIEW1_ID = UUID.randomUUID();
    private static final String REVIEW1_TITLE = "Amazing";
    private static final int REVIEW1_RATING = 5;
    private static final String REVIEW1_DESCRIPTION = "The quality of the wine was amazing";

    private static final UUID REVIEW2_ID = UUID.randomUUID();
    private static final String REVIEW2_TITLE = "Good";
    private static final int REVIEW2_RATING = 3;
    private static final String REVIEW2_DESCRIPTION = "Wine was good but cheese not as much";

    @BeforeEach
    public void setMockOutput() {

        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER1_ID)) {
                User user = new User();
                user.setId(USER1_ID);
                user.setUsername(USER1_USERNAME);
                user.setPassword(USER1_PASSWORD);
                user.setCreated(USER1_CREATED);
                return user;
            } else if (invocation.getArgument(0).equals(USER2_ID)) {
                User user = new User();
                user.setId(USER2_ID);
                user.setUsername(USER2_USERNAME);
                user.setPassword(USER2_PASSWORD);
                user.setCreated(USER2_CREATED);
                return user;
            } else if (invocation.getArgument(0).equals(USER3_ID)) {
                User user = new User();
                user.setId(USER3_ID);
                user.setUsername(USER3_USERNAME);
                user.setPassword(USER3_PASSWORD);
                user.setCreated(USER3_CREATED);
                return user;
            } else {
                return null;
            }
        });

        lenient().when(eventTypeService.getEventType(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument((0)).equals(EVENT_TYPE1_ID)) {
                EventType eventType = new EventType();
                eventType.setId(EVENT_TYPE1_ID);
                eventType.setName(EVENT_TYPE1_NAME);
                return eventType;
            } else if (invocation.getArgument((0)).equals(EVENT_TYPE2_ID)) {
                EventType eventType = new EventType();
                eventType.setId(EVENT_TYPE2_ID);
                eventType.setName(EVENT_TYPE2_NAME);
                return eventType;
            } else {
                return null;
            }
        });

        lenient().when(eventService.getEvent(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT1_ID)) {
                EventSchedule eventSchedule = new EventSchedule();
                eventSchedule.setStartDateTime(EVENT1_START);
                eventSchedule.setEndDateTime(EVENT1_END);

                Event event = new Event();
                event.setId(EVENT1_ID);
                event.setName(EVENT1_NAME);
                event.setDescription(EVENT1_DESCRIPTION);
                event.setAddress(EVENT1_ADDRESS);
                event.setEmail(EVENT1_EMAIL);
                event.setPhoneNumber(EVENT1_PHONE_NUMBER);
                event.setCapacity(EVENT1_CAPACITY);
                event.setCost(EVENT1_COST);
                event.setOrganizer(userService.getUser(USER1_ID));
                event.setEventSchedule(eventSchedule);
                event.setEventTypes(Collections.singletonList(eventTypeService.getEventType(EVENT_TYPE1_ID)));
                return event;

            } else if (invocation.getArgument(0).equals(EVENT2_ID)) {
                EventSchedule eventSchedule = new EventSchedule();
                eventSchedule.setStartDateTime(EVENT2_START);
                eventSchedule.setEndDateTime(EVENT2_END);

                Event event = new Event();
                event.setId(EVENT2_ID);
                event.setName(EVENT2_NAME);
                event.setDescription(EVENT2_DESCRIPTION);
                event.setAddress(EVENT2_ADDRESS);
                event.setEmail(EVENT2_EMAIL);
                event.setPhoneNumber(EVENT2_PHONE_NUMBER);
                event.setCapacity(EVENT2_CAPACITY);
                event.setCost(EVENT2_COST);
                event.setOrganizer(userService.getUser(USER2_ID));
                event.setEventSchedule(eventSchedule);
                event.setEventTypes(Collections.singletonList(eventTypeService.getEventType(EVENT_TYPE2_ID)));

                return event;
            } else {
                return null;
            }
        });

        lenient().when(reviewRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(REVIEW1_ID)) {
                Review review1 = new Review();
                review1.setId(REVIEW1_ID);
                review1.setTitle(REVIEW1_TITLE);
                review1.setRating(REVIEW1_RATING);
                review1.setDescription(REVIEW1_DESCRIPTION);
                review1.setEvent(eventService.getEvent(EVENT1_ID));
                review1.setUser(userService.getUser(USER2_ID));

                return Optional.of(review1);
            }
            else if(invocation.getArgument(0).equals(REVIEW2_ID)) {
                Review review2 = new Review();
                review2.setId(REVIEW2_ID);
                review2.setTitle(REVIEW2_TITLE);
                review2.setRating(REVIEW2_RATING);
                review2.setDescription(REVIEW2_DESCRIPTION);
                review2.setEvent(eventService.getEvent(EVENT2_ID));
                review2.setUser(userService.getUser(USER3_ID));

                return Optional.of(review2);
            } else {
                return Optional.empty();
            }

        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(reviewRepository.save(any(Review.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void editReviewSuccess(){
        ReviewDTO reviewDTO = Conversion.convertToDTO(reviewRepository.findById(REVIEW1_ID).get());
        reviewDTO.setTitle("Great");
        reviewDTO.setRating(4);
        reviewDTO.setDescription("Wine was great");

        try{
            Review review = reviewService.editReview(reviewDTO);
            assertEquals(reviewDTO.getTitle(), review.getTitle());
            assertEquals(reviewDTO.getRating(), review.getRating());
            assertEquals(reviewDTO.getDescription(), review.getDescription());
            assertEquals(reviewDTO.getEvent().getId(), review.getEvent().getId());
            assertEquals(reviewDTO.getUser().getId(), review.getUser().getId());
        }catch (IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void editReviewWrongUser(){
        ReviewDTO reviewDTO = Conversion.convertToDTO(reviewRepository.findById(REVIEW2_ID).get());
        reviewDTO.setTitle("Great");
        reviewDTO.setRating(4);
        reviewDTO.setDescription("Wine was great");
        reviewDTO.setUserId(USER2_ID);

        try{
            reviewService.editReview(reviewDTO);
        }catch (IllegalArgumentException e){
            assertEquals("You cannot edit another user's review", e.getMessage());
        }
    }

    @Test
    public void editNonExistentReview(){
        UUID id = UUID.randomUUID();
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(id);
        try{
            reviewService.editReview(reviewDTO);
        }catch (IllegalArgumentException e){
            assertEquals("Review " + id + " not found", e.getMessage());
        }
    }
}
