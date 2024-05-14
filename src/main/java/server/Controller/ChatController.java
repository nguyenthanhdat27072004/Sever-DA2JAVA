package server.Controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import server.model.Message;
import server.services.ChatService;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import server.ObjectGson.SignupRequest;
import server.model.Account;
import server.services.AuthenticationService;
import util.EmailService;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;


public class ChatController {
    private ChatService chatService = new ChatService();
    private Gson gson = new Gson();

    public void handleSendMessage(HttpExchange exchange) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)) {
            Message message = gson.fromJson(isr, Message.class);
            chatService.saveMessage(message);
            sendResponse(exchange, 200, "Message sent successfully");
        } catch (Exception e) {
            sendResponse(exchange, 400, "Bad Request");
        }
    }

    public void handleGetMessages(HttpExchange exchange) throws IOException {
        List<Message> messages = chatService.getAllMessages();
        String jsonResponse = gson.toJson(messages);
        sendResponse(exchange, 200, jsonResponse);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] responseData = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseData.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseData);
            os.flush();
        }
    }
}
