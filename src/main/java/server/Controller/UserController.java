package server.Controller;

import DAO.UserDAO;
import server.ObjectGson.GsonForServer.SV_ListUserInfor;
import util.StreamSocket;

import java.net.Socket;

public class UserController {
    public static void getTop3User(Socket socket){
        //gui du lieu cho client
        SV_ListUserInfor sv_listUserInfor = UserDAO.getTop3UserInfor();
        new StreamSocket<SV_ListUserInfor>().sendDataToCLient(socket,sv_listUserInfor);
    }
}
