package dao;

import model.Profile;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface ProfileRepository extends CrudRepository<Profile, UUID> {
}