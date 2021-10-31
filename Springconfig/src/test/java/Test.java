import com.wantoper.Config.WanConfig;
import com.wantoper.bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(WanConfig.class);
        User s = context.getBean("getuser",User.class);
        System.out.println(s);
    }
}
