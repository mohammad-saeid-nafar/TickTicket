package tickticket.acceptance;

import java.util.*;

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
import static org.mockito.Mockito.*;

import tickticket.dao.*;
import tickticket.dto.EventTypeDTO;
import tickticket.model.*;
import tickticket.service.*;

@ExtendWith(MockitoExtension.class)
public class ID017AddNewEventType {

    @Mock
    private EventTypeRepository eventTypeRepository;

    @InjectMocks
    private EventTypeService eventTypeService;

    // Data for event type
    private static final UUID EVENT_TYPE1_ID = UUID.randomUUID();
    private static final String EVENT_TYPE1_NAME = "Pop Music";
    private static final String EVENT_TYPE1_DESCRIPTION = "Music";
    private static final int EVENT_TYPE1_AGE_REQUIREMENT = 0;


    @BeforeEach
    public void setMockOutput() {

        lenient().when(eventTypeRepository.findEventTypeByName(any(String.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_TYPE1_NAME)) {

                EventType eventType1 = new EventType();
                eventType1.setId(EVENT_TYPE1_ID);
                eventType1.setName(EVENT_TYPE1_NAME);
                eventType1.setDescription(EVENT_TYPE1_DESCRIPTION);
                eventType1.setAgeRequirement(EVENT_TYPE1_AGE_REQUIREMENT);

                return Optional.of(eventType1);

            }
            else {
                return Optional.empty();
            }
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(eventTypeRepository.save(any(EventType.class))).thenAnswer(returnParameterAsAnswer);
    }


    @Test
    public void addNewEventTypeSuccess() {

        EventTypeDTO newEventType = new EventTypeDTO();
        newEventType.setName("Party");
        newEventType.setDescription("Big Party");
        newEventType.setAgeRequirement(18);

        try {
            EventType eventType = eventTypeService.createEventType(newEventType);
            assertEquals(newEventType.getName(), eventType.getName());
            assertEquals(newEventType.getDescription(), eventType.getDescription());
            assertEquals(newEventType.getAgeRequirement(), eventType.getAgeRequirement());
        } catch (IllegalArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void addNewEventTypeAlreadyExistsFail() {

        EventTypeDTO newEventType = new EventTypeDTO();
        newEventType.setId(UUID.randomUUID());
        newEventType.setName("Pop Music");
        newEventType.setDescription("description");
        newEventType.setAgeRequirement(1);

        try {
            eventTypeService.createEventType(newEventType);
        } catch (IllegalArgumentException e) {
            assertEquals("Event type Pop Music already exists", e.getMessage());
        }
    }

    @Test
    public void addNewEventTypeBlankNameFail() {

        EventTypeDTO newEventType = new EventTypeDTO();
        newEventType.setId(UUID.randomUUID());
        newEventType.setName("");
        newEventType.setDescription("description");
        newEventType.setAgeRequirement(0);

        try {
            eventTypeService.createEventType(newEventType);
        } catch (IllegalArgumentException e) {
            assertEquals("Name of event type cannot be blank", e.getMessage());
        }
    }

    @Test
        public void addNewEventTypeBlankDescriptionFail() {

        EventTypeDTO newEventType = new EventTypeDTO();
        newEventType.setId(UUID.randomUUID());
        newEventType.setName("TestName");
        newEventType.setDescription("");
        newEventType.setAgeRequirement(0);

        try {
            eventTypeService.createEventType(newEventType);
        } catch (IllegalArgumentException e) {
            assertEquals("Description of event type cannot be blank", e.getMessage());
        }
    }

    @Test
    public void addNewEventTypeInvalidAgeFail() {

        EventTypeDTO newEventType = new EventTypeDTO();
        newEventType.setId(UUID.randomUUID());
        newEventType.setName("testName");
        newEventType.setDescription("description");
        newEventType.setAgeRequirement(-1);

        try {
            eventTypeService.createEventType(newEventType);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid age requirement", e.getMessage());
        }
    }
}
