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

public class HistoryHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final Gson gson = new GsonBuilder().create();
    private final TaskManager taskManager;
    public HistoryHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod(), exchange.getRequestURI().getQuery());

        switch (endpoint) {
            case GET_HISTORY: {
                handleGetHistory(exchange);
                break;
            }
            case DELETE_HISTORY: {
                handleRemoveTask(exchange);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпонта не существует", 404);
        }
    }
    private void handleRemoveTask(HttpExchange exchange) throws IOException {
        taskManager.clearHistory();
        writeResponse(exchange, "История очищена",200);
    }
    private void handleGetHistory(HttpExchange exchange) throws IOException {
        writeResponse(exchange, gson.toJson(taskManager.getHistory()),200);
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
        if (pathParts.length == 3 && pathParts[2].equals("history")) {
            if (query == null) {
                switch (requestMethod) {
                    case "GET":
                        return Endpoint.GET_HISTORY;
                    case "DELETE":
                        return Endpoint.DELETE_HISTORY;
                }
            }
        }
        return Endpoint.UNKNOWN;
    }
}
