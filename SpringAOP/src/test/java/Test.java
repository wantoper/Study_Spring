import com.wantoper.Services.MyServices;
import com.wantoper.Services.Serverices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Aopzhujie.xml");
        Serverices serverices = context.getBean("autoServices",Serverices.class);
        serverices.add();
//        serverices.remove();
//        serverices.update();
//        serverices.select();

    }
}
