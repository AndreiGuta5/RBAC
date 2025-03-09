package rbac_app.rbac;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jvnet.hk2.annotations.Service;
import rbac_app.EntityManagerInstance;

import java.util.List;

@Service
public class RbacDAO {
    @Inject
    private EntityManagerInstance entityManagerInstance;

    public void createRbacItem(RbacItem rbacItem) {
        EntityManager em = entityManagerInstance.get();
        em.getTransaction().begin();
        em.persist(rbacItem);
        em.getTransaction().commit();
    }
   public void deleteRoleFromRbacItem(RbacItem rbacItem) {
       EntityManager em = entityManagerInstance.get();
        em.getTransaction().begin();
        em.remove(rbacItem);
        em.getTransaction().commit();
   }
    public List<RbacItem> getRbacItems() {
        EntityManager em = entityManagerInstance.get();
        return em.createQuery("select r from RbacItem r",RbacItem.class).getResultList();
    }
    public List<Integer> getRolesForUser(int id) {
        EntityManager em = entityManagerInstance.get();
        return em.createQuery("select role.id from RbacItem where user.id =: user_id")
                .setParameter("user_id",id).getResultList();
    }

    public List<Integer> getRbacItemRolesId() {
        EntityManager em = entityManagerInstance.get();
        return em.createQuery("select role.id from RbacItem").getResultList();
    }
    public boolean rbacItemExist(RbacItem rbacItem){
        EntityManager em = entityManagerInstance.get();
        return !em.createQuery("Select r from RbacItem r where r.user.id =: user_id and r.role.id =: role_id")
                .setParameter("user_id",rbacItem.getUser().getId()).setParameter("role_id",rbacItem.getRole().getId()).getResultList().isEmpty();
    }
}
