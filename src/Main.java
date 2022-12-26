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


        fileBackedTasksManager.addEpicTask(taskManager.createEpicTask("эпик", "описание эпика"));
        fileBackedTasksManager.addSubTask(taskManager.createSubTask("подзадача", "описание подзадачи", 1));
        //System.out.println(taskManager.getTask(2));
        //System.out.println(taskManager.getTask(3));
        //System.out.println(taskManager.getTask(1));

        System.out.println(taskManager.getHistory());
    }
}
