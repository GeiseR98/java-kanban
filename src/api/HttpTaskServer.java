package api;

import com.sun.net.httpserver.HttpServer;
import tasks.TaskManager;
import utilit.Manager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {
    protected final HttpServer httpServer;
    protected static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public HttpTaskServer() throws IOException, InterruptedException {
        TaskManager taskManager = Manager.getDefault();
        this.httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/");
        httpServer.createContext("/tasks/task");
        httpServer.createContext("/tasks/epic");
        httpServer.createContext("/tasks/subtask");
        httpServer.createContext("/tasks/history");
    }
    public void start() {
        httpServer.start();
    }
    public void stop() {
        httpServer.stop(1);
    }
}
