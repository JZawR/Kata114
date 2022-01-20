package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE User ("
                    + "id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "name VARCHAR(45), "
                    + "lastName VARCHAR(45), "
                    + "age TINYINT, "
                    + "PRIMARY KEY(id));").executeUpdate();
            transaction.commit();
            System.out.println("Таблица User успешно создана");
        } catch (PersistenceException e) {
            System.out.println("Такая таблица уже существует");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE User").executeUpdate();
            transaction.commit();
            System.out.println("Таблица User успешно удалена");
        } catch (PersistenceException e) {
            System.out.println("Такой таблицы не существует");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User();

            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            transaction.commit();
            System.out.println("User c именем " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);

            session.delete(user);
            transaction.commit();
            System.out.println("User с id " + id + " усепшно удален");
        } catch (Exception e) {
            System.out.println("Не получилось удалить User с id" + id);
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return Util.getSessionFactory().openSession().createQuery("From User").list();
        // сырой тип здесь - это плохо?
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("DELETE FROM User e WHERE e.id > 0");
            query.executeUpdate();
            //session.createSQLQuery("TRUNCATE TABLE USER").executeUpdate();
            transaction.commit();
            System.out.println("Таблица User очищена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
