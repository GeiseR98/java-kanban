package api.handlers;

import api.Endpoint;
import api.adapters.DurationAdapter;
import api.adapters.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import tasks.Status;
import tasks.Task;
import tasks.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TasksHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();
    private final TaskManager taskManager;
    public TasksHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod(), exchange.getRequestURI().getQuery());

        switch (endpoint) {
            case GET_TASKS: {
                handleGetAllTask(exchange);
                break;
            }
            case DELETE_TASKS: {
                handleRemoveAllTask(exchange);
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
                handleChange(exchange);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпонта не существует", 404);
        }
    }
    private void handleChange(HttpExchange exchange) throws IOException {
        try {
            if (taskManager.containsKey(findId(exchange))) {
                String body = new String(exchange.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
                String[] box = body.split(":");
                String content = box[1].substring(2,box[1].length()-2);
                if (box.length == 2) {
                    if (box[0].contains("name")) {
                        taskManager.changeName(findId(exchange), content);
                        writeResponse(exchange, gson.toJson("Задача с номером " + findId(exchange) + " переименована."),200);
                    } else if (box[0].contains("description")) {
                        taskManager.changeDescription(findId(exchange), content);
                        writeResponse(exchange, gson.toJson("У задачи с номером " + findId(exchange) + " изменено описание."),200);
                    } else if (box[0].contains("status")) {
                        if (box[1].contains("NEW")) {
                            taskManager.changeStatus(findId(exchange), Status.NEW);
                            writeResponse(exchange, gson.toJson("Задаче с номером " + findId(exchange) + " присвоен статус: " + Status.NEW),200);
                        } else if (box[1].contains("DONE")) {
                            taskManager.changeStatus(findId(exchange), Status.DONE);
                            writeResponse(exchange, gson.toJson("Задаче с номером " + findId(exchange) + " присвоен статус: " + Status.DONE),200);
                        } else if (box[1].contains("IN_PROGRESS")) {
                            taskManager.changeStatus(findId(exchange), Status.IN_PROGRESS);
                            writeResponse(exchange, gson.toJson("Задаче с номером " + findId(exchange) + " присвоен статус: " + Status.IN_PROGRESS),200);
                        } else {
                            writeResponse(exchange, "Получен некорректный статус", 400);
                        }
                    } else {
                        writeResponse(exchange, "Получен некорректный JSON", 400);
                    }
                } else {
                    writeResponse(exchange, "Получен некорректный JSON", 400);
                }
            } else {
                writeResponse(exchange, "Задача под номером " + findId(exchange) + " не найдена", 404);
            }
        } catch (StringIndexOutOfBoundsException e) {
            writeResponse(exchange, gson.toJson("В запросе отсутствует необходимый параметр id"),400);
        } catch (NumberFormatException e) {
            writeResponse(exchange, gson.toJson("Неверный формат id"),400);
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
        } catch (StringIndexOutOfBoundsException e) {
            writeResponse(exchange, gson.toJson("В запросе отсутствует необходимый параметр id"),400);
        } catch (NumberFormatException e) {
            writeResponse(exchange, gson.toJson("Неверный формат id"),400);
        }
    }
    private void handleRemoveAllTask(HttpExchange exchange) throws IOException {
        taskManager.removeAllTask();
        writeResponse(exchange, gson.toJson("Все задачи удалены"),200);
    }
    private void handleGetAllTask(HttpExchange exchange) throws IOException {
        List<Task> allList = new ArrayList<>();
        allList.addAll(taskManager.getListAllJustTask());
        allList.addAll(taskManager.getListAllEpicTask());
        allList.addAll(taskManager.getListAllSubTask());
        writeResponse(exchange, gson.toJson(allList), 200);
    }
    private void handleGetTask(HttpExchange exchange) throws IOException {
        try {
            if (taskManager.containsKey(findId(exchange))) {
                writeResponse(exchange, gson.toJson(taskManager.getTask(findId(exchange))),200);
            } else {
                writeResponse(exchange, "Задача под номером " + findId(exchange) + " не найдена", 404);
            }
        } catch (StringIndexOutOfBoundsException e) {
            writeResponse(exchange, gson.toJson("В запросе отсутствует необходимый параметр id"),400);
        } catch (NumberFormatException e) {
            writeResponse(exchange, gson.toJson("Неверный формат id"),400);
        }
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
        if (pathParts.length == 2 && pathParts[1].equals("tasks")) {
            if (query == null) {
                switch (requestMethod) {
                    case "GET":
                        return Endpoint.GET_TASKS;
                    case "DELETE":
                        return Endpoint.DELETE_TASKS;
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
