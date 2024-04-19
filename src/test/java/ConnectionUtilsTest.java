
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.dragomirov.utils.ConnectionUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectionUtilsTest {
    @Test
    public void shouldGetSessionFactory() {
        SessionFactory sessionFactory = ConnectionUtils.getSessionFactory();
        assertNotNull(sessionFactory);
    }
}
