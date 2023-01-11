package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class  Task {
    private int id;
    private String name;
    private String description;
    private Status status;
    private final Duration duration;
    private final LocalDateTime startTime;


    public Task(int id, String name, String description, Status status, LocalDateTime startTime, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    @Override
    public String toString() {
        return " - {" +
                "id'" + id + '\'' +
                " название: '" + name + '\'' +
                ", описание: '" + description + '\'' +
                ", статус: '" + status + '\'' +
                '}';
    }

}
