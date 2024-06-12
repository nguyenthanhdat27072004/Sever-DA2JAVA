package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import server.ObjectGson.GsonForClient.CL_IdUser;
import server.ObjectGson.GsonForServer.SV_ListScore;
import server.ObjectGson.GsonForServer.SV_Score;
import util.HibernateUtil;

import java.util.List;

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
    public static void updateScores(SV_Score sv_score){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            SV_Score score = new SV_Score();
            score.setUserId(score.getUserId());
            score.setScore(score.getScore());
            session.save(score);

            transaction.commit();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static SV_ListScore getTop3Scores(){
        SV_ListScore sv_listScore = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<SV_Score> query = session.createQuery("from SV_Score order by score desc", SV_Score.class);
            query.setMaxResults(3);

            sv_listScore = (SV_ListScore) query.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        return sv_listScore;
    }
    public static SV_Score getScoreOfUser(SV_Score svscore){
        SV_Score sv_score = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<SV_Score> query = session.createQuery("from SV_Score where userId = :idUser", SV_Score.class);
            query.setParameter("idUser",svscore.getUserId());
            sv_score = query.uniqueResult();
        } catch (Exception e){
            e.printStackTrace();
        }
        return sv_score;
    }
}
