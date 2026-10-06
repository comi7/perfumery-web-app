package rs.ac.bg.fon.perfumeryproject.repository.impl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.perfumeryproject.entity.impl.EmailVerification;
/**
 *
 * @author Milica
 */

@Repository
public class EmailVerificationRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(EmailVerification ev) {
        if (ev.getId() == null) em.persist(ev);
        else em.merge(ev);
    }

    public EmailVerification findByToken(String token) {
        List<EmailVerification> list = em.createQuery(
                "SELECT e FROM EmailVerification e WHERE e.token = :token",
                EmailVerification.class)
                .setParameter("token", token)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public EmailVerification findByEmail(String email) {
        List<EmailVerification> list = em.createQuery(
                "SELECT e FROM EmailVerification e WHERE e.email = :email ORDER BY e.createdAt DESC",
                EmailVerification.class)
                .setParameter("email", email)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
}
