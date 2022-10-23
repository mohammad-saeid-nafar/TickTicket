package tickticket.model;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Event {

    private UUID id;
    private String name;
    private String description;
    private int capacity;
    private double cost;
    private String address;
    private String email;
    private String phoneNumber;
    private User organizer;
    private List<EventType> eventTypes;
    private EventSchedule eventSchedule;

    public Event() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    @OneToOne
    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    @ManyToMany
    public List<EventType> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public EventSchedule getEventSchedule() {
        return eventSchedule;
    }

    public void setEventSchedule(EventSchedule eventSchedule) {
        this.eventSchedule = eventSchedule;
    }
}
