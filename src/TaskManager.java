import java.util.ArrayList;

public interface TaskManager {


    Integer saveJustTask(String name, String description);
    Integer saveEpicTask(String name, String description);
    Integer saveSubTask(String name, String description, Integer idMaster);

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


}
