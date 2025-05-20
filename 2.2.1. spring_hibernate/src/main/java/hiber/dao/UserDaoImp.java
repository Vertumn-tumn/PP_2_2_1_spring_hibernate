package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<User> listUsersByCarModelAndSeries(String carModel, int carSeries) {
        return sessionFactory.getCurrentSession().createQuery(
                        "SELECT u FROM User u " +
                                "JOIN u.car c " +
                                "WHERE c.model = :carModel AND c.series = :carSeries", User.class)
                .setParameter("carModel", carModel)
                .setParameter("carSeries", carSeries)
                .getResultList();
    }

}
