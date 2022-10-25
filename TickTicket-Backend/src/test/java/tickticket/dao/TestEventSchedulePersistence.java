package tickticket.dao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import tickticket.model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestEventSchedulePersistence {


    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventScheduleRepository eventScheduleRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @AfterEach
    public void clearDatabase() {
        reviewRepository.deleteAll();
        ticketRepository.deleteAll();
        eventRepository.deleteAll();
        userRepository.deleteAll();
        profileRepository.deleteAll();
        eventTypeRepository.deleteAll();
        eventScheduleRepository.deleteAll();
    }

    @Transactional
    @Test
    public void testPersistAndLoadEventSchedule() {
        LocalDateTime start = LocalDateTime.of(2022, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2022, 1, 1, 23, 59);

        EventSchedule eventSchedule = new EventSchedule();
        eventSchedule.setStartDateTime(start);
        eventSchedule.setEndDateTime(end);

        eventScheduleRepository.save(eventSchedule);


        eventSchedule = toList(eventScheduleRepository.findAll()).get(0);

        assertEquals(eventSchedule.getStartDateTime(), start);
        assertEquals(eventSchedule.getEndDateTime(), end);
        Hibernate.initialize(eventSchedule);
    }

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
