package files;

import history.HistoryManager;
import tasks.*;
import timeAndDate.TimeManager;
import utilit.Manager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class FileBackedTasksManager extends InMemoryTaskManager {
    @Override
    public Integer addJustTask(JustTask justTask){
        super.addJustTask(justTask);
        recoveryTimeTask(justTask, justTask.getTimeStatus());
        save();
        return justTask.getId();
    }
    @Override
    public Integer addEpicTask(EpicTask epicTask){
        super.addEpicTask(epicTask);
        save();
        return epicTask.getId();
    }

    @Override
    public Integer addSubTask(SubTask subTask){
        super.addSubTask(subTask);
        save();
        return subTask.getId();
    }
    @Override
    public void removeTask(Integer id){
        super.removeTask(id);
        save();
    }

    @Override
    public void removeAllTask(){
        super.removeAllTask();
        save();
    }

    @Override
    public void changeStatus(Integer id, Status status){
        super.changeStatus(id, status);
        save();
    }

    @Override
    public void changeDescription(Integer id, String description){
        super.changeDescription(id, description);
        save();
    }

    @Override
    public void setIdTask(int idTask) {
        super.setIdTask(idTask);
    }

    public static void save()  { // сохранение всего, надо будет переделать на редактирование файла
        TaskManager taskManager = Manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        TimeManager timeManager = Manager.getDefaultTime();
        // иначе не получилось сделать save статическим
        fileBackedTasksManager.remouveAndCreatFile();
        Writer fileWriter = null;
        try {
            fileWriter = new FileWriter("saves" + File.separator + "file.csv", true);
            fileWriter.write("id,тип,название,статус,описание,начало,продолжительность,окончание,statusTime,idMaster(для подзадач)\n");
        for (Task justTask : taskManager.getListAllJustTask()) {
            fileWriter.write(fileBackedTasksManager.toString((JustTask) justTask) + "\n");
        }
        for (Task epicTasks : taskManager.getListAllEpicTask()) {
            fileWriter.write(fileBackedTasksManager.toString((EpicTask) epicTasks) + "\n");
        }
        for (Task subTask : taskManager.getListAllSubTask()) {
            fileWriter.write(fileBackedTasksManager.toString((SubTask) subTask) + "\n");
        }
        fileWriter.write("\n");
        if (!historyManager.getHistory().isEmpty()) {
            StringBuilder listId = new StringBuilder();
            for (Task id : historyManager.getHistory()) {
                listId.append(id.getId());
                listId.append(",");
            }
            listId.deleteCharAt(listId.length() - 1);
            fileWriter.write(String.valueOf(listId));
        } else {
            fileWriter.write("История задач пуста");
        }
        } catch (IOException e) {
            System.out.println("Произошла ошибка записи файла");
        }
        try {
            fileWriter.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка закрытия потока");
        }

    }

    private void remouveAndCreatFile() {
        try {
            Files.delete(Path.of("saves" + File.separator + "file.csv"));
        } catch (IOException e) {
            System.out.println("Произошла ошибка при удалении файла.");
            e.printStackTrace();
        }
        try {
            Files.createFile(Path.of("saves" + File.separator + "file.csv"));
        } catch (IOException e) {
            System.out.println("Произошла ошибка при создании файла.");
            e.printStackTrace();
        }
    }

    public String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Че то не работает");
            return null;
        }
    }

    public static FileBackedTasksManager loadFromFile() throws IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        ArrayList<Integer> listAllId = new ArrayList<>();
        String file = fileBackedTasksManager.readFile("saves" + File.separator + "file.csv");

        String[] lines = file.split("\r?\n");
        for (int i = 1; i < (lines.length - 2); i++) {
            String line = lines[i];
            if (line.contains(String.valueOf(Types.JUSTTASK))) {
                listAllId.add(fileBackedTasksManager.addJustTask(fileBackedTasksManager.recoveryJustTask(line)));
            } else if (line.contains(String.valueOf(Types.EPICTASK))) {
                listAllId.add(fileBackedTasksManager.addEpicTask(fileBackedTasksManager.recovertEpicTask(line)));
            } else if (line.contains(String.valueOf(Types.SUBTASK))) {
                listAllId.add(fileBackedTasksManager.addSubTask(fileBackedTasksManager.recoverySubTask(line)));
            }
        }
        if (!listAllId.isEmpty()) {     // если задачи не восстановились, то историю задач восстанавливать бессмысленно.
            fileBackedTasksManager.setIdTask(Collections.max(listAllId));

            if (!lines[lines.length - 1].contains("История задач пуста")) {
                String[] listId = lines[lines.length - 1].split(",");
                for (String id : listId) {
                    fileBackedTasksManager.getTask(Integer.parseInt(id));
                }
            }
        }
        return fileBackedTasksManager;
    }

    public JustTask recoveryJustTask(String line) {
        String[] elements = line.split(",");
        int id = Integer.parseInt(elements[0]);
        String name = elements[2];
        String description = elements[4];
        Status status = Status.valueOf(elements[3]);
        LocalDateTime startTime = LocalDateTime.parse(elements[5]);
        Duration duration = Duration.parse(elements[6]);
        JustTask justTask = new JustTask(id, name, description, status, startTime, duration, Byte.parseByte(elements[8]));
        recoveryTimeTask(justTask, Byte.parseByte(elements[8]));
        return justTask;
    }

    public EpicTask recovertEpicTask(String line) {
        Status status = Status.NEW;
        ArrayList<Integer> listIdSubtask = new ArrayList<>();
        String[] elements = line.split(",");
        return new EpicTask(Integer.parseInt(elements[0]), elements[2], elements[4], status, listIdSubtask);
    }

    public SubTask recoverySubTask(String line) {
        String[] elements = line.split(",");
        SubTask subTask = new SubTask(Integer.parseInt(elements[0]), elements[2], elements[4], Status.valueOf(elements[3]),
                LocalDateTime.parse(elements[5]), Duration.parse(elements[6]), Byte.parseByte(elements[8]), Integer.parseInt(elements[9]));
        recoveryTimeTask(subTask, Byte.parseByte(elements[8]));
        return subTask;
    }
    String toString(JustTask justTask) {


        String line;
        line = justTask.getId() + "," +
                Types.JUSTTASK + "," +
                justTask.getName() + "," +
                justTask.getStatus() + "," +
                justTask.getDescription() + "," +
                justTask.getStartTime() + "," +
                justTask.getDuration() + "," +
                justTask.getEndTime() + "," +
                justTask.getTimeStatus();

        return line;
    }

    String toString(EpicTask epicTask) {
        String line;
        StringBuilder listSub = new StringBuilder();
        if (!epicTask.getListIdSubtask().isEmpty()) {
            for (Integer sub : epicTask.getListIdSubtask()) {
                listSub.append(sub);
                listSub.append("_");
            }
            listSub.deleteCharAt(listSub.length() - 1);
        }
        line = epicTask.getId() + "," +
                Types.EPICTASK + "," +
                epicTask.getName() + "," +
                epicTask.getStatus() + "," +
                epicTask.getDescription() + "," +
                epicTask.getStartTime() + "," +
                epicTask.getDuration() + "," +
                epicTask.getEndTime() + "," +
                listSub.toString();
        return line;
    }

    String toString(SubTask subTask) {
        String line;
        line = subTask.getId() + "," +
                Types.SUBTASK + "," +
                subTask.getName() + "," +
                subTask.getStatus() + "," +
                subTask.getDescription() + "," +
                subTask.getStartTime() + "," +
                subTask.getDuration() + "," +
                subTask.getEndTime() + "," +
                subTask.getIdMaster();
        return line;
    }
}
