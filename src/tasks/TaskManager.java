package tasks;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    JustTask createJustTask(String name, String description, LocalDateTime startTime, Duration duration);
    JustTask createJustTask(String name, String description, Duration duration);

    Integer addJustTask(JustTask justTask);
    EpicTask createEpicTask(String name, String description);
    Integer addEpicTask(EpicTask epicTask);
    SubTask createSubTask(String name,
                          String description,
                          LocalDateTime startTime,
                          Duration duration,
                          Integer idMaster);
    SubTask createSubTask(String name,
                          String description,
                          Duration duration,
                          Integer idMaster);
    Integer addSubTask(SubTask subTask);

    void printAllJustTask();
    ArrayList<Task> getListAllJustTask();

    void printAllEpicTask();
    ArrayList<EpicTask> getListAllEpicTask();

    void printAllSubTask();
    ArrayList<Task> getListAllSubTask();

    Task getTask(Integer id);
    void removeTask(Integer id);
    void removeAllTask();

    void changeStatus(Integer id, Status status);

    void changeDescription(Integer id, String description);

    List<Task>  getHistory();
    byte getStatusTime(LocalDateTime startTime);
    List<Task> getPrioritizedTasks();
}
