package tickticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tickticket.dto.EventDTO;
import tickticket.model.Event;
import tickticket.model.EventType;
import tickticket.service.EventService;
import tickticket.service.EventTypeService;
import tickticket.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventTypeService eventTypeService;

    @PostMapping(value = {"/create_event", "/create_event/"})
    public ResponseEntity<?> createEvent(@RequestParam String name, @RequestParam String description, @RequestParam Integer capacity,
                                                @RequestParam Double cost, @RequestParam String address, @RequestParam String email,
                                                @RequestParam String phoneNumber, @RequestParam String username, @RequestParam List<String> eventTypesNames,
                                                @RequestParam String start, @RequestParam String end){
        List<EventType> eventTypes = new ArrayList<>();
        for(String eventTypeName : eventTypesNames){
            eventTypes.add(eventTypeService.getEventType(eventTypeName));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);
        Event event;
        try{
            event = eventService.createEvent(name, description, capacity, cost, address, email, phoneNumber, userService.getUser(username), eventTypes, startDateTime, endDateTime);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(event), HttpStatus.CREATED);
    }

    @PatchMapping(value = {"/update_event", "/update_event/"})
    public ResponseEntity<?> updateEvent(@RequestParam UUID id, @RequestParam String name, @RequestParam String description, @RequestParam Integer capacity,
                                         @RequestParam Double cost, @RequestParam String address, @RequestParam String email,
                                         @RequestParam String phoneNumber, @RequestParam String username, @RequestParam List<String> eventTypesNames){
        List<EventType> eventTypes = new ArrayList<>();
        for(String eventTypeName : eventTypesNames){
            eventTypes.add(eventTypeService.getEventType(eventTypeName));
        }
        Event event;
        try{
            event = eventService.updateEvent(id, name, description, capacity, cost, address, email, phoneNumber, userService.getUser(username), eventTypes);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(event), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete_event/{id}"})
    public ResponseEntity<?> deleteEvent(@PathVariable("id") UUID id){
        try{
            eventService.deleteEventById(id);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = {"/view_event/{id}"})
    public ResponseEntity<?> getEventById(@PathVariable("id") UUID id){
        Event event;
        try{
            event = eventService.getEventById(id);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(event), HttpStatus.OK);
    }

    @GetMapping(value = {"/view_events", "/view_events/"})
    public ResponseEntity<?> getEvents(){
        List<EventDTO> eventsDTO = new ArrayList<>();
        try{
            List<Event> events = eventService.getAllEvents();
            for(Event event : events){
                eventsDTO.add(Conversion.convertToDTO(event));
            }
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/view_events_organizer/{username}"})
    public ResponseEntity<?> getEventsByOrganizer(@PathVariable("username") String username){
        List<EventDTO> eventsDTO = new ArrayList<>();
        try{
           List<Event> events = eventService.getAllEventsFromOrganizer(userService.getUser(username));
           for(Event event : events){
               eventsDTO.add(Conversion.convertToDTO(event));
           }
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/view_events_event_types"})
    public ResponseEntity<?> getEventsByEventTypes(@RequestParam List<String> eventTypesNames){
        List<EventType> eventTypes = new ArrayList<>();
        for(String eventTypeName : eventTypesNames){
            eventTypes.add(eventTypeService.getEventType(eventTypeName));
        }
        List<EventDTO> eventsDTO = new ArrayList<>();
        try{
            List<Event> events = eventService.getAllEventsFromType(eventTypes);
            for(Event event : events){
                eventsDTO.add(Conversion.convertToDTO(event));
            }
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }

}
