package tickticket.controller;

import java.util.UUID;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tickticket.dto.ProfileDTO;
import tickticket.model.Profile;
import tickticket.service.ProfileService;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody ProfileDTO profileDTO) {
        Profile profile;
        try {
            profile = profileService.createProfile(profileDTO);
        }catch(IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(Conversion.convertToDTO(profile), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> editProfile(@RequestBody ProfileDTO profileDTO) {

        Profile profile;
        try {
            profile = profileService.updateProfile(profileDTO);
        }catch(IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Conversion.convertToDTO(profile), HttpStatus.CREATED);

    }

    @GetMapping(value = {"/{id}"})
    public ProfileDTO viewProfile(@PathVariable("id") UUID id) {
        return Conversion.convertToDTO(profileService.getProfile(id));
    }

    @GetMapping(value = {"/email/{email}"})
    public ProfileDTO viewProfileByEmail(@PathVariable("email") String email) {
        return Conversion.convertToDTO(profileService.getProfileByEmail(email));
    }

    @GetMapping
    public List<ProfileDTO> viewProfiles(){
        return profileService.getAllProfiles().stream()
                .map(Conversion::convertToDTO)
                .toList();
    }

}
