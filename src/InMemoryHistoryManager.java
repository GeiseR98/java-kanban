import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    int storySize = 10;
    public static List<Task> history = new ArrayList<>();

    public void addHistory(Task task) {
        if (history.size() == storySize) {
            history.remove(0);
        }
        if (task != null) {
            history.add(task);
        }
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
