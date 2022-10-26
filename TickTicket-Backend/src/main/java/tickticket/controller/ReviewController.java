package tickticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tickticket.dto.ReviewDTO;
import tickticket.model.Event;
import tickticket.model.Review;
import tickticket.model.User;
import tickticket.service.EventService;
import tickticket.service.ReviewService;
import tickticket.service.UserService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @PostMapping(value = {"/create_review/"})
    public ResponseEntity<?> createReview(@RequestParam("eventId") UUID eventId, @RequestParam("userId") UUID userId,
                                          @RequestParam("title") String title, @RequestParam("rating") int rating,
                                          @RequestParam("description") String description ) {

        Review review;
        try {
            review = reviewService.createReview(eventService.getEvent(eventId), userService.getUser(userId), title, description, rating);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Conversion.convertToDTO(review), HttpStatus.CREATED);
    }

    @PatchMapping(value = {"/edit_review/{id}"})
    public ResponseEntity<?> editReview(@PathVariable("id") UUID id,
                                        @RequestParam("newTitle") String newTitle, @RequestParam("newRating") int newRating,
                                        @RequestParam("newDescription") String newDescription) {


        Review review;
        try {
            review = reviewService.editReview(id, newTitle, newDescription, newRating);
        }catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Conversion.convertToDTO(review), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete_review/{id}"})
    public boolean deleteReview(@PathVariable("id") UUID id) {
        return reviewService.deleteReview(id);
    }

    @GetMapping(value = {"/view_all_reviews"})
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews().stream().map(Conversion::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping(value = {"/view_reviews_for_event/{id}"})
    public List<ReviewDTO> viewReviewsForService(@PathVariable("id") UUID id) {
        Event event = eventService.getEvent(id);
        return reviewService.viewReviewsOfEvent(event).stream().map(Conversion::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping(value = {"/view_reviews_of_user/{id}"})
    public List<ReviewDTO> viewReviewsOfUser(@PathVariable("id") UUID id) {
        User user = userService.getUser(id);
        return reviewService.viewReviewsOfUser(user).stream().map(Conversion::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping(value = {"/get_average_service_review/{id}"})
    public double getAverageEventReview(@PathVariable("id") UUID id) {
        return reviewService.getAverageEventReview(eventService.getEvent(id));
    }



}
