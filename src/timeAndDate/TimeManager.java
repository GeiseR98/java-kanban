package timeAndDate;

import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface TimeManager {
    void addPrioritizedTasks(Task task);

    boolean checkingFreeTime(LocalDateTime startTime, Duration duration);
    void addFixedTime(Task task);

    void addTuskTime(Task task);
    void addDailyTime();
    LocalDateTime findFreeTime(LocalDateTime startTime, Duration duration);
    LocalDateTime findFreeTime( Duration duration);
    List<Task> getPrioritizedTasks();
}
