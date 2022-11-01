import java.util.List;

public interface HistoryManager {
    void add(JustTask justTask);
    List<JustTask>  getHistory();
}
