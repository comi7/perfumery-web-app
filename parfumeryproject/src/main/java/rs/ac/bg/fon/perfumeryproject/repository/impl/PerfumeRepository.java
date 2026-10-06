package rs.ac.bg.fon.perfumeryproject.repository.impl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Perfume;
import rs.ac.bg.fon.perfumeryproject.repository.MyAppRepository;
/**
 *
 * @author Milica
 */
@Repository
public class PerfumeRepository implements MyAppRepository<Perfume, Integer> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Perfume> findAll() {
        return entityManager.createQuery("SELECT p FROM Perfume p", Perfume.class).getResultList();
    }

    @Override
    public Perfume findById(Integer id) throws Exception {
        Perfume perfume = entityManager.find(Perfume.class, id);
        if (perfume == null) {
            throw new Exception("Perfume not found!");
        }
        return perfume;
    }

    @Override
    @Transactional
    public void save(Perfume entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Perfume perfume = entityManager.find(Perfume.class, id);
        if (perfume != null) {
            entityManager.remove(perfume);
        }
    }

    public List<Perfume> findByBrand(Integer brandId) {
        return entityManager.createQuery(
                "SELECT p FROM Perfume p WHERE p.brand.id = :bid", Perfume.class)
                .setParameter("bid", brandId)
                .getResultList();
    }
}
