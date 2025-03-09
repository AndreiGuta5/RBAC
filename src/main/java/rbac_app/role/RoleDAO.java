package rbac_app.role;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.jvnet.hk2.annotations.Service;
import rbac_app.EntityManagerInstance;

import java.util.Collection;
@Service
public class RoleDAO {
   @Inject
    private EntityManagerInstance entityManagerInstance;

    public void createRole(Role role) {
        EntityManager em = entityManagerInstance.get();
        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
    }
    public void updateRole(Role role) {
        EntityManager em = entityManagerInstance.get();
        em.getTransaction().begin();
        em.merge(role);
        em.getTransaction().commit();
    }

    public void deleteRole(int id) {
        EntityManager em = entityManagerInstance.get();
        em.getTransaction().begin();
        em.remove(em.find(Role.class,id));
        em.getTransaction().commit();
    }

    public Role readRole(int id) {
        EntityManager em = entityManagerInstance.get();
        return em.find(Role.class,id);
    }
    public Role getRoleByName(String name){
        EntityManager em = entityManagerInstance.get();
        try {
        return em.createQuery("select r from Role r where r.name =: name", Role.class).setParameter("name",name).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    public Collection<Role> getRoles() {
        EntityManager em = entityManagerInstance.get();
        return em.createQuery("select r from Role r",Role.class).getResultList();
    }
}
