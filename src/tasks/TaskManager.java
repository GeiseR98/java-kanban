package tasks;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    JustTask createJustTask(String name, String description);
    Integer addJustTask(JustTask justTask);
    EpicTask createEpicTask(String name, String description);
    Integer addEpicTask(EpicTask epicTask);
    SubTask createSubTask(String name, String description, Integer idMaster);
    Integer addSubTask(SubTask subTask);

    void printAllJustTask();
    ArrayList<Task> getListAllJustTask();

    void printAllEpicTask();
    ArrayList<EpicTask> getListAllEpicTask();

    void printAllSubTask();
    ArrayList<Task> getListAllSubTask();

    Task getTask(Integer id);

    void showTask(Integer id);
    void removeTask(Integer id);
    void removeAllTask();

    void changeStatus(Integer id, Status status);

    void changeDescription(Integer id, String description);

    List<Task>  getHistory();
}
