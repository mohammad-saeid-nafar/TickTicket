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
import tickticket.dao.EventRepository;
import tickticket.dao.EventScheduleRepository;
import tickticket.dao.EventTypeRepository;
import tickticket.model.Event;
import tickticket.model.EventSchedule;
import tickticket.model.EventType;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class EventTypeServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventScheduleRepository eventScheduleRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

    private static final String EVENT_TYPE1_NAME = "Party";
    private static final String EVENT_TYPE2_NAME = "Rock";
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

                return eventType1;

            } else {
                return null;
            }
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(eventRepository.save(any(Event.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(eventScheduleRepository.save(any(EventSchedule.class))).thenAnswer(returnParameterAsAnswer);
        lenient().doNothing().when(eventRepository).delete(any(Event.class));
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
        EventType eventType = eventTypeRepository.findEventTypeByName(EVENT_TYPE1_NAME);
        try {
            eventType = eventTypeService.createEventType(EVENT_TYPE1_NAME, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Event type already exists");
        }
    }

    @Test
    public void testCreateEventTypeNullName() {
        EventType eventType = null;
        try {
            eventType = eventTypeService.createEventType(null, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Name of event type cannot be blank");
        }
    }

    @Test
    public void testCreateTypeBlankName() {
        EventType eventType = null;
        try {
            eventType = eventTypeService.createEventType(null, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Name of event type cannot be blank");
        }
    }
    @Test
    public void testCreateEventTypeNullDescription() {
        EventType eventType = null;
        try {
            eventType = eventTypeService.createEventType(EVENT_TYPE1_NAME,null, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Description of event type cannot be blank");
        }
    }
    @Test
    public void testCreateEventTypeBlankDescription() {
        EventType eventType = null;
        try {
            eventType = eventTypeService.createEventType(EVENT_TYPE1_NAME,"", EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Description of event type cannot be blank");
        }
    }
    @Test
    public void testCreateEventTypeNegativeAge() {
        EventType eventType = null;
        int ageTester = -1;
        try {
            eventType = eventTypeService.createEventType(EVENT_TYPE1_NAME,EVENT_TYPE1_DESCRIPTION,ageTester);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Age of event type cannot be negative");
        }
    }

    @Test
    public void testUpdateEventTypeDoesNotExists() {
        try {
            EventType eventType = eventTypeService.updateEventType(EVENT_TYPE2_NAME, EVENT_TYPE1_NAME, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Event type does not exist");
        }
    }

    @Test
    public void testUpdateEventTypeNullOldName() {
        EventType eventType = null;
        try {
            eventType = eventTypeService.updateEventType(null, EVENT_TYPE2_NAME, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Old name of event type cannot be blank");
        }
    }

    @Test
    public void testUpdateEventTypeNullNewName() {
        EventType eventType = null;
        try {
            eventType = eventTypeService.updateEventType(EVENT_TYPE1_NAME, null, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "New name of event type cannot be blank");
        }
    }

    @Test
    public void testUpdateTypeBlankOldName() {
        EventType eventType = null;
        try {
            eventType = eventTypeService.updateEventType(null, EVENT_TYPE2_NAME, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Old name of event type cannot be blank");
        }
    }

    @Test
    public void testUpdateTypeBlankNewName() {
        EventType eventType = null;
        try {
            eventType = eventTypeService.updateEventType(EVENT_TYPE1_NAME, null, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "New name of event type cannot be blank");
        }
    }

    @Test
    public void testUpdateEventTypeNullDescription() {
        EventType eventType = null;
        try {
            eventType = eventTypeService.updateEventType(EVENT_TYPE1_NAME, EVENT_TYPE2_NAME, null, EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Description of event type cannot be blank");
        }
    }
    @Test
    public void testUpdateEventTypeBlankDescription() {
        EventType eventType = null;
        try {
            eventType = eventTypeService.updateEventType(EVENT_TYPE1_NAME, EVENT_TYPE2_NAME, "", EVENT_TYPE1_AGE);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Description of event type cannot be blank");
        }
    }
    @Test
    public void testUpdateEventTypeNegativeAge() {
        EventType eventType = null;
        int ageTester = -1;
        try {
            eventType = eventTypeService.updateEventType(EVENT_TYPE1_NAME, EVENT_TYPE2_NAME, EVENT_TYPE1_DESCRIPTION, ageTester);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Age of event type cannot be negative");
        }
    }

    @Test
    public void testDeleteEventType() {
        boolean deleted = eventTypeService.deleteByName(EVENT_TYPE1_NAME);
        assertTrue(deleted);
        assertTrue(eventTypeRepository.existsEventTypeByName(EVENT_TYPE1_NAME) == false);
    }

    @Test
    public void testDeleteEventTypeNotFound() {
        try {
            boolean deleted = eventTypeService.deleteByName("Hi");
        } catch(Exception e) {
            assertEquals(e.getMessage(), "EventType not found.");
        }
    }

}