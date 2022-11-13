package tickticket.controller;

import lombok.AllArgsConstructor;
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

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/event-types")
public class EventTypeController {

    private EventTypeService eventTypeService;

    @PostMapping
    public ResponseEntity<?> createEventType(@RequestBody EventTypeDTO eventTypeDTO) {
        EventType eventType;
        try {
            eventType = eventTypeService.createEventType(eventTypeDTO);
        }catch(IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(eventType), HttpStatus.CREATED);

    }

    @PatchMapping
    public ResponseEntity<?> updateProfile(@RequestBody EventTypeDTO eventTypeDTO) {

        EventType eventType;
        try {
            eventType = eventTypeService.updateEventType(eventTypeDTO);
        }catch(IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(eventType), HttpStatus.OK);

    }

    @DeleteMapping(value = {"/{id}"})
    public boolean deleteEventType(@PathVariable("id") UUID id) {
        return eventTypeService.deleteEventType(id);
    }

    @GetMapping(value = {"/{id}"})
    public EventTypeDTO viewEventType(@PathVariable("id") UUID id) {
        return Conversion.convertToDTO(eventTypeService.getEventType(id));
    }

    @GetMapping(value = {"/name/{name}"})
    public EventTypeDTO viewEventTypeByName(@PathVariable("name") String name) {
        return Conversion.convertToDTO(eventTypeService.getEventTypeByName(name));
    }

    @GetMapping
    public List<EventTypeDTO> viewAllEventTypes() {
        return eventTypeService.getAllEventTypes().stream()
                .map(Conversion::convertToDTO)
                .toList();
    }


}
