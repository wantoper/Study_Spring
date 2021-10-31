public class Rent extends Seller{
    private Seller seller = new Seller();

    //静态代理
    public void Sell(){
        System.out.println("通过中介购买");
        seller.Sell();
    }

}
