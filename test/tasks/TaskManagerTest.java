package tasks;

import org.junit.jupiter.api.Test;
import timeAndDate.InMemoryTimeManager;
import timeAndDate.TimeManager;
import utilit.Manager;

import java.time.Duration;
import java.time.LocalDateTime;

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
    @Test
    public void shouldCreateJustTask() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(29));
        assertNotEquals(0, justTask.getId());
        assertEquals(++idTask, justTask.getId());
        assertNotNull(justTask.getName());
        assertNotNull(justTask.getDescription());
        assertNotNull(justTask.getStatus());
        assertNotNull(justTask.getStartTime());
        assertNotNull(justTask.getDuration());
        assertNotEquals(0, justTask.getTimeStatus());
        assertEquals(2, justTask.getTimeStatus());
    }

    @Test
    void testCreateJustTask() {
    }

    @Test
    void addJustTask() {
    }

    @Test
    void createEpicTask() {
    }

    @Test
    void addPrioritizedTasks() {
    }

    @Test
    void addEpicTask() {
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