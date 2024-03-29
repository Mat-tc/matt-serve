package mat.mat_t.web.repository;


import lombok.RequiredArgsConstructor;
import mat.mat_t.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User User) {
        em.persist(User);
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findByLoginId(String name) {
        return em.createQuery("select u from User u where u.loginId = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    public User findByUserId(String userId) {
        return em.createQuery("select u from User u where u.loginId = :userId", User.class)
                        .setParameter("userId", userId)
                        .getSingleResult();
    }

    public void remove(User user) {
        em.remove(user);
    }

    public User findByWriter(String name) {
        return em.createQuery("select u from User u where u.loginId = :name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
