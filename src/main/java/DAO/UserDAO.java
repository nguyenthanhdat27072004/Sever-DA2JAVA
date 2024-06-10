package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import server.Controller.HashController;
import server.ObjectGson.GsonForClient.CL_ChangePass;
import server.ObjectGson.GsonForClient.CL_RegisterInformation;
import server.ObjectGson.GsonForServer.SV_User;
import util.HibernateUtil;

import java.util.List;

public class UserDAO {
    public static List<SV_User> allUser(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<SV_User> query = session.createQuery("from SV_User", SV_User.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // tra ve danh sach rong neu co ngoai le
        } finally {
            session.close();
        }
    }
    public static int registerAccount(CL_RegisterInformation cl_registerInformation){
        int idUser = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //bat dau mot phien giao dich
            Transaction transaction = session.beginTransaction();
            SV_User sv_user = new SV_User();
            //set du lieu co doi tuong
            sv_user.setUsername(cl_registerInformation.getUsername());
            sv_user.setPassword(HashController.sha256(cl_registerInformation.getPassword()));
            sv_user.setEmail(cl_registerInformation.getEmail());
            //luu cac thay doi
            session.save(sv_user);
            //thuc hien commit vinh vien len database
            transaction.commit();

            idUser = sv_user.getUserId();
        } catch (Exception e){
            e.printStackTrace();
        }
        return idUser;
    }
    public static String getMailByUsername(String username){
        String email = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<SV_User> query = session.createQuery("from SV_User where username = :username", SV_User.class);
            query.setParameter("username",username);
            SV_User sv_user = query.uniqueResult();
            if (sv_user != null){
                email = sv_user.getEmail();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return email;
    }
    public static void changePassword(CL_ChangePass cl_changePass){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<SV_User> query = session.createQuery("update SV_User set password = :password where username = :username");
            query.setParameter("password",HashController.sha256(cl_changePass.getNewPass()));
            query.setParameter("username",cl_changePass.getUsername());

            int result = query.executeUpdate();
            transaction.commit();

            System.out.println("Co "+result+" dong duoc thay doi");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
