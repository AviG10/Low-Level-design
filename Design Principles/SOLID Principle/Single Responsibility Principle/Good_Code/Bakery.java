package Good_Code;

import Good_Code.Bakery_Functionalities.*;

public class Bakery {
    public static void main(String[] args) {
        BreadBaker baker = new BreadBaker();
        BakeryCleaner cleaner = new BakeryCleaner();
        CustomerService service = new CustomerService();
        InventoryManager manager = new InventoryManager();
        SupplyOrders supplyOrders = new SupplyOrders();

        baker.bakeBread();
        cleaner.cleanBakery();
        service.serveCustomers();
        supplyOrders.orderSupplies();
        manager.manageInventory();
    }
}
