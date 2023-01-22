package tasks;

import files.FileBackedTasksManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import timeAndDate.InMemoryTimeManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

    T taskManager;
    InMemoryTimeManager timeManager = new InMemoryTimeManager();


    EpicTask createEpicTask(int idTask) {
        String name = "эпик";
        String description = "описание";
        LocalDateTime startTime = null;
        Duration duration = null;
        LocalDateTime endTime = null;
        Status status = Status.NEW;
        ArrayList<Integer> listIdSubtask = new ArrayList<>();
        return new EpicTask(idTask, name, description, status, startTime, duration, endTime, listIdSubtask);
    }
    SubTask createSubTask(int id, int idMaster) {
        String name = "эпик";
        String description = "описание";
        Status status = Status.NEW;
        Duration duration = Duration.ofMinutes(14);
        LocalDateTime startTime = timeManager.findFreeTime(duration);
        return new SubTask(id, name, description, status, startTime, duration, InMemoryTimeManager.timeStatusTusk, idMaster);
    }

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
    void getListAllJustTaskTest() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        List<JustTask> list = new ArrayList<>();
        list.add(justTask);
        assertNotNull(taskManager.getListAllJustTask().toArray());
        assertArrayEquals(list.toArray(), taskManager.getListAllJustTask().toArray());
    }

    @Test
    void getListAllEpicTaskTest() {
        EpicTask epicTask = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask);
        List<EpicTask> list = new ArrayList<>();
        list.add(epicTask);
        assertNotNull(taskManager.getListAllEpicTask().toArray());
        assertArrayEquals(list.toArray(), taskManager.getListAllEpicTask().toArray());
    }

    @Test
    void getListAllSubTaskTest() {
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
    void getTaskTest() {
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
    void removeTaskTest() {
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
    void removeAllTaskTest() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        EpicTask epicTask1 = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask1);
        SubTask subTask1 = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask1.getId());
        taskManager.addSubTask(subTask1);
        taskManager.removeAllTask();
        UnsupportedOperationException exJustTask = assertThrows(
                UnsupportedOperationException.class,
                () -> taskManager.getTask(justTask.getId())
        );
        Assertions.assertEquals("Задачи под таким номером не найдено", exJustTask.getMessage(), "removeAllTask удаляет все justTask-и");
        UnsupportedOperationException exEpicTask1 = assertThrows(
                UnsupportedOperationException.class,
                () -> taskManager.getTask(epicTask1.getId())
        );
        Assertions.assertEquals("Задачи под таким номером не найдено", exEpicTask1.getMessage(), "removeAllTask удаляет все epicTask-и");
        UnsupportedOperationException exSubTask1 = assertThrows(
                UnsupportedOperationException.class,
                () -> taskManager.getTask(subTask1.getId())
        );
        Assertions.assertEquals("Задачи под таким номером не найдено", exSubTask1.getMessage(), "removeAllTask удаляет все subTask-и");
    }

    @Test
    void shouldChangeStatusForJustTask() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        justTask.setStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, justTask.getStatus());
        justTask.setStatus(Status.DONE);
        assertEquals(Status.DONE, justTask.getStatus());
    }

    @Test
    void shouldChangeStatusForSubTask() {
        EpicTask epicTask = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask);
        SubTask subTask = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask.getId());
        taskManager.addSubTask(subTask);
        subTask.setStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, subTask.getStatus());
        subTask.setStatus(Status.DONE);
        assertEquals(Status.DONE, subTask.getStatus());
    }

    @Test
    void shouldChangeStatusForEpicTaskWithOutSubTusk() {
        EpicTask epicTask = createEpicTask(1);
        taskManager.addEpicTask(epicTask);
        assertEquals(Status.NEW, epicTask.getStatus(), "Статус epicTask-а с пустым списком подзадач: NEW");
    }

    @Test
    void shouldChangeStatusForEpicTaskWithSubTaskNEWAndNEW() {
        EpicTask epicTask = createEpicTask(1);
        taskManager.addEpicTask(epicTask);
        SubTask subTask1 = createSubTask(2,1);
        taskManager.addSubTask(subTask1);
        SubTask subTask = createSubTask(4,1);
        taskManager.addSubTask(subTask);
        assertEquals(Status.NEW, epicTask.getStatus(), "Статус epicTask-а с подзадачами NEW и NEW: NEW");
    }

    @Test
    void shouldChangeStatusForEpicTaskWithSubTaskNEWAndDONE() {
        EpicTask epicTask = createEpicTask(1);
        taskManager.addEpicTask(epicTask);
        SubTask subTask1 = createSubTask(2,1);
        taskManager.addSubTask(subTask1);
        SubTask subTask = createSubTask(4,1);
        taskManager.addSubTask(subTask);
        taskManager.changeStatus(4, Status.DONE);
        assertEquals(Status.IN_PROGRESS, epicTask.getStatus(), "Статус epicTask-а с подзадачами NEW и DONE: IN_PROGRESS");
    }

    @Test
    void shouldChangeStatusForEpicTaskWithSubTaskDONEAndDONE() {
        EpicTask epicTask = createEpicTask(1);
        taskManager.addEpicTask(epicTask);
        SubTask subTask1 = createSubTask(2,1);
        taskManager.addSubTask(subTask1);
        SubTask subTask = createSubTask(4,1);
        taskManager.addSubTask(subTask);
        taskManager.changeStatus(4, Status.DONE);
        taskManager.changeStatus(2, Status.DONE);
        assertEquals(Status.DONE, epicTask.getStatus(), "Статус epicTask-а с подзадачами DONE и DONE: DONE");
    }

    @Test
    void shouldChangeStatusForEpicTaskWithSubTaskIN_PROGRESSAndIN_PROGRESS() {
        EpicTask epicTask = createEpicTask(1);
        taskManager.addEpicTask(epicTask);
        SubTask subTask1 = createSubTask(2,1);
        taskManager.addSubTask(subTask1);
        SubTask subTask = createSubTask(4,1);
        taskManager.addSubTask(subTask);
        taskManager.changeStatus(4, Status.IN_PROGRESS);
        taskManager.changeStatus(2, Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, epicTask.getStatus(), "Статус epicTask-а с подзадачами IN_PROGRESS и IN_PROGRESS: IN_PROGRESS");
    }

    @Test
    void changeDescriptionTest() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        EpicTask epicTask = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask);
        SubTask subTask = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask.getId());
        taskManager.addSubTask(subTask);
        taskManager.changeDescription(justTask.getId(),"новое описание");
        taskManager.changeDescription(epicTask.getId(),"новое описание");
        taskManager.changeDescription(subTask.getId(),"новое описание");
        assertEquals("новое описание", justTask.getDescription(), "метод изменяет описание jastTask-а");
        assertEquals("новое описание", epicTask.getDescription(),"метод изменяет описание jastTask-а");
        assertEquals("новое описание", subTask.getDescription(), "метод изменяет описание jastTask-а");
    }

    @Test
    void changeNameTest() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        EpicTask epicTask = taskManager.createEpicTask("задача", "описание задачи");
        taskManager.addEpicTask(epicTask);
        SubTask subTask = taskManager.createSubTask("задача", "описание задачи", Duration.ofMinutes(15), epicTask.getId());
        taskManager.addSubTask(subTask);
        taskManager.changeName(justTask.getId(),"новое имя");
        taskManager.changeName(epicTask.getId(),"новое имя");
        taskManager.changeName(subTask.getId(),"новое имя");
        assertEquals("новое имя", justTask.getName(), "метод изменяет имя justTask-а");
        assertEquals("новое имя", epicTask.getName(),"метод изменяет имя epicTask-а");
        assertEquals("новое имя", subTask.getName(), "метод изменяет имя subTask-а");
    }

    @Test
    void getHistoryWithEmptyList() {
        assertTrue(taskManager.getHistory().isEmpty(), "история задач пуста");
    }

    @Test
    void getHistoryWithNotEmptyList() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        taskManager.getTask(justTask.getId());
        assertFalse(taskManager.getHistory().isEmpty(), "история задач не пуста");
        assertEquals(1, taskManager.getHistory().size(), "история задач не пуста");
    }

    @Test
    void getPrioritizedTasksWithEmptyList() {
        assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "список задач пуст");
    }

    @Test
    void getPrioritizedWithNotEmptyList() {
        JustTask justTask = taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
        taskManager.addJustTask(justTask);
        assertFalse(taskManager.getPrioritizedTasks().isEmpty(), "список задач не пуст");
        assertEquals(1, timeManager.getPrioritizedTasks().size(), "список задач не пуст");
    }
}