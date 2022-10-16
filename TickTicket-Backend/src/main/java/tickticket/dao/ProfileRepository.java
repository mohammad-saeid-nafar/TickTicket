package tickticket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tickticket.model.Event;
import tickticket.model.Profile;
import tickticket.model.Review;
import tickticket.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, UUID> {
    boolean existsByEmail(String email );
    Profile findProfileByEmail(String email);

}