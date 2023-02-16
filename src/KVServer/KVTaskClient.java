package KVServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class KVTaskClient {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private String apiToken;
    private final String serverURL;
    public KVTaskClient(String serverURL) {
        this.serverURL = serverURL;
    }
    public void register() {
        URI uri = URI.create(this.serverURL + "/register");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header("Content-Type", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                apiToken = response.body();
            } else {
                apiToken = String.valueOf(400);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void put(String key, String json) {
        URI uri = URI.create(this.serverURL + "/save/" + key + "?API_TOKEN=" + apiToken);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .header("Content-Type", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(DEFAULT_CHARSET));
            if (response.statusCode() != 200) {
                System.out.println("Не удалось сохранить данные");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public String load(String key) {
        URI uri = URI.create(this.serverURL + "/load/" + key + "?API_TOKEN=" + apiToken);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header("Content-Type", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(DEFAULT_CHARSET));
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.out.println("Error: " + response.statusCode());
                return response.body();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Во время запроса произошла ошибка";
        }
    }
}
