package timeAndDate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTimeManager {

    byte statusTimeFree = 0;  // индекс свободного времени
    byte statusTimeDaily = 1; // индекс повседневных заданий
    byte statusTimeTusk = 2;  // индекс обычной задачи
    byte statusTimeFixed = 3; // индекс фиксированой задачи

    Map<String, Interval> day = new HashMap<>(96);


    class Interval {
       Duration periodichnost = Duration.ofMinutes(15);
       //periodov v den = 96;
    }
}
