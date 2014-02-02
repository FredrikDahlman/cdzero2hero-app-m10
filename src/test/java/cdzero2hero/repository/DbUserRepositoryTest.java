package cdzero2hero.repository;

import cdzero2hero.domain.User;
import cdzero2hero.test.categories.SmallTest;
import cdzero2hero.util.DataBase;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Category(SmallTest.class)
public class DbUserRepositoryTest {

    private static final String MR_TEST = "Mr. Test";

    @BeforeClass
    public static void setUp() {
        DataBase.INSTANCE.migrate();
    }

    @Test
    public void addUser() {
        User mrTest = new User(MR_TEST);

        UserRepository repository = DbUserRepository.INSTANCE;
        assertNull(repository.getUserByName(MR_TEST));
        repository.addUser(mrTest);
        assertEquals(mrTest, repository.getUserByName(MR_TEST));
    }
}
