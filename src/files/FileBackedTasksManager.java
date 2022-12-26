package files;

import history.HistoryManager;
import tasks.*;
import utilit.Manager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class FileBackedTasksManager extends InMemoryTaskManager {

        @Override
    public Integer addJustTask(JustTask justTask) throws IOException {
        super.addJustTask(justTask);
        save();
        return justTask.getId();
    }

    @Override
    public Integer addEpicTask(EpicTask epicTask) throws IOException {
        super.addEpicTask(epicTask);
        save();
        return epicTask.getId();
    }
    @Override
    public Integer addSubTask(SubTask subTask) throws IOException {
        super.addSubTask(subTask);
        save();
        return subTask.getId();
    }
    @Override
    public Task getTask(Integer id) throws IOException {
        return super.getTask(id);
    }

    @Override
    public void removeTask(Integer id) throws IOException {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeAllTask() throws IOException {
        super.removeAllTask();
        save();
    }

    @Override
    public void changeStatus(Integer id, Status status) throws IOException {
        super.changeStatus(id, status);
        save();
    }

    @Override
    public void changeDescription(Integer id, String description) throws IOException {
        super.changeDescription(id, description);
        save();
    }

    @Override
    public void setIdTask(int idTask) {
        super.setIdTask(idTask);
    }

    public static void save() throws IOException { // сохранение всего, надо будет переделать на редактирование файла
            TaskManager taskManager = Manager.getDefault();
            HistoryManager historyManager = Manager.getDefaultHistory();;
            FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
            // иначе не получилось сделать save статическим
        fileBackedTasksManager.remouveAndCreatFile();
        Writer fileWriter = new FileWriter("saves" + File.separator + "file.csv", true);
        fileWriter.write("id,тип,название,статус,описание,idMaster(для подзадач)\n");
        for (Task justTask : taskManager.getListAllJustTask()){
            fileWriter.write(fileBackedTasksManager.toString((JustTask) justTask)+ "\n");
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


        fileWriter.close();
    }
    private void remouveAndCreatFile() {
        try {
            Files.delete(Path.of("saves" + File.separator + "file.csv"));
        }
        catch (IOException e) {
            System.out.println("Произошла ошибка при удалении файла.");
            e.printStackTrace();
        }
        try {
            Files.createFile(Path.of("saves" + File.separator + "file.csv"));
            // создайте файл
        }
        catch (IOException e) {
            System.out.println("Произошла ошибка при создании файла.");
            e.printStackTrace();
        }
    }

    public String readFile(String path) {
        try {
            String file = Files.readString(Path.of(path));
            return file;
        } catch (IOException e) {
            System.out.println("Че то не работает");
            return null;
        }
    }
    public void loadFromFile() throws IOException {
        ArrayList<Integer> listAllId = new ArrayList<>();
        String file = readFile("saves" + File.separator + "file.csv");

        String[] lines = file.split("\r?\n");
        for (int i = 1; i < (lines.length - 2); i++) {
            String line = lines[i];
            if (line.contains(String.valueOf(Types.JUSTTASK))) {
                listAllId.add(addJustTask(recoveryJustTask(line)));
            } else if (line.contains(String.valueOf(Types.EPICTASK))) {
                listAllId.add(addEpicTask(recovertEpicTask(line)));
            } else if (line.contains(String.valueOf(Types.SUBTASK))) {
                listAllId.add(addSubTask(recoverySubTask(line)));
            }
        }
        if (!listAllId.isEmpty()) {     // если задачи не восстановились, то историю задач восстанавливать бессмысленно.
            setIdTask(Collections.max(listAllId));

            if (!lines[lines.length - 1].contains("История задач пуста") ) {
                String[] listId = lines[lines.length - 1].split(",");
                for (String id : listId) {
                    getTask(Integer.parseInt(id));
                }
            }
        }
    }
    public JustTask recoveryJustTask(String line) {
        String[] elements = line.split(",");
        int id = Integer.parseInt(elements[0]);
        String name = elements[2];
        String description = elements[4];
        Status status = Status.valueOf(elements[3]);
        return new JustTask(id, name, description, status);
    }
    public EpicTask recovertEpicTask(String line) {
        Status status = Status.NEW;
        ArrayList<Integer> listIdSubtask = new ArrayList<>();
        String[] elements = line.split(",");


    /*  кусок кода понадобится при будущих обновлениях
        if (elements.length == 6) {
            String[] listIdSub = elements[5].split("_");
            for (String idSub : listIdSub) {
                listIdSubtask.add(Integer.parseInt(idSub));
            }
        }*/
        return new EpicTask(
                Integer.parseInt(elements[0]), elements[2], elements[4], status, listIdSubtask);
    }
    public SubTask recoverySubTask(String line) {
        String[] elements = line.split(",");
        return new SubTask(
                Integer.parseInt(elements[0]),
                elements[2],
                elements[4],
                Status.valueOf(elements[3]),
                Integer.parseInt(elements[5]));
    }
    String toString(JustTask justTask) {
        String line = null;
        line = justTask.getId() + "," +
                Types.JUSTTASK + "," +
                justTask.getName() + "," +
                justTask.getStatus() + "," +
                justTask.getDescription();
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
                subTask.getIdMaster();
        return line;
    }
}
