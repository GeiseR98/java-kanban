public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = Manager.getDefault();
        taskManager.saveJustTask("Найти оружие", "лопата валяется рядом с трейлером");
        taskManager.saveJustTask("Покормить Чампа", "нужно добыть где то собачье лакомство");
        taskManager.saveEpicTask("понедельник", "пройтись по списку жены");
        taskManager.saveEpicTask("вторник", "снова какой то список");
        taskManager.saveSubTask("взять чек", "Чек находится в кабинете главы кампании Running With Scissors",  3);
        taskManager.saveSubTask("обналичить чек", "Чек можно обналичить в Банке Парадайз",  3);
        taskManager.saveSubTask("купить молоко", "Молоко продаётся в магазине 'счастливый Ганеш'",  3);
        taskManager.printAllJustTask();
        taskManager.printAllEpicTask();
        taskManager.printAllSubTask();
        taskManager.changeDescription(1, "рядом с местом голосования вход в здание с игровыми автоматами, " +
                "дальше черный ход и над выходом будет обрез");
        taskManager.removeTask(2);
        taskManager.removeTask(4);
        taskManager.changeStatus(5, Status.DONE);
        taskManager.showTask(3);
        taskManager.showTask(1);
        taskManager.showTask(5);
        taskManager.showTask(6);
        taskManager.showTask(3);
        taskManager.showTask(1);
        taskManager.showTask(6);
        taskManager.showTask(5);
        taskManager.showTask(3);
        taskManager.showTask(1);
        taskManager.showTask(1);
        taskManager.showTask(1);
        taskManager.showTask(5);
    }
}
