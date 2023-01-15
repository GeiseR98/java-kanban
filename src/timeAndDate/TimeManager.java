package timeAndDate;

import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface TimeManager {
    boolean checkingFreeTime(LocalDateTime startTime, LocalDateTime endTime);
    void addFixedTime();
    void addTuskTime();
    void addDailyTime();
    LocalDateTime findFreeTime(LocalDateTime startTime, Duration duration);
    List<Task> getPrioritizedTasks();
}
