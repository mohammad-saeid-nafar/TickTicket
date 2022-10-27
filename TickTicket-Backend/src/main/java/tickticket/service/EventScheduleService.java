package tickticket.service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tickticket.dao.EventScheduleRepository;
import tickticket.model.EventSchedule;

@Service
public class EventScheduleService {

    @Autowired
    EventScheduleRepository eventScheduleRepository;

    @Transactional
    public EventSchedule createEventSchedule(LocalDateTime start, LocalDateTime end){
		if(start==null) throw new IllegalArgumentException("Start of event schedule cannot be blank");
        if(end==null) throw new IllegalArgumentException("End of event schedule cannot be blank");

        EventSchedule eventSchedule = new EventSchedule();
        eventSchedule.setStartDateTime(start);
        eventSchedule.setEndDateTime(end);

        eventScheduleRepository.save(eventSchedule);

        return eventSchedule;

    }

    @Transactional
	public EventSchedule editEventSchedule(UUID id, LocalDateTime start, LocalDateTime end) {
		EventSchedule eventSchedule = getEventSchedule(id);
		if(start != null){
			eventSchedule.setStartDateTime(start);
		}
		if(end != null){
			eventSchedule.setEndDateTime(end);
		}
		eventScheduleRepository.save(eventSchedule);
		return eventSchedule;
	}

	@Transactional
	public boolean deleteEventSchedule(UUID id) {
		EventSchedule eventSchedule = getEventSchedule(id);
		eventScheduleRepository.delete(eventSchedule);
		return true;
	}

    @Transactional
    public EventSchedule getEventSchedule(UUID id){
		return eventScheduleRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Event schedule " + id + " not found"));
    }

    @Transactional
	public List<EventSchedule> getAllEventSchedules(){
		return eventScheduleRepository.findAll();
	}

}