package Ugly_Code;

public class Bakery {
    public void bakeBread(){
        System.out.println("Baking High-Quality cake..");
    }

    public void manageInventory(){
        System.out.println("Managing inventory...");
    }

    public void orderSupplies(){
        System.out.println("Ordering supplies...");
    }

    public void serveCustomers(){
        System.out.println("Serving customers...");
    }

    public void cleanBakery(){
        System.out.println("Cleaning bakery...");
    }

    public static void main(String[] args) {
        Bakery bakery = new Bakery();
        bakery.bakeBread();
        bakery.manageInventory();
        bakery.orderSupplies();
        bakery.serveCustomers();
        bakery.cleanBakery();
    }
}
