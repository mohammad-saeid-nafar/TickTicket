package tickticket.controller;

import java.time.LocalDate;
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
		User user = null;
		try {
			user = userService.login(username, password);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		if(user instanceof User) {

			return new ResponseEntity<>(Conversion.convertToDTO((User) user), HttpStatus.OK);
		}
		return null;
	}

    @PostMapping(value = {"/create_user/","/create_user"})
	public ResponseEntity<?> createUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber,
			@RequestParam String email, @RequestParam String address, @RequestParam String profilePicture, @RequestParam LocalDate dateOfBirth, 
			@RequestParam String eventTypeName ,@RequestParam String username, @RequestParam String password) {
		
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
		
		User user = null;
		try {
			user = userService.createUser(username, password, profile);
		}catch(IllegalArgumentException exception) {
			profileService.deleteByEmail(email);
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<>(Conversion.convertToDTO(user), HttpStatus.CREATED);

	}

    @DeleteMapping(value = {"/delete_user/{username}"})
	public boolean deleteUser(@PathVariable("username") String username) {
		return userService.deleteUser(username);
	}

    @GetMapping(value = {"/view_user/{username}"})
	public UserDTO viewUser(@PathVariable("username") String username) {
		return Conversion.convertToDTO(userService.getUser(username));
	}

    @GetMapping(value = {"/view_users", "/view_users/"})
	public List<UserDTO> viewUsers(){

		return userService.getAllUsers().stream().map(user ->
		Conversion.convertToDTO(user)).collect(Collectors.toList());
	
	}

    @PatchMapping(value = {"/change_password/{username}"})
	public ResponseEntity<?> changePassword(@PathVariable("username") String username,@RequestParam String oldPassword, @RequestParam String newPassword) {
		User user = userService.getUser(username);
		if(user==null) {
			return new ResponseEntity<>("User not found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(!oldPassword.equals(user.getPassword())) {
			return new ResponseEntity<>("Current password entered is incorrect", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			user=userService.editUserPassword(username, newPassword);
		}catch(IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(Conversion.convertToDTO(user), HttpStatus.OK);
	}
}
