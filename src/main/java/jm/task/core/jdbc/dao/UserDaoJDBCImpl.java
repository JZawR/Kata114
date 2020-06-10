package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement statement = null;

        String newTable = "CREATE TABLE User ("
                + "id BIGINT NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(45), "
                + "lastName VARCHAR(45), "
                + "age TINYINT, "
                + "PRIMARY KEY(id));";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(newTable);
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Такая таблица уже существует");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        Statement statement = null;
        String sql = "DROP TABLE kata113.user;";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Такая таблица не существует");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO User (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, (byte) age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM USER WHERE ID=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM USER";
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        Statement statement = null;
        String sql = "TRUNCATE TABLE USER";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
