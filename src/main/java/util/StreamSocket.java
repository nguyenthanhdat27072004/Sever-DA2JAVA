package util;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

public class StreamSocket {


    public static String readJsonFromClient(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return reader.readLine(); // Đọc dữ liệu từ client
        } catch (IOException e) {
            System.out.println("Error reading from socket: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gửi thông tin dạng JSON tới client.
     * @param socket Socket kết nối tới client.
     * @param data Đối tượng để gửi tới client.
     */
    public static void sendValueToClient(Socket socket, Object data) {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(data);
            writer.println(jsonResponse); // Gửi JSON trở lại client
        } catch (IOException e) {
            System.out.println("Error writing to socket: " + e.getMessage());
        }
    }
}
