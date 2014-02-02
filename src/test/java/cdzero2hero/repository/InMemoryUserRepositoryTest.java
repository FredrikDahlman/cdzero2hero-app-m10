package cdzero2hero.repository;

import cdzero2hero.domain.User;
import cdzero2hero.repository.InMemoryUserRepository;
import cdzero2hero.repository.UserRepository;
import cdzero2hero.test.categories.SmallTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Category(SmallTest.class)
public class InMemoryUserRepositoryTest {

    private static final String MR_TEST = "Mr. Test";

    @Test
    public void addUser() {
        User mrTest = new User(MR_TEST);

        UserRepository repository = InMemoryUserRepository.INSTANCE;
        assertNull(repository.getUserByName(MR_TEST));
        repository.addUser(mrTest);
        assertEquals(mrTest, repository.getUserByName(MR_TEST));
    }
}
