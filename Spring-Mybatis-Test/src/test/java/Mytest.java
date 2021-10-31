import com.wantoper.mapper.UserMapper;
import com.wantoper.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class Mytest {
    @Test
    public void test() throws  IOException{
        ApplicationContext context = new ClassPathXmlApplicationContext("AppilcationContext.xml");
        UserMapper usermap = context.getBean("usermap2",UserMapper.class);
        List<User> users = usermap.selectUser();
        for (User user : users) {
            System.out.println(user);
        }
    }


}
