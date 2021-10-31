Spring

[toc]

# Maven依赖

```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.6</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.21</version>
        </dependency>
    </dependencies>
```

## 允许访问src下的Xml

```xml
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
        </resources>
    </build>
```

## xml头

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.0.xsd">
</beans>
```

# IOC
IOC控制反转，意思是你不需要去控制new对象了 交给 Spring来new
## XML配置
```xml
	<!--配置普通的数据-->
	 <bean id="f" class="com.wantoper.Fruit">
        <property name="name" value="苹da果"></property>
    </bean>
	
	<~--配置类型的参数-->
	<bean id="student" class="com.wantoper.Student">
		<property name="fruit" ref="f"></property>
	</bean>
```
使用p命名空间：
xml头中需要引入`xmlns:p="http://www.springframework.org/schema/p"`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.0.xsd">
		
		<~--配置类类型的参数-->
		<bean id="frt" class="com.wantoper.Fruit" p:name="苹果"></bean>
		<~--配置类类型的参数-->
		<bean id="studentt" class="com.wantoper.Student" p:fruit-ref="f"></bean>

</beans>
```
## 构造器注入
### 第一种：constructor-arg
```xml
	<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
 
    <bean id="user" class="org.com.qst.service.impl.User">
    <constructor-arg index="0" value="1"></constructor-arg>
    <constructor-arg index="1" ref="xxx"></constructor-arg>
    <constructor-arg name="name" value="mys"></constructor-arg>
    <constructor-arg name="age" value="21"></constructor-arg>
    </bean>
</beans>
```

### 第二种：c命名空间
使用c命名空间
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/c
       http://www.springframework.org/schema/c.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.0.xsd">

    <bean id="user" class="org.com.qst.service.impl.User" c:id="0" c:name="mys"></bean>
</beans>
```

## 自动注解

```xml
<context:annotation-config></context:annotation-config>
<context:component-scan base-package="com.wantoper"></context:component-scan>
```
- @Component("h")
    放在Bean类前
- @AutoWired
    自动装配类属性
- @Nullable
    该值可以为Null
- @Resource(name="f")
    根据name自动装配指定的类
- @Value("")
	 装配普通类型的数据
- @Scope()
    单例 多例模式
	
## 类配置IOC
用类来替代XML文件
### User Bean类
```java
package com.wantoper.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component()
public class User {
    @Value("小米")
    private String name;
    @Resource(name ="getfff")
    private Fruit fruit;
	
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Fruit getFruit() {return fruit;}
    public void setFruit(Fruit fruit) {this.fruit = fruit;}

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", fruit=" + fruit +
                '}';
    }
}

