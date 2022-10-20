package tickticket.service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import tickticket.dao.EventTypeRepository;
import tickticket.dao.ProfileRepository;
import tickticket.model.EventType;
import tickticket.model.Profile;

public class ProfileService {
    @Autowired
	ProfileRepository profileRepository;

	@Autowired
	EventTypeRepository eventTypeRepository;


	@Transactional
	public Profile createProfile(String firstName, String lastName, String address, String email, String phoneNumber, String profilePicture, LocalDate dateOfBirth, List<EventType> interests) {

		if(firstName ==null || firstName =="") 
			throw new IllegalArgumentException("First name cannot be blank.");

		if(lastName ==null || lastName =="") 
			throw new IllegalArgumentException("Last name cannot be blank.");

		if(address ==null || address =="") 
			throw new IllegalArgumentException("Address cannot be blank.");

        if(email ==null || email =="") 
            throw new IllegalArgumentException("Email cannot be blank.");

		if(phoneNumber ==null || phoneNumber =="") 
			throw new IllegalArgumentException("Phone number cannot be blank.");

        if(dateOfBirth == null)
            throw new IllegalArgumentException("Date of birth cannot be blank.");

		if(!emailIsValid(email)) 
			throw new IllegalArgumentException("Invalid email.");
		
		if(profileRepository.findProfileByEmail(email)!=null) {
			throw new IllegalArgumentException("User with email entered already exists.");
		}
		
		Profile profile = new Profile();
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setAddress(address);
		profile.setEmail(email);
		profile.setPhoneNumber(phoneNumber);
		profile.setProfilePicture(profilePicture);
		profile.setDateOfBirth(dateOfBirth);
		profile.setInterests(interests);

		profileRepository.save(profile);

		return profile;
    }

    @Transactional
	public boolean deleteByEmail(String email) {
		Profile profile = profileRepository.findProfileByEmail(email);
		if(profile!=null) { 
			profileRepository.delete(profile);
			return true;
		}
		return false;
	}

    private boolean emailIsValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
				"[a-zA-Z0-9_+&*-]+)*@" + 
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
				"A-Z]{2,7}$"; 

		Pattern pat = Pattern.compile(emailRegex); 

		return pat.matcher(email).matches(); 
	}
}
