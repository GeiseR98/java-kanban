package timeAndDate;

import java.time.LocalDateTime;

public interface TimeManager {
    boolean checkingFreeTime(LocalDateTime startTime, LocalDateTime endTime);
    void addFixedTime();
    void addTuskTime();
    void addDailyTime();
    void findFreeTime();



}
