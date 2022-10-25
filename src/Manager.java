import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, JustTask> justTask = new HashMap<>();
    HashMap<Integer, Epic> epicTask = new HashMap<>();
    HashMap<Integer, Subtask> subTask = new HashMap<>();
    int idTask = 0;

    void saveJustTask(String name, String description) {
        ++idTask;
        String status = "New";
        if (!justTask.containsKey(idTask)) {
            justTask.put(idTask, new JustTask(name, description, status));
        }
    }

    void saveEpicTask(String name, String description) {
        ++idTask;
        String status = "NEW";
        if (!epicTask.containsKey(idTask)) {
            ArrayList<Integer> listIdSubtask = new ArrayList<>();
            epicTask.put(idTask, new Epic(name, description, status, listIdSubtask));
        }
    }

    void saveSubTask(String name, String description, Integer idMaster) {
        ++idTask;
        String status = "NEW";
        if (!epicTask.containsKey(idMaster)) {
            System.out.println("Такого эпика не существует, создайте сначала эпик");
        } else {
            if (!subTask.containsKey(idTask)) {
                subTask.put(idTask, new Subtask(name, description, status, idMaster));
                epicTask.get(idMaster).getListIdSubtask().add(idTask);
            }
        }
        checkEpicStatus(idMaster);
    }

    void printAllJustTask() {
        System.out.println("Список задач: ");
        for (Integer key : justTask.keySet()) {
            System.out.println("Задача №" + key + justTask.get(key));
        }
    }

    void printAllEpicTask() {
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
    void printAllSubTask(){
        System.out.println("Список подзадач: ");
        for (Integer key : subTask.keySet()) {
            System.out.println("Подзадача №:" + key + subTask.get(key) + ", находится в эпике №"
                    + subTask.get(key).getIdMaster());
        }
    }
    void showTask(Integer id) {
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
    void removeTask(Integer id){
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
    void removeAllTask(){
        for (Integer key : justTask.keySet()) justTask.remove(key);
        for (Integer key : subTask.keySet()) subTask.remove(key);
        for (Integer key : epicTask.keySet()) epicTask.remove(key);
        System.out.println("Все задачи удалены");
    }
    void changeStatus(Integer id, String status){
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
    void changeDescription(Integer id, String description){
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
                if (subTask.get(epicTask.get(idMaster).getListIdSubtask().get(i)).getStatus().equals("NEW")){
                    wordNew++;
                } else if (subTask.get(epicTask.get(idMaster).getListIdSubtask().get(i)).getStatus().equals("DONE")){
                    wordDone++;
                }
            }
            if (wordNew == epicTask.get(idMaster).getListIdSubtask().size()){
                epicTask.get(idMaster).setStatus("NEW");
            } else if (wordDone == epicTask.get(idMaster).getListIdSubtask().size()){
                epicTask.get(idMaster).setStatus("DONE");
            } else {
                epicTask.get(idMaster).setStatus("IN_PROGRESS");
            }
        }
    }
     private boolean checkInputStatus(String status){
        boolean inputStatus = false;
        if (status.equals("NEW") || status.equals("IN_PROGRESS") || status.equals("DONE")) inputStatus = true;
        return inputStatus;
     }
}
