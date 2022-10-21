package tickticket.dto;

import java.time.LocalDate;
import java.util.List;

public class ProfileDTO {

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private LocalDate dateOfBirth;
    private List<EventTypeDTO> interests;

    public ProfileDTO(String firstName, String lastName, String address, String email, String phoneNumber,
           String profilePicture, LocalDate dateOfBirth, List<EventTypeDTO> interests){
            
            this.firstName=firstName;
            this.lastName = lastName;
            this.address=address;
            this.phoneNumber=phoneNumber;
            this.profilePicture=profilePicture;
            this.dateOfBirth=dateOfBirth;
            this.interests=interests;
    }

    public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public String getAddress() {
		return address;
	}


	public String getProfilePicture() {
		return profilePicture;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public String getEmail() {
		return email;
	}

    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }

    public List<EventTypeDTO> getInterests(){
        return interests;
    }
    
}
