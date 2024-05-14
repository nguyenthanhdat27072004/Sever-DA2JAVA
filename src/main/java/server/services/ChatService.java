package server.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import server.model.Message;
import util.HibernateUtil;
import java.util.List;

public class ChatService {


    public void saveMessage(Message message) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public List<Message> getAllMessages() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Message", Message.class).list();
        } finally {
            session.close();
        }
    }

    public List<Object[]> getMessagesWithSenders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("SELECT m, u.username FROM Message m JOIN m.sender u", Object[].class).list();
        } finally {
            session.close();
        }
    }
}
