import history.HistoryManager;
import tasks.Status;
import tasks.TaskManager;
import utilit.Manager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        taskManager.changeDescription(6, "Подпиши петицию или я приду к тебе домой и убью твою собаку!");
        taskManager.changeStatus(8, Status.DONE);
        System.out.println(taskManager.getTask(1));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getTask(4));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getTask(1));
        System.out.println(taskManager.getHistory());
        taskManager.removeTask(1);
        taskManager.removeTask(4);
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getTask(3));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getTask(8));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getTask(7));
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getTask(3));
        System.out.println(taskManager.getHistory());
        taskManager.removeTask(3);
        System.out.println(taskManager.getHistory());
    }
}
