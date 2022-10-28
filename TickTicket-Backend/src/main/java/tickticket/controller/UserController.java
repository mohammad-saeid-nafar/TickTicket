package tickticket.controller;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tickticket.model.User;
import tickticket.dto.UserDTO;
import tickticket.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
		User user;
		try {
			user = userService.login(username, password);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(Conversion.convertToDTO(user), HttpStatus.OK);

	}

    @PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
		User user;
		try {
			user = userService.createUser(userDTO);
		}catch(IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(Conversion.convertToDTO(user), HttpStatus.CREATED);

	}

	@PatchMapping(value = {"/change-password/{id}"})
	public ResponseEntity<?> changePassword(@PathVariable UUID id, @RequestParam String oldPassword, @RequestParam String newPassword) {
		User user;
		try {
			user = userService.editUserPassword(id, oldPassword, newPassword);
		}catch(IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(Conversion.convertToDTO(user), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public boolean deleteUserByUsername(@PathVariable UUID id) {
		return userService.deleteUser(id);
	}

	@GetMapping("/{id}")
	public UserDTO viewUserByUsername(@PathVariable UUID id) {
		return Conversion.convertToDTO(userService.getUser(id));
	}

    @DeleteMapping(value = {"/username/{username}"})
	public boolean deleteUserByUsername(@PathVariable String username) {
		return userService.deleteUserByUsername(username);
	}

    @GetMapping(value = {"/username/{username}"})
	public UserDTO viewUserByUsername(@PathVariable String username) {
		return Conversion.convertToDTO(userService.getUserByUsername(username));
	}

    @GetMapping
	public List<UserDTO> viewUsers(){
		return userService.getAllUsers().stream().map(Conversion::convertToDTO).collect(Collectors.toList());
	}

}
