package tickticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import tickticket.dao.EventTypeRepository;
import tickticket.model.EventType;

public class EventTypeService {

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Transactional
    public EventType createEventType(String name, String description, int ageRequirement){
		if(name==null || name=="") throw new IllegalArgumentException("Name of event type cannot be blank");

        nameIsValid(name);

        EventType eventType = new EventType();
        eventType.setName(name);
        eventType.setDescription(description);
        eventType.setAgeRequirement(ageRequirement);

        eventTypeRepository.save(eventType);

        return eventType;

    }

    @Transactional
    public EventType getEventType(String name){
		return eventTypeRepository.findEventTypeByName(name);
    }

    private boolean nameIsValid(String name) {
		if(eventTypeRepository.findEventTypeByName(name) ==null) return true;
		else throw new IllegalArgumentException("Event type already exists");
	}
    
}
