package tickticket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import tickticket.dao.EventRepository;
import tickticket.dao.ReviewRepository;
import tickticket.dao.UserRepository;
import tickticket.dto.ReviewDTO;
import tickticket.model.Event;
import tickticket.model.EventSchedule;
import tickticket.model.Review;
import tickticket.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.lenient;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventService eventService;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private ReviewService reviewService;

    // Review1
    private static final UUID reviewID1 = UUID.randomUUID();
    private static final String title1 = "First Review";
    private static final int rating1 = 5;
    private static final String reviewDescription1 = "Best experience";
    private static final String newTitle = "Test Review";
    private static final int newRating = 1;
    private static final String newDescription = "Test experience";

    // Review2
    private static final UUID reviewID2 = UUID.randomUUID();
    private static final String title2 = "Second Review";
    private static final int rating2 = 4;
    private static final String reviewDescription2 = "Worst experience";

    // User1
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_USERNAME = "User1";
    private static final String USER_PASSWORD = "Password123";
    private static final LocalDate USER_CREATED = LocalDate.of(2022, 10, 1);

    // User2
    private static final UUID USER_ID2 = UUID.randomUUID();
    private static final String USER_USERNAME2 = "User2";
    private static final String USER_PASSWORD2 = "Password123";
    private static final LocalDate USER_CREATED2 = LocalDate.of(2022, 10, 1);

    // Event
    private static final UUID EVENT_ID = UUID.randomUUID();
    private static final String EVENT_NAME = "event name";
    private static final String EVENT_DESCRIPTION = "event description";
    private static final int EVENT_CAPACITY = 100;
    private static final double EVENT_COST = 150.0;
    private static final String EVENT_ADDRESS = "123 test ave";
    private static final String EVENT_EMAIL = "testevent@mail.ca";
    private static final String EVENT_PHONE_NUMBER = "12345678";

    private static final LocalDateTime EVENT_START = LocalDateTime.of(2022, 10, 2, 12, 0);
    private static final LocalDateTime EVENT_END = LocalDateTime.of(2022, 10, 2, 23, 59);

    private ReviewDTO reviewDTO;

    @BeforeEach
    public void setMockOutput() {
        reviewDTO = new ReviewDTO();
        reviewDTO.setId(reviewID1);
        reviewDTO.setTitle(title1);
        reviewDTO.setRating(rating1);
        reviewDTO.setDescription(reviewDescription1);
        reviewDTO.setUserId(USER_ID2);
        reviewDTO.setEventId(EVENT_ID);

        lenient().when(userRepository.findUserByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER_USERNAME)) {
                User user = new User();
                user.setId(USER_ID);
                user.setUsername(USER_USERNAME);
                user.setPassword(USER_PASSWORD);
                user.setCreated(USER_CREATED);

                return Optional.of(user);
            }
            else if (invocation.getArgument(0).equals(USER_USERNAME2)){
                User user2 = new User();
                user2.setId(USER_ID2);
                user2.setUsername(USER_USERNAME2);
                user2.setPassword(USER_PASSWORD2);
                user2.setCreated(USER_CREATED2);
                return Optional.of(user2);
            }
            else {
                return Optional.empty();
            }
        });

        lenient().when(userService.getUser(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER_ID)) {
                User user = new User();
                user.setId(USER_ID);
                user.setUsername(USER_USERNAME);
                user.setPassword(USER_PASSWORD);
                user.setCreated(USER_CREATED);

                return user;
            }
            else if (invocation.getArgument(0).equals(USER_ID2)){
                User user2 = new User();
                user2.setId(USER_ID2);
                user2.setUsername(USER_USERNAME2);
                user2.setPassword(USER_PASSWORD2);
                user2.setCreated(USER_CREATED2);
                return user2;
            }
            else {
                return null;
            }
        });

        lenient().when(eventRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_ID)) {

                EventSchedule eventSchedule = new EventSchedule();
                eventSchedule.setStartDateTime(EVENT_START);
                eventSchedule.setEndDateTime(EVENT_END);

                User organizer = userService.getUser(USER_ID);

                Event event = new Event();
                event.setId(EVENT_ID);
                event.setName(EVENT_NAME);
                event.setDescription(EVENT_DESCRIPTION);
                event.setAddress(EVENT_ADDRESS);
                event.setEmail(EVENT_EMAIL);
                event.setPhoneNumber(EVENT_PHONE_NUMBER);
                event.setCapacity(EVENT_CAPACITY);
                event.setCost(EVENT_COST);
                event.setEventSchedule(eventSchedule);
                event.setOrganizer(organizer);

                return Optional.of(event);

            } else {
                return Optional.empty();
            }
        });

        lenient().when(eventService.getEvent(any(UUID.class))).thenAnswer((InvocationOnMock invocation)
                -> eventRepository.findById(invocation.getArgument(0)).get());

        lenient().when(reviewRepository.existsByEventAndUser(any(Event.class), any(User.class))).thenAnswer((InvocationOnMock invocation)
                -> ((Event) invocation.getArgument(0)).getName().equals(EVENT_NAME)
                && ((User) invocation.getArgument(1)).getId().equals(USER_ID));


        lenient().when(reviewRepository.findReviewsByUser(any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (((User) invocation.getArgument(0)).getId().equals(USER_ID)) {
                User user = userRepository.findUserByUsername(USER_USERNAME).orElse(null);
                Event event = eventRepository.findById(EVENT_ID).orElse(null);

                Review review1 = new Review();
                review1.setId(reviewID1);
                review1.setTitle(title1);
                review1.setDescription(reviewDescription1);
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
            if (((Event) invocation.getArgument(0)).getId().equals(EVENT_ID)) {
                User user = userRepository.findUserByUsername(USER_USERNAME).orElse(null);
                Event event = eventRepository.findById(EVENT_ID).orElse(null);

                User user2 = new User();
                user2.setId(USER_ID2);
                user2.setUsername(USER_USERNAME2);
                user2.setPassword(USER_PASSWORD2);
                user2.setCreated(USER_CREATED2);

                Review review1 = new Review();
                review1.setId(reviewID1);
                review1.setTitle(title1);
                review1.setDescription(reviewDescription1);
                review1.setRating(rating1);
                review1.setUser(user);
                review1.setEvent(event);

                Review review2 = new Review();
                review2.setId(reviewID2);
                review2.setTitle(title2);
                review2.setDescription(reviewDescription2);
                review2.setRating(rating2);
                review2.setUser(user2);
                review2.setEvent(event);

                List<Review> reviews = new ArrayList<>();
                reviews.add(review1);
                reviews.add(review2);

                return reviews;

            }else{
                return null;
            }
        });

        lenient().when(reviewRepository.findReviewByEventAndUser(any(Event.class), any(User.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (((Event) invocation.getArgument(0)).getName().equals(EVENT_NAME) && ((User) invocation.getArgument(1)).getId().equals(USER_ID)) {

                User user = userRepository.findUserByUsername(USER_USERNAME).orElse(null);
                Event event = eventRepository.findById(EVENT_ID).orElse(null);

                Review review1 = new Review();
                review1.setId(reviewID1);
                review1.setTitle(title1);
                review1.setDescription(reviewDescription1);
                review1.setRating(rating1);
                review1.setUser(user);
                review1.setEvent(event);

                return Optional.of(review1);
            }
            else {
                return Optional.empty();
            }
        });

        lenient().when(reviewRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if ((invocation.getArgument(0)).equals(reviewID1)) {

                User user = userRepository.findUserByUsername(USER_USERNAME).orElse(null);
                Event event = eventRepository.findById(EVENT_ID).orElse(null);

                Review review1 = new Review();
                review1.setId(reviewID1);
                review1.setTitle(title1);
                review1.setDescription(reviewDescription1);
                review1.setRating(rating1);
                review1.setUser(user);
                review1.setEvent(event);

                return Optional.of(review1);
            }
            else {
                return Optional.empty();
            }
        });

        lenient().when(ticketService.existsByEventAndUser(any(Event.class), any(User.class))).thenAnswer((InvocationOnMock invocation) -> true);

    }

    @Test
    public void testCreateReview(){
        Review review = null;
        when(reviewRepository.findReviewByEventAndUser(any(), any())).thenReturn(Optional.empty());
        try{
            review = reviewService.createReview(reviewDTO);
        }catch(Exception e){
            System.out.println(e.getMessage());
            fail();
        }

        assertNotNull(review);
        assertEquals(review.getEvent().getName(), EVENT_NAME);
        assertEquals(review.getEvent().getDescription(), EVENT_DESCRIPTION);
        assertEquals(review.getEvent().getAddress(), EVENT_ADDRESS);
        assertEquals(review.getEvent().getEmail(), EVENT_EMAIL);
        assertEquals(review.getEvent().getPhoneNumber(), EVENT_PHONE_NUMBER);
        assertEquals(review.getEvent().getCapacity(), EVENT_CAPACITY);
        assertEquals(review.getEvent().getCost(), EVENT_COST);
        assertEquals(review.getUser().getUsername(), USER_USERNAME2);
        assertEquals(review.getUser().getPassword(), USER_PASSWORD2);
        assertEquals(review.getUser().getCreated(), USER_CREATED2);
        assertEquals(review.getTitle(), title1);
        assertEquals(review.getDescription(), reviewDescription1);
        assertEquals(review.getRating(), rating1);
    }

    @Test
    public void testCreateReviewNullTitle(){
        reviewDTO.setTitle(null);
        try{
            reviewService.createReview(reviewDTO);
        }catch(Exception e){
            assertEquals(e.getMessage(), "Review must have a title");
        }
    }

    @Test
    public void testCreateReviewNullDescription(){
        reviewDTO.setDescription(null);
        try{
            reviewService.createReview(reviewDTO);
        }catch(Exception e){
            assertEquals(e.getMessage(), "Description must contain at least 1 character");
        }
    }

    @Test
    public void testCreateReviewInvalidRating(){
        reviewDTO.setRating(6);
        try{
            reviewService.createReview(reviewDTO);
        }catch(Exception e){
            assertEquals(e.getMessage(), "Event rating must be between 0 and 5 (inclusive)");
        }
    }

    @Test
    public void testCreateReviewBlankDescription(){
        reviewDTO.setDescription("");
        try{
            reviewService.createReview(reviewDTO);
        }catch(Exception e){
            assertEquals(e.getMessage(), "Description must contain at least 1 character");
        }
    }


    @Test
    public void testEditReview(){
        reviewDTO.setTitle(newTitle);
        reviewDTO.setDescription(newDescription);
        reviewDTO.setRating(newRating);
        reviewDTO.setUserId(null);
        Review review = null;
        try{
            review = reviewService.editReview(reviewDTO);
        }catch(Exception e){
            fail();
        }

        assertNotNull(review);
        assertEquals(review.getEvent().getName(), EVENT_NAME);
        assertEquals(review.getEvent().getDescription(), EVENT_DESCRIPTION);
        assertEquals(review.getEvent().getAddress(), EVENT_ADDRESS);
        assertEquals(review.getEvent().getEmail(), EVENT_EMAIL);
        assertEquals(review.getEvent().getPhoneNumber(), EVENT_PHONE_NUMBER);
        assertEquals(review.getEvent().getCapacity(), EVENT_CAPACITY);
        assertEquals(review.getEvent().getCost(), EVENT_COST);
        assertEquals(review.getUser().getUsername(), USER_USERNAME);
        assertEquals(review.getUser().getPassword(), USER_PASSWORD);
        assertEquals(review.getUser().getCreated(), USER_CREATED);
        assertEquals(review.getTitle(), newTitle);
        assertEquals(review.getDescription(), newDescription);
        assertEquals(review.getRating(), newRating);

    }

    @Test
    public void testDeleteReview(){
        boolean success = false;
        try{
            success = reviewService.deleteReview(reviewID1);
        }catch(Exception e){
            fail();
        }

        assertTrue(success);

    }

    @Test
    public void testViewReviewsOfUser(){
        List<Review> reviews = new ArrayList<>();

        try{
            reviews = reviewService.viewReviewsOfUser(USER_ID);
        }catch(Exception e){
            System.out.println(e.getMessage());
            fail();
        }
        System.out.println(reviews);
        assertNotNull(reviews);
        assertEquals(reviews.size(), 1);
        assertEquals(reviews.get(0).getEvent().getName(), EVENT_NAME);
        assertEquals(reviews.get(0).getEvent().getDescription(), EVENT_DESCRIPTION);
        assertEquals(reviews.get(0).getEvent().getAddress(), EVENT_ADDRESS);
        assertEquals(reviews.get(0).getEvent().getEmail(), EVENT_EMAIL);
        assertEquals(reviews.get(0).getEvent().getPhoneNumber(), EVENT_PHONE_NUMBER);
        assertEquals(reviews.get(0).getEvent().getCapacity(), EVENT_CAPACITY);
        assertEquals(reviews.get(0).getEvent().getCost(), EVENT_COST);
        assertEquals(reviews.get(0).getUser().getUsername(), USER_USERNAME);
        assertEquals(reviews.get(0).getUser().getPassword(), USER_PASSWORD);
        assertEquals(reviews.get(0).getUser().getCreated(), USER_CREATED);
        assertEquals(reviews.get(0).getDescription(), reviewDescription1);
        assertEquals(reviews.get(0).getTitle(), title1);
        assertEquals(reviews.get(0).getRating(), rating1);
    }

    @Test
    public void testViewReviewsOfEvent(){
        List<Review> reviews = new ArrayList<>();

        try{
            reviews = reviewService.viewReviewsOfEvent(EVENT_ID);
        }catch(Exception e){
            fail();
        }
        assertNotNull(reviews);
        assertEquals(reviews.size(), 2);
        assertEquals(reviews.get(0).getUser().getUsername(), USER_USERNAME);
        assertEquals(reviews.get(0).getUser().getPassword(), USER_PASSWORD);
        assertEquals(reviews.get(0).getUser().getCreated(), USER_CREATED);
        assertEquals(reviews.get(0).getDescription(), reviewDescription1);
        assertEquals(reviews.get(0).getTitle(), title1);
        assertEquals(reviews.get(0).getRating(), rating1);
        assertEquals(reviews.get(1).getUser().getUsername(), USER_USERNAME2);
        assertEquals(reviews.get(1).getUser().getPassword(), USER_PASSWORD2);
        assertEquals(reviews.get(1).getUser().getCreated(), USER_CREATED2);
        assertEquals(reviews.get(1).getDescription(), reviewDescription2);
        assertEquals(reviews.get(1).getTitle(), title2);
        assertEquals(reviews.get(1).getRating(), rating2);
    }

    @Test
    public void testGetAverageEventReview(){
        double average = 0.0;

        try{
            average = reviewService.getAverageEventReview(EVENT_ID);
        }catch(Exception e){
            fail();
        }

        assertEquals(average, 4.5);

    }

}