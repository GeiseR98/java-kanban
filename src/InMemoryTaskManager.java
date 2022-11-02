import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    public HashMap<Integer, JustTask> justTask = new HashMap<>();

    public HashMap<Integer, EpicTask> epicTask = new HashMap<>();

    public HashMap<Integer, SubTask> subTask = new HashMap<>();

    HistoryManager historyManager = Manager.getDefaultHistory();

    int idTask = 0;

    @Override
    public Integer saveJustTask(String name, String description) {
        ++idTask;
        Status status = Status.NEW;
        if (!justTask.containsKey(idTask)) {
            justTask.put(idTask, new JustTask(idTask, name, description, status));
            System.out.println("Задача сохранена под номером '" + idTask + "'");
        }
        return idTask;
    }

    @Override
    public Integer saveEpicTask(String name, String description) {
        ++idTask;
        Status status = Status.NEW;
        if (!epicTask.containsKey(idTask)) {
            ArrayList<Integer> listIdSubtask = new ArrayList<>();
            epicTask.put(idTask, new EpicTask(idTask, name, description, status, listIdSubtask));
            System.out.println("Задача сохранена под номером '" + idTask + "'");
        }
        return idTask;
    }

    @Override
    public Integer saveSubTask(String name, String description, Integer idMaster) {
        ++idTask;
        Status status = Status.NEW;
        if (!epicTask.containsKey(idMaster)) {
            System.out.println("Такого эпика не существует, создайте сначала эпик");
        } else {
            if (!subTask.containsKey(idTask)) {
                subTask.put(idTask, new SubTask(idTask, name, description, status, idMaster));
                epicTask.get(idMaster).getListIdSubtask().add(idTask);
                System.out.println("Задача сохранена под номером '" + idTask + "'");
            }
        }
        checkEpicStatus(idMaster);
        return idTask;
    }

    @Override
    public void printAllJustTask() {
        System.out.println("Список задач: ");
        for (Integer key : justTask.keySet()) {
            System.out.println("Задача №" + key + justTask.get(key));
        }
    }
    @Override
    public ArrayList<Task> getListAllJustTask() {
        ArrayList<Task> list = new ArrayList<>();
        for (Integer key : justTask.keySet()) {
            list.add(justTask.get(key));
        }
        return list;
    }
    @Override
    public void printAllEpicTask() {
        System.out.println("Список эпиков: ");
        for (Integer key : epicTask.keySet()) {
            if (epicTask.get(key).getListIdSubtask().size() != 0) {
                System.out.println("Эпик №" + key + epicTask.get(key));
                System.out.println("    подзадачи эпика: ");
                for (int i = 0; i < epicTask.get(key).getListIdSubtask().size(); i++) {
                    System.out.println("        подзадача №" + epicTask.get(key).getListIdSubtask().get(i)
                            + " - " + subTask.get(epicTask.get(key).getListIdSubtask().get(i)).getName());
                }
            } else {
                System.out.println("Эпик №" + key + epicTask.get(key));
                System.out.println("    Подзадач ещё не добавлено ");
            }
        }
    }
    @Override
    public ArrayList<EpicTask> getListAllEpicTask() {
        ArrayList<EpicTask> list = new ArrayList<>();
        for (Integer key : epicTask.keySet()) {
            list.add(epicTask.get(key));
        }
        return list;
    }
    @Override
    public void printAllSubTask(){
        System.out.println("Список подзадач: ");
        for (Integer key : subTask.keySet()) {
            System.out.println("Подзадача №:" + key + subTask.get(key) + ", находится в эпике №"
                    + subTask.get(key).getIdMaster());
        }
    }
    @Override
    public ArrayList<Task> getListSubTask() {
        ArrayList<Task> list = new ArrayList<>();
        for (Integer key : subTask.keySet()) {
            list.add(subTask.get(key));
        }
        return list;
    }
    @Override
    public Task getTask (Integer id) {
        if (justTask.get(id) != null) {
            historyManager.addHistory(justTask.get(id));
            return justTask.get(id);
        } else if (epicTask.get(id) != null) {
            historyManager.addHistory(epicTask.get(id));
            return epicTask.get(id);
        } else if (subTask.get(id) != null) {
            historyManager.addHistory(subTask.get(id));
            return subTask.get(id);
        } else {
            return null;
        }
    }

    @Override
    public void showTask(Integer id) {
        if (justTask.get(id) != null) System.out.println("Задача №" + id + justTask.get(id));
        else if (epicTask.get(id) != null) {
            System.out.println("Эпик №" + id + epicTask.get(id));
            System.out.println("    подзадачи эпика: ");
            for (int i = 0; i < epicTask.get(id).getListIdSubtask().size(); i++) {
                System.out.println("        подзадача №" + epicTask.get(id).getListIdSubtask().get(i)
                        + " - " + subTask.get(epicTask.get(id).getListIdSubtask().get(i)).getName());
            }
        } else if (subTask.get(id) != null) System.out.println("Подзадача №:" + id + subTask.get(id)
                + ", находится в эпике №" + subTask.get(id).getIdMaster());
        else System.out.println("задачи с таким номером не обнаружено");
    }
    @Override
    public void removeTask(Integer id){
        if (justTask.get(id) != null){
            justTask.remove(id);
            System.out.println("Задача №" + id + " успешно удалена...");
        } else if (epicTask.get(id) != null){
            if (epicTask.get(id).getListIdSubtask().size() != 0) {
                for (int i = 0; i < epicTask.get(id).getListIdSubtask().size(); i++) {
                    subTask.remove(epicTask.get(id).getListIdSubtask().get(i));
                }
                epicTask.remove(id);
                System.out.println("Эпик №" + id + " успешно удален вместе с подзадачами.");
            } else {
                epicTask.remove(id);
                System.out.println("Эпик №" + id + " успешно удален");
            }
        } else if (subTask.get(id) != null) {
            int idMaster = subTask.get(id).getIdMaster();
            subTask.remove(id);
            checkEpicStatus(idMaster);
        } else {
            System.out.println("Такой задачи не обнаружено");
        }
    }
    @Override
    public void removeAllTask(){
        for (Integer key : justTask.keySet()) justTask.remove(key);
        for (Integer key : subTask.keySet()) subTask.remove(key);
        for (Integer key : epicTask.keySet()) epicTask.remove(key);
        System.out.println("Все задачи удалены");
    }



    @Override
    public void changeStatus(Integer id, Status status){
        if (checkInputStatus(status)) {
            if (justTask.get(id) != null) {
                justTask.get(id).setStatus(status);
            }
            else if (subTask.get(id) != null) {
                int idMaster = subTask.get(id).getIdMaster();
                subTask.get(id).setStatus(status);
                checkEpicStatus(idMaster);
            } else if (epicTask.get(id) != null) {
                System.out.println("Вы не можете менять статус эпика, он рассчитывается исходя из статусов подзадач");
            } else {
                System.out.println("Вы не верно ввели номер задачи, попробуйте снова");
            }
        } else {
            System.out.println("Вы неверно ввели статус, попробуйте снова");
        }
    }
    @Override
    public void changeDescription(Integer id, String description){
        if (justTask.get(id) != null) justTask.get(id).setDescription(description);
        else if (subTask.get(id) != null) subTask.get(id).setDescription(description);
        else if (epicTask.get(id) != null) epicTask.get(id).setDescription(description);
        else System.out.println("Вы не верно ввели номер задачи, попробуйте снова");
    }

    void changeName(Integer id, String name){
        if (justTask.get(id) != null) justTask.get(id).setName(name);
        else if (subTask.get(id) != null) subTask.get(id).setName(name);
        else if (epicTask.get(id) != null) epicTask.get(id).setName(name);
        else System.out.println("Вы не верно ввели номер задачи, попробуйте снова");
    }
    private void checkEpicStatus(Integer idMaster){
        if (epicTask.get(idMaster).getListIdSubtask().size() != 0){
            int wordNew = 0;
            int wordDone = 0;
            for (int i = 0; i < epicTask.get(idMaster).getListIdSubtask().size(); i++){
                if (subTask.get(epicTask.get(idMaster).getListIdSubtask().get(i)).getStatus().equals(Status.NEW)){
                    wordNew++;
                } else if (subTask.get(epicTask.get(idMaster).getListIdSubtask().get(i)).getStatus().equals(Status.DONE)){
                    wordDone++;
                }
            }
            if (wordNew == epicTask.get(idMaster).getListIdSubtask().size()){
                epicTask.get(idMaster).setStatus(Status.NEW);
            } else if (wordDone == epicTask.get(idMaster).getListIdSubtask().size()){
                epicTask.get(idMaster).setStatus(Status.DONE);
            } else {
                epicTask.get(idMaster).setStatus(Status.IN_PROGRESS);
            }
        }
    }
    private boolean checkInputStatus(Status status){
        boolean inputStatus = false;
        if (status.equals(Status.NEW)
                || status.equals(Status.IN_PROGRESS)
                || status.equals(Status.DONE)) {
            inputStatus = true;
        }
        return inputStatus;
    }
}
