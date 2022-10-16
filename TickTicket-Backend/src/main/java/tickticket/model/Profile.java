package tickticket.model;
import javax.persistence.*;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
public class Profile {

    private UUID id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private LocalDate dateOfBirth;
    private List<EventType> interests;

    public Profile() { }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public List<EventType> getInterests() {
        return interests;
    }

    public void setInterests(List<EventType> interests) {
        this.interests = interests;
    }
}
