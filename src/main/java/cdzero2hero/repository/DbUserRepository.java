package cdzero2hero.repository;

import cdzero2hero.domain.User;
import cdzero2hero.util.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public enum DbUserRepository implements UserRepository {
    INSTANCE;

    @Override
    public User getUserById(Integer id) {
        try (Connection connection = DataBase.INSTANCE.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER WHERE ID=?")) {
                statement.setInt(1, id);
                return getUser(statement);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to get user with id: " + id, e);
        }
    }

    @Override
    public User getUserByName(String name) {
        try (Connection connection = DataBase.INSTANCE.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER WHERE NAME=?")) {
                statement.setString(1, name);
                return getUser(statement);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to get user with name: " + name, e);
        }
    }

    private User getUser(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                User user = new User(resultSet.getString("NAME"));
                user.setId(resultSet.getInt("ID"));
                return user;
            }
            return null;
        }
    }

    @Override
    public void addUser(User user) {
        try (Connection connection = DataBase.INSTANCE.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO USER (NAME) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getName());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    user.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable add user with name: " + user.getName(), e);
        }
    }
}
