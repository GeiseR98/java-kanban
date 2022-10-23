public class Subtask extends JustTask {

    private Integer idMaster;
    public Subtask(String name, String description, String status, Integer idMaster) {
        super(name, description, status);
        this.idMaster = idMaster;
    }

    public void setIdMaster(Integer idMaster) {
        this.idMaster = idMaster;
    }
    // добавить поле принадлежности к эпику
}
