package server.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import server.model.Score;
import util.HibernateUtil;

import java.util.List;

public class ScoreService {

    public boolean saveScore(Score score) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(score);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public List<Score> getTopScores() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Score s ORDER BY s.score DESC", Score.class)
                    .setMaxResults(10)
                    .list();
        } finally {
            session.close();
        }
    }
}
