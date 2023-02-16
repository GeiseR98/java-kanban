package KVServer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import files.FileBackedTasksManager;
import tasks.EpicTask;
import tasks.JustTask;
import tasks.Task;
import utilit.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {
    private final Gson gson;
    private KVTaskClient client;

    public HttpTaskManager(int port) {
        this(port, false);
    }
    public HttpTaskManager(int port, boolean load) {
        super();
        gson = Manager.getGson();
        if (load) {
            load();
        }
    }
    public static void save() {
        HttpTaskManager taskManager = new HttpTaskManager(8078);
        String jsonJustTasks = taskManager.gson.toJson(new ArrayList<>(justTasks.values()));
        taskManager.client.put("task", jsonJustTasks);
        String jsonSubTasks = taskManager.gson.toJson(new ArrayList<>(subTasks.values()));
        taskManager.client.put("subtask", jsonSubTasks);
        String jsonEpicTask = taskManager.gson.toJson(new ArrayList<>(epicTasks.values()));
        taskManager.client.put("epic", jsonEpicTask);

        String jsonHistory = taskManager.gson.toJson(taskManager.getHistory().stream().map(Task::getId).collect(Collectors.toList()));
        taskManager.client.put("history", jsonHistory);
    }
    private void load() {
//        List<JustTask> justTasks = gson.fromJson(client.load("tasks"), new TypeToken<ArrayList<JustTask>>() {
//        }.getType());
//        recoveryJustTask(justTasks);
//
//        List<EpicTask> epicTasks = gson.fromJson(client.load("epics"), new TypeToken<ArrayList<EpicTask>>() {
//        }.getType());
//        addEpicTask(epicTasks);
    }
}
