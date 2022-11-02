import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    List<Task> history = new ArrayList<>();

    Integer saveJustTask(String name, String description);
    Integer saveEpicTask(String name, String description);
    Integer saveSubTask(String name, String description, Integer idMaster);

    void printAllJustTask();
    ArrayList<JustTask> getListAllJustTask();

    void printAllEpicTask();
    ArrayList<EpicTask> getListAllEpicTask();

    void printAllSubTask();
    ArrayList<SubTask> getListSubTask();

    void showTask(Integer id);
    void removeTask(Integer id);
    void removeAllTask();

    void changeStatus(Integer id, Status status);

    void changeDescription(Integer id, String description);
    List<Task> getHistory();


}
