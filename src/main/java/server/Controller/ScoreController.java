package server.Controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import server.model.Score;
import server.services.ScoreService;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ScoreController {
    private ScoreService scoreService = new ScoreService();
    private Gson gson = new Gson();

    public ScoreController(ScoreService scoreService) {
        this.scoreService=scoreService;
    }

    public void handleScore(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Score score = gson.fromJson(requestBody, Score.class);
        boolean success = scoreService.saveScore(score);
        String response = gson.toJson(success ? "Score updated successfully" : "Failed to update score");
        exchange.sendResponseHeaders(success ? 200 : 500, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    public void getScores(HttpExchange exchange) throws IOException {
        List<Score> scores = scoreService.getTopScores();
        String response = gson.toJson(scores);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }


}
