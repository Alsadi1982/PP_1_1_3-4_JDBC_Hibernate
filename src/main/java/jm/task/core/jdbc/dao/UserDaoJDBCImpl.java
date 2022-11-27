package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "create table if not exists users (" +
                "id bigserial," +
                "name varchar(100)," +
                "last_name varchar(100)," +
                "age smallint);";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();){
            statement.executeUpdate(sql);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public void dropUsersTable() {
        String sql = "drop table if exists users;";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();){
            statement.executeUpdate(sql);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users (name, last_name, age) values (?, ?, ?);";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            statement.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from users where id = ?;";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users;";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return users;
    }

    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}
