package tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeAndDate.InMemoryTimeManager;
import timeAndDate.TimeManager;
import utilit.Manager;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

    T taskManager;
    InMemoryTimeManager timeManager = new InMemoryTimeManager();

    @Test
    public void shouldCreateJustTaskWithStartTime() {
        String name = "название";
        String description = "описание";
        Duration duration = Duration.ofMinutes(29);
        LocalDateTime startTime = timeManager.findFreeTime(duration);
        JustTask justTask = taskManager.createJustTask(name, description, startTime, duration);
        assertEquals(startTime, justTask.getStartTime());
        assertEquals(name, justTask.getName());
        assertEquals(description, justTask.getDescription());
        assertEquals(Status.NEW, justTask.getStatus());
        assertEquals(duration, justTask.getDuration());
        assertEquals(InMemoryTimeManager.timeStatusFixed, justTask.getTimeStatus());
    }

    @Test
    public void shouldCreateJustTaskWithoutStartTime() {
        String name = "название";
        String description = "описание";
        Duration duration = Duration.ofMinutes(29);
        JustTask justTask = taskManager.createJustTask(name, description, Duration.ofMinutes(29));
        assertNotNull(justTask.getStartTime());
        assertEquals(name, justTask.getName());
        assertEquals(description, justTask.getDescription());
        assertEquals(Status.NEW, justTask.getStatus());
        assertEquals(duration, justTask.getDuration());
        assertEquals(InMemoryTimeManager.timeStatusTusk, justTask.getTimeStatus());
    }

    @Test
    void shouldJustTaskAddInListJustTasks() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(29));
        taskManager.addJustTask(justTask);
        List<JustTask> justTasks = taskManager.getListAllJustTask();
        assertFalse(justTasks.isEmpty());
        assertEquals(1, justTasks.size());
        assertEquals(justTask, InMemoryTaskManager.justTasks.get(justTask.getId()));
     }

    @Test
    void shouldCreateEpicTask() {
        String name = "название";
        String description = "описание";
        EpicTask epicTask = taskManager.createEpicTask(name, description);
        assertNotNull(epicTask);
        assertEquals(name, epicTask.getName());
        assertEquals(Status.NEW, epicTask.getStatus());
        assertEquals(description, epicTask.getDescription());
    }

    @Test
    void shouldEpicTaskAddInListEpicTasks() {
        EpicTask epicTask = taskManager.createEpicTask("Epic", "description");
        taskManager.addEpicTask(epicTask);
        List<EpicTask> epicTasks = taskManager.getListAllEpicTask();
        assertFalse(epicTasks.isEmpty());
        assertEquals(1, epicTasks.size());
        assertEquals(epicTask, InMemoryTaskManager.epicTasks.get(epicTask.getId()));
    }

    @Test
    void subtaskWillNotBeCreatedWithAnIncorrectEpicNumber() {
        String name = "название";
        String description = "описание";
        Integer idMaster = -1;
        Duration duration = Duration.ofMinutes(10);
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> taskManager.createSubTask(name, description, duration, idMaster)
                );
        Assertions.assertEquals("Такого эпика не существует, создайте сначала эпик", ex.getMessage());
    }


    @Test
    public void shouldCreateSubTaskWithStartTime() {
        EpicTask epicTask = taskManager.createEpicTask("Epic", "description");
        taskManager.addEpicTask(epicTask);
        String name = "название";
        String description = "описание";
        Duration duration = Duration.ofMinutes(10);
        LocalDateTime startTime = timeManager.findFreeTime(duration);
        Integer idMaster = epicTask.getId();
        SubTask subTask = taskManager.createSubTask(name, description, startTime, duration, idMaster);
        assertEquals(startTime, subTask.getStartTime());
        assertEquals(name, subTask.getName());
        assertEquals(description, subTask.getDescription());
        assertEquals(Status.NEW, subTask.getStatus());
        assertEquals(duration, subTask.getDuration());
        assertEquals(InMemoryTimeManager.timeStatusFixed, subTask.getTimeStatus());
    }

    @Test
    public void shouldCreateSubTaskWithoutStartTime() {
        EpicTask epicTask = taskManager.createEpicTask("Epic", "description");
        taskManager.addEpicTask(epicTask);
        String name = "название";
        String description = "описание";
        Duration duration = Duration.ofMinutes(15);
        Integer idMaster = epicTask.getId();
        SubTask subTask = taskManager.createSubTask(name, description, Duration.ofMinutes(15), idMaster);
        assertNotNull(subTask.getStartTime());
        assertEquals(name, subTask.getName());
        assertEquals(description, subTask.getDescription());
        assertEquals(Status.NEW, subTask.getStatus());
        assertEquals(duration, subTask.getDuration());
        assertEquals(InMemoryTimeManager.timeStatusTusk, subTask.getTimeStatus());
    }

    @Test
    void shouldSubTaskAddInListSubTasks() {
        EpicTask epicTask = taskManager.createEpicTask("Epic", "description");
        taskManager.addEpicTask(epicTask);
        SubTask subTask = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask.getId());
        taskManager.addSubTask(subTask);
        assertFalse(taskManager.getListAllSubTask().isEmpty());
        assertEquals(1, taskManager.getListAllSubTask().size());
        assertEquals(subTask, InMemoryTaskManager.subTasks.get(subTask.getId()));
    }

    @Test
    void getListAllJustTask() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        List<JustTask> list = new ArrayList<>();
        list.add(justTask);
        assertNotNull(taskManager.getListAllJustTask().toArray());
        assertArrayEquals(list.toArray(), taskManager.getListAllJustTask().toArray());
    }
    @Test
    void getListAllEpicTask() {
        EpicTask epicTask = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask);
        List<EpicTask> list = new ArrayList<>();
        list.add(epicTask);
        assertNotNull(taskManager.getListAllEpicTask().toArray());
        assertArrayEquals(list.toArray(), taskManager.getListAllEpicTask().toArray());
    }
    @Test
    void getListAllSubTask() {
        EpicTask epicTask = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask);
        SubTask subTask = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask.getId());
        taskManager.addSubTask(subTask);
        List<SubTask> list = new ArrayList<>();
        list.add(subTask);
        assertNotNull(taskManager.getListAllSubTask().toArray());
        assertArrayEquals(list.toArray(), taskManager.getListAllSubTask().toArray());
    }
    @Test
    void getTask() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        EpicTask epicTask = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask);
        SubTask subTask = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask.getId());
        taskManager.addSubTask(subTask);
        assertEquals(justTask, taskManager.getTask(justTask.getId()), "getTask работает верно при вызове существующего justTask-а");
        assertEquals(epicTask, taskManager.getTask(epicTask.getId()), "getTask работает верно при вызове существующего epicTask-а");
        assertEquals(subTask, taskManager.getTask(subTask.getId()), "getTask работает верно при вызове существующего subTask-а");
        UnsupportedOperationException ex = assertThrows(
                UnsupportedOperationException.class,
                () -> taskManager.getTask(-1)
        );
        Assertions.assertEquals("Задачи под таким номером не найдено", ex.getMessage(), "getTask работает верно при вызове не существующей задачи");
    }
    @Test
    void removeTask() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        EpicTask epicTask1 = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask1);
        EpicTask epicTask2 = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask2);
        SubTask subTask1 = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask1.getId());
        taskManager.addSubTask(subTask1);
        SubTask subTask2 = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask1.getId());
        taskManager.addSubTask(subTask2);
        taskManager.removeTask(justTask.getId());
        UnsupportedOperationException exJustTask = assertThrows(
                UnsupportedOperationException.class,
                () -> taskManager.getTask(justTask.getId())
        );
        Assertions.assertEquals("Задачи под таким номером не найдено", exJustTask.getMessage(), "removeTask работает верно при удалении justTusk-а");
        taskManager.removeTask(subTask2.getId());
        UnsupportedOperationException exSubTask2 = assertThrows(
                UnsupportedOperationException.class,
                () -> taskManager.getTask(subTask2.getId())
        );
        Assertions.assertEquals("Задачи под таким номером не найдено", exSubTask2.getMessage(), "removeTask работает верно при удалении subTusk-а");
        taskManager.removeTask(epicTask2.getId());
        UnsupportedOperationException exEpicTask2 = assertThrows(
                UnsupportedOperationException.class,
                () -> taskManager.getTask(epicTask2.getId())
        );
        Assertions.assertEquals("Задачи под таким номером не найдено", exEpicTask2.getMessage(), "removeTask работает верно при удалении epicTusk-а без подзадач");
        taskManager.removeTask(epicTask1.getId());
        UnsupportedOperationException exEpicTask1 = assertThrows(
                UnsupportedOperationException.class,
                () -> taskManager.getTask(epicTask1.getId())
        );
        Assertions.assertEquals("Задачи под таким номером не найдено", exEpicTask1.getMessage(), "removeTask работает верно при удалении epicTusk-а с подзадачами");
        UnsupportedOperationException exSubTask1 = assertThrows(
                UnsupportedOperationException.class,
                () -> taskManager.getTask(subTask1.getId())
        );
        Assertions.assertEquals("Задачи под таким номером не найдено", exSubTask1.getMessage(), "removeTask удаляет subTask-и при удалении epicTask-а");
        UnsupportedOperationException ex = assertThrows(
                UnsupportedOperationException.class,
                () -> taskManager.removeTask(-1)
        );
        Assertions.assertEquals("Такой задачи не обнаружено", ex.getMessage(), "removeTask работает верно при попытке удаления несуществующей задачи");
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