package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class  Task {
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, duration, startTime);
    }

    private int id;
    private String name;
    private String description;
    private Status status;
    private Duration duration;
    private LocalDateTime startTime;


    public Task(int id, String name, String description, Status status, LocalDateTime startTime, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }
    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
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
    public void setStartTime(LocalDateTime startTime) { // проверять возможность
        this.startTime = startTime;
    }
    public void setDuration(Duration duration) {        // проверять возможность
        this.duration = duration;
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
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public Duration getDuration() {
        return duration;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getId() == task.getId() && getName().equals(task.getName())
                && getDescription().equals(task.getDescription()) && getStatus() == task.getStatus();
    }
}
