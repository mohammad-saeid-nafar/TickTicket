package tickticket.dto;


import java.time.LocalDateTime;

public class EventScheduleDTO {

    private LocalDateTime start;
    private LocalDateTime end;

    public EventScheduleDTO(LocalDateTime start, LocalDateTime end){
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart(){
        return start;
    }
    
    public LocalDateTime getEnd(){
        return end;
    }

    
}
