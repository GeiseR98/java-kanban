package tasks;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class EpicTask extends Task {

    private ArrayList<Integer> listIdSubtask;

    @Override
    public LocalDateTime getEndTime() {
        if ((getStartTime() != null) && (getDuration() != null)) {
            return super.getEndTime();
        }
        else {
            return null;
        }
    }

    private LocalDateTime endTime;

    public ArrayList<Integer> getListIdSubtask() {
        return listIdSubtask;
    }
    public EpicTask(int id,
                    String name,
                    String description,
                    Status status,
                    ArrayList<Integer> listIdSubtask) {
        super(id, name, description, status);
        this.listIdSubtask = listIdSubtask;
    }

    public EpicTask(int id,
                    String name,
                    String description,
                    Status status,
                    LocalDateTime startTime,
                    Duration duration,
                    LocalDateTime endTime,
                    ArrayList<Integer> listIdSubtask) {
        super(id, name, description, status, startTime, duration);
        this.endTime = endTime;
        this.listIdSubtask = listIdSubtask;
    }

    public void setListIdSubtask(ArrayList<Integer> listIdSubtask) {
        this.listIdSubtask = listIdSubtask;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    @Override
    public String toString() {
        return " - {" +
                "id'" + super.getId() + '\'' +
                " название: '" + super.getName() + '\'' +
                ", описание: '" + super.getDescription() + '\'' +
                ", статус: '" + super.getStatus() + '\'' +
                ", начало: '" + super.getStartTime() + '\'' +
                ", продолжительность: '" + super.getDuration() + '\'' +
                ", окончание: '" + super.getEndTime() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EpicTask)) return false;
        if (!super.equals(o)) return false;
        EpicTask epicTask = (EpicTask) o;
        return getListIdSubtask().equals(epicTask.getListIdSubtask()) && Objects.equals(getEndTime(), epicTask.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getListIdSubtask(), getEndTime());
    }
}
