package server.Controller;

import DAO.ScoreDAO;
import com.google.gson.Gson;
import server.ObjectGson.GsonForServer.SV_Score;
import util.StreamSocket;

import java.net.Socket;

public class ScoreController {
    public static void updateScore(Socket socket){
        Gson gson = new Gson();
        //check ket noi
        StreamSocket.checkConnect(socket);
        //doc file du lieu lan 2
        SV_Score fromClient = gson.fromJson(StreamSocket.readGsonFromClient(socket),SV_Score.class);
        //so sanh
        SV_Score sv_score = ScoreDAO.getScoreOfUser(fromClient);

        if (Integer.parseInt(sv_score.getScore()) < Integer.parseInt(fromClient.getScore())) {
            ScoreDAO.updateScores(fromClient);
        }
    }
}
