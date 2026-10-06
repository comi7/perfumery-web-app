package rs.ac.bg.fon.perfumeryproject.repository.impl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Order;
import rs.ac.bg.fon.perfumeryproject.repository.MyAppRepository;
/**
 *
 * @author Milica
 */
@Repository
public class OrderRepository implements MyAppRepository<Order, Integer> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }

    @Override
    public Order findById(Integer id) throws Exception {
        Order o = em.find(Order.class, id);
        if (o == null) throw new Exception("Order not found: " + id);
        return o;
    }

    @Override
    @Transactional
    public void save(Order entity) {
        if (entity.getId() == null) em.persist(entity);
        else em.merge(entity);
    }

    @Override
    public void deleteById(Integer id) {
        Order o = em.find(Order.class, id);
        if (o != null) em.remove(o);
    }
}
