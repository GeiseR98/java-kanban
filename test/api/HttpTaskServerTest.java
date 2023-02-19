package api;

import kvServer.HttpTaskManager;
import kvServer.KVServer;
import com.google.gson.Gson;
import tasks.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timeAndDate.InMemoryTimeManager;
import utilit.Manager;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.Duration;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {
    KVServer kvServer;
    HttpTaskServer taskServer;
    Gson gson = Manager.getGson();
    HttpClient client;
    TaskManager taskManager;
    InMemoryTimeManager timeManager;
    String TASK_URL = "http://localhost:8080/tasks/task/";
    String EPIC_URL = "http://localhost:8080/tasks/epic/";
    String SUBTASK_URL = "http://localhost:8080/tasks/subtask/";

    JustTask createJustTask() {
        return taskManager.createJustTask("задача", "описание задачи", Duration.ofMinutes(10));
    }
    EpicTask createEpic() {
        return taskManager.createEpicTask("эпик", "описание эпика");
    }
    SubTask createSubTusk() {
        return taskManager.createSubTask("подзадача", "описание подзадачи", Duration.ofMinutes(10), 1);
    }
    @BeforeAll
    static void beforeAll() {
        HttpTaskManager.autoSave = false;
    }
    @BeforeEach
    public void beforeEach() {
        try {
            taskManager = new InMemoryTaskManager();
            timeManager = new InMemoryTimeManager();
            taskServer = new HttpTaskServer(taskManager);
            taskServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @AfterEach
    public void afterEach() {
        taskServer.stop();
        taskManager.removeAllTask();
        timeManager.cleaneTimeManager();
    }
    @Test
    void DELETE_in_tasks_shouldRemoveAllTask() {
        taskManager.addEpicTask(createEpic());
        taskManager.addJustTask(createJustTask());
        taskManager.addSubTask(createSubTusk());
        String query = "http://localhost:8080/tasks";
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);
            connection.connect();
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                System.out.println(connection.getResponseCode());
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        assertEquals(Collections.EMPTY_LIST, taskManager.getListAllJustTask(), "Задачи удалены");
        assertEquals(Collections.EMPTY_LIST, taskManager.getListAllSubTask(), "Подзадачи удалены");
        assertEquals(Collections.EMPTY_LIST, taskManager.getListAllEpicTask(),"Эпики удалены");
    }
    @Test
    void DELETE_in_tasks_task_shouldRemoveTaskBiId(){
        taskManager.addEpicTask(createEpic());
        taskManager.addJustTask(createJustTask());
        taskManager.addSubTask(createSubTusk());
        String query = "http://localhost:8080/tasks/task/?id=2";
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);
            connection.connect();
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                System.out.println(connection.getResponseCode());
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        assertEquals(Collections.EMPTY_LIST, taskManager.getListAllJustTask(), "Задачи удалены");
        assertNotEquals(Collections.EMPTY_LIST, taskManager.getListAllSubTask(), "Подзадачи удалены");
        assertNotEquals(Collections.EMPTY_LIST, taskManager.getListAllEpicTask(),"Эпики удалены");
    }
    @Test
    void GET_in_tasks_task_shouldGetJustTask(){
        String name = "задача";
        String description = "описание задачи";
        Duration duration = Duration.ofMinutes(10);
        JustTask justTask = taskManager.createJustTask(name, description, duration);
        JustTask justTaskWithRequest;
        taskManager.addJustTask(justTask);

        String query = "http://localhost:8080/tasks/task/?id=1";
        HttpURLConnection connection = null;
        InputStreamReader isR = null;
        BufferedReader bfR = null;
        StringBuilder sb = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.addRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);
            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                isR = new InputStreamReader(connection.getInputStream());
                bfR = new BufferedReader(isR);
                String line;
                while ((line = bfR.readLine()) != null) {
                    sb.append(line);
                }
                justTaskWithRequest = gson.fromJson(new String(sb.toString()), JustTask.class);
                assertEquals(justTask, justTaskWithRequest);
            }
        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    @Test
    void POST_in_tasks_task_shouldCreateAndSaveJustTask(){
        String name = "задача";
        String description = "описание задачи";
        Duration duration = Duration.ofMinutes(10);
        JustTask justTask = taskManager.createJustTask(name, description, duration);

        String query = "http://localhost:8080/tasks/task";
        HttpURLConnection connection = null;
        OutputStream os = null;
        InputStreamReader isR = null;
        BufferedReader bfR = null;
        StringBuilder sb = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.addRequestProperty("Content-Type", "application/json; StandardCharsets.UTF_8");
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);
            connection.connect();
            try {
                os = connection.getOutputStream();
                String json = "{\"timeStatus\":2," +
                        "\"id\":1," +
                        "\"name\":\"задача\"," +
                        "\"description\":\"описание задачи\"," +
                        "\"status\":\"NEW\"," +
                        "\"duration\":\"0 часов, 10 минут\"," +
                        "\"startTime\":\"18.02.2023 23:45\"}";
                byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
                os.write(bytes);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                os.close();
            }
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                isR = new InputStreamReader(connection.getInputStream());
                bfR = new BufferedReader(isR);
                System.out.println(bfR);
                String line;
                while ((line = bfR.readLine()) != null) {
                    sb.append(line);
                }
                System.out.println(sb + "ответ");
            }
            System.out.println(connection.getResponseCode());
            System.out.println(connection.getResponseMessage());
        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        assertEquals(justTask.getName(), taskManager.getTask(2).getName());
        assertEquals(justTask.getDescription(), taskManager.getTask(2).getDescription());
        assertEquals(justTask.getStatus(), taskManager.getTask(2).getStatus());
        assertEquals(justTask.getDuration(), taskManager.getTask(2).getDuration());
    }
}

