package timeAndDate;

import tasks.InMemoryTaskManager;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class InMemoryTimeManager implements TimeManager{

    final byte statusTimeFree = 0;  // индекс свободного времени
    final byte statusTimeDaily = 1; // индекс повседневных заданий
    final byte statusTimeTusk = 2;  // индекс обычной задачи
    final byte statusTimeFixed = 3; // индекс фиксированой задачи

    Map<LocalDate, Day> year = new HashMap<>();
    Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }
    @Override
    public void addPrioritizedTasks(Task task) {
        prioritizedTasks.add(task);
    }
    @Override
    public void addFixedTime(Task task) {
        LocalDateTime endTime = task.getStartTime().plus(task.getDuration());
        LocalDateTime firstInterval = LocalDateTime.of(task.getStartTime().toLocalDate(), searchNearestInterval(task.getStartTime()));
        LocalDateTime lastInterval = LocalDateTime.of(endTime.toLocalDate(), searchNearestInterval(endTime));
        LocalDateTime nextInterval = firstInterval;
        while (nextInterval.isBefore(lastInterval) || nextInterval.equals(lastInterval)) {
            if (!year.containsKey(nextInterval.toLocalDate())) {
                year.put(nextInterval.toLocalDate(), new Day());
            }
            if (!year.get(nextInterval.toLocalDate()).day.containsKey(nextInterval.toLocalTime())) {
                year.get(nextInterval.toLocalDate()).day.put(nextInterval.toLocalTime(), new Interval(statusTimeFixed, task.getId()));
            }
            if (year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus == statusTimeFree ||
                year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus == statusTimeDaily
            ) {
                year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus = statusTimeFixed;
                year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).idTask = task.getId();
                nextInterval = nextInterval.plusMinutes(15);
            }
        }
    }

    @Override
    public boolean checkingFreeTime(LocalDateTime startTime, LocalDateTime endTime) {
        // проверяет свободно ли время в указанном интервале
        boolean isFree = false;
        LocalTime firstTime = searchNearestInterval(startTime);
        LocalTime secondTime = searchNearestInterval(endTime);
        while (firstTime.isBefore(secondTime) || firstTime.equals(secondTime)) {
            if (!(year.get(startTime.toLocalDate()).day.containsKey(firstTime)) || year.get(startTime.toLocalDate()).day.get(firstTime).timeStatus == statusTimeFree) {
                isFree = true;
                firstTime = firstTime.plusMinutes(15);
            } else if (year.get(startTime.toLocalDate()).day.get(firstTime).timeStatus == statusTimeDaily) {
                System.out.println("Обычно в это время у вас ");
                InMemoryTaskManager.showTask(year.get(startTime.toLocalDate()).day.get(firstTime).idTask);
                // добавить в следующх обновлениях флажек на "заменить/не заменять"
                isFree = true;
                firstTime = firstTime.plusMinutes(15);
            } else if (year.get(startTime.toLocalDate()).day.get(firstTime).timeStatus == statusTimeTusk || year.get(startTime.toLocalDate()).day.get(firstTime).timeStatus == statusTimeFixed) {
                System.out.println("В это время у вас:");
                InMemoryTaskManager.showTask(year.get(startTime.toLocalDate()).day.get(firstTime).idTask);
                isFree = false;
                return false;
                // для задач без фиксированого времени предлагать ближайшее свободное
                
            }
        }
        return isFree;
    }
    @Override
    public LocalDateTime findFreeTime(LocalDateTime startTime, Duration duration) {
        // ищет свободное время
        LocalDateTime firstFreeTime = LocalDateTime.of(startTime.toLocalDate(), searchNearestInterval(startTime));
        LocalDateTime nextTime = firstFreeTime;
        int amountInterval = (int) Math.ceil(duration.toMinutes() / 15.0);
        int amountFreeInterval = 0;
        while (amountFreeInterval <= amountInterval) {
            if (!(year.get(nextTime.toLocalDate()).day.containsKey(nextTime.toLocalTime())) ||
            year.get(nextTime.toLocalDate()).day.get(nextTime.toLocalTime()).timeStatus == statusTimeFree) {
                if (amountFreeInterval == 0) {
                    firstFreeTime = nextTime;
                }
                if (amountFreeInterval != amountInterval) {
                    nextTime = nextTime.plusMinutes(15);
                }
                amountFreeInterval += 1;
            } else {
                amountFreeInterval = 0;
            }
        }
        return firstFreeTime;
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
    public void addTuskTime() {
        // добаввляет задачу подбирая под неё первое свободное время
    }

    @Override
    public void addDailyTime() {
        // метод будет добавлять повседневное занятие
        // начиная от new до конца "года", или указанного срока
        // можно добавить реализацию с графиком...
    }




    class Interval {
       byte timeStatus;
       int idTask;

       public Interval(byte timeStatus, int idTask) {
           this.timeStatus = timeStatus;
           this.idTask = idTask;
       }
    }
    class Day {
        Map<LocalTime, Interval> day;

        public Day(Map<LocalTime, Interval> day) {
            this.day = day;
        }
        public Day() {
        }
    }
}
