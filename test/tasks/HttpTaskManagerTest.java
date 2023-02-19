package tasks;

import kvServer.HttpTaskManager;
import kvServer.KVServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    private KVServer server;
    @BeforeAll
    static void beforeAll() {
        HttpTaskManager.autoSave = false;
    }
    @BeforeEach
    public void beforeEach() {
        try {
            server = new KVServer();
            server.start();
        } catch (IOException e) {
            System.out.println("Ошибка создания менеджера");
        }
        taskManager = new HttpTaskManager("http://localhost:8078");
    }
    @AfterEach
    public void afterEach() {
        taskManager.removeAllTask();
        timeManager.cleaneTimeManager();
        server.stop();
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
        HttpTaskManager httpTaskManager = taskManager;
        httpTaskManager.load();
        assertEquals(justTask, httpTaskManager.getTask(justTask.getId()));
        assertEquals(epicTask, httpTaskManager.getTask(epicTask.getId()));
        assertEquals(subTask, httpTaskManager.getTask(subTask.getId()));
        assertEquals(history, httpTaskManager.getHistory());
    }
}