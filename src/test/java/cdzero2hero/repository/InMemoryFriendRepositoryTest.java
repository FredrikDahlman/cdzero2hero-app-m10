package cdzero2hero.repository;

import cdzero2hero.domain.Friend;
import cdzero2hero.domain.User;
import cdzero2hero.repository.FriendRepository;
import cdzero2hero.repository.InMemoryFriendRepository;
import cdzero2hero.repository.InMemoryUserRepository;
import cdzero2hero.repository.UserRepository;
import cdzero2hero.test.categories.SmallTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Category(SmallTest.class)
public class InMemoryFriendRepositoryTest {
    @Test
    public void addFriend() {
        User marge = new User("Marge");
        User maggie = new User("Maggie");

        UserRepository userRepository = InMemoryUserRepository.INSTANCE;
        userRepository.addUser(marge);
        userRepository.addUser(maggie);

        Friend barney = new Friend(marge, "Barney", "Gumble");

        FriendRepository friendRepository = InMemoryFriendRepository.INSTANCE;
        friendRepository.addFriend(barney);

        assertTrue(friendRepository.getFriendsForUser(marge).contains(barney));
        assertFalse(friendRepository.getFriendsForUser(maggie).contains(barney));
    }
}
