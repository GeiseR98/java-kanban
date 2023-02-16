package utilit;

import api.DurationAdapter;
import api.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import files.FileBackedTasksManager;
import history.HistoryManager;
import history.InMemoryHistoryManager;
import tasks.InMemoryTaskManager;
import tasks.TaskManager;
import timeAndDate.InMemoryTimeManager;
import timeAndDate.TimeManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class Manager {

    public static TaskManager getDefault(File file) throws IOException {
        return FileBackedTasksManager.loadFromFile(file);
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
    public static TimeManager getDefaultTime() {
        return new InMemoryTimeManager();
    }
    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
    }
}
