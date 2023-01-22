package tasks;

import files.FileBackedTasksManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeAll
    static void beforeAll() {
        FileBackedTasksManager.setFileName("fileTest.csv");
    }

    @BeforeEach
    public void beforeEach() {
        taskManager = new FileBackedTasksManager();
    }
    @AfterEach
    public void afterEach() {
        taskManager.removeAllTask();
        taskManager.setIdTask(0);
    }
    @Test
    public void shouldCorrectlySaveAndLoad() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        EpicTask epicTask = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask);
        SubTask subTask = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask.getId());
        taskManager.addSubTask(subTask);
        taskManager.getTask(justTask.getId());
        taskManager.getTask(epicTask.getId());
        taskManager.getTask(subTask.getId());
        assertEquals(justTask, taskManager.getTask(justTask.getId()));
        assertEquals(epicTask, taskManager.getTask(epicTask.getId()));
        assertEquals(subTask, taskManager.getTask(subTask.getId()));
    }
    @Test
    public void shouldSaveAndLoadEmptyTasks() throws IOException {
        FileBackedTasksManager.loadFromFile();
        assertEquals(Collections.EMPTY_LIST, taskManager.getListAllJustTask());
        assertEquals(Collections.EMPTY_LIST, taskManager.getListAllEpicTask());
        assertEquals(Collections.EMPTY_LIST, taskManager.getListAllSubTask());
    }
    @Test
    public void shouldSaveAndLoadEmptyHistory() {
        assertEquals(Collections.EMPTY_LIST, taskManager.getHistory());
    }
}
