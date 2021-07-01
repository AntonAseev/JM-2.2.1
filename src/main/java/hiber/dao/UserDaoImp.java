package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
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
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserWithCar(int series, String model) {
      TypedQuery<Car> queryCar=sessionFactory.getCurrentSession().createQuery(
                        "from Car WHERE series = :series and model = :model").setParameter(
                        "model", model).setParameter("series", series);
      long id = queryCar.getSingleResult().getId();
      TypedQuery<User> queryUser=sessionFactory.getCurrentSession().createQuery("from User WHERE id = :id")
                           .setParameter("id", id);
      User userWithCar = queryUser.getSingleResult();
      return userWithCar;
   }
}
