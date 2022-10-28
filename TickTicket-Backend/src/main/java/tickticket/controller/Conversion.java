package tickticket.controller;

import java.util.*;

import tickticket.dto.*;
import tickticket.model.*;

public class Conversion {
    public static UserDTO convertToDTO(User user) {
		if(user == null) throw new IllegalArgumentException("User not found.");
		Profile profile = user.getProfile();
		UserDTO dto = new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getCreated(), profile == null ? null : convertToDTO(profile));
		dto.setProfileId(profile == null ? null : profile.getId());
		return dto;
	}

    public static ProfileDTO convertToDTO(Profile profile) {
		if(profile == null) throw new IllegalArgumentException("Profile not found.");
        List<EventTypeDTO> interests = new ArrayList<>();

		for (EventType interest : profile.getInterests() ) {
			interests.add(convertToDTO(interest));
		}

		ProfileDTO dto = new ProfileDTO(profile.getFirstName(), profile.getLastName(), profile.getAddress(), profile.getEmail(), profile.getPhoneNumber(),
				profile.getProfilePicture(), profile.getDateOfBirth(), interests);
		dto.setId(profile.getId());
		return dto;
	}

    public static EventTypeDTO convertToDTO(EventType eventType){
		if(eventType == null) throw new IllegalArgumentException("Event Type not found.");

		EventTypeDTO dto = new EventTypeDTO(eventType.getName(), eventType.getDescription(), eventType.getAgeRequirement());
		dto.setId(eventType.getId());
		return dto;
    }

	public static ReviewDTO convertToDTO(Review review) {
		if(review==null) throw new IllegalArgumentException("Review not found.");
		ReviewDTO reviewDTO = new ReviewDTO(review.getTitle(), review.getRating(), review.getDescription(),
				convertToDTO(review.getUser()), convertToDTO(review.getEvent()));
		reviewDTO.setId(review.getId());
		return reviewDTO;
	}

	public static EventScheduleDTO convertToDTO(EventSchedule eventSchedule){
		if(eventSchedule == null) throw new IllegalArgumentException("Event Schedule not found.");

		EventScheduleDTO eventScheduleDTO = new EventScheduleDTO(eventSchedule.getStartDateTime(), eventSchedule.getEndDateTime());
		eventScheduleDTO.setId(eventSchedule.getId());
		return eventScheduleDTO;
    }

	public static EventDTO convertToDTO(Event event){
		if(event == null) throw new IllegalArgumentException("Event not found");
		List<EventTypeDTO> eventTypes = new ArrayList<>();
		for(EventType type : event.getEventTypes()){
			eventTypes.add(convertToDTO(type));
		}
		return new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getCapacity(), event.getCost(),
				event.getAddress(), event.getEmail(), event.getPhoneNumber(),
				convertToDTO(event.getOrganizer()), convertToDTO(event.getEventSchedule()),eventTypes);
	}

}
