package tickticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tickticket.dao.EventTypeRepository;
import tickticket.model.EventType;

import java.util.List;
import java.util.UUID;

@Service
public class EventTypeService {

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Transactional
    public EventType createEventType(String name, String description, int ageRequirement){
		if(name==null || name.equals("")) throw new IllegalArgumentException("Name of event type cannot be blank");
        if(description==null || description.equals("")) throw new IllegalArgumentException("Description of event type cannot be blank");
        if(ageRequirement<=0) throw new IllegalArgumentException("Invalid age requirement");

        if(eventTypeRepository.findEventTypeByName(name).isPresent()) throw new IllegalArgumentException("Event type "+name+" already exists");

        EventType eventType = new EventType();
        eventType.setName(name);
        eventType.setDescription(description);
        eventType.setAgeRequirement(ageRequirement);

        eventTypeRepository.save(eventType);

        return eventType;

    }

    @Transactional
    public EventType updateEventType(UUID id, String name, String description, int ageRequirement){
        EventType eventType = getEventType(id);

        if(name!=null && !name.equals("") && !eventType.getName().equals(name)){
            if(eventTypeRepository.findEventTypeByName(name).isPresent()) throw new IllegalArgumentException("Event type "+name+" already exists");
            eventType.setName(name);
        }
        if(description!=null && !description.equals("")){
            eventType.setDescription(description);
        }

        if(ageRequirement<0) throw new IllegalArgumentException("Invalid age requirement");

        if(ageRequirement != 0) eventType.setAgeRequirement(ageRequirement);

        eventTypeRepository.save(eventType);

        return eventType;
    }

    @Transactional
    public boolean deleteEventType(UUID id) {
        EventType eventType = getEventType(id);
        eventTypeRepository.delete(eventType);
        return true;
    }

    @Transactional
    public boolean deleteEventTypeByName(String name) {
        EventType eventType = getEventTypeByName(name);
        eventTypeRepository.delete(eventType);
        return true;
    }

    @Transactional
    public EventType getEventType(UUID id){
        return eventTypeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Event type " + id + " not found"));
    }

    @Transactional
    public EventType getEventTypeByName(String name){
        return eventTypeRepository.findEventTypeByName(name).orElseThrow(() -> new IllegalArgumentException("Event type " + name + " not found"));
    }

    @Transactional
    public List<EventType> getAllEventTypes(){
        return eventTypeRepository.findAll();
    }
}
