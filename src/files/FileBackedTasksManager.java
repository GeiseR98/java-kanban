package files;

import tasks.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileBackedTasksManager extends InMemoryTaskManager {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    static boolean doSave = false;

    static void main(String[] args) {

    }

    public JustTask recoveryJustTask(String line) {
        String[] elements = line.split(",");
        return new JustTask(Integer.parseInt(elements[0]), elements[2], elements[4], Status.valueOf(elements[3]));
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
                Integer.parseInt(elements[0]), elements[2], elements[4], Status.valueOf(elements[3]), listIdSubtask);
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
    void fromString(String path) {
        String saves = readFile(path);
        String[] lines = saves.split("\r?\n");
        for (int i = 1; i < lines.length - 2; i++) {
            String line = lines[i];
            if (line.contains(String.valueOf(Types.JUSTTASK))) {
                addJustTask(recoveryJustTask(line));
            }
            if (line.contains(String.valueOf(Types.EPICTASK))) {
                addEpicTask(recovertEpicTask(line));
            }
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


    @Override
    public Integer addJustTask(JustTask justTask) {
        super.addJustTask(justTask);
        if (doSave){
            save();
        }
        return justTask.getId();
    }

    @Override
    public Integer addEpicTask(EpicTask epicTask) {
        super.addEpicTask(epicTask);
        save();
        return epicTask.getId();
    }

    @Override
    public Integer addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
        return subTask.getId();
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
        String line = null;
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
        String line = null;
        line = subTask.getId() + "," +
                Types.SUBTASK + "," +
                subTask.getName() + "," +
                subTask.getStatus() + "," +
                subTask.getDescription() + "," +
                subTask.getIdMaster();
        return line;
    }





}
