package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class JustTask extends Task{
    private byte timeStatus;

    public JustTask(int id, String name, String description, Status status, LocalDateTime startTime, Duration duration, byte timeStatus) {
        super(id, name, description, status, startTime, duration);
        this.timeStatus = timeStatus;
    }

    public byte getTimeStatus() {
        return timeStatus;
    }

    public void setTimeStatus(byte timeStatus) {
        this.timeStatus = timeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JustTask)) return false;
        JustTask justTask = (JustTask) o;
        return getTimeStatus() == justTask.getTimeStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTimeStatus());
    }
}
