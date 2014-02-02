package cdzero2hero.repository;

import cdzero2hero.domain.Friend;
import cdzero2hero.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum InMemoryFriendRepository implements FriendRepository {
    INSTANCE;

    private int nextId;

    private final Map<User, List<Friend>> friends = new HashMap<>();

    @Override
    public List<Friend> getFriendsForUser(User user) {
        List<Friend> userFriends = friends.get(user);
        if (userFriends == null) {
            return new ArrayList<>();
        }
        return userFriends;
    }

    @Override
    public void addFriend(Friend newFriend) {
        if (newFriend.getId() == null) {
            newFriend.setId(++nextId);
        }

        User user = newFriend.getUser();
        if (!friends.containsKey(user)) {
            friends.put(user, new ArrayList<Friend>());
        }
        friends.get(user).add(newFriend);
    }
}
