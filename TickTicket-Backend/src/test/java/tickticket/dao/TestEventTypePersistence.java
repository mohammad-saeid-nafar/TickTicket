package tickticket.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tickticket.model.EventType;
import tickticket.model.Profile;
import tickticket.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestEventTypePersistence {

    EntityManager entityManager;

    @Autowired
    private EventTypeRepository eventTypeRepository;

//    @Autowired
//    private ProfileRepository profileRepository;
//
//    @Autowired
//    private UserRepository userRepository;

    @BeforeEach
    public void clearDatabase() {
//        userRepository.deleteAll();
//        profileRepository.deleteAll();
        eventTypeRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadEventType(){
        EventType testEventType = new EventType();
        testEventType.setName("Test Type");
        testEventType.setDescription("Persistence Test!");
        testEventType.setAgeRequirement(13);

        eventTypeRepository.save(testEventType);

//        testUser = null;
//        testEventType = null;

//        boolean exists = userRepository.existsUserByUsername(username);
        boolean exists = eventTypeRepository.existsById(testEventType.getId());
//        testEventType = eventTypeRepository.findById(testEventType.getId());
//        testUser = userRepository.findUserByUsername(username);

//        assertNotNull(testUser);
        assertEquals(exists, true);
        assertEquals(testEventType.getDescription(), "Persistence Test!");
        assertEquals(testEventType.getName(), "Test Type");
        assertEquals(testEventType.getAgeRequirement(), 13);

    }

}
