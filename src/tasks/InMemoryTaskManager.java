package tasks;

import files.FileBackedTasksManager;
import history.HistoryManager;
import timeAndDate.InMemoryTimeManager;
import timeAndDate.TimeManager;
import utilit.Manager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    public static Map<Integer, JustTask> justTasks = new HashMap<>();

    public static Map<Integer, EpicTask> epicTasks = new HashMap<>();

    public static Map<Integer, SubTask> subTasks = new HashMap<>();

    HistoryManager historyManager = Manager.getDefaultHistory();
    TimeManager timeManager = Manager.getDefaultTime();

    private static int idTask = 0;

    public static boolean autoSave = false;

    @Override
    public JustTask createJustTask(String name, String description, LocalDateTime startTime, Duration duration) {
        ++idTask;
        Status status = Status.NEW;
        if (timeManager.checkingFreeTime(startTime, duration)) {
            JustTask justTask = new JustTask(idTask, name, description, status, startTime, duration, InMemoryTimeManager.timeStatusFixed);
            timeManager.addFixedTime(justTask);
            return justTask;
        } else {
            throw new UnsupportedOperationException("Ближайшее свободное время: " + timeManager.findFreeTime(duration));
        }
    }
    @Override
    public JustTask createJustTask(String name, String description, Duration duration) {
        ++idTask;
        Status status = Status.NEW;
        LocalDateTime startTime = timeManager.findFreeTime(duration);
        JustTask justTask = new JustTask(idTask, name, description, status, startTime, duration, InMemoryTimeManager.timeStatusTusk);
        timeManager.addTuskTime(justTask);
        return justTask;
    }
    @Override
    public Integer addJustTask(JustTask justTask){
        if (!justTasks.containsKey(justTask.getId())) {
            justTasks.put(justTask.getId(), justTask);
            timeManager.addPrioritizedTasks(justTask);
            System.out.println("Задача сохранена под номером '" + justTask.getId() + "'");
            if (autoSave) {
                FileBackedTasksManager.save();
            }
        }
        return justTask.getId();
    }
    @Override
    public EpicTask createEpicTask(String name, String description) {
        ++idTask;
        LocalDateTime startTime = null;
        Duration duration = null;
        LocalDateTime endTime = null;
        Status status = Status.NEW;
        ArrayList<Integer> listIdSubtask = new ArrayList<>();
        return new EpicTask(idTask, name, description, status, startTime, duration, endTime, listIdSubtask);
    }
    @Override
    public Integer addEpicTask(EpicTask epicTask){
        if (!epicTasks.containsKey(epicTask.getId())) {
            epicTasks.put(epicTask.getId(), epicTask);
            System.out.println("Задача сохранена под номером '" + epicTask.getId() + "'");
            if (autoSave) {
                FileBackedTasksManager.save();
            }
        }
        return epicTask.getId();
    }
    @Override
    public SubTask createSubTask(String name,
                                 String description,
                                 LocalDateTime startTime,
                                 Duration duration,
                                 Integer idMaster) {
        ++idTask;
        Status status = Status.NEW;
        if (!epicTasks.containsKey(idMaster)) {
            throw new IllegalStateException("Такого эпика не существует, создайте сначала эпик");
        } else {
            if (timeManager.checkingFreeTime(startTime, duration)) {
                SubTask subTask = new SubTask(idTask, name, description, status, startTime, duration, InMemoryTimeManager.timeStatusFixed, idMaster);
                timeManager.addFixedTime(subTask);
                return subTask;
            } else {
                throw new UnsupportedOperationException("Ближайшее свободное время: " + timeManager.findFreeTime(duration));
            }
        }
    }
    @Override
    public boolean isThereSuchEpicTask(Integer idMaster) {
        if (epicTasks.containsKey(idMaster)) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public SubTask createSubTask(String name,
                                 String description,
                                 Duration duration,
                                 Integer idMaster) {
        ++idTask;
        Status status = Status.NEW;
        if (!epicTasks.containsKey(idMaster)) {
            throw new IllegalStateException("Такого эпика не существует, создайте сначала эпик");
        } else {
            LocalDateTime startTime = timeManager.findFreeTime(duration);
            SubTask subTask = new SubTask(idTask, name, description, status, startTime, duration, InMemoryTimeManager.timeStatusTusk, idMaster);
            timeManager.addTuskTime(subTask);
            return subTask;
        }
    }
    @Override
    public Integer addSubTask(SubTask subTask){
        if (!subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            epicTasks.get(subTask.getIdMaster()).getListIdSubtask().add(subTask.getId());
            System.out.println("Задача сохранена под номером '" + subTask.getId() + "'");
            }
        calculationEpicStatus(subTask.getIdMaster());
        calculationEpicTime(subTask.getIdMaster());
        timeManager.addPrioritizedTasks(subTask);
        if (autoSave) {
            FileBackedTasksManager.save();
        }
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
    public List<JustTask> getListAllJustTask() {
        List<JustTask> list = new ArrayList<>();
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
    public List<EpicTask> getListAllEpicTask() {
        List<EpicTask> list = new ArrayList<>();
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
    public List<SubTask> getListAllSubTask() {
        List<SubTask> list = new ArrayList<>();
        for (Integer key : subTasks.keySet()) {
            list.add(subTasks.get(key));
        }
        return list;
    }
    @Override
    public Task getTask (Integer id){
        Task task;
        if (justTasks.containsKey(id)) {
            historyManager.addHistory(justTasks.get(id));
            task = justTasks.get(id);
        } else if (epicTasks.containsKey(id)) {
            historyManager.addHistory(epicTasks.get(id));
            task = epicTasks.get(id);
        } else if (subTasks.containsKey(id)) {
            historyManager.addHistory(subTasks.get(id));
            task = subTasks.get(id);
        } else {
            throw new UnsupportedOperationException("Задачи под таким номером не найдено");
        }
        if (autoSave) {
            FileBackedTasksManager.save();
        }
        return task;
    }
    @Override
    public void removeTask(Integer id){
        if (justTasks.containsKey(id)){
            timeManager.recoveryTimeTask(justTasks.get(id), InMemoryTimeManager.statusTimeFree);
            timeManager.removeTaskFromPrioritizedTasks(justTasks.get(id));
            justTasks.remove(id);
            historyManager.remove(id);
            System.out.println("Задача №" + id + " успешно удалена...");

            if (autoSave) {
                FileBackedTasksManager.save();
            }
        } else if (epicTasks.containsKey(id)){
            if (epicTasks.get(id).getListIdSubtask().size() != 0) {
                for (int i = 0; i < epicTasks.get(id).getListIdSubtask().size(); i++) {
                    timeManager.removeTaskFromPrioritizedTasks(subTasks.get(epicTasks.get(id).getListIdSubtask().get(i)));
                    timeManager.recoveryTimeTask(subTasks.get(epicTasks.get(id).getListIdSubtask().get(i)), InMemoryTimeManager.statusTimeFree);
                    historyManager.remove(epicTasks.get(id).getListIdSubtask().get(i));
                    subTasks.remove(epicTasks.get(id).getListIdSubtask().get(i));
                }
                historyManager.remove(id);
                epicTasks.remove(id);
                System.out.println("Эпик №" + id + " успешно удален вместе с подзадачами.");
                if (autoSave) {
                    FileBackedTasksManager.save();
                }
            } else {
                epicTasks.remove(id);
                historyManager.remove(id);
                System.out.println("Эпик №" + id + " успешно удален");
                if (autoSave) {
                    FileBackedTasksManager.save();
                }
            }
        } else if (subTasks.containsKey(id)) {
            int idMaster = subTasks.get(id).getIdMaster();
            epicTasks.get(idMaster).getListIdSubtask().remove(id) ;
            timeManager.removeTaskFromPrioritizedTasks(subTasks.get(id));
            timeManager.recoveryTimeTask(subTasks.get(id), InMemoryTimeManager.statusTimeFree);
            subTasks.remove(id);
            historyManager.remove(id);
            calculationEpicStatus(idMaster);
            calculationEpicTime(idMaster);
            System.out.println("Задача №" + id + " успешно удалена...");
            if (autoSave) {
                FileBackedTasksManager.save();
            }
        } else {
            throw new UnsupportedOperationException("Такой задачи не обнаружено");
        }
    }
    @Override
    public void removeAllTask(){
        historyManager.removeAllHistory();
        timeManager.removeAllPrioritizedTasks();
        timeManager.cleaneTimeManager();
        justTasks.clear();
        subTasks.clear();
        epicTasks.clear();
        idTask = 0;
        FileBackedTasksManager.save();
        System.out.println("Все задачи удалены");
    }
    @Override
    public void changeName(Integer id, String name){
        if (justTasks.get(id) != null) {
            justTasks.get(id).setName(name);
            if (autoSave) {
                FileBackedTasksManager.save();
            }
        }
        else if (subTasks.get(id) != null) {
            subTasks.get(id).setName(name);
            if (autoSave) {
                FileBackedTasksManager.save();
            }
        }
        else if (epicTasks.get(id) != null) {
            epicTasks.get(id).setName(name);
            if (autoSave) {
                FileBackedTasksManager.save();
            }
        } else {
            System.out.println("Вы не верно ввели номер задачи, попробуйте снова");
        }
    }
    @Override
    public void changeStatus(Integer id, Status status){
        if (checkInputStatus(status)) {
            if (justTasks.get(id) != null) {
                justTasks.get(id).setStatus(status);
                if (autoSave) {
                    FileBackedTasksManager.save();
                }
            }
            else if (subTasks.get(id) != null) {
                int idMaster = subTasks.get(id).getIdMaster();
                subTasks.get(id).setStatus(status);
                calculationEpicStatus(idMaster);
                if (autoSave) {
                    FileBackedTasksManager.save();
                }
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
            if (autoSave) {
                FileBackedTasksManager.save();
            }
        }
        else if (subTasks.get(id) != null) {
            subTasks.get(id).setDescription(description);
            if (autoSave) {
                FileBackedTasksManager.save();
            }
        }
        else if (epicTasks.get(id) != null) {
            epicTasks.get(id).setDescription(description);
            if (autoSave) {
                FileBackedTasksManager.save();
            }
        }
        else {
            System.out.println("Вы не верно ввели номер задачи, попробуйте снова");
        }
    }
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
    @Override
    public List<Task> getPrioritizedTasks() {
        return timeManager.getPrioritizedTasks();
    }
    @Override
    public boolean containsKey(Integer id) {
        if (justTasks.containsKey(id) || epicTasks.containsKey(id) || subTasks.containsKey(id)) {
            return true;
        } else {
            return false;
        }
    }
    public byte getStatusTime(LocalDateTime startTime) {
        return timeManager.getStatusTime(startTime);
    }
    public static void showTask(Integer id) {
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
    public void recoveryTimeTask(Task task, byte statusTime) {
        timeManager.recoveryTimeTask(task, statusTime);
    }

    public void setIdTask(int idTaskMax) {
        idTask = idTaskMax;
    }
    private void calculationEpicStatus(Integer idMaster){
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
    private void calculationEpicTime(Integer idMaster) {
        if (epicTasks.get(idMaster).getListIdSubtask().size() != 0) {
            LocalDateTime min = null;
            LocalDateTime max = null;
            for (int i = 0; i < epicTasks.get(idMaster).getListIdSubtask().size(); i++) {
                if (min == null) {
                    min = subTasks.get(epicTasks.get(idMaster).getListIdSubtask().get(i)).getStartTime();
                    max = subTasks.get(epicTasks.get(idMaster).getListIdSubtask().get(i)).getStartTime().plus(
                            subTasks.get(epicTasks.get(idMaster).getListIdSubtask().get(i)).getDuration());
                } else {
                    if (subTasks.get(epicTasks.get(idMaster).getListIdSubtask().get(i)).getStartTime().isBefore(min)) {
                        min = subTasks.get(epicTasks.get(idMaster).getListIdSubtask().get(i)).getStartTime();
                    }
                    if (subTasks.get(epicTasks.get(idMaster).getListIdSubtask().get(i)).getStartTime().plus(
                        subTasks.get(epicTasks.get(idMaster).getListIdSubtask().get(i)).getDuration()).isAfter(max)) {
                        max = (subTasks.get(epicTasks.get(idMaster).getListIdSubtask().get(i)).getStartTime().plus(
                                subTasks.get(epicTasks.get(idMaster).getListIdSubtask().get(i)).getDuration()));
                    }
                }
            }
            epicTasks.get(idMaster).setStartTime(min);
            epicTasks.get(idMaster).setEndTime(max);
            epicTasks.get(idMaster).setDuration(Duration.between(min,max));
        } else {
            epicTasks.get(idMaster).setEndTime(null);
            epicTasks.get(idMaster).setStartTime(null);
            epicTasks.get(idMaster).setDuration(null);
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
