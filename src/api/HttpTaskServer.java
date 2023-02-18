package api;

import com.sun.net.httpserver.HttpServer;
import managers.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    protected final HttpServer httpServer;
    protected static final int PORT = 8080;
    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/managers", new TasksHandler(taskManager));
        httpServer.createContext("/managers/task", new JustTaskHandler(taskManager));
        httpServer.createContext("/managers/epic", new EpicTaskHandler(taskManager));
        httpServer.createContext("/managers/subtask", new SubTaskHandler(taskManager));
        httpServer.createContext("/managers/history", new HistoryHandler(taskManager));
        httpServer.createContext("/managers/prioritized", new PrioritizedHandler(taskManager));
    }
    public void start() {
        httpServer.start();
    }
    public void stop() {
        httpServer.stop(1);
    }
}
