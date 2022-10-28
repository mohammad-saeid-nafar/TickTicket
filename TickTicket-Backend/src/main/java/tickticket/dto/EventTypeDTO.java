package tickticket.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EventTypeDTO {

    private UUID id;
    private String name;
    private String description;
    private int ageRequirement;

    public EventTypeDTO(String name, String description, int ageRequirement){
        this.name=name;
        this.description=description;
        this.ageRequirement=ageRequirement;
    }

    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }

    public int getAgeRequirement(){
        return ageRequirement;
    }
}
