package tickticket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import tickticket.dao.EventRepository;
import tickticket.dto.EventDTO;
import tickticket.model.Event;
import tickticket.model.User;
import tickticket.service.EventService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
public class ID009EditEventTests {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    private static final UUID ORGANIZER_ID = UUID.randomUUID();
    private static final String ORGANIZER_USERNAME = "aly1 ";
    private static final String ORGANIZER_PASSWORD = "Aly1235! ";
    private static final LocalDate ORGANIZER_CREATED = LocalDate.of(2022, 10, 1);

    private static final UUID EVENT_ID = UUID.randomUUID();
    private static final String EVENT_NAME = "event1";
    private static final String EVENT_DESCRIPTION = "description1";
    private static final int EVENT_CAPACITY = 80;
    private static final double EVENT_COST = 20;
    private static final String EVENT_ADDRESS = "123 rue Stanley";
    private static final String EVENT_EMAIL = "aly1@gmail.com";
    private static final String EVENT_PHONE_NUMBER = "514-888-8888";


    @BeforeEach
    public void setMockOutput() {
        lenient().when(eventRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_ID)) {
                User organizer = new User();
                organizer.setId(ORGANIZER_ID);
                organizer.setUsername(ORGANIZER_USERNAME);
                organizer.setPassword(ORGANIZER_PASSWORD);
                organizer.setCreated(ORGANIZER_CREATED);

                Event event = new Event();
                event.setId(EVENT_ID);
                event.setName(EVENT_NAME);
                event.setDescription(EVENT_DESCRIPTION);
                event.setAddress(EVENT_ADDRESS);
                event.setEmail(EVENT_EMAIL);
                event.setPhoneNumber(EVENT_PHONE_NUMBER);
                event.setCapacity(EVENT_CAPACITY);
                event.setCost(EVENT_COST);
                event.setOrganizer(organizer);

                return Optional.of(event);
            }else{
                return Optional.empty();
            }

        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(eventRepository.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testUpdateEvent() {

        Event event = null;
        String newName = "event2";
        String newDescription = "description2";
        String newAddress = "321 rue Stanley";
        String newEmail = "aly2@gmail.com";
        String newPhoneNumber = "514-777-7777";
        int newCapacity = 100;
        double newCost = 50;

        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(EVENT_ID);
        eventDTO.setName(newName);
        eventDTO.setDescription(newDescription);
        eventDTO.setAddress(newAddress);
        eventDTO.setEmail(newEmail);
        eventDTO.setPhoneNumber(newPhoneNumber);
        eventDTO.setCapacity(newCapacity);
        eventDTO.setCost(newCost);
        eventDTO.setEventTypeIds(null);

        try {
            event = eventService.updateEvent(eventDTO);
        } catch (Exception e) {
            fail();
        }

        assertNotNull(event);
        assertEquals(event.getId(), EVENT_ID);
        assertEquals(event.getName(), newName);
        assertEquals(event.getDescription(), newDescription);
        assertEquals(event.getCapacity(), newCapacity);
        assertEquals(event.getCost(), newCost);
        assertEquals(event.getAddress(), newAddress);
        assertEquals(event.getEmail(), newEmail);
        assertEquals(event.getPhoneNumber(), newPhoneNumber);
    }

    @Test
    public void testUpdateEventNotFound() {
        UUID id = UUID.randomUUID();
        String newName = "event2";
        String newDescription = "description2";
        String newAddress = "321 rue Stanley";
        String newEmail = "aly2@gmail.com";
        String newPhoneNumber = "514-777-7777";
        int newCapacity = 100;
        double newCost = 50;

        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(id);
        eventDTO.setName(newName);
        eventDTO.setDescription(newDescription);
        eventDTO.setAddress(newAddress);
        eventDTO.setEmail(newEmail);
        eventDTO.setPhoneNumber(newPhoneNumber);
        eventDTO.setCapacity(newCapacity);
        eventDTO.setCost(newCost);
        eventDTO.setEventTypeIds(null);

        try {
            eventService.updateEvent(eventDTO);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Event " + id + " not found");
        }

    }
}
