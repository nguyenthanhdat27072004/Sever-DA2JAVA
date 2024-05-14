package server.GameServer;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import server.Controller.AuthenticationController;
import server.services.AuthenticationService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private static final int PORT = 8080; // Port cho GameServer
    private ServerSocket serverSocket;
    private SessionFactory sessionFactory;

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.initializeHibernate();
        server.start();
    }

    public GameServer() {
        // Constructor không cần làm gì ở đây
    }

    private void initializeHibernate() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml"); // Đảm bảo rằng tệp này tồn tại trong classpath
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Failed to create sessionFactory object: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("GameServer is listening on port " + PORT);

            AuthenticationService authService = new AuthenticationService(sessionFactory);
            AuthenticationController authController = new AuthenticationController(authService);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClientConnection(clientSocket, authController);
            }
        } catch (IOException e) {
            System.err.println("Exception caught when trying to listen on port " + PORT + " or listening for a connection: " + e.getMessage());
        } finally {
            closeServerSocket();
        }
    }

    private void handleClientConnection(Socket clientSocket, AuthenticationController authController) {
        new Thread(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equalsIgnoreCase("logout")) {
                        authController.handleLogout(null); // Call logout handler
                        out.println("Logout successful!");
                    } else {
                        out.println("Unknown command: " + inputLine);
                    }
                }
            } catch (IOException e) {
                System.err.println("Exception in handling client connection: " + e.getMessage());
            } finally {
                closeClientSocket(clientSocket);
            }
        }).start();
    }

    private void closeServerSocket() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Failed to close server socket: " + e.getMessage());
            }
        }
    }

    private void closeClientSocket(Socket socket) {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Could not close client socket: " + e.getMessage());
            }
        }
    }
}
