public class Subtask extends JustTask {

    private Integer idMaster;
    public Subtask(int id, String name, String description, String status, Integer idMaster) {
        super(id, name, description, status);
        this.idMaster = idMaster;
    }

    public void setIdMaster(Integer idMaster) {
        this.idMaster = idMaster;
    }

    public Integer getIdMaster() {
        return idMaster;
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
