package api;

import KVServer.KVServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.http.HttpClient;
import java.time.LocalDateTime;

public class HttpTaskServerTest {
    private static KVServer kvServer;
    private static HttpTaskServer taskServer;
    private static HttpClient client;
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
    private static final String TASK_URL = "http://localhost:8080/tasks/task/";
    private static final String EPIC_URL = "http://localhost:8080/tasks/epic/";
    private static final String SUBTASK_URL = "http://localhost:8080/tasks/subtask/";


}
