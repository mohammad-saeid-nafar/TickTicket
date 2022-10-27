package tickticket.service;

import java.time.LocalDate;
import java.util.List;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tickticket.dao.UserRepository;
import tickticket.model.Profile;
import tickticket.model.User;

@Service
public class UserService {
    
    @Autowired
	private UserRepository userRepository;

	@Transactional
	public User login(String username, String password) {

		if(!userRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("Invalid username");
		}

        User user = userRepository.findUserByUsername(username).orElse(null);
        if(user!=null && user.getPassword().equals(password))
            return user;

		throw new IllegalArgumentException("Incorrect password");

	}

    @Transactional
	public User createUser(String username, String password, Profile profile) {

		if(username==null || username.equals("")) throw new IllegalArgumentException("Username cannot be blank");

		if(password==null || password.equals("")) throw new IllegalArgumentException("Password cannot be blank");

		usernameIsValid(username);

		passwordIsValid(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setProfile(profile);
        user.setCreated(LocalDate.now());

		userRepository.save(user);

		return user;
	}

    @Transactional
	public User editUserPassword(UUID id, String oldPassword, String password) {
		User user = getUser(id);
		if(password==null || password.equals("")) throw new IllegalArgumentException("New password cannot be blank.");
		if(!oldPassword.equals(user.getPassword())) {
			throw new IllegalArgumentException("Old password entered is incorrect");
		}
		if(passwordIsValid(password)) {
			user.setPassword(password);
		}
		userRepository.save(user);
		return user;
	}

	@Transactional
	public boolean deleteUser(UUID id) {
		User user = getUser(id);
		userRepository.delete(user);
		return true;
	}

	@Transactional
	public boolean deleteUserByUsername(String username) {
		User user = getUserByUsername(username);
		userRepository.delete(user);
		return true;
	}

	@Transactional
	public User getUser(UUID id) {
		return userRepository.findById(id).orElseThrow(()
				-> new IllegalArgumentException("User " + id + " not found."));
	}

    @Transactional
	public User getUserByUsername(String username) {
		return userRepository.findUserByUsername(username).orElseThrow(()
				-> new IllegalArgumentException("User " + username + " not found."));
	}

	@Transactional
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

    private void usernameIsValid(String username) {
		if(userRepository.findUserByUsername(username).orElse(null)!=null) throw new IllegalArgumentException("Username is already taken");
	}

    private boolean passwordIsValid(String password){
		if (password.length()<8) throw new IllegalArgumentException("Password must have at least 8 characters");
		if (password.length()>20) throw new IllegalArgumentException("Password must not have more than 20 characters");

		boolean upperCaseFlag = false;
		boolean lowerCaseFlag = false;
		boolean numberFlag = false;

		for(int i=0; i<password.length(); i++) {
			if(Character.isUpperCase(password.charAt(i))) upperCaseFlag = true;
			else if(Character.isLowerCase(password.charAt(i))) lowerCaseFlag = true;
			else if(Character.isDigit(password.charAt(i))) numberFlag = true;
		}

		if(!upperCaseFlag) throw new IllegalArgumentException ("Password must contain at least one uppercase character");
		if(!lowerCaseFlag) throw new IllegalArgumentException ("Password must contain at least one lowercase character");
		if(!numberFlag) throw new IllegalArgumentException ("Password must contain at least one numeric character");

		return true;
	}
}
