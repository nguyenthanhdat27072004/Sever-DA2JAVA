package server.routers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import server.Controller.ScoreController;
import server.services.ScoreService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ScoreRouter implements HttpHandler {
    private ScoreController scoreController;

    public ScoreRouter() {
        ScoreService scoreService = new ScoreService();
        scoreController = new ScoreController(scoreService); // Assuming correct constructor
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/scores", new ScoreRouter());
        server.setExecutor(null);
        server.start();
        System.out.println("Score service started on port 8080");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        try {
            switch (path) {
                case "/api/scores/get":
                    if ("GET".equalsIgnoreCase(method)) {
                        scoreController.getScores(exchange);
                    }
                    break;
                case "/api/scores/post":
                    if ("POST".equalsIgnoreCase(method)) {
                        scoreController.handleScore(exchange);
                    }
                    break;
                default:
                    sendResponse(exchange, 404, "Not Found");
            }
        } catch (Exception e) {
            sendResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
