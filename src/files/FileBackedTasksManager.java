package files;

import tasks.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class FileBackedTasksManager extends InMemoryTaskManager {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    static boolean doSave = false;

    @Override
    public Integer addJustTask(JustTask justTask) {
        return inMemoryTaskManager.addJustTask(justTask);
    }

    @Override
    public Integer addEpicTask(EpicTask epicTask) {
        super.addEpicTask(epicTask);
        if (doSave){
            save();
        }
        return epicTask.getId();
    }
    @Override
    public Integer addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        if (doSave){
            save();
        }
        return subTask.getId();
    }
    @Override
    public Task getTask(Integer id) {
        return super.getTask(id);
    }

    @Override
    public void setIdTask(int idTask) {
        super.setIdTask(idTask);
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
    public void fromString() {
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
            } else {
                System.out.println("не считалось");
            }
        }
        if (!listAllId.isEmpty()) {
            setIdTask(Collections.max(listAllId));
            String[] listId = lines[lines.length - 1].split(",");
            for (String id : listId) {
                getTask(Integer.parseInt(id));
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
        String[] listIdSub = elements[5].split("-");
        for (String idSub : listIdSub) {
            listIdSubtask.add(Integer.parseInt(idSub));
        }
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


    private void save() {

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
        for (Integer sub : epicTask.getListIdSubtask()) {
            listSub.append(sub);
            listSub.append("-");
        }
        listSub.deleteCharAt(listSub.length() - 1);
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
