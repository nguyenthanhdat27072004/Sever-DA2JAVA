package DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import server.ObjectGson.GsonForServer.SV_Message;
import util.HibernateUtil;


import java.util.List;

public class MessageDAO {
    public static List<SV_Message> getAllMessage(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<SV_Message> query = session.createQuery("from SV_Message ", SV_Message.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // tra ve danh sach rong neu co ngoai le
        } finally {
            session.close();
        }
    }
}
