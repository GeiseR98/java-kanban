import History.InMemoryHistoryManager;
import Tasks.Status;
import Tasks.TaskManager;
import utilit.Manager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = Manager.getDefault();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        taskManager.saveJustTask("кошки", "тишина любит кошек");
        taskManager.saveJustTask("оружие", "на бутылке с зажигательной смесью," +
                " можно увидеть типичную советскую этикетку с надписью 'В.М.Молотов' вместе с его фотографией");
        taskManager.saveEpicTask("вторник", "пройтись по списку жены");
        taskManager.saveEpicTask("труба", "за домом нашлась какая то труба, нужно узнать что там");
        taskManager.saveSubTask("исповедоваться в грехах",
                " - Вы внесли пожертвования в ящик у входа? - Да - Тогда вы прощены сын мой!",3);
        taskManager.saveSubTask("Собрать подписи",
                "Придется застегнуть ширинку - иначе квест не пройти.",  3);
        taskManager.saveSubTask("Вернуть книгу",
                "'Спаси дерево - сожги книги', 'Ахтунг - Гитлер написал книгу!', 'Книги - зло'",  3);
        taskManager.saveSubTask("Получить автограф",
                "'Скажи, а разве такие штуки не становятся более ценными, когда человек... эээ... помрёт?'",  3);
        taskManager.printAllJustTask();
        taskManager.printAllEpicTask();
        taskManager.printAllSubTask();
        taskManager.changeDescription(6, "Подпиши петицию или я приду к тебе домой и убью твою собаку!");
        taskManager.changeStatus(8, Status.DONE);
        System.out.println(taskManager.getTask(1));
        System.out.println(taskManager.getTask(2));
        System.out.println(taskManager.getTask(3));
        System.out.println(taskManager.getTask(4));
        System.out.println(taskManager.getTask(5));
        System.out.println(taskManager.getTask(6));
        System.out.println(taskManager.getTask(7));
        System.out.println(taskManager.getTask(8));
        System.out.println(taskManager.getTask(4));
        System.out.println(taskManager.getTask(5));
        System.out.println(taskManager.getTask(6));
        System.out.println(taskManager.getTask(7));
        System.out.println(taskManager.getTask(8));
        // System.out.println(History.InMemoryHistoryManager.history);  // получение истории списком
        // inMemoryHistoryManager.showHistory();                // получение истории "позадачно"
    }
}
