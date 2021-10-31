Spring-Mybatis

# 没有整合之前

## UserMapper接口

```java
package com.wantoper.mapper;

import com.wantoper.pojo.User;

import java.util.List;

public interface UserMapper {
    public List<User> selectUser();
}
```



## UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">


<mapper namespace="com.wantoper.mapper.UserMapper">
    <select id="selectUser" resultType="User">
        select * from user.user;
    </select>

</mapper>
```



## mybatis-config.xml（原配置）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="ssssmysql">
        <environment id="ssssmysql">
            <transactionManager type="JDBC"></transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"></property>
                <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybbs"></property>
                <property name="username" value="root"></property>
                <property name="password" value="root"></property>
            </dataSource>
        </environment>
    </environments>
    <mappers>       
        <mapper resource="com.wantoper.mapper.UserMapper.xml"></mapper>
    </mappers>
</configuration>
```



## Test类

```java
import com.wantoper.mapper.UserMapper;
import com.wantoper.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Mytest {
    @Test
    public void test() throws  IOException{
        String resouces="mybatis-config.xml";
        InputStream in = Resources.getResourceAsStream(resouces);

        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = sessionFactory.openSession(true);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectUser();
        for(User s:users){
            System.out.println(s);
        }
    }
}

```



# 整合了之后 方式一：

为UserMapper接口写一个实现类，然后实现类中内部整合一个SqlSessionTemplate，然后用Spring注入一个SqlSessionTemplate

## UserMapper接口

```java
package com.wantoper.mapper;

import com.wantoper.pojo.User;

import java.util.List;

public interface UserMapper {
    public List<User> selectUser();
}
```



## UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">


<mapper namespace="com.wantoper.mapper.UserMapper">
    <select id="selectUser" resultType="User">
        select * from user.user;
    </select>

</mapper>
```



## mybatis-config.xml（新配置）

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
        <package name="com.wantoper.pojo"/>
    </typeAliases>
</configuration>
```

## spring-dao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <bean id="datasource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
        <property name="url" value="jdbc:mysql://172.16.1.58:3306/user"></property>
        <property name="username" value="root"></property>
        <property name="password" value="Password123$"></property>
    </bean>


    <bean id="sqlsessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--绑定Mybatis需要的前置数据-->
        <property name="dataSource" ref="datasource"></property>
        <!--与mybatis-config.xml 结合-->
        <property name="configLocation" value="classpath:mybatis-config.xml"></property>
        <!--相当于Mappers-->
        <property name="mapperLocations" value="classpath:com/wantoper/mapper/UserMapper.xml"></property>
    </bean>

    <!--sqlSession 写死的-->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <!--由于SqlSessionTemplate没有Set方法 只能用构造器注入-->
        <constructor-arg index="0" ref="sqlsessionFactory"></constructor-arg>
    </bean>

    <!--这就是实体类的Bean-->
    <bean id="usermap" class="com.wantoper.mapper.UserMapperImpl">
        <property name="sqlSessionTemplate" ref="sqlSession"></property>
    </bean>

</beans>
```



## 为UserMapper写一个实现类UserMapperImpl

```java
package com.wantoper.mapper;

import com.wantoper.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class UserMapperImpl implements UserMapper{
    private SqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public List<User> selectUser() {
        UserMapper mapper =  sqlSessionTemplate.getMapper(UserMapper.class);
        return mapper.selectUser();
    }
}

```



## Test类

```java
import com.wantoper.mapper.UserMapper;
import com.wantoper.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Mytest {
    @Test
    public void test() throws  IOException{
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper usermap = context.getBean("usermap",UserMapper.class);
        List<User> users = usermap.selectUser();
        for (User user : users) {
            System.out.println(user);
        }
    }
}

```

# 整合了之后 方式二：

让接口的实现类去继承SqlSessionDaoSupport，SqlSessionDaoSupport类中有getSqlSession方法可以得到SqlSession 不用像方式一里的SqlSessionTemplate需要注入

## UserMapper接口

```java
package com.wantoper.mapper;

import com.wantoper.pojo.User;

import java.util.List;

public interface UserMapper {
    public List<User> selectUser();
}
```



## UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">


<mapper namespace="com.wantoper.mapper.UserMapper">
    <select id="selectUser" resultType="User">
        select * from user.user;
    </select>

</mapper>
```



## mybatis-config.xml（新配置）

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
        <package name="com.wantoper.pojo"/>
    </typeAliases>
</configuration>
```

## spring-dao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="datasource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
        <property name="url" value="jdbc:mysql://172.16.1.58:3306/user"></property>
        <property name="username" value="root"></property>
        <property name="password" value="Password123$"></property>
    </bean>


    <bean id="sqlsessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--绑定Mybatis需要的前置数据-->
        <property name="dataSource" ref="datasource"></property>
        <!--与mybatis-config.xml 结合-->
        <property name="configLocation" value="classpath:mybatis-config.xml"></property>
        <!--相当于Mappers-->
        <property name="mapperLocations" value="classpath:com/wantoper/mapper/UserMapper.xml"></property>
    </bean>

    <!--第二种方式 不需要sqlSession了！！--> 
	!! <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <!--由于SqlSessionTemplate没有Set方法 只能用构造器注入-->
        <constructor-arg index="0" ref="sqlsessionFactory"></constructor-arg>
    !! </bean>
    	
    <!--往usermap2里放sqlsessionFactory 就行-->
	<bean id="usermap2" class="com.wantoper.mapper.UserMapperImpl2">
        <property name="sqlSessionFactory" ref="sqlsessionFactory"></property>
    </bean>		

</beans>
```

### 接口的实现类UserMapperImpl2

```java
package com.wantoper.mapper;

import com.wantoper.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImpl2 extends SqlSessionDaoSupport implements UserMapper{
    @Override
    public List<User> selectUser() {
        return getSqlSession().getMapper(UserMapper.class).selectUser();
    }
}

```

### Test类

```java
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
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml.xml");
        UserMapper usermap = context.getBean("usermap2",UserMapper.class);
        List<User> users = usermap.selectUser();
        for (User user : users) {
            System.out.println(user);
        }
    }


}

```

