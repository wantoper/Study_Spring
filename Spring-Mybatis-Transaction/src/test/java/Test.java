import com.wantoper.mapper.UserMapper;
import com.wantoper.pojo.User;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("AppilcationContext.xml");
        UserMapper usermap = context.getBean("usermap",UserMapper.class);
//        User user = new User();
//        user.setUsername("Administrator");
//        user.setPassword("123456");
//        usermap.adduser(user);
        usermap.deleteuser(4);
        for (User s :usermap.selectuser()){
            System.out.println(s);
        }
    }
}
