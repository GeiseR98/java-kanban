package tasks;

import files.FileBackedTasksManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{
    @BeforeAll
    static void beforeAll() {
        FileBackedTasksManager.setFileName("fileTest.csv");
    }
    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }
    @AfterEach
    public void afterEach() {
        taskManager.removeAllTask();
        taskManager.setIdTask(0);
        timeManager.cleaneTimeManager();
    }
}
