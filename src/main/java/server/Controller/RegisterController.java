package server.Controller;

import DAO.ScoreDAO;
import DAO.SkinDAO;
import DAO.UserDAO;
import com.google.gson.Gson;
import server.ObjectGson.GsonForClient.CL_RegisterInformation;
import server.ObjectGson.GsonForServer.SV_Check;
import util.StreamSocket;

import java.net.Socket;

public class RegisterController {
    public static void requestRegiter(Socket socket){
        Gson gson = new Gson();
        //chech ket noi
        StreamSocket.checkConnect(socket);
        //doc file du lieu lan 2
        CL_RegisterInformation cl_registerInformation = gson.fromJson(StreamSocket.readGsonFromClient(socket), CL_RegisterInformation.class);
        try {
            // thuc hien luu tai khoan moi len database
            int idUser = UserDAO.registerAccount(cl_registerInformation);
                ScoreDAO.newUser(idUser);
                SkinDAO.newUser(idUser);
            // gui thong tin dang ky ve cho client
            SV_Check sv_check = new SV_Check(true);
            new StreamSocket<SV_Check>().sendDataToCLient(socket, sv_check);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