```
### Fuit Bean类
```java
package com.wantoper.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component()
public class Fruit {
    private int id;
    private String name;
    private double price;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name; }
    public void setName(String name) {this.name = name;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    @Override
    public String toString() {
        return "Fruit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

```

### 配置类WanConfig
```java
package com.wantoper.Config;

import com.wantoper.bean.Fruit;
import com.wantoper.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//相当于<benas>
@Configuration
//引入
//@Import(WanConfig2.class)
public class WanConfig {
    //方法名相当于相当于 bean中的id
    //返回值就是bean 中的 class
    @Bean
    public User getuser(){
        return new User();
    }

    @Bean
    public Fruit getfff(){
        Fruit fruit = new Fruit();
        fruit.setId(1);
        fruit.setName("香蕉");
        fruit.setPrice(1.8);
        return fruit;
    }
}

```
@Configuration就相当于一个`<beans> </beans>`中的内容
如果有多个配置文件的话可以使用`@Import(WanConfig2.class)`导入
每一个方法都是一个`<bean>`方法名就是bena的 id 返回值类型就是class

### 测试类
```java
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
```
![9fad0f04e2755b968dec6060708ac5c5.png](/_resources/0c3713b780cb48a2a77313c59838c093.png)

# Java动态代理和静态代理(AOP 底层)
## 静态代理
### 业务类接口
```java
public interface RentSell {
     public void Sell();
     public void fakeyou();
     public void showprice();
}
```
### 业务实现类
```java
public class Seller implements RentSell{
    public void Sell(){
        System.out.println("卖方出售东西");
    }

    public void fakeyou(){
        System.out.println("卖家很生气并且问候了你");
    }

    public void showprice(){
        System.out.println("一千万一套！");
    }

}
```
### 静态代理类:
```java
public class Rent extends Seller{
    private Seller seller = new Seller();

    public void Sell(){
        System.out.println("通过中介购买");
        seller.Sell();
    }

}
```

### 业务主类实现业务
```java
public class Test {
    public static void main(String[] args) {
		   Seller seller = new Seller();
        seller.Sell();
        seller.showprice();
        System.out.println("太贵了 买不起 跑掉了");
        seller.fakeyou();
    }
}
```

![826ef1a80cbb66a116dfd177e43fabcb.png](/_resources/6ffa355bd9bf4f1797dd5333458230e9.png)

### 代理实现业务
```java
public class Test {
    public static void main(String[] args) {
		   Seller seller = new Seller();
        seller.Sell();
        seller.showprice();
        System.out.println("太贵了 买不起 跑掉了");
        seller.fakeyou();
    }
}
```
![442d0a31e734a8f74d54048630bdde9d.png](/_resources/d8a227cd6a2f4a0bb49910ca9c839595.png)


## 动态代理
### 业务类接口
```java
public interface RentSell {
     public void Sell();
     public void fakeyou();
     public void showprice();
}
```
### 业务实现类
```java
public class Seller implements RentSell{
    public void Sell(){
        System.out.println("卖方出售东西");
    }

    public void fakeyou(){
        System.out.println("卖家很生气并且问候了你");
    }

    public void showprice(){
        System.out.println("一千万一套！");
    }

}
```
### 动态代理类:
```java

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SellProxy implements InvocationHandler {

    private RentSell rent;
    public void setseller(RentSell rent){
        this.rent=rent; 
    }


    //得到代理类代理类具有RentSell(rent.class)的所有方法
    // 返回的代理类就是一个静态代理RentSell
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),rent.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log(method.getName());
        //执行rent的方法 这里是最主要的方法
        Object result = method.invoke(rent,args);
        return result;
    }

    public void log(String msg){
        System.out.println("执行了"+msg+"方法");
    }

}

```

### 代理实现业务
```java
public class Test {
    public static void main(String[] args) {
    Seller seller = new Seller();
    SellProxy sellProxy= new SellProxy();
    sellProxy.setseller(seller);
    RentSell rent = (RentSell) sellProxy.getProxy();
    rent.Sell();
    }
}
```



# AOP

可以在不改动核心业务功能的代码下，增加辅助功能的方法
![9efdc14f0c4e36a0f87604822fcc46b0.png](/_resources/83f81583547d4886aed878afca725c91.png)

### 实现AOP方式一：Api接口配置

#### xml头

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.0.xsd">
```

#### Serverices接口:业务的接口（这个业务有哪些方法）

```java
public interface Serverices {
    public void add();
    public void remove();
    public void update();
    public void select();
}
```

#### MyServices类：业务接口的实现类（具体实现接口的方法）

```java
public class MyServices implements Serverices{
    @Override
    public void add() {System.out.println("增加了一个用户");}

    @Override
    public void remove() {System.out.println("删除了一个用户");}

    @Override
    public void update() {System.out.println("修改了一个用户");}

    @Override
    public void select() {System.out.println("查找了一个用户");}
}
```

#### Beforlog类：开头执行的日志

```java
public class Beforlog implements MethodBeforeAdvice {

    @Override
    //在业务开始前会调用 MethodBeforeAdvice的before方法
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(target.getClass().getName()+"的"+method.getName()+"被执行了");
    }
}
```

#### Afterlog类: 结尾执行的日志

```java
public class Afterlog implements AfterReturningAdvice{

    @Override
    //在业务完毕后会调用 AfterReturningAdvice的afterReturning方法
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行了"+method.getName()+"返回结果为"+returnValue);
    }
}
```

#### appilcationContext.xml

```xml
<!--把业务和日志都添加到Spring里-->
    <bean id="mycservices" class="com.wantoper.Services.MyServices"></bean>
    <bean id="beforlog" class="com.wantoper.serverLog.Beforlog"></bean>
    <bean id="afterlog" class="com.wantoper.serverLog.Afterlog"></bean>

    <aop:config>
        <aop:pointcut id="pointcut" expression="execution(* com.wantoper.Services.MyServices.*(..))"/>
        <aop:advisor advice-ref="beforlog" pointcut-ref="pointcut"></aop:advisor>
        <aop:advisor advice-ref="afterlog" pointcut-ref="pointcut"></aop:advisor>
    </aop:config>
```

