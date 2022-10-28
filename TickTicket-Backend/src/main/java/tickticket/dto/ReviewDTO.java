package tickticket.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private UUID id;
    private String title;
    private int rating;
    private String description;

    private UUID userId;
    private UserDTO user;

    private UUID eventId;
    private EventDTO event;

    public ReviewDTO(String title, int rating, String description, UserDTO user, EventDTO event) {

        this.title = title;
        this.rating = rating;
        this.description = description;
        this.user = user;
        this.event = event;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public UserDTO getUser() {
        return user;
    }

    public EventDTO event() {
        return event;
    }

}
