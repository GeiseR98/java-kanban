public class JustTask {

    private String name;                               // имя задачи (вводит пользователь)
    private String description;                        // описание (вводит пользователь)
    private String status;                             // статус задачи (хрен знает кто и как вводит, ну у эпиков расчитывается исходя из подзадач


    public JustTask(String name, String description, String status) {
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
                "Название: '" + name + '\'' +
                ", описание: '" + description + '\'' +
                ", статус: '" + status + '\'' +
                '}';
    }

}
