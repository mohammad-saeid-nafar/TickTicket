package tickticket.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tickticket.dao.ReviewRepository;
import tickticket.model.Event;
import tickticket.model.Review;
import tickticket.model.User;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Transactional
    public Review createReview(Event event, User user, String title, String description, int rating) {


        if(title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Rating must have a title");
        }

        if(rating < 0 || rating > 5 ) {
            throw new IllegalArgumentException("Event rating must be between 0 and 5 (inclusive)");
        }

        if(description == null) {
            throw new IllegalArgumentException("No description");
        }

        if(description.isEmpty()) {
            throw new IllegalArgumentException("Description must contain at least 1 character");
        }


        Review review = reviewRepository.findReviewByEventAndUser(event, user).orElse(null);
        if(review != null) {
            throw new IllegalArgumentException("Review already exists");
        }

        if(event.getEventSchedule().getStartDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot create a review for a future event");
        }
        review = new Review();
        review.setEvent(event);
        review.setUser(user);
        review.setRating(rating);
        review.setTitle(title);
        review.setDescription(description);
        reviewRepository.save(review);
        return review;
    }

    @Transactional
    public Review editReview(UUID id, String newTitle, String newDescription, int newRating) {


        Review review = getReviewById(id);

        if (newTitle.isEmpty()) {
            throw new IllegalArgumentException("New title must contain at least 1 character");
        }

        if(newRating < 0 || newRating > 5) {
            throw new IllegalArgumentException("Event rating must be between 0 and 5 (inclusive)");
        }

        if(newDescription.isEmpty()) {
            throw new IllegalArgumentException("New description must contain at least 1 character");
        }

        review.setTitle(newTitle);
        review.setDescription(newDescription);
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
    public List<Review> viewReviewsOfUser(User user) {
        return reviewRepository.findReviewsByUser(user);
    }

    @Transactional
    public List<Review> viewReviewsOfEvent(Event event) {
        return reviewRepository.findReviewsByEvent(event);
    }

    @Transactional
    public double getAverageEventReview(Event event) {

        List<Review> reviewsForEvent = viewReviewsOfEvent(event);
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
        return toList(reviewRepository.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }


}
