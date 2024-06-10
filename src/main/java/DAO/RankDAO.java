package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import server.ObjectGson.GsonForClient.CL_Login;
import server.ObjectGson.GsonForServer.SV_Rank;
import util.HibernateUtil;

import java.util.List;

public class RankDAO {
    public static List<SV_Rank> getAllRank(){
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            Query<SV_Rank> query = session.createQuery("from SV_Rank ", SV_Rank.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void updateRank(){

    }
    public static void newUser(int userId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            SV_Rank svRank = new SV_Rank();
            svRank.setUserId(userId);
            svRank.setRank("1000");
            session.save(svRank);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
