package tickticket.controller;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import tickticket.dto.ProfileDTO;
import tickticket.model.EventType;
import tickticket.model.Profile;
import tickticket.service.EventTypeService;
import tickticket.service.ProfileService;


@RestController
public class ProfileController {

    private ProfileService profileService;

    private EventTypeService eventTypeService;

    @PostMapping(value = {"/create_profile/","/create_user"})
    public ResponseEntity<?> createProfile(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber,
                                        @RequestParam String email, @RequestParam String address, @RequestParam String profilePicture, @RequestParam LocalDate dateOfBirth,
                                        @RequestParam String eventTypeName) {

        EventType eventType = null;
        try{
            eventType = eventTypeService.getEventType(eventTypeName);
        }catch(IllegalArgumentException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<EventType> interests = new ArrayList<>();
        interests.add(eventType);

        Profile profile = null;
        try {
            profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
        }catch(IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Conversion.convertToDTO(profile), HttpStatus.CREATED);

    }

    @DeleteMapping(value = {"/delete_profile/{email}"})
    public boolean deleteProfile(@PathVariable("email") String email) {
        return profileService.deleteByEmail(email);
    }

    @GetMapping(value = {"/view_profile/{username}"})
    public ProfileDTO viewProfile(@PathVariable("email") String email) {
        return Conversion.convertToDTO(profileService.getProfileByEmail(email));
    }

    @GetMapping(value = {"/view_profiles", "/view_profiles/"})
    public List<ProfileDTO> viewProfiles(){

        return profileService.getAllProfiles().stream().map(profile ->
                Conversion.convertToDTO(profile)).collect(Collectors.toList());

    }

    @PatchMapping(value = {"/update_profile/{email}"})
    public ResponseEntity<?> updateProfile(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber,
                                           @PathVariable("email") String email, @RequestParam String address, @RequestParam String profilePicture, @RequestParam LocalDate dateOfBirth,
                                           @RequestParam String eventTypeName) {

        EventType eventType = null;
        try{
            eventType = eventTypeService.getEventType(eventTypeName);
        }catch(IllegalArgumentException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<EventType> interests = new ArrayList<>();
        interests.add(eventType);

        Profile profile = null;
        try {
            profile = profileService.updateProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
        }catch(IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Conversion.convertToDTO(profile), HttpStatus.OK);

    }
}
