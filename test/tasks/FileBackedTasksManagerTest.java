package tasks;

import org.junit.jupiter.api.BeforeEach;

class FileBackedTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
        taskManager.setIdTask(0);
    }
}
