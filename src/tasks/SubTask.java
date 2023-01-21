package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

    private Integer idMaster;
    private byte timeStatus;
    public SubTask(int id, String name, String description, Status status, LocalDateTime startTime, Duration duration, byte timeStatus, Integer idMaster) {
        super(id, name, description, status, startTime, duration);
        this.idMaster = idMaster;
        this.timeStatus = timeStatus;
    }

    public void setIdMaster(Integer idMaster) {
        this.idMaster = idMaster;
    }
    public void setTimeStatus(byte timeStatus) {
        this.timeStatus = timeStatus;
    }

    public Integer getIdMaster() {
        return idMaster;
    }
    public byte getTimeStatus() {
        return timeStatus;

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
