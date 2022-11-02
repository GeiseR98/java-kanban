import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    public static List<Task> history = new ArrayList<>();
    
   // public static InMemoryHistoryManager memoryHistory = new InMemoryHistoryManager();

    public void addHistory(Task task) {
        if (history.size() == 10) history.remove(0);
        if (task != null) history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    public void showHistory() {
        for (Task view : history) {
            System.out.println(view);
        }
    }
}
