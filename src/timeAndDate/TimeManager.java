package timeAndDate;

import managers.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface TimeManager {
    void addPrioritizedTasks(Task task);
    void removeTaskFromPrioritizedTasks(Task task);
    void removeAllPrioritizedTasks();
    List<Task> getPrioritizedTasks();
    boolean checkingFreeTime(LocalDateTime startTime, Duration duration);
    void addFixedTime(Task task);
    void addTuskTime(Task task);
    void addDailyTime();
    void recoveryTimeTask(Task task, byte statusTime);
    byte getStatusTime(LocalDateTime startTime);
    LocalDateTime findFreeTime(LocalDateTime startTime, Duration duration);
    LocalDateTime findFreeTime( Duration duration);
    void cleaneTimeManager();

}
