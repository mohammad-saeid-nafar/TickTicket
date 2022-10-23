package tickticket.service;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tickticket.dao.EventScheduleRepository;
import tickticket.model.EventSchedule;
import tickticket.model.EventSchedule.DayOfWeek;

@Service
public class EventScheduleService {

    @Autowired
    EventScheduleRepository eventScheduleRepository;

    @Transactional
    public EventSchedule createEventSchedule(LocalDateTime start, LocalDateTime end, boolean isRecurrent, List<DayOfWeek> recurrences){
		if(start==null) throw new IllegalArgumentException("Start of event schedule cannot be blank");
        if(end==null) throw new IllegalArgumentException("End of event schedule cannot be blank");

        startIsValid(start);

        EventSchedule eventSchedule = new EventSchedule();
        eventSchedule.setStartDateTime(start);
        eventSchedule.setEndDateTime(end);
        eventSchedule.setRecurrent(isRecurrent);
        eventSchedule.setRecurrences(recurrences);

        eventScheduleRepository.save(eventSchedule);

        return eventSchedule;

    }

    @Transactional
	public EventSchedule editEnd(LocalDateTime start, LocalDateTime end) {
		EventSchedule eventSchedule = eventScheduleRepository.findEventScheduleByStart(start);
		if(eventSchedule==null) throw new IllegalArgumentException("Event Schedule not found.");
		if(end==null) throw new IllegalArgumentException("New end cannot be blank.");
		else {
			eventSchedule.setEndDateTime(end);
		}
		eventScheduleRepository.save(eventSchedule);
		return eventSchedule;
	}

    @Transactional
    public EventSchedule getEventSchedule(LocalDateTime start){
		return eventScheduleRepository.findEventScheduleByStart(start);
    }

    @Transactional
	public boolean deleteEventScheduleByStart(LocalDateTime start) {
		EventSchedule eventSchedule = eventScheduleRepository.findEventScheduleByStart(start);
		if(eventSchedule!=null) { 
			eventScheduleRepository.delete(eventSchedule);
			return true;
		}
		return false;
	}

    @Transactional
	public List<EventSchedule> getAllEventSchedules(){
		return EventService.toList(eventScheduleRepository.findAll());
	}

    private boolean startIsValid(LocalDateTime start) {
		if(eventScheduleRepository.findEventScheduleByStart(start) ==null) return true;
		else throw new IllegalArgumentException("Event schedule already exists");
	}

}