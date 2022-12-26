import files.FileBackedTasksManager;
import history.HistoryManager;
import tasks.Task;
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
            fileBackedTasksManager.fromString();
        } else {
            System.out.println("Невозможно прочитать файл. Возможно, файл не находится в нужной директории.");
        }
        taskManager.addEpicTask(taskManager.createEpicTask("подзадача2", "описание"));
        taskManager.addSubTask(taskManager.createSubTask("подзадача3", "описание", 8));
        System.out.println(taskManager.getTask(1));
        System.out.println(taskManager.getTask(5));
        System.out.println(taskManager.getTask(9));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(7));
        System.out.println(taskManager.getTask(8));
        System.out.println(taskManager.getHistory());
    }
}
