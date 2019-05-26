package mangolost.es;

import mangolost.es.service.EsDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@SpringBootTest(classes = EsDemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EsDemoApplicationTest {

    @Autowired
    private EsDataService esDataService;

    @Test
    public void testImportAll() throws IOException {

        esDataService.indexAllData();

    }

}
