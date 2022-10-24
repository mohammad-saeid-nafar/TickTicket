package tickticket.dto;

import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class EventDTO {
    private String name;
    private String description;
    private Integer capacity;
    private Double cost;
    private String address;
    private String email;
    private String phoneNumber;
    private UserDTO organizer;
    private EventScheduleDTO eventSchedule;
    private List<EventTypeDTO> eventTypes;


    public EventDTO(String name, String description, Integer capacity, 
        Double cost, String address, String email, String phoneNumber,
                    UserDTO organizer, EventScheduleDTO eventSchedule, List<EventTypeDTO> eventType) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.cost = cost;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.organizer = organizer;
        this.eventSchedule = eventSchedule;
        this.eventTypes = eventType;
    }

    public EventDTO(String name, String description, Integer capacity,
        Double cost, String address, String email, String phoneNumber, UserDTO organizer, List<EventTypeDTO> eventType ) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.cost = cost;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.organizer = organizer;
        this.eventTypes = eventType;
    }

    public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

    public Integer capacity(){
        return capacity;
    }

    public Double cost(){
        return cost;
    }

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

    public UserDTO getOrganizer(){
        return organizer;
    }

    public EventScheduleDTO getEventSchedule() { return eventSchedule; }

    public List<EventTypeDTO> getEventType(){
        return eventTypes;
    }
}
