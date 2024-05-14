package server.routers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import server.Controller.AuthenticationController;
import server.services.AuthenticationService;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.module.Configuration;
import java.net.InetSocketAddress;

public class AuthenticationRouter implements HttpHandler {
    private final AuthenticationController authController;
    private HttpServer server;

    public AuthenticationRouter(SessionFactory sessionFactory) {
        AuthenticationService authService = new AuthenticationService(sessionFactory);
        authController = new AuthenticationController(authService);
    }

    public void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8081), 0); // Đổi cổng nếu cần
        server.createContext("/api/auth", this);
        server.setExecutor(null); // Sử dụng executor mặc định
        server.start();
        System.out.println("Authentication server started on port 8081");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        try {
            switch (path) {
                case "/api/auth/register":
                    if ("POST".equalsIgnoreCase(method)) {
                        authController.handleRegister(exchange);
                    } else {
                        sendResponse(exchange, 405, "Method Not Allowed");
                    }
                    break;
                case "/api/auth/login":
                    if ("POST".equalsIgnoreCase(method)) {
                        authController.handleLogin(exchange);
                    } else {
                        sendResponse(exchange, 405, "Method Not Allowed");
                    }
                    break;
                case "/api/auth/logout":
                    if ("POST".equalsIgnoreCase(method)) {
                        authController.handleLogout(exchange);
                    } else {
                        sendResponse(exchange, 405, "Method Not Allowed");
                    }
                    break;
                default:
                    sendResponse(exchange, 404, "Not Found");
            }
        } catch (Exception e) {
            sendResponse(exchange, 500, "Internal Server Error");
            e.printStackTrace();
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    // Phương thức main để chạy độc lập cho mục đích kiểm thử

}
