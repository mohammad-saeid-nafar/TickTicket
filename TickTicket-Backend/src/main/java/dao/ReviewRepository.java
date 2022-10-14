package dao;

import model.Review;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface ReviewRepository extends CrudRepository<Review, UUID> {
}