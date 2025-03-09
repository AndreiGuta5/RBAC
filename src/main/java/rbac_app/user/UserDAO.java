package rbac_app.user;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.jvnet.hk2.annotations.Service;
import java.util.Collection;
import rbac_app.*;

@Service
public class UserDAO {

    EntityManagerInstance entityManagerInstance;

    @Inject
    public UserDAO(EntityManagerInstance entityManagerInstance){
        this.entityManagerInstance = entityManagerInstance;
    }

    public void createUser(MyUser MyUser) {
        EntityManager em = entityManagerInstance.get();
        em.getTransaction().begin();
        em.persist(MyUser);
        em.getTransaction().commit();
    }

    public void updateUser(MyUser MyUser){
        EntityManager em = entityManagerInstance.get();
        em.getTransaction().begin();
        em.merge(MyUser);
        em.getTransaction().commit();
    }

    public void deleteUser(int id) {
        EntityManager em = entityManagerInstance.get();
        em.getTransaction().begin();
        em.remove(em.find(MyUser.class, id));
        em.getTransaction().commit();
    }

    public MyUser readUser(int id) {
        EntityManager em = entityManagerInstance.get();
        return em.find(MyUser.class, id);
    }
    public MyUser getUserByEmail(String email) {
        EntityManager em = entityManagerInstance.get();
        try {
            return em.createQuery("select u from MyUser u where u.email =: email", MyUser.class).setParameter("email", email).getSingleResult();
        }   catch (NoResultException e){
            return null;
        }
    }

    public Collection<MyUser> getUsers() {
        EntityManager em = entityManagerInstance.get();
        return em.createQuery("select u from MyUser u",MyUser.class).getResultList();
    }

}


