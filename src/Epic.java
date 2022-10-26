import java.util.ArrayList;

public class Epic extends JustTask {

    private ArrayList<Integer> listIdSubtask;

    public ArrayList<Integer> getListIdSubtask() {
        return listIdSubtask;
    }


    public Epic(int id, String name, String description, String status, ArrayList<Integer> listIdSubtask) {
        super(id, name, description, status);
        this.listIdSubtask = listIdSubtask;
    }

    public void setListIdSubtask(ArrayList<Integer> listIdSubtask) {
        this.listIdSubtask = listIdSubtask;
    }

    @Override
    public String toString() {
        return " - {" +
                "id'" + super.getId() + '\'' +
                " название: '" + super.getName() + '\'' +
                ", описание: '" + super.getDescription() + '\'' +
                ", статус: '" + super.getStatus() + '\'' +
                '}';
    }
}
