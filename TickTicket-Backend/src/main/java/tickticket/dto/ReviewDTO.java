package tickticket.dto;

public class ReviewDTO {
    private String title;
    private int rating;
    private String description;
    private UserDTO user;
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
