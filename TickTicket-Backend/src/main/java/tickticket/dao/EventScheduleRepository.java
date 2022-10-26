package tickticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tickticket.model.EventSchedule;

import java.util.UUID;

@Repository
public interface EventScheduleRepository extends JpaRepository<EventSchedule, UUID> {

}