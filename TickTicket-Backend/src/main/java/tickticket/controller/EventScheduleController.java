package tickticket.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tickticket.model.EventSchedule;
import tickticket.service.EventScheduleService;
import tickticket.dto.EventScheduleDTO;

@RestController
public class EventScheduleController {

	@Autowired
	private EventScheduleService eventScheduleService;

    @PostMapping(value = {"/create_eventSchedule/", "/create_eventSchedule"})
    public ResponseEntity<?> createEventSchedule(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
    

        EventSchedule eventSchedule;
        try {
            eventSchedule = eventScheduleService.createEventSchedule(start, end);
        }catch(IllegalArgumentException exception) {
             return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
          return new ResponseEntity<>(Conversion.convertToDTO(eventSchedule), HttpStatus.CREATED);

    }

    @PatchMapping(value = {"/edit_eventSchedule/{id}"})
    public ResponseEntity<?> changeEnd(@PathVariable("id") UUID id,@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        EventSchedule eventSchedule;
        try {
            eventSchedule = eventScheduleService.editEventSchedule(id, start, end);
        }catch(IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Conversion.convertToDTO(eventSchedule), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete_eventSchedule/{id}"})
	public boolean deleteEventSchedule(@PathVariable("id") UUID id) {
		return eventScheduleService.deleteEventSchedule(id);
	}

    @GetMapping(value = {"/view_eventSchedule/{id}"})
    public EventScheduleDTO viewEventSchedule(@PathVariable("id") UUID id) {
        return Conversion.convertToDTO(eventScheduleService.getEventSchedule(id));
    }

    @GetMapping(value = {"/view_eventSchedules", "/view_eventSchedules/"})
    public List<EventScheduleDTO> viewEventSchedules(){

        return eventScheduleService.getAllEventSchedules().stream().map(Conversion::convertToDTO).collect(Collectors.toList());

    }


}