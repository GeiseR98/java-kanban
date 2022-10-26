public class JustTask {

    private int id;
    private String name;
    private String description;
    private String status;


    public JustTask(int id, String name, String description, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return " - {" +
                "id'" + id + '\'' +
                " название: '" + name + '\'' +
                ", описание: '" + description + '\'' +
                ", статус: '" + status + '\'' +
                '}';
    }

}
