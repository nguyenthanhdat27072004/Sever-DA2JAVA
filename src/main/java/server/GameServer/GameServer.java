package server.GameServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private ServerSocket serverSocket;
    private int port = 5525;

    public GameServer() throws IOException{
        //tao sever socket
        serverSocket = new ServerSocket(port);
        System.out.println("Server open port: "+port);

        //lap vo han de accept client
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("Accept connect a Client!");

            //thuc thi request o 1 luong rieng biet
            Thread threadClient = new Thread(new Runnable() {
                @Override
                public void run() {
                    ClientHandle.ExecuteClientRequest(socket);
                }
            });
            threadClient.start();
        }
    }

    public static void main(String[] args) throws Exception{
        new GameServer();
    }
}
