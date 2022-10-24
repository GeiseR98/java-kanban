public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Manager manager = new Manager();
        manager.saveJustTask("первый", "описание");
        manager.saveJustTask("второй", "описание2");
        manager.saveEpicTask("название", "описание эпика");
        manager.saveEpicTask("сыграть в доту", "кайфануть");
        manager.saveSubTask("третий", "описание3",  3);
        manager.saveSubTask("четвертый", "описание5",  3);
        manager.saveSubTask("третий", "описание3",  2);
        manager.removeTask(3);
        manager.showTask(5);



    }
}
