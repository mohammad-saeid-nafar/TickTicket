package tickticket.controller;

import java.time.LocalDateTime;
import java.util.List;
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

import tickticket.model.EventSchedule.DayOfWeek;
import tickticket.model.EventSchedule;
import tickticket.service.EventScheduleService;
import tickticket.dto.EventScheduleDTO;

@RestController
public class EventScheduleController {

	@Autowired
	private EventScheduleService eventScheduleService;

    @PostMapping(value = {"/create_eventSchedule/", "/create_eventSchedule"})
    public ResponseEntity<?> createEventSchedule(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end,
            @RequestParam boolean isRecurrent, @RequestParam List<DayOfWeek> recurrences) {
    

        EventSchedule eventSchedule = null;
        try {
            eventSchedule = eventScheduleService.createEventSchedule(start, end, isRecurrent, recurrences);
        }catch(IllegalArgumentException exception) {
             return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
          return new ResponseEntity<>(Conversion.convertToDTO(eventSchedule), HttpStatus.CREATED);

    }

    @DeleteMapping(value = {"/delete_eventSchedule/{start}"})
	public boolean deleteEventSchedule(@PathVariable("start") LocalDateTime start) {
		return eventScheduleService.deleteEventScheduleByStart(start);
	}

    @GetMapping(value = {"/view_eventSchedule/{name}"})
    public EventScheduleDTO viewEventSchedule(@PathVariable("start") LocalDateTime start) {
        return Conversion.convertToDTO(eventScheduleService.getEventSchedule(start));
    }

    @GetMapping(value = {"/view_eventSchedules", "/view_eventSchedules/"})
    public List<EventScheduleDTO> viewEventSchedules(){

        return eventScheduleService.getAllEventSchedules().stream().map(eventSchedule ->
                Conversion.convertToDTO(eventSchedule)).collect(Collectors.toList());

    }

    @PatchMapping(value = {"/change_end/{start}"})
	public ResponseEntity<?> changeEnd(@PathVariable("start") LocalDateTime start,@RequestParam LocalDateTime oldEnd, @RequestParam LocalDateTime newEnd) {
		EventSchedule eventSchedule = eventScheduleService.getEventSchedule(start);
		if(start==null) {
			return new ResponseEntity<>("EventSchedule not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(!oldEnd.equals(eventSchedule.getEndDateTime())) {
			return new ResponseEntity<>("Current end entered is incorrect", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			eventSchedule = eventScheduleService.editEnd(start, newEnd);
		}catch(IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(Conversion.convertToDTO(eventSchedule), HttpStatus.OK);
	}

}