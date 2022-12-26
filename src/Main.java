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

        taskManager.changeStatus(6, Status.DONE);
        //taskManager.addJustTask(taskManager.createJustTask("новый", "надоело"));
        //taskManager.addSubTask(taskManager.createSubTask("подзадача", "первая", 2));

        System.out.println(taskManager.getHistory());
        fileBackedTasksManager.save();
    }
}
