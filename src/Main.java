import history.HistoryManager;
import tasks.Status;
import tasks.TaskManager;
import utilit.Manager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        taskManager.addJustTask("кошки", "тишина любит кошек");
        taskManager.addJustTask("оружие", "на бутылке с зажигательной смесью," +
                " можно увидеть типичную советскую этикетку с надписью 'В.М.Молотов' вместе с его фотографией");
        taskManager.addEpicTask("вторник", "пройтись по списку жены");
        taskManager.addEpicTask("труба", "за домом нашлась какая то труба, нужно узнать что там");
        taskManager.addSubTask("исповедоваться в грехах",
                " - Вы внесли пожертвования в ящик у входа? - Да - Тогда вы прощены сын мой!",3);
        taskManager.addSubTask("Собрать подписи",
                "Придется застегнуть ширинку - иначе квест не пройти.",  3);
        taskManager.addSubTask("Вернуть книгу",
                "'Спаси дерево - сожги книги', 'Ахтунг - Гитлер написал книгу!', 'Книги - зло'",  3);
        taskManager.addSubTask("Получить автограф",
                "'Скажи, а разве такие штуки не становятся более ценными, когда человек... эээ... помрёт?'",  3);
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
