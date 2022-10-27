package tickticket.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tickticket.dao.EventTypeRepository;
import tickticket.dao.ProfileRepository;
import tickticket.model.EventType;
import tickticket.model.Profile;

@Service
public class ProfileService {
    @Autowired
	ProfileRepository profileRepository;

	@Autowired
	EventTypeRepository eventTypeRepository;

	@Transactional
	public Profile createProfile(String firstName, String lastName, String address, String email, String phoneNumber, String profilePicture, LocalDate dateOfBirth, List<EventType> interests) {

		if(firstName ==null || firstName.equals(""))
			throw new IllegalArgumentException("First name cannot be blank.");

		if(lastName ==null || lastName.equals(""))
			throw new IllegalArgumentException("Last name cannot be blank.");

		if(address ==null || address.equals(""))
			throw new IllegalArgumentException("Address cannot be blank.");

        if(email ==null || email.equals(""))
            throw new IllegalArgumentException("Email cannot be blank.");

		if(phoneNumber ==null || phoneNumber.equals(""))
			throw new IllegalArgumentException("Phone number cannot be blank.");

        if(dateOfBirth == null)
            throw new IllegalArgumentException("Date of birth cannot be blank.");

		if(emailIsNotValid(email))
			throw new IllegalArgumentException("Invalid email.");
		
		Profile profile = new Profile();
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setAddress(address);
		profile.setEmail(email);
		profile.setPhoneNumber(phoneNumber);
		profile.setProfilePicture(profilePicture);
		profile.setDateOfBirth(dateOfBirth);
		profile.setInterests(interests);

		return profile;
    }

	@Transactional
	public Profile updateProfile(UUID id, String firstName, String lastName, String address, String email, String phoneNumber, String profilePicture, LocalDate dateOfBirth, List<EventType> interests) {

		Profile profile = getProfile(id);

		if(email != null && !email.equals("") && !email.equals(profile.getEmail())){
			if(emailIsNotValid(email)) throw new IllegalArgumentException("Invalid email");
			profile.setEmail(email);
		}

		if(firstName != null && !firstName.equals("")){
			profile.setFirstName(firstName);
		}

		if(lastName != null && !lastName.equals("")){
			profile.setLastName(lastName);
		}

		if(address != null && !address.equals("")){
			profile.setAddress(address);
		}

		if(phoneNumber != null && !phoneNumber.equals("")){
			profile.setPhoneNumber(phoneNumber);
		}

		if(profilePicture != null && !profilePicture.equals("")){
			profile.setProfilePicture(profilePicture);
		}

		if(dateOfBirth != null) {
			profile.setDateOfBirth(dateOfBirth);
		}

		profileRepository.save(profile);
		return profile;
	}


	@Transactional
	public Profile getProfile(UUID id) {
		return profileRepository.findById(id).orElseThrow(() ->
				new IllegalArgumentException("Profile " + id + " not found"));
	}

	@Transactional
	public Profile getProfileByEmail(String email) {
		return profileRepository.findProfileByEmail(email).orElseThrow(() ->
				new IllegalArgumentException("Profile " + email + " not found"));
	}

	@Transactional
	public List<Profile> getAllProfiles(){
		return profileRepository.findAll();
	}

    private boolean emailIsNotValid(String email) {
		if(profileRepository.findProfileByEmail(email).isPresent()){
			throw new IllegalArgumentException("User with email entered already exists.");
		}
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
				"[a-zA-Z0-9_+&*-]+)*@" + 
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
				"A-Z]{2,7}$"; 

		Pattern pat = Pattern.compile(emailRegex);
		return !pat.matcher(email).matches();
	}
}
