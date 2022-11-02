public class Manager {

    static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
