package rbac_app;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jvnet.hk2.annotations.Service;

@Service
public class EntityManagerInstance {
    private static final String PERSISTENCE_UNIT_NAME = "rbac-pu";
    private EntityManager entityManager;

    public EntityManager get() {
        if (entityManager == null) {
            EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            entityManager = emFactory.createEntityManager();
        }
        return entityManager;
    }

}
