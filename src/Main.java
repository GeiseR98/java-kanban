import files.FileBackedTasksManager;
import history.HistoryManager;
import tasks.Status;
import tasks.TaskManager;
import utilit.Manager;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Поехали!");
        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        if (fileBackedTasksManager.readFile("saves" + File.separator + "file.csv") != null) {
            fileBackedTasksManager.loadFromFile();
        } else {
            System.out.println("Невозможно прочитать файл. Возможно, файл не находится в нужной директории.");
        }


        // Создание задачи:
        // fileBackedTasksManager.addJustTask(taskManager.createJustTask("задача", "описание задачи"));

        // Создание эпика:
        //fileBackedTasksManager.addEpicTask(taskManager.createEpicTask("эпик", "описание эпика"));

        // Создание подзадачи:
        //fileBackedTasksManager.addSubTask(taskManager.createSubTask("подзадача", "описание подзадачи", (idMaster)));

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
