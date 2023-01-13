package timeAndDate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTimeManager implements TimeManager{

    byte statusTimeFree = 0;  // индекс свободного времени
    byte statusTimeDaily = 1; // индекс повседневных заданий
    byte statusTimeTusk = 2;  // индекс обычной задачи
    byte statusTimeFixed = 3; // индекс фиксированой задачи

    Map<String, Interval> day = new HashMap<>(96);

    @Override
    public boolean checkingFreeTime(LocalDateTime startTime, LocalDateTime endTime) {
        // проверяет свободно ли время в указанном интервале
        return false;
    }

    @Override
    public void addFixedTime() {

    }

    @Override
    public void addTuskTime() {
        // добаввляет задачу подбирая под неё первое свободное время
    }

    @Override
    public void addDailyTime() {
        // метод будет добавлять повседневное занятие
        // начиная от new до конца "года", или указанного срока
        // можно добавить реализацию с графиком...
    }

    @Override
    public void findFreeTime() {
        // щет свободное время
    }


    class Interval {
       Duration periodichnost = Duration.ofMinutes(15);
       //periodov v den = 96;
    }
}
