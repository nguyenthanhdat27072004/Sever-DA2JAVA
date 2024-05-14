package server.routers;

import com.sun.net.httpserver.HttpServer;
import server.Controller.ChatController;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ChatRouter {
    private static final int PORT = 8082; // Đổi cổng nếu cần
    private HttpServer server;

    public void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        ChatController chatController = new ChatController();

        server.createContext("/api/chat/send", exchange -> {
            try {
                chatController.handleSendMessage(exchange);
            } catch (Exception e) {
                handleError(exchange, e);
            }
        });

        server.createContext("/api/chat/messages", exchange -> {
            try {
                chatController.handleGetMessages(exchange);
            } catch (Exception e) {
                handleError(exchange, e);
            }
        });

        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("ChatRouter is listening on port " + PORT);
    }

    private void handleError(com.sun.net.httpserver.HttpExchange exchange, Exception e) {
        e.printStackTrace();
        try {
            String response = "Internal server error: " + e.getMessage();
            exchange.sendResponseHeaders(500, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
