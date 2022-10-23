package tickticket.service;

import java.util.ArrayList;
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
		if(start != null && !eventSchedule.getStartDateTime().equals(start)){
			eventSchedule.setStartDateTime(start);
		}
		if(end != null && !eventSchedule.getEndDateTime().equals(end)){
			eventSchedule.setEndDateTime(end);
		}
		eventScheduleRepository.save(eventSchedule);
		return eventSchedule;
	}

	@Transactional
	public boolean deleteEventSchedule(UUID id) {
		EventSchedule eventSchedule = eventScheduleRepository.findEventScheduleById(id);
		if(eventSchedule==null) throw new IllegalArgumentException("Event schedule not found");
		eventScheduleRepository.delete(eventSchedule);
		return true;
	}

    @Transactional
    public EventSchedule getEventSchedule(UUID id){
		EventSchedule schedule = eventScheduleRepository.findEventScheduleById(id);
		if(schedule==null) throw new IllegalArgumentException("Event schedule not found");
		return schedule;
    }

    @Transactional
	public List<EventSchedule> getAllEventSchedules(){
		return toList(eventScheduleRepository.findAll());
	}


	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;

	}

}