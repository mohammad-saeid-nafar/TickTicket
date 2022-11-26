package tickticket.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tickticket.dao.ReviewRepository;
import tickticket.dto.ReviewDTO;
import tickticket.model.Event;
import tickticket.model.Review;
import tickticket.model.User;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;
    private UserService userService;
    private EventService eventService;
    private TicketService ticketService;

    @Transactional
    public Review createReview(ReviewDTO reviewDTO) {
        String title = reviewDTO.getTitle();
        int rating = reviewDTO.getRating();
        String description = reviewDTO.getDescription();

        User user = userService.getUser(reviewDTO.getUserId());
        Event event = eventService.getEvent(reviewDTO.getEventId());

        if(event == null) throw new IllegalArgumentException("Event "+reviewDTO.getEvent().getId()+" not found");

        if(!ticketService.existsByEventAndUser(event, user)){
            throw new IllegalArgumentException("You did not buy a ticket for this event");
        }

        if(title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Review must have a title");
        }

        if(rating < 0 || rating > 5 ) {
            throw new IllegalArgumentException("Event rating must be between 0 and 5 (inclusive)");
        }

        if(description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description must contain at least 1 character");
        }

        Review review = reviewRepository.findReviewByEventAndUser(event, user).orElse(null);
        if(review != null) {
            throw new IllegalArgumentException("Review already exists");
        }

        if(user.getId().equals(event.getOrganizer().getId())){
            throw new IllegalArgumentException("You cannot review an event you have organized");
        }

        if(event.getEventSchedule().getStartDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot create a review for a future event");
        }
        review = new Review();
        System.out.println("review created");
        review.setEvent(event);
        review.setUser(user);
        review.setRating(rating);
        review.setTitle(title);
        review.setDescription(description);
        reviewRepository.save(review);
        return review;
    }

    @Transactional
    public Review editReview(ReviewDTO reviewDTO) {
        UUID id = reviewDTO.getId();
        String newTitle = reviewDTO.getTitle();
        int newRating = reviewDTO.getRating();
        String newDescription = reviewDTO.getDescription();

        Review review = getReviewById(id);

        if(reviewDTO.getUserId() != null && !review.getUser().getId().equals(reviewDTO.getUserId())) {
            throw new IllegalArgumentException("You cannot edit another user's review");
        }

        if (newTitle!= null && !newTitle.isEmpty()) {
            review.setTitle(newTitle);
        }

        if(newDescription!= null && !newDescription.isEmpty()) {
            review.setDescription(newDescription);
        }

        if(newRating < 0 || newRating > 5) {
            throw new IllegalArgumentException("Event rating must be between 0 and 5 (inclusive)");
        }

        review.setRating(newRating);
        reviewRepository.save(review);
        return review;
    }

    @Transactional
    public boolean deleteReview(UUID id) {
        reviewRepository.delete(getReviewById(id));
        return true;
    }

    public Review getReviewById(UUID id){
        return reviewRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Review " + id + " not found"));
    }

    @Transactional
    public List<Review> viewReviewsOfUser(UUID userId) {
        User user = userService.getUser(userId);
        return reviewRepository.findReviewsByUser(user);
    }

    @Transactional
    public List<Review> viewReviewsOfEvent(UUID eventId) {
        Event event = eventService.getEvent(eventId);
        return reviewRepository.findReviewsByEvent(event);
    }

    @Transactional
    public double getAverageEventReview(UUID eventId) {
        List<Review> reviewsForEvent = viewReviewsOfEvent(eventId);
        int totalEventRating = 0;
        for(Review review : reviewsForEvent) {
            totalEventRating = totalEventRating + review.getRating();
        }
        double averageEventRating = (double)totalEventRating/(double)reviewsForEvent.size();
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return Double.parseDouble(numberFormat.format(averageEventRating));
    }

    @Transactional
    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }
}
