package rs.ac.bg.fon.perfumeryproject.repository.impl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Brand;
import rs.ac.bg.fon.perfumeryproject.repository.MyAppRepository;
/**
 *
 * @author Milica
 */
@Repository
public class BrandRepository implements MyAppRepository<Brand, Integer> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Brand> findAll() {
        return entityManager.createQuery("SELECT b FROM Brand b", Brand.class).getResultList();
    }

    @Override
    public Brand findById(Integer id) throws Exception {
        Brand brand = entityManager.find(Brand.class, id);
        if (brand == null) {
            throw new Exception("Brand not found!");
        }
        return brand;
    }

    @Override
    @Transactional
    public void save(Brand entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Brand brand = entityManager.find(Brand.class, id);
        if (brand != null) {
            entityManager.remove(brand);
        }
    }
}
