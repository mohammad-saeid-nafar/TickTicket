package tickticket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import tickticket.dao.EventTypeRepository;
import tickticket.dto.EventTypeDTO;
import tickticket.model.EventType;
import tickticket.service.EventTypeService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ID023SetAgeRequirementForEvents {
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

        lenient().when(eventTypeRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(EVENT_TYPE1_ID)) {

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
    public void setEventType1AgeRequirementSuccess() {

        EventTypeDTO newEventType = new EventTypeDTO();
        newEventType.setId(EVENT_TYPE1_ID);
        newEventType.setAgeRequirement(18);

        try {
            EventType eventType = eventTypeService.updateEventType(newEventType);
            assertEquals(newEventType.getId(), eventType.getId());
            assertEquals(EVENT_TYPE1_NAME, eventType.getName());
            assertEquals(EVENT_TYPE1_DESCRIPTION, eventType.getDescription());
            assertEquals(newEventType.getAgeRequirement(), eventType.getAgeRequirement());
        } catch (IllegalArgumentException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void setEventTypeAgeRequirementInvalidAgeFail() {

        EventTypeDTO newEventType = new EventTypeDTO();
        newEventType.setId(EVENT_TYPE1_ID);
        newEventType.setAgeRequirement(-1);

        try {
            eventTypeService.updateEventType(newEventType);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid age requirement", e.getMessage());
        }
    }

    @Test
    public void setEventTypeAgeRequirementNotFoundFail() {

        EventTypeDTO newEventType = new EventTypeDTO();
        UUID id = UUID.randomUUID();
        newEventType.setId(id);

        try {
            eventTypeService.updateEventType(newEventType);
        } catch (IllegalArgumentException e) {
            assertEquals("Event type " + id + " not found", e.getMessage());
        }
    }


}
