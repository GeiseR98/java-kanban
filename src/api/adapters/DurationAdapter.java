package api.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DurationAdapter extends TypeAdapter<Duration> {
//            "duration": "0 часов, 29 минут"

    @Override
    public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
        if (duration != null) {
            jsonWriter.value(duration.toHours() + " часов, " + duration.toMinutesPart() + " минут");
        } else {
            jsonWriter.value((String) null);
        }
    }

    @Override
    public Duration read(JsonReader jsonReader) throws IOException {
        String[] split = jsonReader.nextString().split(", ");
        Duration duration = null;
        if (split.length == 2 && split[1] != null) {
            Duration haur = Duration.ofHours(Integer.parseInt(split[0].substring(0, split[0].lastIndexOf(" "))));
            duration = haur.plusMinutes(Integer.parseInt(split[1].substring(0, split[1].lastIndexOf(" "))));
        }
        return duration;
    }
}
