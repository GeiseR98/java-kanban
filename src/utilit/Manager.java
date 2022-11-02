package utilit;

import History.HistoryManager;
import History.InMemoryHistoryManager;
import Tasks.InMemoryTaskManager;
import Tasks.TaskManager;

public class Manager {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
