import java.util.ArrayList;
import java.util.List;

public interface TaskManager {


    Integer saveJustTask(String name, String description);
    Integer saveEpicTask(String name, String description);
    Integer saveSubTask(String name, String description, Integer idMaster);

    void printAllJustTask();
    ArrayList<JustTask> getListAllJustTask();

    void printAllEpicTask();
    ArrayList<EpicTask> getListAllEpicTask();

    void printAllSubTask();
    ArrayList<SubTask> getListSubTask();

    Task getTask(Integer id);

    void showTask(Integer id);
    void removeTask(Integer id);
    void removeAllTask();

    void changeStatus(Integer id, Status status);

    void changeDescription(Integer id, String description);

}
