import api.HttpTaskServer;
import com.sun.net.httpserver.HttpServer;
import files.FileBackedTasksManager;
import history.HistoryManager;
import tasks.InMemoryTaskManager;
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
        FileBackedTasksManager.setFileName("file.csv");
        TaskManager taskManager = Manager.getDefault();
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        TimeManager timeManager = Manager.getDefaultTime();
        if (fileBackedTasksManager.readFile("saves" + File.separator + "file.csv") != null) {
            FileBackedTasksManager.loadFromFile();
        } else {
            System.out.println("Невозможно прочитать файл. Возможно, файл не находится в нужной директории.");
        }

        InMemoryTaskManager.autoSave = true;
        HttpTaskServer httpServer;
        try {
            httpServer = new HttpTaskServer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        httpServer.start();




//        Создание эпика:
//        fileBackedTasksManager.addEpicTask(taskManager.createEpicTask("эпик", "описание эпика"));

//        Создание задачи:
//        taskManager.addJustTask(taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10)));
//        taskManager.addJustTask(taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10)));
//
//        System.out.println(taskManager.getTask(4));
//        System.out.println(taskManager.getHistory());
//
//        // Создание подзадачи:
//         taskManager.addSubTask(taskManager.createSubTask("подзадача", "описание подзадачи", Duration.ofMinutes(10), 1));

        // Изменить описание:
        // taskManager.changeDescription(id);

        // Изменить статус задачи (только для задач и подзадач, статус эпиков меняется самостоятельно):
        // taskManager.changeStatus(id);

        // Просмотреть задачу:
        // System.out.println(taskManager.getTask(id));

        // Просмотреть историю:
        // System.out.println(taskManager.getHistory(id));

        // Удалить задачу:
        // taskManager.removeTask(id);

        // Удалить все задачи:
//         taskManager.removeAllTask();

        // Очистить историю:
        // historyManager.removeAllHistory();
    }
}
