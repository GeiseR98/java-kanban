package utilit;

import history.HistoryManager;
import history.InMemoryHistoryManager;
import tasks.InMemoryTaskManager;
import tasks.TaskManager;
import timeAndDate.InMemoryTimeManager;
import timeAndDate.TimeManager;

public class Manager {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
//    public static TimeManager getDefaultTime() {
//        return new InMemoryTimeManager();
//    }

}
