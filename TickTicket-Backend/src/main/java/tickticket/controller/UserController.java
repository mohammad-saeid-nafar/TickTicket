package tickticket.controller;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

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


import tickticket.model.User;
import tickticket.dto.UserDTO;
import tickticket.model.EventType;
import tickticket.model.Profile;
import tickticket.service.UserService;
import tickticket.service.EventTypeService;
import tickticket.service.ProfileService;


@RestController
public class UserController {
    
	@Autowired
	private UserService userService;

    @Autowired
    private ProfileService profileService;

	@Autowired
	private EventTypeService eventTypeService;
	
	@PostMapping(value = {"/login", "/login/"})
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
		User user;
		try {
			user = userService.login(username, password);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(Conversion.convertToDTO(user), HttpStatus.OK);

	}

    @PostMapping(value = {"/create_user/","/create_user"})
	public ResponseEntity<?> createUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber,
										@RequestParam String email, @RequestParam String address, @RequestParam String profilePicture, @RequestParam LocalDate dateOfBirth,
										@RequestParam List<UUID> eventTypesIds , @RequestParam String username, @RequestParam String password) {

		List<EventType> interests = new ArrayList<>();
		for(UUID eventTypeId : eventTypesIds){
			interests.add(eventTypeService.getEventType(eventTypeId));
		}

		Profile profile;
		try {
			profile = profileService.createProfile(firstName, lastName, address, email, phoneNumber, profilePicture, dateOfBirth, interests);
		}catch(IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		User user;
		try {
			user = userService.createUser(username, password, profile);
		}catch(IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(Conversion.convertToDTO(user), HttpStatus.CREATED);

	}

	@PatchMapping(value = {"/change_password/{id}"})
	public ResponseEntity<?> changePassword(@PathVariable("id") UUID id,@RequestParam String oldPassword, @RequestParam String newPassword) {
		User user;
		try {
			user = userService.editUserPassword(id, oldPassword, newPassword);
		}catch(IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(Conversion.convertToDTO(user), HttpStatus.OK);
	}

	@DeleteMapping(value = {"/delete_user/{id}"})
	public boolean deleteUserByUsername(@PathVariable("id") UUID id) {
		return userService.deleteUser(id);
	}

	@GetMapping(value = {"/view_user/{id}"})
	public UserDTO viewUserByUsername(@PathVariable("id") UUID id) {
		return Conversion.convertToDTO(userService.getUser(id));
	}

    @DeleteMapping(value = {"/delete_user_username/{username}"})
	public boolean deleteUserByUsername(@PathVariable("username") String username) {
		return userService.deleteUserByUsername(username);
	}

    @GetMapping(value = {"/view_user_username/{username}"})
	public UserDTO viewUserByUsername(@PathVariable("username") String username) {
		return Conversion.convertToDTO(userService.getUserByUsername(username));
	}

    @GetMapping(value = {"/view_users", "/view_users/"})
	public List<UserDTO> viewUsers(){
		return userService.getAllUsers().stream().map(Conversion::convertToDTO).collect(Collectors.toList());
	}

}
