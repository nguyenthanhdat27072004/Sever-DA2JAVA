package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import server.ObjectGson.GsonForServer.SV_Score;
import util.HibernateUtil;

public class ScoreDAO {
    public static void newUser(int userId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            SV_Score score = new SV_Score();
            score.setUserId(userId);
            score.setScore("0");
            session.save(score);

            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
