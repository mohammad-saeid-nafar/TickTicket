package tickticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tickticket.model.EventType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, UUID> {
    boolean existsEventTypeByName(String name);
    Optional<EventType> findEventTypeByName(String name);
}