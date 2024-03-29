package tasks;

import files.FileBackedTasksManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    @BeforeAll
    static void beforeAll() {
        FileBackedTasksManager.setFileName("fileTest.csv");
//        FileBackedTasksManager.autoSave = false;
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        taskManager = new FileBackedTasksManager(new File("fileTest.csv"));
    }
    @AfterEach
    public void afterEach() {
        taskManager.removeAllTask();
        taskManager.setIdTask(0);
        timeManager.cleaneTimeManager();
        taskManager.save();
    }
    @Test
    public void shouldCorrectlySaveAndLoad() throws IOException {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        EpicTask epicTask = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask);
        SubTask subTask = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask.getId());
        taskManager.addSubTask(subTask);
        taskManager.getTask(justTask.getId());
        taskManager.getTask(epicTask.getId());
        taskManager.getTask(subTask.getId());
        List<Task> history = taskManager.getHistory();
        taskManager.save();
        FileBackedTasksManager.loadFromFile(new File(FileBackedTasksManager.getFileName()));
        assertEquals(justTask, taskManager.getTask(justTask.getId()));
        assertEquals(epicTask, taskManager.getTask(epicTask.getId()));
        assertEquals(subTask, taskManager.getTask(subTask.getId()));
        assertEquals(history, taskManager.getHistory());
    }
    @Test
    public void shouldSaveAndLoadEmptyTasks() throws IOException {
        FileBackedTasksManager.loadFromFile(new File(FileBackedTasksManager.getFileName()));
        assertEquals(Collections.EMPTY_LIST, taskManager.getListAllJustTask());
        assertEquals(Collections.EMPTY_LIST, taskManager.getListAllEpicTask());
        assertEquals(Collections.EMPTY_LIST, taskManager.getListAllSubTask());
    }
    @Test
    public void shouldSaveAndLoadEmptyHistory() throws IOException {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        EpicTask epicTask = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask);
        SubTask subTask = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask.getId());
        taskManager.addSubTask(subTask);
        assertEquals(justTask, taskManager.getTask(justTask.getId()));
        assertEquals(epicTask, taskManager.getTask(epicTask.getId()));
        assertEquals(subTask, taskManager.getTask(subTask.getId()));
        List<Task> history = taskManager.getHistory();
        taskManager.save();
        FileBackedTasksManager.loadFromFile(new File(FileBackedTasksManager.getFileName()));
        assertEquals(justTask, taskManager.getTask(justTask.getId()));
        assertEquals(epicTask, taskManager.getTask(epicTask.getId()));
        assertEquals(subTask, taskManager.getTask(subTask.getId()));
        assertEquals(history, taskManager.getHistory());
    }
}
