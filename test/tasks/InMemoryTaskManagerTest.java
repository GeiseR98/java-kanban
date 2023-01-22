package tasks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import timeAndDate.InMemoryTimeManager;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{
    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();

    }
    @AfterEach
    public void afterEach() {
        taskManager.removeAllTask();
        taskManager.setIdTask(0);
    }
}
