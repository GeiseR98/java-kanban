package history;

import files.FileBackedTasksManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import managers.*;
import timeAndDate.InMemoryTimeManager;
import utilit.Manager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryTimeManager timeManager = new InMemoryTimeManager();
    HistoryManager historyManager;
    InMemoryTaskManager taskManager;

    @BeforeAll
    static void beforeAll() {
        FileBackedTasksManager.setFileName("fileTest.csv");
        FileBackedTasksManager.autoSave = false;
    }
    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
        taskManager.setIdTask(0);
        historyManager = Manager.getDefaultHistory();
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        EpicTask epicTask = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask);
        SubTask subTask = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask.getId());
        taskManager.addSubTask(subTask);
    }
    @AfterEach
    public void afterEach() {
        taskManager.removeAllTask();
    }

    @Test
    void shouldReturnEmptyList() {
        assertEquals(Collections.EMPTY_LIST, taskManager.getHistory());
    }
    @Test
    void shouldReturnHistory() {
        List<Task> expected = new ArrayList<>();
        expected.add(taskManager.getTask(1));
        expected.add(taskManager.getTask(2));
        expected.add(taskManager.getTask(3));
        assertEquals(3, taskManager.getHistory().size());
        assertArrayEquals(new List[]{expected}, new List[]{taskManager.getHistory()});
    }
    @Test
    void shouldNotDuplicateTask() {
        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getTask(1);
        assertEquals(1, taskManager.getHistory().size());
    }
    @Test
    void shouldRemoveFromTheBeginning() {
        List<Task> expected = new ArrayList<>();
        taskManager.getTask(1);
        expected.add(taskManager.getTask(2));
        expected.add(taskManager.getTask(3));
        historyManager.remove(1);
        assertArrayEquals(new List[]{expected}, new List[]{taskManager.getHistory()});
    }
    @Test
    void shouldRemoveFromTheMiddle() {
        List<Task> expected = new ArrayList<>();
        expected.add(taskManager.getTask(1));
        taskManager.getTask(2);
        expected.add(taskManager.getTask(3));
        historyManager.remove(2);
        assertArrayEquals(new List[]{expected}, new List[]{taskManager.getHistory()});
    }
    @Test
    void shouldRemoveFromTheEnd() {
        List<Task> expected = new ArrayList<>();
        expected.add(taskManager.getTask(1));
        expected.add(taskManager.getTask(2));
        taskManager.getTask(3);
        historyManager.remove(3);
        assertArrayEquals(new List[]{expected}, new List[]{taskManager.getHistory()});
    }
    @Test
    void removeAllHistory() {
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getTask(3);
        historyManager.clearHistory();
        assertEquals(Collections.EMPTY_LIST, taskManager.getHistory());
    }
}