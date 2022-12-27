package tasks;

import files.FileBackedTasksManager;
import history.HistoryManager;
import utilit.Manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    public static Map<Integer, JustTask> justTasks = new HashMap<>();

    public static Map<Integer, EpicTask> epicTasks = new HashMap<>();

    public static Map<Integer, SubTask> subTasks = new HashMap<>();

    HistoryManager historyManager = Manager.getDefaultHistory();

    private static int idTask = 0;


    @Override
    public JustTask createJustTask(String name, String description) {
        ++idTask;
        Status status = Status.NEW;
        return new JustTask(idTask, name, description, status);
    }
    @Override
    public Integer addJustTask(JustTask justTask){
        if (!justTasks.containsKey(justTask.getId())) {
            justTasks.put(justTask.getId(), justTask);
            System.out.println("Задача сохранена под номером '" + justTask.getId() + "'");
        }
        return justTask.getId();
    }

    @Override
    public EpicTask createEpicTask(String name, String description) {
        ++idTask;
        Status status = Status.NEW;
        ArrayList<Integer> listIdSubtask = new ArrayList<>();
        return new EpicTask(idTask, name, description, status, listIdSubtask);
    }
    @Override
    public Integer addEpicTask(EpicTask epicTask){
        if (!epicTasks.containsKey(epicTask.getId())) {
            epicTasks.put(epicTask.getId(), epicTask);
            System.out.println("Задача сохранена под номером '" + epicTask.getId() + "'");
        }
        return epicTask.getId();
    }

    @Override
    public SubTask createSubTask(String name, String description, Integer idMaster) {
        ++idTask;
        Status status = Status.NEW;
        if (!epicTasks.containsKey(idMaster)) {
            System.out.println("Такого эпика не существует, создайте сначала эпик");
            return null;
        } else {
            return new SubTask(idTask, name, description, status, idMaster);
        }
    }
    @Override
    public Integer addSubTask(SubTask subTask){
        if (!subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            epicTasks.get(subTask.getIdMaster()).getListIdSubtask().add(subTask.getId());
            System.out.println("Задача сохранена под номером '" + subTask.getId() + "'");
            }
        checkEpicStatus(subTask.getIdMaster());
        return subTask.getId();
    }

    @Override
    public void printAllJustTask() {
        System.out.println("Список задач: ");
        for (Integer key : justTasks.keySet()) {
            System.out.println("Задача №" + key + justTasks.get(key));
        }
    }
    @Override
    public ArrayList<Task> getListAllJustTask() {
        ArrayList<Task> list = new ArrayList<>();
        for (Integer key : justTasks.keySet()) {
            list.add(justTasks.get(key));
        }
        return list;
    }
    @Override
    public void printAllEpicTask() {
        System.out.println("Список эпиков: ");
        for (Integer key : epicTasks.keySet()) {
            if (epicTasks.get(key).getListIdSubtask().size() != 0) {
                System.out.println("Эпик №" + key + epicTasks.get(key));
                System.out.println("    подзадачи эпика: ");
                for (int i = 0; i < epicTasks.get(key).getListIdSubtask().size(); i++) {
                    System.out.println("        подзадача №" + epicTasks.get(key).getListIdSubtask().get(i)
                            + " - " + subTasks.get(epicTasks.get(key).getListIdSubtask().get(i)).getName());
                }
            } else {
                System.out.println("Эпик №" + key + epicTasks.get(key));
                System.out.println("    Подзадач ещё не добавлено ");
            }
        }
    }
    @Override
    public ArrayList<EpicTask> getListAllEpicTask() {
        ArrayList<EpicTask> list = new ArrayList<>();
        for (Integer key : epicTasks.keySet()) {
            list.add(epicTasks.get(key));
        }
        return list;
    }
    @Override
    public void printAllSubTask(){
        System.out.println("Список подзадач: ");
        for (Integer key : subTasks.keySet()) {
            System.out.println("Подзадача №:" + key + subTasks.get(key) + ", находится в эпике №"
                    + subTasks.get(key).getIdMaster());
        }
    }
    @Override
    public ArrayList<Task> getListAllSubTask() {
        ArrayList<Task> list = new ArrayList<>();
        for (Integer key : subTasks.keySet()) {
            list.add(subTasks.get(key));
        }
        return list;
    }
    @Override
    public Task getTask (Integer id){
        if (justTasks.get(id) != null) {
            historyManager.addHistory(justTasks.get(id));
            FileBackedTasksManager.save();
            return justTasks.get(id);
        } else if (epicTasks.get(id) != null) {
            historyManager.addHistory(epicTasks.get(id));
            FileBackedTasksManager.save();
            return epicTasks.get(id);
        } else if (subTasks.get(id) != null) {
            historyManager.addHistory(subTasks.get(id));
            FileBackedTasksManager.save();
            return subTasks.get(id);
        } else {
            System.out.println("Задачи под таким номером не найдено");
            return null;
        }
    }

    @Override
    public void showTask(Integer id) {
        if (justTasks.get(id) != null) {
            System.out.println("Задача №" + id + justTasks.get(id));
        }
        else if (epicTasks.get(id) != null) {
            System.out.println("Эпик №" + id + epicTasks.get(id));
            System.out.println("    подзадачи эпика: ");
            if (epicTasks.get(id).getListIdSubtask().size() != 0) {
                for (int i = 0; i < epicTasks.get(id).getListIdSubtask().size(); i++) {
                    System.out.println("        подзадача №" + epicTasks.get(id).getListIdSubtask().get(i)
                            + " - " + subTasks.get(epicTasks.get(id).getListIdSubtask().get(i)).getName());
                }
            } else {
                System.out.println("Этот эпик ещё не имеет подзадач");
            }

        } else if (subTasks.get(id) != null) System.out.println("Подзадача №:" + id + subTasks.get(id)
                + ", находится в эпике №" + subTasks.get(id).getIdMaster());
        else System.out.println("задачи с таким номером не обнаружено");
    }
    @Override
    public void removeTask(Integer id){
        if (justTasks.get(id) != null){
            justTasks.remove(id);
            historyManager.remove(id);
            System.out.println("Задача №" + id + " успешно удалена...");
        } else if (epicTasks.get(id) != null){
            if (epicTasks.get(id).getListIdSubtask().size() != 0) {
                for (int i = 0; i < epicTasks.get(id).getListIdSubtask().size(); i++) {
                    historyManager.remove(epicTasks.get(id).getListIdSubtask().get(i));
                    subTasks.remove(epicTasks.get(id).getListIdSubtask().get(i));
                }
                historyManager.remove(id);
                epicTasks.remove(id);
                System.out.println("Эпик №" + id + " успешно удален вместе с подзадачами.");
            } else {
                epicTasks.remove(id);
                historyManager.remove(id);
                System.out.println("Эпик №" + id + " успешно удален");
            }
        } else if (subTasks.get(id) != null) {
            int idMaster = subTasks.get(id).getIdMaster();
            epicTasks.get(idMaster).getListIdSubtask().remove(id) ;
            subTasks.remove(id);
            historyManager.remove(id);
            checkEpicStatus(idMaster);
        } else {
            System.out.println("Такой задачи не обнаружено");
        }
    }
    @Override
    public void removeAllTask(){
        for (Integer key : justTasks.keySet()) justTasks.remove(key);
        for (Integer key : subTasks.keySet()) subTasks.remove(key);
        for (Integer key : epicTasks.keySet()) epicTasks.remove(key);
        historyManager.removeAllHistory();
        System.out.println("Все задачи удалены");
    }
    @Override
    public void changeStatus(Integer id, Status status){
        if (checkInputStatus(status)) {
            if (justTasks.get(id) != null) {
                justTasks.get(id).setStatus(status);
            }
            else if (subTasks.get(id) != null) {
                int idMaster = subTasks.get(id).getIdMaster();
                subTasks.get(id).setStatus(status);
                checkEpicStatus(idMaster);
            } else if (epicTasks.get(id) != null) {
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
        if (justTasks.get(id) != null) {
            justTasks.get(id).setDescription(description);
        }
        else if (subTasks.get(id) != null) {
            subTasks.get(id).setDescription(description);
        }
        else if (epicTasks.get(id) != null) {
            epicTasks.get(id).setDescription(description);
        }
        else {
            System.out.println("Вы не верно ввели номер задачи, попробуйте снова");
        }
    }
    @Override
    public List<Task>  getHistory() {
        return historyManager.getHistory();
    }

    public void setIdTask(int idTaskMax) {
        idTask = idTaskMax;
    }

    void changeName(Integer id, String name){
        if (justTasks.get(id) != null) {
            justTasks.get(id).setName(name);
        }
        else if (subTasks.get(id) != null) {
            subTasks.get(id).setName(name);
        }
        else if (epicTasks.get(id) != null) {
            epicTasks.get(id).setName(name);
        } else {
            System.out.println("Вы не верно ввели номер задачи, попробуйте снова");
        }
    }
    private void checkEpicStatus(Integer idMaster){
        if (epicTasks.get(idMaster).getListIdSubtask().size() != 0){
            int wordNew = 0;
            int wordDone = 0;
            for (int i = 0; i < epicTasks.get(idMaster).getListIdSubtask().size(); i++){
                if (subTasks.get(epicTasks.get(idMaster).getListIdSubtask().get(i)).getStatus().equals(Status.NEW)){
                    wordNew++;
                } else if (subTasks.get(epicTasks.get(idMaster).getListIdSubtask().get(i)).getStatus().equals(Status.DONE)){
                    wordDone++;
                }
            }
            if (wordNew == epicTasks.get(idMaster).getListIdSubtask().size()){
                epicTasks.get(idMaster).setStatus(Status.NEW);
            } else if (wordDone == epicTasks.get(idMaster).getListIdSubtask().size()){
                epicTasks.get(idMaster).setStatus(Status.DONE);
            } else {
                epicTasks.get(idMaster).setStatus(Status.IN_PROGRESS);
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
