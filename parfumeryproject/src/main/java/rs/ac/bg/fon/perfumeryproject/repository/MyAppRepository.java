package rs.ac.bg.fon.perfumeryproject.repository;
import java.util.List;
/**
 *
 * @author Milica
 */
public interface MyAppRepository<E, ID> {
    List<E> findAll();
    E findById(ID id) throws Exception;
    void save(E entity);
    void deleteById(ID id);
}
