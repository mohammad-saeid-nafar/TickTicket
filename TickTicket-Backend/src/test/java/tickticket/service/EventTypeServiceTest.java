package tickticket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import tickticket.dao.EventTypeRepository;
import tickticket.model.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class EventTypeServiceTest {

    @Mock
    private EventTypeRepository eventTypeRepository;

    private static final UUID EVENT_TYPE1_ID = UUID.randomUUID();
    private static final String EVENT_TYPE1_NAME = "Party";
    private static final String EVENT_TYPE1_DESCRIPTION = "Fun & Dancing";
    private static final int EVENT_TYPE1_AGE = 18;

    @InjectMocks
    private EventTypeService eventTypeService;

    @BeforeEach
    public void setMockOutput() {

        lenient().when(eventTypeRepository.findEventTypeByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_TYPE1_NAME)) {
                EventType eventType1 = new EventType();
                eventType1.setName(EVENT_TYPE1_NAME);
                eventType1.setDescription(EVENT_TYPE1_DESCRIPTION);
                eventType1.setAgeRequirement(EVENT_TYPE1_AGE);
                return Optional.of(eventType1);
            } else {
                return Optional.empty();
            }
        });

        lenient().when(eventTypeRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_TYPE1_ID)) {
                return eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME);
            } else {
                return Optional.empty();
            }
        });

        lenient().when(eventTypeRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<EventType> eventTypes = new ArrayList<>();
            eventTypes.add(eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME).orElse(null));
            return eventTypes;
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(eventTypeRepository.save(any(EventType.class))).thenAnswer(returnParameterAsAnswer);
        lenient().doNothing().when(eventTypeRepository).delete(any(EventType.class));
    }

    @Test
    public void testCreateEventType() {
        EventType eventType = null;
        String eventTypeName = "test";

        try {
            eventType = eventTypeService.createEventType(eventTypeName, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            fail();
        }
        assertNotNull(eventType);
        assertEquals(eventType.getName(), eventTypeName);
        assertEquals(eventType.getDescription(), EVENT_TYPE1_DESCRIPTION);
        assertEquals(eventType.getAgeRequirement(), EVENT_TYPE1_AGE);
    }

    @Test
    public void testCreateEventTypeAlreadyExists() {
        try {
            eventTypeService.createEventType(EVENT_TYPE1_NAME, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Event type "+EVENT_TYPE1_NAME+" already exists");
        }
    }

    @Test
    public void testCreateEventTypeNullName() {
        try {
            eventTypeService.createEventType(null, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Name of event type cannot be blank");
        }
    }

    @Test
    public void testCreateTypeBlankName() {
        try {
           eventTypeService.createEventType("", EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Name of event type cannot be blank");
        }
    }
    @Test
    public void testCreateEventTypeNullDescription() {
        try {
            eventTypeService.createEventType(EVENT_TYPE1_NAME,null, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Description of event type cannot be blank");
        }
    }
    @Test
    public void testCreateEventTypeBlankDescription() {
        try {
            eventTypeService.createEventType(EVENT_TYPE1_NAME,"", EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Description of event type cannot be blank");
        }
    }
    @Test
    public void testCreateEventTypeNegativeAge() {
        int ageTester = -1;
        try {
            eventTypeService.createEventType(EVENT_TYPE1_NAME,EVENT_TYPE1_DESCRIPTION,ageTester);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Invalid age requirement");
        }
    }

    @Test
    public void testUpdateEventTypeDoesNotExists() {
        UUID id = UUID.randomUUID();
        try {
            eventTypeService.updateEventType(id, EVENT_TYPE1_NAME, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Event type " + id + " not found");
        }
    }

    @Test
    public void testUpdateEventTypeNegativeAge() {
        int ageTester = -1;
        try {
            eventTypeService.updateEventType(EVENT_TYPE1_ID, EVENT_TYPE1_NAME, EVENT_TYPE1_DESCRIPTION, ageTester);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Invalid age requirement");
        }
    }

    @Test
    public void testDeleteEventType() {
        boolean deleted = eventTypeService.deleteEventType(EVENT_TYPE1_ID);
        assertTrue(deleted);
    }

    @Test
    public void testDeleteEventTypeNotFound() {
        UUID id = UUID.randomUUID();
        try {
            eventTypeService.deleteEventType(id);
        } catch(Exception e) {
            assertEquals(e.getMessage(), "Event type " + id + " not found");
        }
    }

    @Test
    public void testDeleteEventTypeByName() {
        boolean deleted = eventTypeService.deleteEventTypeByName(EVENT_TYPE1_NAME);
        assertTrue(deleted);
    }

    @Test
    public void testDeleteEventTypeByNameNotFound() {
        try {
            eventTypeService.deleteEventTypeByName("Non existent");
        } catch(Exception e) {
            assertEquals(e.getMessage(), "Event type Non existent not found");
        }
    }

    @Test
    public void testGetAllEventTypes(){
        List<EventType> eventTypes = new ArrayList<>();
        try{
            eventTypes = eventTypeService.getAllEventTypes();
        }catch (Exception e){
            fail();
        }

        assertEquals(eventTypes.size(), 1);
        assertEquals(eventTypes.get(0).getName(), EVENT_TYPE1_NAME);
        assertEquals(eventTypes.get(0).getDescription(), EVENT_TYPE1_DESCRIPTION);
        assertEquals(eventTypes.get(0).getAgeRequirement(), EVENT_TYPE1_AGE);
    }

}