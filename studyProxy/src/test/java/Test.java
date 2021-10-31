import java.lang.reflect.InvocationHandler;

public class Test {
    public static void main(String[] args) {
    Seller seller = new Seller();
    SellProxy sellProxy= new SellProxy();
    sellProxy.setseller(seller);
    RentSell rent = (RentSell) sellProxy.getProxy();
    rent.Sell();
    }
}
