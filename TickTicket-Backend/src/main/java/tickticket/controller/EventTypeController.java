package tickticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tickticket.dto.EventTypeDTO;
import tickticket.model.EventType;
import tickticket.model.Profile;
import tickticket.service.EventTypeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @PostMapping(value = {"/create_eventType"})
    public ResponseEntity<?> createEventType(@RequestParam String name, @RequestParam String description,
                                           @RequestParam int ageRequirement) {
        EventType eventType;
        try {
            eventType = eventTypeService.createEventType(name, description, ageRequirement);
        }catch(IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(eventType), HttpStatus.CREATED);

    }

    @PatchMapping(value = {"/update_eventType/{id}"})
    public ResponseEntity<?> updateProfile(@PathVariable("id") UUID id, @RequestParam String newName, @RequestParam String description, @RequestParam int ageRequirement) {

        EventType eventType;
        try {
            eventType = eventTypeService.updateEventType(id, newName, description, ageRequirement);
        }catch(IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(eventType), HttpStatus.OK);

    }

    @DeleteMapping(value = {"/delete_eventType/{id}"})
    public boolean deleteEventType(@PathVariable("id") UUID id) {
        return eventTypeService.deleteEventType(id);
    }

    @GetMapping(value = {"/view_eventType/{id}"})
    public EventTypeDTO viewEventType(@PathVariable("id") UUID id) {
        return Conversion.convertToDTO(eventTypeService.getEventType(id));
    }


}
