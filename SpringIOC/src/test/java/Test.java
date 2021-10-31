import com.wantoper.Hello;
import com.wantoper.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Hello g = context.getBean("h",Hello.class);
        g.getHello();

    }
}
