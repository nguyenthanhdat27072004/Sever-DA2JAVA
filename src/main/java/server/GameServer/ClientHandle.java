package server.GameServer;

import com.google.gson.Gson;
import server.Controller.*;
import server.ObjectGson.GsonForClient.CL_Request;
import util.StreamSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandle {
    public static void ExecuteClientRequest(Socket socket){
        try{
            Gson gson = new Gson();
            //doc du lieu
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("ggd12");
            String clientRequest = fromClient.readLine();
            System.out.println(clientRequest);
            //chuyen doi du lieu gson thanh doi tuong java va anh xa vao model
            CL_Request clRequest = gson.fromJson(clientRequest, CL_Request.class);
            System.out.println(clRequest.getRequest());
            switch (clRequest.getRequest()){
                case "/check/login":{
                    try{
                        LoginController.checkLogin(socket);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case "/register/account":{
                    try{
                        RegisterController.requestRegiter(socket);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
                case "/get/otp":{
                    try{
                        ForgetController.sendOtp(socket);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
                case "/change/password":{
                    try{
                        ForgetController.changePassword(socket);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
                case "/get/rank/score":{
                    try{
                        ScoreController.getTop3Scores(socket);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
                case "/get/rank/username":{
                    try {
                        UserController.getTop3User(socket);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
                case "/update/score":{
                    try {
                        ScoreController.updateScore(socket);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
                case "/update/skin":{
                    try {
                        SkinController.updateSkin(socket);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
                case "/get/skin/of/user":{
                    try {
                        SkinController.getSkinOfUser(socket);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
                case "/get/rank/skin":{
                    try {
                        SkinController.getSkinForRank(socket);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    break;
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
