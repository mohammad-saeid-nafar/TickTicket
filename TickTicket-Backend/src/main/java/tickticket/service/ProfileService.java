package tickticket.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tickticket.dao.ProfileRepository;
import tickticket.dto.ProfileDTO;
import tickticket.model.EventType;
import tickticket.model.Profile;

@Service
@AllArgsConstructor
public class ProfileService {

	private ProfileRepository profileRepository;
	private EventTypeService eventTypeService;

	@Transactional
	public Profile createProfile(ProfileDTO profileDTO) {
		String firstName = profileDTO.getFirstName();
		String lastName = profileDTO.getLastName();
		String address = profileDTO.getAddress();
		String email = profileDTO.getEmail();
		String phoneNumber = profileDTO.getPhoneNumber();
		String profilePicture = profileDTO.getProfilePicture();
		LocalDate dateOfBirth = profileDTO.getDateOfBirth();
		List<UUID> interests = profileDTO.getInterestIds();

		Profile profile = new Profile();

		if(email != null && !email.equals("")){
			if(emailIsNotValid(email))
				throw new IllegalArgumentException("Invalid email.");
			profile.setEmail(email);
		} else {
			throw new IllegalArgumentException("Email cannot be blank.");
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

		if (interests != null) {
			profile.setInterests(eventTypeService.getAllEventTypes(interests));
		}

		return profile;
    }

	@Transactional
	public Profile updateProfile(ProfileDTO profileDTO) {
		String firstName = profileDTO.getFirstName();
		String lastName = profileDTO.getLastName();
		String address = profileDTO.getAddress();
		String email = profileDTO.getEmail();
		String phoneNumber = profileDTO.getPhoneNumber();
		String profilePicture = profileDTO.getProfilePicture();
		LocalDate dateOfBirth = profileDTO.getDateOfBirth();

		Profile profile = getProfile(profileDTO.getId());

		if(email != null && !email.equals("") && !email.equals(profile.getEmail())){
			if(emailIsNotValid(email)) throw new IllegalArgumentException("Invalid email");
			profile.setEmail(email);
		}

		if(firstName != null && !firstName.equals("")){
			profile.setFirstName(firstName);
		} else {
			throw new IllegalArgumentException("The first name cannot be empty");
		}

		if(lastName != null && !lastName.equals("")){
			profile.setLastName(lastName);
		} else {
			throw new IllegalArgumentException("The last name cannot be empty");
		}

		if(address != null && !address.equals("")){
			profile.setAddress(address);
		} else {
			throw new IllegalArgumentException("The address cannot be empty");
		}

		if(phoneNumber != null && !phoneNumber.equals("")){
			profile.setPhoneNumber(phoneNumber);
		} else {
			throw new IllegalArgumentException("The phone number cannot be empty");
		}

		if(profilePicture != null){
			profile.setProfilePicture(profilePicture);
		}

		if(dateOfBirth != null) {
			profile.setDateOfBirth(dateOfBirth);
		} else {
			throw new IllegalArgumentException("The date of birth cannot be empty");
		}

		profileRepository.save(profile);
		return profile;
	}

	@Transactional
	public Profile addEventTypePreference(ProfileDTO profileDTO){
		Profile profile = getProfile(profileDTO.getId());
		if(profileDTO.getInterestIds() != null){
			List<EventType> eventTypes = eventTypeService.getAllEventTypes(profileDTO.getInterestIds());
			profile.setInterests(eventTypes);
			profileRepository.save(profile);
		}
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
