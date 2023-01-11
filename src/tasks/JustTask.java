package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class JustTask extends Task{

    public JustTask(int id, String name, String description, Status status, LocalDateTime startTime, Duration duration) {
        super(id, name, description, status, startTime, duration);

    }
}
