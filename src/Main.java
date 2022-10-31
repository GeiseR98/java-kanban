public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        InMemoryTaskTuskManager manager = new InMemoryTaskTuskManager();
        manager.saveJustTask("Найти оружие", "лопата валяется рядом с трейлером");
        manager.saveJustTask("Покормить Чампа", "нужно добыть где то собачье лакомство");
        manager.saveEpicTask("понедельник", "пройтись по списку жены");
        manager.saveEpicTask("вторник", "снова какой то список");
        manager.saveSubTask("взять чек", "Чек находится в кабинете главы кампании Running With Scissors",  3);
        manager.saveSubTask("обналичить чек", "Чек можно обналичить в Банке Парадайз",  3);
        manager.saveSubTask("купить молоко", "Молоко продаётся в магазине 'счастливый Ганеш'",  3);
        manager.printAllJustTask();
        manager.printAllEpicTask();
        manager.printAllSubTask();
        manager.changeDescription(1, "рядом с местом голосования вход в здание с игровыми автоматами, " +
                "дальше черный ход и над выходом будет обрез");
        manager.removeTask(2);
        manager.removeTask(4);
        manager.changeStatus(5, "DONE");
        manager.showTask(3);
        manager.showTask(1);
        manager.showTask(5);
        manager.showTask(6);
        manager.showTask(3);
        manager.showTask(1);
        manager.showTask(6);
        manager.showTask(5);
        manager.showTask(3);
        manager.showTask(1);
        manager.showTask(1);
        manager.showTask(1);
        manager.showTask(5);
        manager.showHistory();
    }
}
