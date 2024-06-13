package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import server.ObjectGson.GsonForClient.CL_CheckLogin;
import server.ObjectGson.GsonForServer.*;
import util.HibernateUtil;
import java.util.Arrays;
import java.util.List;

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
    public static SV_SkinOfUser getIdSkinOfUser(CL_CheckLogin cl_checkLogin){
        SV_SkinOfUser sv_skinOfUser = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<SV_SkinOfUser> query = session.createQuery("from SV_SkinOfUser where userId = :idUser", SV_SkinOfUser.class);
            query.setParameter("idUser",cl_checkLogin.getIdUser());

            sv_skinOfUser = query.uniqueResult();
        } catch (Exception e){
            e.printStackTrace();
        }
        return sv_skinOfUser;
    }
    public static SV_GetSkin getSkin(SV_SkinOfUser sv_skinOfUser){
        SV_GetSkin sv_getSkin = new SV_GetSkin();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<SV_Skin> query = session.createQuery("from SV_Skin where idskin in (:ids)", SV_Skin.class);
            query.setParameter("ids", Arrays.asList(Integer.parseInt(sv_skinOfUser.getBirdSkin()),Integer.parseInt(sv_skinOfUser.getPipeSkin())));

            List<SV_Skin> skins = query.getResultList();
            sv_getSkin.setSkinBird(skins.get(0).getSkin());
            sv_getSkin.setSkinPipe(skins.get(1).getSkin());
        } catch (Exception e){
            e.printStackTrace();
        }
        return sv_getSkin;
    }

}
