package rs.ac.bg.fon.perfumeryproject.repository.impl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.perfumeryproject.entity.impl.User;
import rs.ac.bg.fon.perfumeryproject.repository.MyAppRepository;
/**
 *
 * @author Milica
 */
@Repository
public class UserRepository implements MyAppRepository<User, Integer> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User findById(Integer id) throws Exception {
        User u = em.find(User.class, id);
        if (u == null) throw new Exception("User not found");
        return u;
    }

    @Transactional
    @Override
    public void save(User entity) {
        if (entity.getId() == null) em.persist(entity);
        else em.merge(entity);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        User u = em.find(User.class, id);
        if (u != null) em.remove(u);
    }

    public User findByUsername(String username) {
        List<User> list = em.createQuery("SELECT u FROM User u WHERE u.username = :un", User.class)
                .setParameter("un", username).getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public boolean existsByUsername(String username) {
        Long c = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :un", Long.class)
                .setParameter("un", username).getSingleResult();
        return c > 0;
    }

    public boolean existsByEmail(String email) {
        Long c = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :em", Long.class)
                .setParameter("em", email).getSingleResult();
        return c > 0;
    }
}