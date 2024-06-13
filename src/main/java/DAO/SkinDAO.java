package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import server.ObjectGson.GsonForServer.SV_Score;
import server.ObjectGson.GsonForServer.SV_SkinOfUser;
import server.ObjectGson.GsonForServer.SV_UserInfor;
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
    public static void updateSkinOfUser(SV_SkinOfUser sv_skinOfUser){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            SV_SkinOfUser svSkinOfUser = session.get(SV_SkinOfUser.class, sv_skinOfUser.getUserId());

            if (sv_skinOfUser.getBirdSkin() != null) {
                svSkinOfUser.setBirdSkin(sv_skinOfUser.getBirdSkin());
            } else {
                svSkinOfUser.setPipeSkin(sv_skinOfUser.getPipeSkin());
            }
            session.update(svSkinOfUser);

            transaction.commit();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void updateSkinUserInfor(SV_SkinOfUser sv_skinOfUser){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            SV_UserInfor svUserInfor = session.get(SV_UserInfor.class, sv_skinOfUser.getUserId());

            if (sv_skinOfUser.getBirdSkin() != null) {
                svUserInfor.setIdSkin(Integer.parseInt(sv_skinOfUser.getBirdSkin()));
                session.update(svUserInfor);
            }

            transaction.commit();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
