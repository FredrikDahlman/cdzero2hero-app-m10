package cdzero2hero.repository;

import cdzero2hero.domain.Friend;
import cdzero2hero.domain.User;
import cdzero2hero.util.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public enum DbFriendRepository implements FriendRepository {
    INSTANCE;

    @Override
    public List<Friend> getFriendsForUser(User user) {
        try (Connection connection = DataBase.INSTANCE.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM FRIEND WHERE USER_ID=?")) {
                statement.setInt(1, user.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Friend> userFriends = new ArrayList<>();
                    while (resultSet.next()) {
                        Friend friend = new Friend(user, resultSet.getString("FIRST_NAME"), resultSet.getString("SURNAME"));
                        friend.setId(resultSet.getInt("ID"));
                        userFriends.add(friend);
                    }
                    return userFriends;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to get friends for user with id: " + user.getId(), e);
        }
    }

    @Override
    public void addFriend(Friend newFriend) {
        try (Connection connection = DataBase.INSTANCE.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO FRIEND (USER_ID, FIRST_NAME, SURNAME) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, newFriend.getUser().getId());
                statement.setString(2, newFriend.getFirstName());
                statement.setString(3, newFriend.getSurname());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    resultSet.next();
                    newFriend.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable add friend with name: " + newFriend.getFirstName(), e);
        }
    }
}
