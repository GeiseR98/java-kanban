package tasks;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {


    Integer addJustTask(String name, String description);
    Integer addEpicTask(String name, String description);
    Integer addSubTask(String name, String description, Integer idMaster);

    void printAllJustTask();
    ArrayList<Task> getListAllJustTask();

    void printAllEpicTask();
    ArrayList<EpicTask> getListAllEpicTask();

    void printAllSubTask();
    ArrayList<Task> getListSubTask();

    Task getTask(Integer id);

    void showTask(Integer id);
    void removeTask(Integer id);
    void removeAllTask();

    void changeStatus(Integer id, Status status);

    void changeDescription(Integer id, String description);

    List<Task>  getHistory();
}
