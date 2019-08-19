package by.haidash.shop.product;

import by.haidash.shop.jpa.JpaModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductServiceApp.class, JpaModule.class})
public class ApplicationTest {

    @Test
    public void contextLoads() throws Exception {
    }
}
