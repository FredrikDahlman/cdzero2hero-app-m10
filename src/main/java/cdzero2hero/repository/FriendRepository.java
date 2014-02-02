package cdzero2hero.repository;

import cdzero2hero.domain.Friend;
import cdzero2hero.domain.User;

import java.util.List;

public interface FriendRepository {
    List<Friend> getFriendsForUser(User user);

    void addFriend(Friend newFriend);
}
