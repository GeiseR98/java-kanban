package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tasks.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class JustTaskHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
    private final TaskManager taskManager;
    public JustTaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case GET_JUSTTASKS: {
                handleGetJustTask(exchange);
                break;
            }
            case POST_JUSTTASK: {
                break;
            }
            case GET_TASK_BY_ID: {
                break;
            }
            case DELETE_TASK_BY_ID: {
                break;
            }
            case PATCH: {
                break;
            }
        }
    }
    private void handleGetJustTask(HttpExchange exchange) throws IOException {
        writeResponse(exchange, gson.toJson(taskManager.getListAllJustTask()), 200);
    }
    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {
        if(responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }

    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");
        if (pathParts.length == 3 && pathParts[2].equals("task")) {
            switch (requestMethod) {
                case "GET":
                    return Endpoint.GET_JUSTTASKS;
                case "POST":
                    return Endpoint.POST_JUSTTASK;
            }
        }
        if (pathParts.length == 4 && pathParts[2].equals("task")) {
            switch (requestMethod) {
                case "GET":
                    return Endpoint.GET_TASK_BY_ID;
                case "DELETE":
                    return Endpoint.DELETE_TASK_BY_ID;
                case "PATCH":
                    return Endpoint.PATCH;
            }
        }
        return Endpoint.UNKNOWN;
    }
}
