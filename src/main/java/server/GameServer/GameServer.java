package server.GameServer;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import server.routers.AuthenticationRouter;
import server.routers.ChatRouter;

import java.io.IOException;

public class GameServer {
    private static final int AUTH_PORT = 8081;
    private static final int CHAT_PORT = 8082;
    private SessionFactory sessionFactory;

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.initializeHibernate();
        server.startRouters();
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

    public void startRouters() {
        try {
            // Khởi tạo và bắt đầu AuthenticationRouter
            AuthenticationRouter authRouter = new AuthenticationRouter(sessionFactory);
            authRouter.startServer();

            // Khởi tạo và bắt đầu ChatRouter
            ChatRouter chatRouter = new ChatRouter();
            chatRouter.startServer();

            System.out.println("All routers started successfully.");

        } catch (IOException e) {
            System.err.println("Exception caught when trying to start routers: " + e.getMessage());
        }
    }
}
