package KVServer;

import com.google.gson.*;
import files.FileBackedTasksManager;
import tasks.EpicTask;
import tasks.JustTask;
import tasks.SubTask;
import tasks.Task;
import utilit.Manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {
    private final Gson gson = Manager.getGson();
    private final KVTaskClient client;

    public HttpTaskManager(String path) {
        super(null);
        client = new KVTaskClient(path);
    }
    @Override
    public void save() {
        client.register();
        System.out.println("зарегались");
        String jsonJustTask = gson.toJson(new ArrayList<>(justTasks.values()));
        client.put("tasks", jsonJustTask);
        String jsonEpicTasks = gson.toJson(new ArrayList<>(epicTasks.values()));
        client.put("epics", jsonEpicTasks);
        String jsonSubTask = gson.toJson(new ArrayList<>(subTasks.values()));
        client.put("subtasks", jsonSubTask);

        String jsonHistory = gson.toJson(getHistory().stream().map(Task::getId).collect(Collectors.toList()));
        client.put("history", jsonHistory);
    }
    private void load() {
        ArrayList<Integer> listAllId = new ArrayList<>();

        JsonElement jsonJustTasks = JsonParser.parseString(client.load("tasks"));
        if (!jsonJustTasks.isJsonNull()) {
            JsonArray jsonJustTaskArray = jsonJustTasks.getAsJsonArray();
            for (JsonElement jsonJustTask : jsonJustTaskArray) {
                JustTask justTask = gson.fromJson(jsonJustTask, JustTask.class);
                addJustTask(justTask);
                listAllId.add(justTask.getId());
                recoveryTimeTask(justTask, justTask.getTimeStatus());
            }
        }
        JsonElement jsonEpicTasks = JsonParser.parseString(client.load("epics"));
        if (!jsonEpicTasks.isJsonNull()) {
            JsonArray jsonEpicTaskArray = jsonEpicTasks.getAsJsonArray();
            for (JsonElement jsonEpicTask : jsonEpicTaskArray) {
                EpicTask epicTask = gson.fromJson(jsonEpicTask, EpicTask.class);
                addEpicTask(epicTask);
                listAllId.add(epicTask.getId());
            }
        }
        JsonElement jsonSubTasks = JsonParser.parseString(client.load("subtasks"));
        if (!jsonSubTasks.isJsonNull()) {
            JsonArray jsonSubTaskArray = jsonSubTasks.getAsJsonArray();
            for (JsonElement jsonSubTask : jsonSubTaskArray) {
                SubTask subTask = gson.fromJson(jsonSubTask, SubTask.class);
                addSubTask(subTask);
                listAllId.add(subTask.getId());
                recoveryTimeTask(subTask, subTask.getTimeStatus());
            }
        }
        JsonElement jsonHistoryList = JsonParser.parseString(client.load("history"));
        if (!jsonHistoryList.isJsonNull()) {
            JsonArray jsonHistoryArray = jsonHistoryList.getAsJsonArray();
            for (JsonElement jsonIdTask : jsonHistoryArray) {
                int idTask = jsonIdTask.getAsInt();
                getTask(idTask);
            }
        }
        if (!listAllId.isEmpty()) {
            setIdTask(Collections.max(listAllId));
        }
    }
}
