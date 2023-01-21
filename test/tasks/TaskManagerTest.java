package tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeAndDate.InMemoryTimeManager;
import timeAndDate.TimeManager;
import utilit.Manager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

    T taskManager;
    InMemoryTimeManager timeManager = new InMemoryTimeManager();

    @Test
    public void shouldCreateJustTaskWithStartTime() {
        String name = "название";
        String description = "описание";
        Duration duration = Duration.ofMinutes(29);
        LocalDateTime startTime = timeManager.findFreeTime(duration);
        JustTask justTask = taskManager.createJustTask(name, description, startTime, duration);
        assertEquals(startTime, justTask.getStartTime());
        assertEquals(name, justTask.getName());
        assertEquals(description, justTask.getDescription());
        assertEquals(Status.NEW, justTask.getStatus());
        assertEquals(duration, justTask.getDuration());
        assertEquals(InMemoryTimeManager.timeStatusFixed, justTask.getTimeStatus());
    }
    @Test
    public void shouldCreateJustTaskWithoutStartTime() {
        String name = "название";
        String description = "описание";
        Duration duration = Duration.ofMinutes(29);
        JustTask justTask = taskManager.createJustTask(name, description, Duration.ofMinutes(29));
        assertNotNull(justTask.getStartTime());
        assertEquals(name, justTask.getName());
        assertEquals(description, justTask.getDescription());
        assertEquals(Status.NEW, justTask.getStatus());
        assertEquals(duration, justTask.getDuration());
        assertEquals(InMemoryTimeManager.timeStatusTusk, justTask.getTimeStatus());
    }
    @Test
    void shouldJustTaskAddInListJustTasks() {
        taskManager.addJustTask(taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(29)));
        List<JustTask> justTasks = taskManager.getListAllJustTask();
        assertFalse(justTasks.isEmpty());
        assertEquals(1, justTasks.size());
     }
    @Test
    void shouldCreateEpicTask() {
        String name = "название";
        String description = "описание";
        EpicTask epicTask = taskManager.createEpicTask(name, description);
        assertNotNull(epicTask);
        assertEquals(name, epicTask.getName());
        assertEquals(Status.NEW, epicTask.getStatus());
        assertEquals(description, epicTask.getDescription());
    }
    @Test
    void shouldEpicTaskAddInListEpicTasks() {
        taskManager.addEpicTask(taskManager.createEpicTask("Epic", "description"));
        List<EpicTask> epicTasks = taskManager.getListAllEpicTask();
        assertFalse(epicTasks.isEmpty());
        assertEquals(1, epicTasks.size());
    }
    @Test
    void subtaskWillNotBeCreatedWithAnIncorrectEpicNumber() {
        String name = "название";
        String description = "описание";
        Integer idMaster = -1;
        Duration duration = Duration.ofMinutes(10);
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> taskManager.createSubTask(name, description, duration, idMaster)
                );
        Assertions.assertEquals("Такого эпика не существует, создайте сначала эпик", ex.getMessage());
    }

    @Test
    public void shouldCreateSubTaskWithStartTime() {
        EpicTask epicTask = taskManager.createEpicTask("Epic", "description");
        taskManager.addEpicTask(epicTask);
        String name = "название";
        String description = "описание";
        Duration duration = Duration.ofMinutes(10);
        LocalDateTime startTime = timeManager.findFreeTime(duration);
        Integer idMaster = epicTask.getId();
        SubTask subTask = taskManager.createSubTask(name, description, startTime, duration, idMaster);
        assertEquals(startTime, subTask.getStartTime());
        assertEquals(name, subTask.getName());
        assertEquals(description, subTask.getDescription());
        assertEquals(Status.NEW, subTask.getStatus());
        assertEquals(duration, subTask.getDuration());
        assertEquals(InMemoryTimeManager.timeStatusFixed, subTask.getTimeStatus());
    }
    @Test
    public void shouldCreateSubTaskWithoutStartTime() {
        String name = "название";
        String description = "описание";
        Duration duration = Duration.ofMinutes(29);
        Integer idMaster = 1;
        SubTask subTask = taskManager.createSubTask(name, description, Duration.ofMinutes(29), idMaster);
        assertNotNull(subTask.getStartTime());
        assertEquals(name, subTask.getName());
        assertEquals(description, subTask.getDescription());
        assertEquals(Status.NEW, subTask.getStatus());
        assertEquals(duration, subTask.getDuration());
        assertEquals(InMemoryTimeManager.timeStatusTusk, subTask.getTimeStatus());
    }

    @Test
    void addSubTask() {
    }

    @Test
    void printAllJustTask() {
    }

    @Test
    void getListAllJustTask() {
    }

    @Test
    void printAllEpicTask() {
    }

    @Test
    void getListAllEpicTask() {
    }

    @Test
    void printAllSubTask() {
    }

    @Test
    void getListAllSubTask() {
    }

    @Test
    void getTask() {
    }

    @Test
    void removeTask() {
    }

    @Test
    void removeAllTask() {
    }

    @Test
    void changeStatus() {
    }

    @Test
    void changeDescription() {
    }

    @Test
    void getHistory() {
    }

    @Test
    void getStatusTime() {
    }

    @Test
    void getPrioritizedTasks() {
    }
}