package timeAndDate;

import java.time.Duration;
import java.time.LocalDateTime;

public interface TimeManager {
    boolean checkingFreeTime(LocalDateTime startTime, LocalDateTime endTime);
    void addFixedTime();
    void addTuskTime();
    void addDailyTime();
    LocalDateTime findFreeTime(LocalDateTime startTime, Duration duration);



}
