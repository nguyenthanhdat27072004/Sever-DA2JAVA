package server.Controller;

import DAO.SkinDAO;
import com.google.gson.Gson;
import server.ObjectGson.GsonForServer.SV_SkinOfUser;
import util.StreamSocket;

import java.net.Socket;

public class SkinController {
    public static void updateSkin(Socket socket){
        Gson gson = new Gson();
        //check ket noi
        StreamSocket.checkConnect(socket);
        //doc file du lieu lan 2
        SV_SkinOfUser sv_skinOfUser = gson.fromJson(StreamSocket.readGsonFromClient(socket), SV_SkinOfUser.class);
        //thuc hien cap nhat skin
        SkinDAO.updateSkinOfUser(sv_skinOfUser);
        SkinDAO.updateSkinUserInfor(sv_skinOfUser);
    }
}
