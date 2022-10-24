package tickticket.controller;

import java.util.*;

import tickticket.dto.*;
import tickticket.model.*;

public class Conversion {
    public static UserDTO convertToDTO(User user) {
		if(user == null) throw new IllegalArgumentException("User not found.");
		return new UserDTO(user.getUsername(),user.getPassword(),user.getCreated(),convertToDTO(user.getProfile()));
	}

    public static ProfileDTO convertToDTO(Profile profile) {
		if(profile == null) throw new IllegalArgumentException("Profile not found.");
        List<EventTypeDTO> interests = new ArrayList<EventTypeDTO>();

		for (EventType interest : profile.getInterests() ) {
			interests.add(convertToDTO(interest));
		}

		return new ProfileDTO(profile.getFirstName(), profile.getLastName(), profile.getAddress(), profile.getEmail(), profile.getPhoneNumber(),
				profile.getProfilePicture(), profile.getDateOfBirth(),interests );
	}

    public static EventTypeDTO convertToDTO(EventType eventType){
		if(eventType == null) throw new IllegalArgumentException("Event Type not found.");

        return new EventTypeDTO(eventType.getName(), eventType.getDescription(), eventType.getAgeRequirement());
    }

//	public static ReviewDTO convertToDTO(Review review) {
//		if(review==null) throw new IllegalArgumentException("Review not found.");
//		return new ReviewDTO(review.getTitle(), review.getRating(), review.getDescription(),
//				convertToDTO(review.getUser()), convertToDTO(review.getEvent()));
//	}

	public static EventScheduleDTO convertToDTO(EventSchedule eventSchedule){
		if(eventSchedule == null) throw new IllegalArgumentException("Event Schedule not found.");

        return new EventScheduleDTO(eventSchedule.getStartDateTime(), eventSchedule.getEndDateTime());
    }

	public static EventDTO convertToDTO(Event event){
		if(event == null) throw new IllegalArgumentException("Event not found");
		List<EventTypeDTO> eventTypes = new ArrayList<>();
		for(EventType type : event.getEventTypes()){
			eventTypes.add(convertToDTO(type));
		}
		return new EventDTO(event.getName(), event.getDescription(), event.getCapacity(), event.getCost(),
				event.getAddress(), event.getEmail(), event.getPhoneNumber(),
				convertToDTO(event.getOrganizer()), convertToDTO(event.getEventSchedule()),eventTypes);
	}

}
