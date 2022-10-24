package tickticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tickticket.dao.EventTypeRepository;
import tickticket.model.EventType;
import tickticket.model.Profile;

@Service
public class EventTypeService {

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Transactional
    public EventType createEventType(String name, String description, int ageRequirement){
		if(name==null || name=="") throw new IllegalArgumentException("Name of event type cannot be blank");
        if(description==null || description=="") throw new IllegalArgumentException("Description of event type cannot be blank");
        if(ageRequirement<=0) throw new IllegalArgumentException("Age of event type cannot be negative");



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


    @Transactional
    public EventType updateEventType(String oldName, String newName, String description, int ageRequirement){
        if(oldName==null || oldName=="") throw new IllegalArgumentException("Old name of event type cannot be blank");
        if(newName==null || newName=="") throw new IllegalArgumentException("New name of event type cannot be blank");
        if(description==null || description=="") throw new IllegalArgumentException("New description of event type cannot be blank");
        if(ageRequirement<=0) throw new IllegalArgumentException("New age of event type cannot be negative");

        EventType eventType = eventTypeRepository.findEventTypeByName(oldName);
        if(eventType == null) throw new IllegalArgumentException("Event type does not exist");

        eventType.setName(newName);
        eventType.setDescription(description);
        eventType.setAgeRequirement(ageRequirement);

        eventTypeRepository.save(eventType);

        return eventType;
    }

    @Transactional
    public boolean deleteByName(String name) {
        EventType eventType = getEventType(name);
        if(eventType==null) throw new IllegalArgumentException("EventType not found.");
        eventTypeRepository.delete(eventType);
        return true;
    }
}
