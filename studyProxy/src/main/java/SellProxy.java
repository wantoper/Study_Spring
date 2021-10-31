
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SellProxy implements InvocationHandler {

    private RentSell rent;
    public void setseller(RentSell rent){
        this.rent=rent;
    }


    //得到代理类代理类具有RentSell(rent.class)的所有方法
    // 返回的代理类就是一个RentSell
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
