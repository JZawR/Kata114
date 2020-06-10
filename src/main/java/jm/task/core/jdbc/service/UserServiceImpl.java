package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl extends UserDaoJDBCImpl implements UserService {
    public void createUsersTable() {
        super.createUsersTable();
        System.out.println("Таблица User успешно создана");
    }

    public void dropUsersTable() {
        super.dropUsersTable();
        System.out.println("Таблица User удалена");
    }

    public void saveUser(String name, String lastName, byte age) {
        super.saveUser(name, lastName, age);
        System.out.println("User c именем " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        super.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return super.getAllUsers();
    }

    public void cleanUsersTable() {
        super.cleanUsersTable();
        System.out.println("Таблица User очищена");
    }
}
