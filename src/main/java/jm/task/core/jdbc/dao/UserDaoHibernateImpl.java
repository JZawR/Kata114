package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE user ("
                    + "id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "name VARCHAR(45), "
                    + "lastName VARCHAR(45), "
                    + "age TINYINT, "
                    + "PRIMARY KEY(id));").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица User успешно создана");
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            System.out.println("Такая таблица уже существует");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE user").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица User успешно удалена");
        } catch (Exception e) {
            System.out.println("Такой таблицы не существует");
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User c именем " + name + " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("Не удалось добавить пользователя с именем " + name + " в базу данных");
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("User с id " + id + " успешно удален");
        } catch (Exception e) {
            System.out.println("Не удалось удалить пользователя с id " + id);
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> userTypedQuery = sessionFactory.openSession().createQuery("FROM User", User.class);
        return userTypedQuery.getResultList();
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE user").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица User успешно очищена");
        } catch (Exception e) {
            System.out.println("Не удалось очистить таблицу");
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }
}
