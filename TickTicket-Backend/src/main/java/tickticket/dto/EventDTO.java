package tickticket.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EventDTO {
    private UUID id;
    private String name;
    private String description;
    private Integer capacity;
    private Double cost;
    private String address;
    private String email;
    private String phoneNumber;

    private UUID organizerId;
    private UserDTO organizer;

    private UUID eventScheduleId;
    private EventScheduleDTO eventSchedule;

    private List<UUID> eventTypeIds = new ArrayList<>();
    private List<EventTypeDTO> eventTypes = new ArrayList<>();

    private LocalDateTime start;
    private LocalDateTime end;

    public EventDTO(UUID id, String name, String description, Integer capacity,
        Double cost, String address, String email, String phoneNumber,
                    UserDTO organizer, EventScheduleDTO eventSchedule, List<EventTypeDTO> eventType) {
        this.id = id;
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

    public UUID getId() { return id;}

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
