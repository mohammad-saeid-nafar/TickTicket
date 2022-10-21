package tickticket.dto;

public class EventTypeDTO {

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