execution(* com.wantoper.Services.MyServices.*(..))
\* 返回任意类型
com.how2java.service.ProductService.* 包名以 com.how2java.service.ProductService 开头的类的任意方法
(..) 参数是任意数量和类型

### 实现AOP方式二：自定义类

#### 第一种 aop:before aop:after

定义一个log类

```java
public class log {
    public void firstlog(){
        System.out.println("firstlog");
    }

    public void lastlog(){
        System.out.println("lastlog");
    }
}
```

#### xml配置:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.0.xsd">


    <bean id="mycservices" class="com.wantoper.Services.MyServices"></bean>
    <bean id="log" class="com.wantoper.serverLog.log"></bean>

    <aop:config>
                <aop:pointcut id="pointcut" expression="execution(* com.wantoper.Services.MyServices.*(..))"/>
            <aop:aspect ref="log">
                <aop:before method="firstlog" pointcut-ref="pointcut"></aop:before>
                <aop:after method="lastlog" pointcut-ref="pointcut"></aop:after>
            </aop:aspect>
    </aop:config>
    
</beans>
```

#### 第二种 :aop:around

在log中新增一个log方法,方法参数为一个ProceedingJoinPoint类型的joinPoint

```java
public class log {
    public void firstlog(){
        System.out.println("firstlog");
    }

    public void lastlog(){
        System.out.println("lastlog");
    }

    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("start log:" + joinPoint.getSignature().getName());
        Object object = joinPoint.proceed();
        System.out.println("end log:" + joinPoint.getSignature().getName());
        return object;
    }
}
```

#### xml配置:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.0.xsd">


    <bean id="mycservices" class="com.wantoper.Services.MyServices"></bean>
    <bean id="log" class="com.wantoper.serverLog.log"></bean>

    <aop:config>
                <aop:pointcut id="pointcut" expression="execution(* com.wantoper.Services.MyServices.*(..))"/>
                <aop:aspect ref="log">
                    <aop:around method="log" pointcut-ref="services"></aop:around>
                </aop:aspect>
    </aop:config>
    
</beans>
```

#### Test代码

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Serverices serverices = context.getBean("mycservices",Serverices.class);
        serverices.add();
        serverices.remove();
        serverices.update();
        serverices.select();

    }
}
```

![55602f4f6837d2f9b3eeb687b05e297e.png](/_resources/31ac003edb424ae190182ab485ad75bd.png)

### 注解实现AOP

#### 定义一个业务类接口：

```java
public interface Serverices {
    public void add();
    public void remove();
    public void update();
    public void select();
}

```

#### 定义一个实现业务接口的实现类

```java
import org.springframework.stereotype.Component;

@Component("autoServices")
public class AutoServices implements Serverices{
    @Override
    public void add() {
        System.out.println("自动注解的方式增加了一个用户");
    }

    @Override
    public void remove() {
        System.out.println("自动注解的方式删除了一个用户");
    }

    @Override
    public void update() {
        System.out.println("自动注解的方式修改了一个用户");
    }

    @Override
    public void select() {
        System.out.println("自动注解的方式查找了一个用户");
    }
}

```

#### 定义一个日志的切面类

```java
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AutoLog {

    @Before(value = "execution(* com.wantoper.Services.AutoServices.*(..))")
    public void firstlog(){
        System.out.println("firstlog");
    }

    @After(value = "execution(* com.wantoper.Services.AutoServices.*(..))")
    public void lastlog(){
        System.out.println("lastlog");
    }

    @Around(value = "execution(* com.wantoper.Services.AutoServices.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("start log:" + joinPoint.getSignature().getName());
        Object object = joinPoint.proceed();
        System.out.println("end log:" + joinPoint.getSignature().getName());
        return object;
    }
}

```

Aopzhujie.xml:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.0.xsd">

    <context:annotation-config></context:annotation-config>
    <context:component-scan base-package="com.wantoper.serverLog"></context:component-scan>
    <context:component-scan base-package="com.wantoper.Services"></context:component-scan>
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
</beans>
```

Test测试类:

```java
import com.wantoper.Services.Serverices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Aopzhujie.xml");
        Serverices serverices = context.getBean("autoServices",Serverices.class);
        serverices.add();


    }
}

```

![777c61272c77b0cebc391724c5cf9d06.png](/_resources/7cf3e89b4af84cffa3099cd82db2da0b.png)