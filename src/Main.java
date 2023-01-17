import files.FileBackedTasksManager;
import history.HistoryManager;
import tasks.Status;
import tasks.TaskManager;
import timeAndDate.TimeManager;
import utilit.Manager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Поехали!");
        TaskManager taskManager = Manager.getDefault();
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        TimeManager timeManager = Manager.getDefaultTime();
        if (fileBackedTasksManager.readFile("saves" + File.separator + "file.csv") != null) {
            FileBackedTasksManager.loadFromFile();
        } else {
            System.out.println("Невозможно прочитать файл. Возможно, файл не находится в нужной директории.");
        }




        // Создание эпика:
//        fileBackedTasksManager.addEpicTask(taskManager.createEpicTask("эпик", "описание эпика"));

        // Создание задачи:
        taskManager.addJustTask(taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(29)));
        taskManager.addEpicTask(taskManager.createEpicTask("эпик", "описание эпика"));
        fileBackedTasksManager.addSubTask(taskManager.createSubTask("подзадача", "описание подзадачи", Duration.ofMinutes(10), 10));
        fileBackedTasksManager.addSubTask(taskManager.createSubTask("подзадача", "описание подзадачи", Duration.ofMinutes(150), 10));
        System.out.println("в таске " + taskManager.getPrioritizedTasks());
        System.out.println("в беке " + fileBackedTasksManager.getPrioritizedTasks());
        System.out.println("в тайм " + timeManager.getPrioritizedTasks());
        System.out.println(taskManager.getTask(2));

//        FileBackedTasksManager.save();
//        System.out.println(taskManager.getTask(1));
//        System.out.println(taskManager.getHistory());

        // Создание подзадачи:


        // Изменить описание:
        // fileBackedTasksManager.changeDescription(id);

        // Изменить статус задачи (только для задач и подзадач, статус эпиков меняется самостоятельно):
        // fileBackedTasksManager.changeStatus(id);

        // Просмотреть задачу:
        // System.out.println(taskManager.getTask(id));

        // Просмотреть историю:
        // System.out.println(taskManager.getHistory(id));

        // Удалить задачу:
        // fileBackedTasksManager.removeTask(id);

        // Удалить все задачи:
        // fileBackedTasksManager.removeAllTask();

        // Очистить историю:
        // historyManager.removeAllHistory();
    }
}
