package tickticket.dao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import tickticket.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestEventSchedulePersistence {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private EventScheduleRepository eventScheduleRepository;

        @BeforeEach
    public void clearDatabase() {
        eventScheduleRepository.deleteAll();
    }

    @Transactional
    @Test
    public void testPersistAndLoadEventSchedule() {
        LocalDateTime start = LocalDateTime.of(2022, 01, 01, 12, 0);
        LocalDateTime end = LocalDateTime.of(2022, 01, 01, 23, 59);
        List<EventSchedule.DayOfWeek> recurrences = new ArrayList<>();
        recurrences.add(EventSchedule.DayOfWeek.Monday);
        recurrences.add(EventSchedule.DayOfWeek.Tuesday);
        recurrences.add(EventSchedule.DayOfWeek.Wednesday);
        recurrences.add(EventSchedule.DayOfWeek.Thursday);
        recurrences.add(EventSchedule.DayOfWeek.Friday);

        EventSchedule eventSchedule = new EventSchedule();
        eventSchedule.setStartDateTime(start);
        eventSchedule.setEndDateTime(end);
        eventSchedule.setRecurrences(recurrences);
        eventSchedule.setRecurrent(true);

        eventScheduleRepository.save(eventSchedule);

        eventSchedule = null;

        eventSchedule = toList(eventScheduleRepository.findAll()).get(0);

        assertEquals(eventSchedule.getStartDateTime(), start);
        assertEquals(eventSchedule.getEndDateTime(), end);
        assertEquals(eventSchedule.isRecurrent(), true);
        Hibernate.initialize(eventSchedule);
        for(int i=0; i<recurrences.size(); i++){
            assertEquals(eventSchedule.getRecurrences().get(i), recurrences.get(i));
        }
    }

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
