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

import java.util.Objects;

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

        if(eventName == null || eventName.equals("")) {
            throw new IllegalArgumentException("Service not found");
        }

        if(username == null || username.equals("")) {
            throw new IllegalArgumentException("User not found");
        }

        if(rating < 0 || rating > 5 ) {
            throw new IllegalArgumentException("Event rating must be between 0 and 5 (inclusive)");
        }

        if(description == null) {
            throw new IllegalArgumentException("No description");
        }

        if(description.equals("")) {
            throw new IllegalArgumentException("Description must contain at least 1 character");
        }

        User user =  userRepository.findUserByUsername(username);
        if(user == null) {
            throw new IllegalArgumentException("No user found");
        }

        Event event = eventRepository.fin
        if(service == null) {
            throw new IllegalArgumentException("No service found");
        }

        Review review = reviewRepository.findReviewByAppointment(appointment);
        if(review != null) {
            throw new IllegalArgumentException("Review already exists");
        }
        review = new Review();
        review.setAppointment(appointment);
        review.setChosenService(service);
        review.setCustomer(customer);
        review.setDescription(description);
        review.setServiceRating(rating);
        reviewRepository.save(review);
        return review;
    }


}
