package tasks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import timeAndDate.InMemoryTimeManager;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{
    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
        taskManager.setIdTask(0);
    }
    @AfterEach
    public void afterEach() {
        removeAllTask();
    }
}
