package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

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
}
