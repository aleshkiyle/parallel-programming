import com.rsreu.rodin.cmo.Customer;
import com.rsreu.rodin.cmo.OnlineShop;
import com.rsreu.rodin.cmo.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OnlineShopTest {

    @Test
    public void createCustomerSuccessTest() {
        OnlineShop onlineShop = new OnlineShop();
        String username = "testUser";
        Double balance = 100.0;

        onlineShop.createCustomer(username, balance);

        List<Customer> customers = new ArrayList<>(onlineShop.getCustomers().values());

        assertEquals(1, customers.size());

        Customer customer = customers.get(0);

        assertEquals(username, customer.getUsername());
        assertEquals(balance, customer.getBalance());
    }

    @Test
    public void createExistingCustomerSuccessTest() {
        OnlineShop onlineShop = new OnlineShop();
        String username = "testUser";
        Double balance1 = 100.0;
        Double balance2 = 200.0;

        onlineShop.createCustomer(username, balance1);
        onlineShop.createCustomer(username, balance2);

        List<Customer> customers = new ArrayList<>(onlineShop.getCustomers().values());

        assertEquals(1, customers.size());

        Customer customer = customers.get(0);

        assertEquals(username, customer.getUsername());
        assertEquals(balance2, customer.getBalance());
    }

    @Test
    public void addGoodSuccessTest() {
        OnlineShop onlineShop = new OnlineShop();
        String goodName = "orange";
        int quantity = 20;
        Double price = 100.0;

        onlineShop.addProduct(goodName, quantity, price);

        List<Product> products = new ArrayList<>(onlineShop.getProducts().values());

        assertEquals(1, products.size());

        Product product = products.get(0);

        assertEquals(quantity, product.getQuantity());
        assertEquals(price, product.getPrice());
        assertEquals(goodName, product.getName());
    }

    @Test
    public void addExistingGoodSuccessTest() {
        OnlineShop onlineShop = new OnlineShop();
        String goodName = "orange";
        int quantity1 = 20;
        Double price1 = 100.0;
        int quantity2 = 25;
        Double price2 = 150.0;

        onlineShop.addProduct(goodName, quantity1, price1);
        onlineShop.addProduct(goodName, quantity2, price2);

        List<Product> products = new ArrayList<>(onlineShop.getProducts().values());

        assertEquals(1, products.size());

        Product product = products.get(0);

        assertEquals(quantity1 + quantity2, product.getQuantity());
        assertEquals(price2, product.getPrice());
        assertEquals(goodName, product.getName());
    }

    @Test
    public void buySuccessTest() {
        OnlineShop onlineShop = new OnlineShop();
        String username = "testUser";
        double balance = 100.0;

        onlineShop.createCustomer(username, balance);
        String goodName = "orange";
        int quantity = 20;
        double price = 5.0;

        onlineShop.addProduct(goodName, quantity, price);

        int buyQuantity = 10;
        onlineShop.buy(username, goodName, buyQuantity);

        List<Product> products = new ArrayList<>(onlineShop.getProducts().values());

        assertEquals(1, products.size());

        Product product = products.get(0);

        List<Customer> customers = new ArrayList<>(onlineShop.getCustomers().values());

        assertEquals(1, customers.size());

        Customer customer = customers.get(0);

        assertEquals(quantity - buyQuantity, product.getQuantity());
        assertEquals(balance - buyQuantity * price, customer.getBalance());
        assertEquals(buyQuantity * price, customer.getSpent());
        assertEquals(buyQuantity * price, onlineShop.getBalance());
    }

    @Test
    public void buyQuantityErrorTest() {
        OnlineShop onlineShop = new OnlineShop();
        String username = "testuser";
        double balance = 100.0;

        onlineShop.createCustomer(username, balance);
        String goodName = "orange";
        int quantity = 2;
        double price = 5.0;

        onlineShop.addProduct(goodName, quantity, price);

        int buyQuantity = 10;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> onlineShop.buy(username, goodName, buyQuantity));

        List<Product> products = new ArrayList<>(onlineShop.getProducts().values());

        assertEquals(1, products.size());

        Product product = products.get(0);

        List<Customer> customers = new ArrayList<>(onlineShop.getCustomers().values());

        assertEquals(1, customers.size());

        Customer customer = customers.get(0);

        assertEquals(quantity, product.getQuantity());
        assertEquals(balance, customer.getBalance());
        assertEquals(0, customer.getSpent());
        assertEquals(0, onlineShop.getBalance());
    }

    @Test
    public void buyBalanceErrorTest() {
        OnlineShop onlineShop = new OnlineShop();
        String username = "testUser";
        double balance = 4.0;

        onlineShop.createCustomer(username, balance);
        String goodName = "orange";
        int quantity = 50;
        double price = 5.0;

        onlineShop.addProduct(goodName, quantity, price);

        int buyQuantity = 10;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> onlineShop.buy(username, goodName, buyQuantity));

        List<Product> goods = new ArrayList<>(onlineShop.getProducts().values());

        assertEquals(1, goods.size());

        Product product = goods.get(0);

        List<Customer> customers = new ArrayList<>(onlineShop.getCustomers().values());

        assertEquals(1, customers.size());

        Customer customer = customers.get(0);

        assertEquals(quantity, product.getQuantity());
        assertEquals(balance, customer.getBalance());
        assertEquals(0, customer.getSpent());
        assertEquals(0, onlineShop.getBalance());
    }
}
