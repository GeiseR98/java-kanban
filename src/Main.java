import files.FileBackedTasksManager;
import tasks.Status;
import tasks.TaskManager;
import utilit.Manager;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Поехали!");
        TaskManager taskManager = Manager.getDefault();
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        if (fileBackedTasksManager.readFile("saves" + File.separator + "file.csv") != null) {
            fileBackedTasksManager.fromString("saves" + File.separator + "file.csv");
            System.out.println("что то есть");
        } else {
            System.out.println("ты туповат");
        }


        System.out.println(taskManager.getTask(1));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getHistory());
    }
}
