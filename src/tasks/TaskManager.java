package tasks;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    JustTask createJustTask(String name, String description);
    Integer addJustTask(JustTask justTask) throws IOException;
    EpicTask createEpicTask(String name, String description);
    Integer addEpicTask(EpicTask epicTask) throws IOException;
    SubTask createSubTask(String name, String description, Integer idMaster);
    Integer addSubTask(SubTask subTask) throws IOException;

    void printAllJustTask();
    ArrayList<Task> getListAllJustTask();

    void printAllEpicTask();
    ArrayList<EpicTask> getListAllEpicTask();

    void printAllSubTask();
    ArrayList<Task> getListAllSubTask();

    Task getTask(Integer id) throws IOException;

    void showTask(Integer id);
    void removeTask(Integer id) throws IOException;
    void removeAllTask() throws IOException;

    void changeStatus(Integer id, Status status) throws IOException;

    void changeDescription(Integer id, String description) throws IOException;

    List<Task>  getHistory();
}
