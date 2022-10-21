package tickticket.dto;

import java.time.LocalDate;

public class UserDTO {
	private String username;
	private String password;
    private LocalDate created;
    private ProfileDTO profile;
	
	public UserDTO(String username, String password, LocalDate created, ProfileDTO profile) {
		this.username=username;
		this.password=password;
        this.created=created;
        this.profile=profile;
	}

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
