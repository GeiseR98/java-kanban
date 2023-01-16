import files.FileBackedTasksManager;
import history.HistoryManager;
import tasks.Status;
import tasks.TaskManager;
import timeAndDate.TimeManager;
import utilit.Manager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Поехали!");
        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        TimeManager timeManager = Manager.getDefaultTime();
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        if (fileBackedTasksManager.readFile("saves" + File.separator + "file.csv") != null) {
            FileBackedTasksManager.loadFromFile();
        } else {
            System.out.println("Невозможно прочитать файл. Возможно, файл не находится в нужной директории.");
        }




        // Создание эпика:
//        fileBackedTasksManager.addEpicTask(taskManager.createEpicTask("эпик", "описание эпика"));

        // Создание задачи:
        taskManager.addJustTask(taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(120)));
        taskManager.getTask(1);
        taskManager.addJustTask(taskManager.createJustTask("задача", "описание задачи", taskManager.getTask(1).getEndTime().plusMinutes(30), Duration.ofMinutes(120)));
        System.out.println(taskManager.getStatusTime(taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(120)).getStartTime()));
        System.out.println(taskManager.getStatusTime(taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(120)).getStartTime()));

        FileBackedTasksManager.save();
//        System.out.println(taskManager.getTask(1));
//        System.out.println(taskManager.getHistory());

//        fileBackedTasksManager.addJustTask(taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(120)));
//        fileBackedTasksManager.addJustTask(taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(120)));
//        fileBackedTasksManager.addJustTask(taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(120)));
        // Создание подзадачи:
//        fileBackedTasksManager.addSubTask(taskManager.createSubTask("подзадача", "описание подзадачи", Duration.ofMinutes(10), 1));
//        fileBackedTasksManager.addSubTask(taskManager.createSubTask("подзадача", "описание подзадачи", Duration.ofMinutes(150), 1));

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
