package tickticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tickticket.model.Event;
import tickticket.model.Review;
import tickticket.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    boolean existsByEventAndUser(Event event, User user);
    List<Review> findReviewsByUser(User user);
    List<Review> findReviewsByEvent(Event event);
    Optional<Review> findReviewByEventAndUser(Event event, User user);
}