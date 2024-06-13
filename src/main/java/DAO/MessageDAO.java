package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import server.ObjectGson.GsonForServer.SV_ListMessage;
import server.ObjectGson.GsonForServer.SV_Message;
import util.HibernateUtil;


import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public static SV_ListMessage getAllMessage(){
        SV_ListMessage svListMessage = new SV_ListMessage();

        try(Session session= HibernateUtil.getSessionFactory().openSession()) {
            Query<SV_Message> query = session.createQuery("from SV_Message ", SV_Message.class);
            List <SV_Message> list = query.getResultList();
            svListMessage.setListMessages(new ArrayList<>(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return svListMessage;
    }
    public static void updateMess(SV_Message svMessage){
        try(Session session= HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction= session.beginTransaction();
            session.update(svMessage);
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
