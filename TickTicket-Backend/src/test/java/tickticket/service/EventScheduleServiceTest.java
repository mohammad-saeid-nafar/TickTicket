package tickticket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import tickticket.dao.EventScheduleRepository;
import tickticket.model.EventSchedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class EventScheduleServiceTest {

    @Mock
    private EventScheduleRepository eventScheduleRepository;

    @InjectMocks
    private EventScheduleService eventScheduleService;

    private static final UUID ID = UUID.randomUUID();
    private static final LocalDateTime START = LocalDateTime.of(2022, 10, 2, 12, 0);
    private static final LocalDateTime END = LocalDateTime.of(2022, 10, 2, 23, 59);

    @BeforeEach
    public void setMockOutput() {
        lenient().when(eventScheduleRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ID)){
                EventSchedule schedule =  new EventSchedule();
                schedule.setId(ID);
                schedule.setStartDateTime(START);
                schedule.setEndDateTime(END);
                return Optional.of(schedule);
            }else{
                return Optional.empty();
            }
        });
        lenient().when(eventScheduleRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            EventSchedule schedule =  new EventSchedule();
            schedule.setId(ID);
            schedule.setStartDateTime(START);
            schedule.setEndDateTime(END);

            List<EventSchedule> schedules = new ArrayList<>();
            schedules.add(schedule);

            return schedules;
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(eventScheduleRepository.save(any(EventSchedule.class))).thenAnswer(returnParameterAsAnswer);
        lenient().doNothing().when(eventScheduleRepository).delete(any(EventSchedule.class));
    }

    @Test
    public void testCreateEventSchedule(){
        EventSchedule schedule = null;
        try{
            schedule = eventScheduleService.createEventSchedule(START, END);
        }catch (Exception e){
            fail();
        }
        assertNotNull(schedule);
        assertEquals(START, schedule.getStartDateTime());
        assertEquals(END, schedule.getEndDateTime());
    }

    @Test
    public void testCreateEventScheduleNullStart(){
        try{
            eventScheduleService.createEventSchedule(null, END);
        }catch (Exception e){
            assertEquals("Start of event schedule cannot be blank", e.getMessage());
        }
    }

    @Test
    public void testCreateEventScheduleNullEnd(){
        try{
            eventScheduleService.createEventSchedule(START, null);
        }catch (Exception e){
            assertEquals("End of event schedule cannot be blank", e.getMessage());
        }
    }

    @Test
    public void testEditEventSchedule(){
        EventSchedule schedule = null;
        LocalDateTime newStart = LocalDateTime.of(2022, 11, 2, 12, 0);
        LocalDateTime newEnd = LocalDateTime.of(2022, 11, 2, 23, 59);
        try{
            schedule = eventScheduleService.editEventSchedule(ID, newStart, newEnd);
        }catch (Exception e){
            fail();
        }
        assertNotNull(schedule);
        assertEquals(ID, schedule.getId());
        assertEquals(newStart, schedule.getStartDateTime());
        assertEquals(newEnd, schedule.getEndDateTime());
    }

    @Test
    public void testDeleteEventSchedule(){
        boolean success = false;
        try{
            success = eventScheduleService.deleteEventSchedule(ID);
        }catch (Exception e){
            fail();
        }
        assertTrue(success);
    }

    @Test
    public void testDeleteEventScheduleNotFound(){
        UUID id = UUID.randomUUID();
        try{
            eventScheduleService.deleteEventSchedule(id);
        }catch (Exception e){
            assertEquals("Event schedule " + id + " not found", e.getMessage());
        }
    }

    @Test
    public void testGetEventSchedule(){
        EventSchedule schedule = null;
        try{
            schedule = eventScheduleService.getEventSchedule(ID);
        }catch (Exception e){
            fail();
        }
        assertEquals(ID, schedule.getId());
        assertEquals(START, schedule.getStartDateTime());
        assertEquals(END, schedule.getEndDateTime());
    }

    @Test
    public void testGetEventScheduleNotFound(){
        UUID id = UUID.randomUUID();
        try{
            eventScheduleService.getEventSchedule(id);
        }catch (Exception e){
            assertEquals("Event schedule " + id + " not found", e.getMessage());
        }
    }

    @Test
    public void testGetAllEventSchedules(){
        List<EventSchedule> schedules = null;
        try{
            schedules = eventScheduleService.getAllEventSchedules();
        }catch (Exception e){
            fail();
        }
        assertEquals(1, schedules.size());
        assertEquals(ID, schedules.get(0).getId());
        assertEquals(START, schedules.get(0).getStartDateTime());
        assertEquals(END, schedules.get(0).getEndDateTime());
    }

}
