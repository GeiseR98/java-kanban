import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    HashMap<Integer, JustTask> justTask = new HashMap<>();
    HashMap<Integer, EpicTask> epicTask = new HashMap<>();
    HashMap<Integer, SubTask> subTask = new HashMap<>();

    List<Task> history = new ArrayList<>();

    int idTask = 0;

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
