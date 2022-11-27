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
import tickticket.dao.EventTypeRepository;
import tickticket.dto.EventTypeDTO;
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

    private EventTypeDTO eventTypeDTO;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventTypeService eventTypeService;

    @BeforeEach
    public void setMockOutput() {
        eventTypeDTO = new EventTypeDTO(EVENT_TYPE1_ID, EVENT_TYPE1_NAME, EVENT_TYPE1_DESCRIPTION, EVENT_TYPE1_AGE);

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

        lenient().when(eventRepository.findEventsByEventTypesIn(any())).thenAnswer((InvocationOnMock invocation) -> new ArrayList<>());


        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(eventTypeRepository.save(any(EventType.class))).thenAnswer(returnParameterAsAnswer);
        lenient().doNothing().when(eventTypeRepository).delete(any(EventType.class));
    }

    @Test
    public void testCreateEventType() {
        EventType eventType = null;
        String eventTypeName = "test";

        eventTypeDTO.setName(eventTypeName);

        try {
            eventType = eventTypeService.createEventType(eventTypeDTO);
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
            eventTypeService.createEventType(eventTypeDTO);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Event type "+EVENT_TYPE1_NAME+" already exists");
        }
    }

    @Test
    public void testCreateEventTypeNullName() {
        eventTypeDTO.setName(null);
        try {
            eventTypeService.createEventType(eventTypeDTO);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Name of event type cannot be blank");
        }
    }

    @Test
    public void testCreateTypeBlankName() {
        eventTypeDTO.setName("");
        try {
           eventTypeService.createEventType(eventTypeDTO);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Name of event type cannot be blank");
        }
    }
    @Test
    public void testCreateEventTypeNullDescription() {
        eventTypeDTO.setDescription(null);
        try {
            eventTypeService.createEventType(eventTypeDTO);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Description of event type cannot be blank");
        }
    }
    @Test
    public void testCreateEventTypeBlankDescription() {
        eventTypeDTO.setDescription("");
        try {
            eventTypeService.createEventType(eventTypeDTO);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Description of event type cannot be blank");
        }
    }
    @Test
    public void testCreateEventTypeNegativeAge() {
        eventTypeDTO.setAgeRequirement(-1);
        try {
            eventTypeService.createEventType(eventTypeDTO);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Invalid age requirement");
        }
    }

    @Test
    public void testUpdateEventTypeDoesNotExists() {
        UUID id = UUID.randomUUID();
        eventTypeDTO.setId(id);
        try {
            eventTypeService.updateEventType(eventTypeDTO);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Event type " + id + " not found");
        }
    }

    @Test
    public void testUpdateEventTypeNegativeAge() {
        eventTypeDTO.setAgeRequirement(-1);
        try {
            eventTypeService.updateEventType(eventTypeDTO);
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