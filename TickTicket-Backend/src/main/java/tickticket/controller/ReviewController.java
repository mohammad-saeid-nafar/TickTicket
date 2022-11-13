package tickticket.controller;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO) {

        Review review;
        try {
            review = reviewService.createReview(reviewDTO);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Conversion.convertToDTO(review), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> editReview(@RequestBody ReviewDTO reviewDTO) {


        Review review;
        try {
            review = reviewService.editReview(reviewDTO);
        }catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Conversion.convertToDTO(review), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public boolean deleteReview(@PathVariable("id") UUID id) {
        return reviewService.deleteReview(id);
    }

    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews().stream()
                .map(Conversion::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = {"/event/{id}"})
    public List<ReviewDTO> viewReviewsForService(@PathVariable("id") UUID id) {
        return reviewService.viewReviewsOfEvent(id).stream()
                .map(Conversion::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = {"/user/{id}"})
    public List<ReviewDTO> viewReviewsOfUser(@PathVariable("id") UUID id) {
        return reviewService.viewReviewsOfUser(id).stream()
                .map(Conversion::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = {"/event/{id}/average"})
    public double getAverageEventReview(@PathVariable("id") UUID id) {
        return reviewService.getAverageEventReview(id);
    }



}
