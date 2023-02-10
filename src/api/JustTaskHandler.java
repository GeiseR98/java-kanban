package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tasks.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class JustTaskHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();
    private final TaskManager taskManager;
    public JustTaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod(), exchange.getRequestURI().getQuery());

        switch (endpoint) {
            case GET_JUSTTASKS: {
                handleGetJustTask(exchange);
                break;
            }
            case POST_JUSTTASK: {
                break;
            }
            case GET_TASK_BY_ID: {
                handleGetTask(exchange);
                break;
            }
            case DELETE_TASK_BY_ID: {
                handleRemoveTask(exchange);
                break;
            }
            case PATCH: {
                break;
            }
        }
    }
    private void handleRemoveTask(HttpExchange exchange) throws IOException {
        try {
            if (taskManager.containsKey(findId(exchange))) {
                taskManager.removeTask(findId(exchange));
                writeResponse(exchange, gson.toJson("Задача с номером " + findId(exchange) + " удалена."),200);
            } else {
                writeResponse(exchange, "Задача под номером " + findId(exchange) + " не найдена", 404);
            }

        }catch (StringIndexOutOfBoundsException e) {
            writeResponse(exchange, gson.toJson("В запросе отсутствует необходимый параметр id"),400);
        } catch (NumberFormatException e) {
            writeResponse(exchange, gson.toJson("Неверный формат id"),400);
        }

    }
    private void handleGetJustTask(HttpExchange exchange) throws IOException {
        writeResponse(exchange, gson.toJson(taskManager.getListAllJustTask()), 200);
    }
    private void handleGetTask(HttpExchange exchange) throws IOException {
        writeResponse(exchange, gson.toJson(taskManager.getTask(findId(exchange))),200);
    }
    private Integer findId(HttpExchange exchange) {
        String query = exchange.getRequestURI().getQuery();
        return Integer.parseInt(query.substring(query.indexOf("id=") + 3));
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

    private Endpoint getEndpoint(String requestPath, String requestMethod, String query) {
        String[] pathParts = requestPath.split("/");
        if (pathParts.length == 3 && pathParts[2].equals("task")) {
            if (query == null) {
                switch (requestMethod) {
                    case "GET":
                        return Endpoint.GET_JUSTTASKS;
                    case "POST":
                        return Endpoint.POST_JUSTTASK;
                }
            } else {
                switch (requestMethod) {
                    case "GET":
                        return Endpoint.GET_TASK_BY_ID;
                    case "DELETE":
                        return Endpoint.DELETE_TASK_BY_ID;
                    case "PATCH":
                        return Endpoint.PATCH;
                }
            }
        }
        return Endpoint.UNKNOWN;
    }
}
