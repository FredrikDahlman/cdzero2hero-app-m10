package cdzero2hero.repository;

import cdzero2hero.domain.Friend;
import cdzero2hero.domain.User;
import cdzero2hero.test.categories.SmallTest;
import cdzero2hero.util.DataBase;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;

@Category(SmallTest.class)
public class DbFriendRepositoryTest {
    @BeforeClass
    public static void setUp() {
        DataBase.INSTANCE.migrate();
    }

    @Test
    public void addFriend() {
        User marge = new User("Marge");
        User maggie = new User("Maggie");

        UserRepository userRepository = DbUserRepository.INSTANCE;
        userRepository.addUser(marge);
        userRepository.addUser(maggie);

        Friend barney = new Friend(marge, "Barney", "Gumble");

        FriendRepository friendRepository = DbFriendRepository.INSTANCE;
        friendRepository.addFriend(barney);

        assertTrue(friendRepository.getFriendsForUser(marge).contains(barney));
        assertFalse(friendRepository.getFriendsForUser(maggie).contains(barney));
    }
}
