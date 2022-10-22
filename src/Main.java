public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        Manager manager = new Manager();
        manager.saveJustTask("первый", "описание", "статус");
        manager.saveJustTask("второй", "описание", "статус");

    }
}
