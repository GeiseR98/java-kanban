import java.util.ArrayList;

public class Epic extends JustTask {

    public ArrayList<Integer> getListIdSubtask() {
        return listIdSubtask;
    }

    private ArrayList<Integer> listIdSubtask;

    public Epic(String name, String description, String status, ArrayList<Integer> listIdSubtask) {
        super(name, description, status);
        this.listIdSubtask = listIdSubtask;
    }

    public void setListIdSubtask(ArrayList<Integer> listIdSubtask) {
        this.listIdSubtask = listIdSubtask;
    }
    // добавить поле списка подзадач
}
