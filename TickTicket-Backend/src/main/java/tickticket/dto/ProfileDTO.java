package tickticket.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProfileDTO {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private LocalDate dateOfBirth;
    private List<UUID> interestIds = new ArrayList<>();
    private List<EventTypeDTO> interests = new ArrayList<>();

    public ProfileDTO(String firstName, String lastName, String address, String email, String phoneNumber,
           String profilePicture, LocalDate dateOfBirth, List<EventTypeDTO> interests){
            
            this.firstName=firstName;
            this.lastName = lastName;
            this.address=address;
            this.email=email;
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
