package server.Controller;

import DAO.SkinDAO;
import com.google.gson.Gson;
import server.ObjectGson.GsonForClient.CL_CheckLogin;
import server.ObjectGson.GsonForServer.SV_GetSkin;
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
    public static void getSkinOfUser(Socket socket){
        Gson gson = new Gson();
        //check ket noi
        StreamSocket.checkConnect(socket);
        //doc file du lieu lan 2
        CL_CheckLogin cl_checkLogin = gson.fromJson(StreamSocket.readGsonFromClient(socket), CL_CheckLogin.class);
        //thuc hien tra du lieu ve
        SV_SkinOfUser sv_skinOfUser = SkinDAO.getIdSkinOfUser(cl_checkLogin);
        SV_GetSkin sv_getSkin = SkinDAO.getSkin(sv_skinOfUser);
        new StreamSocket<SV_GetSkin>().sendDataToCLient(socket, sv_getSkin);
    }
}
