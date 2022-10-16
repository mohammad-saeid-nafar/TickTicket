package tickticket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tickticket.model.Review;

import java.util.UUID;

@Repository
public interface ReviewRepository extends CrudRepository<Review, UUID> {
}