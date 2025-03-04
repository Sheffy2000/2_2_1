package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession ().save (user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        String hql = "FROM User u JOIN FETCH u.car";
        TypedQuery<User> query = sessionFactory.getCurrentSession ().createQuery (hql, User.class);
        return query.getResultList ();
    }

    @Override
    public User getUserbyCar(String model, int series) {
        String hql = "FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series";
        return sessionFactory.getCurrentSession ()
                .createQuery (hql, User.class)
                .setParameter ("model", model)
                .setParameter ("series", series)
                .uniqueResult ();
    }
}
