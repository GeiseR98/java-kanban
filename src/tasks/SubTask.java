package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

    private Integer idMaster;
    public SubTask(int id, String name, String description, Status status, LocalDateTime startTime, Duration duration, Integer idMaster) {
        super(id, name, description, status, startTime, duration);
        this.idMaster = idMaster;
    }

    public void setIdMaster(Integer idMaster) {
        this.idMaster = idMaster;
    }

    public Integer getIdMaster() {
        return idMaster;
    }

    @Override
    public String toString() {
        return " - {" +
                "id'" + super.getId() + '\'' +
                " название: '" + super.getName() + '\'' +
                ", описание: '" + super.getDescription() + '\'' +
                ", статус: '" + super.getStatus() + '\'' +
                '}';
    }
}
