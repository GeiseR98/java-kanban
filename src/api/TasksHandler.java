package api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class TasksHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
    private Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

    }
}
