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

@RestController
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @PostMapping(value = {"/create_eventtype"})
    public ResponseEntity<?> createEventType(@RequestParam String name, @RequestParam String description,
                                           @RequestParam int ageRequirement) {
        EventType eventType = null;
        try {
            eventType = eventTypeService.createEventType(name, description, ageRequirement);
        }catch(IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(eventType), HttpStatus.CREATED);

    }

    @PatchMapping(value = {"/update_eventtype"})
    public ResponseEntity<?> updateProfile(@RequestParam String oldName, @RequestParam String newName, @RequestParam String description, @RequestParam int ageRequirement) {

        EventType eventType = null;

        try {
            eventType = eventTypeService.updateEventType(oldName, newName, description, ageRequirement);
        }catch(IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(eventType), HttpStatus.OK);

    }

    @DeleteMapping(value = {"/delete_eventtype"})
    public boolean deleteEventType(@RequestParam String name) {
        return eventTypeService.deleteByName(name);
    }

    @GetMapping(value = {"/view_eventtype"})
    public EventTypeDTO viewEventType(@RequestParam String name) {
        return Conversion.convertToDTO(eventTypeService.getEventType(name));
    }


}
