package tickticket.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tickticket.dao.EventRepository;
import tickticket.dao.ReviewRepository;
import tickticket.dao.UserRepository;
import tickticket.model.Event;
import tickticket.model.Review;
import tickticket.model.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @Transactional
    public Review createReview(String eventName, String username, String title, String description, int rating) {

        if(eventName == null || eventName.isEmpty()) {
            throw new IllegalArgumentException("Service not found");
        }

        if(username == null || username.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

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

        User user =  userRepository.findUserByUsername(username);
        if(user == null) {
            throw new IllegalArgumentException("No user found");
        }

        Event event = eventRepository.findEventsByName(eventName);
        if(event == null) {
            throw new IllegalArgumentException("No event found");
        }

        Review review = reviewRepository.findReviewByEventAndUser(event, user);
        if(review != null) {
            throw new IllegalArgumentException("Review already exists");
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
    public Review editReview(Event event, User user, String newTitle, String newDescription, int newRating) {

        if(event == null) {
            throw new IllegalArgumentException("Event not found");
        }

        if(user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Review review = reviewRepository.findReviewByEventAndUser(event, user);

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
    public boolean deleteReview(Event event, User user) {

        if(event == null) {
            throw new IllegalArgumentException("Review not deleted. Event not found");
        }

        if(user == null) {
            throw new IllegalArgumentException("Review not deleted. User not found");
        }
        Review review = reviewRepository.findReviewByEventAndUser(event, user);
        reviewRepository.delete(review);
        return true;
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
    public double getAverageEventReview(String eventName) {
        Event event = eventRepository.findEventsByName(eventName);

        if(event == null) {
            throw new IllegalArgumentException("Event not found");
        }
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
