import com.GeneralMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 'ms.x' on 2017/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
public class GeneralTest {
	@Autowired
	private GeneralMain generalMain;
	
	
	@Test
	public void allTable() throws Exception {
		generalMain.start();
	}
	
	@Test
	public void diyTable() throws Exception {
		generalMain.general("cron");
	}
	
	
}
