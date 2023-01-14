package timeAndDate;

import tasks.InMemoryTaskManager;
import tasks.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTimeManager implements TimeManager{

    final byte statusTimeFree = 0;  // индекс свободного времени
    final byte statusTimeDaily = 1; // индекс повседневных заданий
    final byte statusTimeTusk = 2;  // индекс обычной задачи
    final byte statusTimeFixed = 3; // индекс фиксированой задачи

    Map<LocalTime, Interval> day = new HashMap<>(96);


    @Override
    public boolean checkingFreeTime(LocalDateTime startTime, LocalDateTime endTime) {
        // проверяет свободно ли время в указанном интервале
        boolean isFree = false;
        LocalTime firstTime = searchNearestInterval(startTime);
        LocalTime secondTime = searchNearestInterval(endTime);
        while (firstTime.isBefore(secondTime) || firstTime.equals(secondTime)) {
            if (day.containsKey(firstTime) || day.get(firstTime).status == statusTimeFree) {
                isFree = true;
                firstTime.plusMinutes(15);
            } else if (day.get(firstTime).status == statusTimeDaily) {
                System.out.println("Обычно в это время у вас ");
                InMemoryTaskManager.showTask(day.get(firstTime).idTask);
                // добавить в следующх обновлениях флажек на "заменить/не заменять"
                isFree = true;
                firstTime.plusMinutes(15);
            } else if (day.get(firstTime).status == statusTimeTusk || day.get(firstTime).status == statusTimeFixed) {
                System.out.println("В это время у вас:");
                InMemoryTaskManager.showTask(day.get(firstTime).idTask);
                isFree = false;
                return false;
                // для задач без фиксированого времени предлагать ближайшее свободное
                
            }
        }
        return isFree;
    }
    private LocalTime searchNearestInterval(LocalDateTime localDateTime) {
        LocalTime time = localDateTime.toLocalTime();
        if (time.getMinute() >= 15) {
            if (time.getMinute() >= 30) {
                if (time.getMinute() >= 45) {
                    return LocalTime.of(time.getHour(), 45);
                }
                return LocalTime.of(time.getHour(), 30);
            }
            return LocalTime.of(time.getHour(), 15);
        } else {
            return LocalTime.of(time.getHour(), 00);
        }
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
       byte status;
       int idTask;

       public Interval(byte status, int idTask) {
           this.status = status;
           this.idTask = idTask;
       }
    }
}
