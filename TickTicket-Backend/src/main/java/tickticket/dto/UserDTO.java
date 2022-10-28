package tickticket.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private UUID id;
	private String username;
	private String password;
    private LocalDate created;
	UUID profileId;
    private ProfileDTO profile;
	
	public UserDTO(UUID id, String username, String password, LocalDate created, ProfileDTO profile) {
		this.id = id;
		this.username=username;
		this.password=password;
        this.created=created;
        this.profile=profile;
	}

	public UUID getId() { return  id; }

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

    public LocalDate getCreatedDate(){
        return created;
    }

    public ProfileDTO getProfile() {
		return profile;
	}

}
