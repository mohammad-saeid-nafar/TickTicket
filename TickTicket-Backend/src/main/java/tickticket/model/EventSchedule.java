package tickticket.model;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class EventSchedule {

    public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }
    private UUID id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private boolean isRecurrent;
    // private List<DayOfWeek> recurrences;

    public EventSchedule() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isRecurrent() {
        return isRecurrent;
    }

    public void setRecurrent(boolean recurrent) {
        isRecurrent = recurrent;
    }

    // public List<DayOfWeek> getRecurrences() {
    //     return recurrences;
    // }

    // public void setRecurrences(List<DayOfWeek> recurrences) {
    //     this.recurrences = recurrences;
    // }

}