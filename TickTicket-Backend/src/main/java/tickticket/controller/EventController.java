package tickticket.controller;

import lombok.AllArgsConstructor;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

    private EventService eventService;
    private UserService userService;
    private EventTypeService eventTypeService;

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
//        List<EventType> eventTypes = new ArrayList<>();
//        for(UUID eventTypeId : eventTypesIds){
//            eventTypes.add(eventTypeService.getEventType(eventTypeId));
//        }
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
//        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);
        Event event;
        try{
            event = eventService.createEvent(eventDTO);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(event), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> updateEvent(@RequestBody EventDTO eventDTO) {
//        List<EventType> eventTypes = new ArrayList<>();
//        for(UUID eventTypeId : eventTypesIds){
//            eventTypes.add(eventTypeService.getEventType(eventTypeId));
//        }
        Event event;
        try{
            event = eventService.updateEvent(eventDTO);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(event), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<?> deleteEvent(@PathVariable("id") UUID id){
        try{
            eventService.deleteEvent(id, eventService.getEvent(id).getOrganizer().getId());
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?> getEventById(@PathVariable("id") UUID id){
        Event event;
        try{
            event = eventService.getEvent(id);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(event), HttpStatus.OK);
    }

    @GetMapping
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

    @GetMapping(value = {"/cost"})
    public ResponseEntity<?> getEventsByCostRange(@RequestParam double minCost, @RequestParam double maxCost){
        List<EventDTO> eventsDTO = new ArrayList<>();
        try{
            List<Event> events = eventService.getEventsByCostRange(minCost, maxCost);
            for(Event event : events){
                eventsDTO.add(Conversion.convertToDTO(event));
            }
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/organizer/{username}"})
    public ResponseEntity<?> getEventsByOrganizer(@PathVariable("username") String username){
        List<EventDTO> eventsDTO = new ArrayList<>();
        try{
           List<Event> events = eventService.getAllEventsFromOrganizer(userService.getUserByUsername(username));
           for(Event event : events){
               eventsDTO.add(Conversion.convertToDTO(event));
           }
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/capacity/{min}/{max}"})
    public ResponseEntity<?> getEventsByCapacityRange(@PathVariable("min") int min, @PathVariable("max") int max){
        List<EventDTO> eventsDTO = new ArrayList<>();
        try{
            List<Event> events = eventService.getEventsWithCapacityRange(min, max);
            for(Event event : events){
                eventsDTO.add(Conversion.convertToDTO(event));
            }
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/event-type"})
    public ResponseEntity<?> getEventsByEventTypes(@RequestParam String eventTypeName){
        List<EventDTO> eventsDTO = new ArrayList<>();
        try{
            List<Event> events = eventService.getAllEventsFromType(eventTypeName);
            for(Event event : events){
                eventsDTO.add(Conversion.convertToDTO(event));
            }
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }


//    @GetMapping(value = {"/view_events_event_types"})
//    public ResponseEntity<?> getEventsByEventTypes(@RequestParam String eventTypeName){
//        List<EventDTO> eventsDTO = new ArrayList<>();
//        try{
//            List<Event> events = eventService.getAllEventsFromType(eventTypeService.getEventTypeByName(eventTypeName));
//            for(Event event : events){
//                eventsDTO.add(Conversion.convertToDTO(event));
//            }
//        }catch(IllegalArgumentException e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
//    }


    @GetMapping(value = {"/date"})
    public ResponseEntity<?> getEventsByDate(@RequestParam String date){
        List<EventDTO> eventsDTO = new ArrayList<>();
        try{
            List<Event> events = eventService.getAllEventsByDate(LocalDate.parse(date));
            for(Event event : events){
                eventsDTO.add(Conversion.convertToDTO(event));
            }
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/address"})
    public ResponseEntity<?> getEventsByAddress(@RequestParam String address){
        List<EventDTO> eventsDTO = new ArrayList<>();
        try{
            List<Event> events = eventService.getAllEventsByAddress(address);
            for(Event event : events){
                eventsDTO.add(Conversion.convertToDTO(event));
            }
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/upcoming/{id}"})
    public ResponseEntity<?> getUserUpcomingEvents(@PathVariable("id") UUID id){
        List<EventDTO> eventsDTO = new ArrayList<>();
        try{
            List<Event> events = eventService.getUserUpcomingEvents(id, LocalDateTime.now());
            for(Event event : events){
                eventsDTO.add(Conversion.convertToDTO(event));
            }
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/past/{id}"})
    public ResponseEntity<?> getUserPastEvents(@PathVariable("id") UUID id){
        List<EventDTO> eventsDTO = new ArrayList<>();
        try{
            List<Event> events = eventService.getUserPastEvents(id, LocalDateTime.now());
            for(Event event : events){
                eventsDTO.add(Conversion.convertToDTO(event));
            }
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventsDTO, HttpStatus.OK);
    }

}
