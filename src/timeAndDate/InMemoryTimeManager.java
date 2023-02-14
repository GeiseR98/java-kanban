package timeAndDate;

import tasks.InMemoryTaskManager;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class InMemoryTimeManager implements TimeManager{

    public final static byte statusTimeFree = 0;  // индекс свободного времени
    public final static byte statusTimeDaily = 1; // индекс повседневных заданий
    public final static byte timeStatusTusk = 2;  // индекс обычной задачи
    public final static byte timeStatusFixed = 3; // индекс фиксированой задачи

    static Map<LocalDate, Day> year = new HashMap<>();
    static Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(InMemoryTimeManager.prioritizedTasks);
    }
    @Override
    public void addPrioritizedTasks(Task task) {
        prioritizedTasks.add(task);
    }
    @Override
    public void removeTaskFromPrioritizedTasks(Task task) {
        prioritizedTasks.remove(task);
    }
    @Override
    public void removeAllPrioritizedTasks() {
        prioritizedTasks.clear();
    }
    @Override
    public void cleaneTimeManager() {
        year.clear();
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
                year.get(nextInterval.toLocalDate()).day.put(nextInterval.toLocalTime(), new Interval(timeStatusFixed, task.getId()));
            }
            if (year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus == statusTimeFree ||
                year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus == statusTimeDaily
            ) {
                year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus = timeStatusFixed;
                year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).idTask = task.getId();
                nextInterval = nextInterval.plusMinutes(15);
            }
        }
    }
    @Override
    public void addTuskTime(Task task) {
        LocalDateTime endTime = task.getStartTime().plus(task.getDuration());
        LocalDateTime firstInterval = LocalDateTime.of(task.getStartTime().toLocalDate(), searchNearestInterval(task.getStartTime()));
        LocalDateTime lastInterval = LocalDateTime.of(endTime.toLocalDate(), searchNearestInterval(endTime));
        LocalDateTime nextInterval = firstInterval;
        while (nextInterval.isBefore(lastInterval) || nextInterval.equals(lastInterval)) {
            if (!year.containsKey(nextInterval.toLocalDate())) {
                year.put(nextInterval.toLocalDate(), new Day());
            }
            if (!year.get(nextInterval.toLocalDate()).day.containsKey(nextInterval.toLocalTime())) {
                year.get(nextInterval.toLocalDate()).day.put(nextInterval.toLocalTime(), new Interval(timeStatusTusk, task.getId()));
            }
            if (year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus == statusTimeFree ||
                    year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus == statusTimeDaily
            ) {
                year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus = timeStatusTusk;
                year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).idTask = task.getId();
                nextInterval = nextInterval.plusMinutes(15);
            }
        }
    }
    @Override
    public void recoveryTimeTask(Task task, byte statusTime) {
        LocalDateTime endTime = task.getStartTime().plus(task.getDuration());
        LocalDateTime firstInterval = LocalDateTime.of(task.getStartTime().toLocalDate(), searchNearestInterval(task.getStartTime()));
        LocalDateTime lastInterval = LocalDateTime.of(endTime.toLocalDate(), searchNearestInterval(endTime));
        LocalDateTime nextInterval = firstInterval;
        while (nextInterval.isBefore(lastInterval) || nextInterval.equals(lastInterval)) {
            if (!year.containsKey(nextInterval.toLocalDate())) {
                year.put(nextInterval.toLocalDate(), new Day());
            }
            year.get(nextInterval.toLocalDate()).day.put(nextInterval.toLocalTime(), new Interval(statusTime, task.getId()));
            nextInterval = nextInterval.plusMinutes(15);
        }
    }
    @Override
    public boolean checkingFreeTime(LocalDateTime startTime, Duration duration) {
        // проверяет свободно ли время в указанном интервале
        LocalDateTime endTime = startTime.plus(duration);
        LocalDateTime firstInterval = LocalDateTime.of(startTime.toLocalDate(), searchNearestInterval(startTime));
        LocalDateTime lastInterval = LocalDateTime.of(endTime.toLocalDate(), searchNearestInterval(endTime));
        LocalDateTime nextInterval = firstInterval;
        boolean isFree = false;
        while (nextInterval.isBefore(lastInterval) || nextInterval.equals(lastInterval)) {
            if (!year.containsKey(nextInterval.toLocalDate())) {
                year.put(nextInterval.toLocalDate(), new Day());
            }
            if (!year.get(nextInterval.toLocalDate()).day.containsKey(nextInterval.toLocalTime())) {
                year.get(nextInterval.toLocalDate()).day.put(nextInterval.toLocalTime(), new Interval(statusTimeFree));
            }
            if (year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus == statusTimeFree) {
                isFree = true;
                nextInterval = nextInterval.plusMinutes(15);
            } else if (year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus == statusTimeDaily) {
                System.out.println("Обычно в это время у вас ");
                InMemoryTaskManager.showTask(year.get(nextInterval.toLocalDate()).day.get(nextInterval).idTask);
                // добавить в следующих обновлениях флажок на "заменить/переместить"
                isFree = true;
                nextInterval = nextInterval.plusMinutes(15);
            } else if (year.get(nextInterval.toLocalDate()).day.get(nextInterval.toLocalTime()).timeStatus == timeStatusTusk) {
                isFree = false;
                break;
                // добавить вопрос о перемещении
            } else {
                isFree = false;
                break;
            }
        }
        return isFree;
    }
    @Override
    public LocalDateTime findFreeTime(LocalDateTime startTime, Duration duration) {
        // ищет свободное время
        LocalDateTime firstFreeInterval = LocalDateTime.of(startTime.toLocalDate(), searchNearestInterval(startTime));
        LocalDateTime nextFreeInterval = firstFreeInterval;
        int amountInterval = (int) Math.ceil((Duration.between(firstFreeInterval, startTime.plus(duration))).toMinutes() / 15.0);
        int amountFreeInterval = 0;
        while (amountFreeInterval < amountInterval) {
            if (!year.containsKey(nextFreeInterval.toLocalDate())) {
                year.put(nextFreeInterval.toLocalDate(), new Day());
            }
            if (!year.get(nextFreeInterval.toLocalDate()).day.containsKey(nextFreeInterval.toLocalTime())) {
                year.get(nextFreeInterval.toLocalDate()).day.put(nextFreeInterval.toLocalTime(), new Interval(statusTimeFree));
            }
            if (year.get(nextFreeInterval.toLocalDate()).day.get(nextFreeInterval.toLocalTime()).timeStatus == statusTimeFree) {
                amountFreeInterval += 1;
                nextFreeInterval = nextFreeInterval.plusMinutes(15);
            } else {
                amountFreeInterval = 0;
                nextFreeInterval = nextFreeInterval.plusMinutes(15);
                firstFreeInterval = nextFreeInterval;
            }
        }
        return firstFreeInterval;
    }
    @Override
    public LocalDateTime findFreeTime(Duration duration) {
        // ищет свободное время
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(15);
        LocalDateTime firstFreeInterval = LocalDateTime.of(startTime.toLocalDate(), searchNearestInterval(startTime));
        LocalDateTime nextFreeInterval = firstFreeInterval;
        int amountInterval = (int) Math.ceil((Duration.between(firstFreeInterval, startTime.plus(duration))).toMinutes() / 15.0);
        int amountFreeInterval = 0;
        while (amountFreeInterval < amountInterval) {
            if (!year.containsKey(nextFreeInterval.toLocalDate())) {
                year.put(nextFreeInterval.toLocalDate(), new Day());
            }
            if (!year.get(nextFreeInterval.toLocalDate()).day.containsKey(nextFreeInterval.toLocalTime())) {
                year.get(nextFreeInterval.toLocalDate()).day.put(nextFreeInterval.toLocalTime(), new Interval(statusTimeFree));
            }
            if (year.get(nextFreeInterval.toLocalDate()).day.get(nextFreeInterval.toLocalTime()).timeStatus == statusTimeFree) {
                amountFreeInterval += 1;
                nextFreeInterval = nextFreeInterval.plusMinutes(15);
            } else {
                amountFreeInterval = 0;
                nextFreeInterval = nextFreeInterval.plusMinutes(15);
                firstFreeInterval = nextFreeInterval;
            }
        }
        return firstFreeInterval;
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
    public byte getStatusTime(LocalDateTime startTime) {
        return year.get(startTime.toLocalDate()).day.get(searchNearestInterval(startTime)).timeStatus;
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
       public Interval(byte timeStatus) {
           this.timeStatus = timeStatus;
       }
    }
    class Day {
        Map<LocalTime, Interval> day = new HashMap<>();

        public Day(Map<LocalTime, Interval> day) {
            this.day = new HashMap<>();
        }
        public Day() {
        }
    }
}
