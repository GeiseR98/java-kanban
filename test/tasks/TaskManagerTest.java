package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeAndDate.InMemoryTimeManager;
import timeAndDate.TimeManager;
import utilit.Manager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;

    int idTask = 0;
     JustTask createJustTask(String name, String description, LocalDateTime startTime, Duration duration) {
             ++idTask;
             Status status = Status.NEW;
             JustTask justTask = new JustTask(1, name, description, status, startTime, duration, InMemoryTimeManager.timeStatusFixed);
             return justTask;
    }
    JustTask createJustTask(String name, String description, Duration duration) {
        ++idTask;
        Status status = Status.NEW;
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(15);
        JustTask justTask = new JustTask(1, name, description, status, startTime, duration, InMemoryTimeManager.timeStatusFixed);
        return justTask;
    }
    @Test
    public void shouldCreateJustTaskWithStartTime() {
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(100);
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", startTime, Duration.ofMinutes(29));
        assertEquals(startTime, justTask.getStartTime());
        assertNotNull(justTask.getName());
        assertNotNull(justTask.getDescription());
        assertNotNull(justTask.getStatus());
        assertNotNull(justTask.getStartTime());
        assertNotNull(justTask.getDuration());
        assertNotEquals(0, justTask.getTimeStatus());
        assertEquals(InMemoryTimeManager.timeStatusFixed, justTask.getTimeStatus());
    }
    @Test
    public void shouldCreateJustTaskWithoutStartTime() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(29));
        assertEquals(++idTask, justTask.getId());
        assertNotNull(justTask.getName());
        assertNotNull(justTask.getDescription());
        assertNotNull(justTask.getStatus());
        assertNotNull(justTask.getStartTime());
        assertNotNull(justTask.getDuration());
        assertNotEquals(0, justTask.getTimeStatus());
        assertEquals(InMemoryTimeManager.statusTimeTusk, justTask.getTimeStatus());
    }

    @Test
    void addJustTask() {
        taskManager.addJustTask(createJustTask("задача", "описание задачи", Duration.ofMinutes(29)));
        List<JustTask> justTasks = taskManager.getListAllJustTask();
        assertFalse(justTasks.isEmpty());
        assertEquals(1, justTasks.size());
     }

    @Test
    void createEpicTask() {

    }

    @Test
    void addPrioritizedTasks() {
    }

    @Test
    void addEpicTaskTest() {
         taskManager.addEpicTask(taskManager.createEpicTask("Epic", "description"));
         List<EpicTask> epicTasks = taskManager.getListAllEpicTask();
        assertFalse(epicTasks.isEmpty());
        assertEquals(1, epicTasks.size());
    }

    @Test
    void createSubTask() {
    }

    @Test
    void testCreateSubTask() {
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