import java.util.ArrayList;
import java.util.HashMap;

public interface Manager {
    HashMap<Integer, JustTask> justTask = new HashMap<>();
    HashMap<Integer, Epic> epicTask = new HashMap<>();
    HashMap<Integer, Subtask> subTask = new HashMap<>();
    int idTask = 0;

    Integer saveJustTask(String name, String description);


    Integer saveEpicTask(String name, String description);

    Integer saveSubTask(String name, String description, Integer idMaster);

    void printAllJustTask();
    ArrayList<JustTask> getListAllJustTask();

    void printAllEpicTask();
    ArrayList<Epic> getListAllEpicTask();

    void printAllSubTask();
    ArrayList<Subtask> getListSubTask();
    void showTask(Integer id);
    void removeTask(Integer id);
    void removeAllTask();
    void changeStatus(Integer id, String status);
    void changeDescription(Integer id, String description);
}
