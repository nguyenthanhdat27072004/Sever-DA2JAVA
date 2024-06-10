package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import server.ObjectGson.GsonForServer.SV_SkinOfUser;
import util.HibernateUtil;

public class SkinDAO {
    public static void newUser(int idUser){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            SV_SkinOfUser svSkinOfUser = new SV_SkinOfUser();

            svSkinOfUser.setUserId(idUser);
            svSkinOfUser.setBirdSkin("1");
            svSkinOfUser.setPipeSkin("4");

            session.save(svSkinOfUser);

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
