public class Subtask extends JustTask {
    private int idMaster;
    public Subtask(String name, String description, String status, Integer idMaster) {
        super(name, description, status);
        this.idMaster = idMaster;
    }

    public void setIdMaster(int idMaster) {
        this.idMaster = idMaster;
    }
    // добавить поле принадлежности к эпику
}
