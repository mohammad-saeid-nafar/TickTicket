package tickticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tickticket.dao.EventRepository;
import tickticket.dao.UserRepository;
import tickticket.dto.ReviewDTO;
import tickticket.model.Event;
import tickticket.model.Review;
import tickticket.model.User;
import tickticket.service.ReviewService;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping(value = {"/create_review/"})
    public ResponseEntity<?> createReview(@RequestParam("eventName") String eventName, @RequestParam("username") String username,
                                          @RequestParam("title") String title, @RequestParam("rating") int rating,
                                          @RequestParam("description") String description ) {

        Review review = null;
        try {
            review = reviewService.createReview(eventName, username, title, description, rating);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Conversion.convertToDTO(review), HttpStatus.CREATED);
    }

    @PatchMapping(value = {"/edit_review/"})
    public ResponseEntity<?> editReview(@RequestParam("eventName") String eventName, @RequestParam("username") String username,
                                        @RequestParam("newTitle") String newTitle, @RequestParam("newRating") int newRating,
                                        @RequestParam("newDescription") String newDescription) {

        Event event = eventRepository.findEventsByName(eventName);
        User user = userRepository.findUserByUsername(username);

        Review review = null;
        try {
            review = reviewService.editReview(event, user, newTitle, newDescription, newRating);
        }catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Conversion.convertToDTO(review), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete_review/"})
    public boolean deleteReview(@RequestParam("eventName") String eventName,
                                @RequestParam("username") String username) {

        Event event = eventRepository.findEventsByName(eventName);
        User user = userRepository.findUserByUsername(username);

        return reviewService.deleteReview(event, user);
    }

    @GetMapping(value = {"/view_all_reviews"})
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews().stream().map(Conversion::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping(value = {"/view_reviews_for_event"})
    public List<ReviewDTO> viewReviewsForService(@RequestParam("eventName") String eventName) {
        Event event = eventRepository.findEventsByName(eventName);
        return reviewService.viewReviewsOfEvent(event).stream().map(Conversion::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping(value = {"/view_reviews_of_user"})
    public List<ReviewDTO> viewReviewsOfUser(@RequestParam("username") String username) {
        User user = userRepository.findUserByUsername(username);
        return reviewService.viewReviewsOfUser(user).stream().map(Conversion::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping(value = {"/get_average_service_review"})
    public double getAverageEventReview(@RequestParam("eventName") String eventName) {
        return reviewService.getAverageEventReview(eventName);
    }



}
