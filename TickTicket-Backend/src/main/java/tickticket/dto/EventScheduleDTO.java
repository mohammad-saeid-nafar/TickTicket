package tickticket.dto;


import java.time.LocalDateTime;
import java.util.List;

import tickticket.model.EventSchedule.DayOfWeek;

public class EventScheduleDTO {

    private LocalDateTime start;
    private LocalDateTime end;
    private boolean isRecurrent;
    private List<DayOfWeek> recurrences;

    public EventScheduleDTO(LocalDateTime start, LocalDateTime end, boolean isRecurrent, List<DayOfWeek> recurrences){
        this.start = start;
        this.end = end;
        this.isRecurrent = isRecurrent;
        this.recurrences = recurrences;
    }

    public LocalDateTime getStart(){
        return start;
    }
    
    public LocalDateTime getEnd(){
        return end;
    }

    public boolean getIsRecurrent(){
        return isRecurrent;
    }

    public List<DayOfWeek> getRecurrences(){
        return recurrences;
    }
    
}
