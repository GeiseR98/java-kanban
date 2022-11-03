package tasks;

public class SubTask extends Task {

    private Integer idMaster;
    public SubTask(int id, String name, String description, Status status, Integer idMaster) {
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
