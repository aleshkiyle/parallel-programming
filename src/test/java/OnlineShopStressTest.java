import com.rsreu.rodin.cmo.OnlineShop;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OnlineShopStressTest {
    @Test
        public void createCustomerStressTest() throws ExecutionException, InterruptedException {
        class CustomerCreator implements Runnable {

            private final int start;
            private final int end;
            private final OnlineShop onlineShop;
            private final double balance;

            public CustomerCreator(OnlineShop shop, int start, int end, double balance) {
                this.start = start;
                this.end = end;
                this.balance = balance;
                this.onlineShop = shop;
            }

            @Override
            public void run() {
                for (int i = start; i <= end; i++) {
                    this.onlineShop.createCustomer("Customer " + i, balance);
                }
            }
        }
        OnlineShop onlineShop = new OnlineShop();

        List<Runnable> tasks = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            CustomerCreator customerCreator = new CustomerCreator(onlineShop, 1, 30, 100.0);
            tasks.add(customerCreator);
        }

        for (int i = 1; i <= 10; i++) {
            CustomerCreator customerCreator = new CustomerCreator(onlineShop, 31, 50, 200.0);
            tasks.add(customerCreator);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        List<Future<?>> futures = new ArrayList<>();
        for (Runnable task : tasks) {
            futures.add(executorService.submit(task));
        }

        for (Future<?> future : futures) {
            future.get();
        }
        assertEquals(50, onlineShop.getCustomers().size());

    }

    @Test
    public void buyStressTest() throws ExecutionException, InterruptedException {
        class BuyRunnable implements Runnable {

            private final String customerName;
            private final String goodName;
            private final int quantity;
            private final int repeats;
            private final OnlineShop onlineShop;

            public BuyRunnable(OnlineShop shop, String customerName, String goodName, int quantity, int repeats) {
                this.quantity = quantity;
                this.goodName = goodName;
                this.customerName = customerName;
                this.onlineShop = shop;
                this.repeats = repeats;
            }

            @Override
            public void run() {
                for (int i = 0; i < repeats; i++) {
                    this.onlineShop.buy(customerName, goodName, quantity);
                }
            }
        }
        String goodName = "Orange";
        double goodPrice = 2.0;
        OnlineShop onlineShop = new OnlineShop();
        onlineShop.addProduct(goodName, 1000, goodPrice);

        for (int i = 0; i < 10; i++) {
            onlineShop.createCustomer("Customer " + i, 1000.0);
        }
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new BuyRunnable(onlineShop, "Customer " + i, goodName, 10, 10));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<?>> futures = new ArrayList<>();
        for (Runnable task : tasks) {
            futures.add(executorService.submit(task));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        while(!onlineShop.getBlockingQueue().isEmpty()){}

        assertEquals(0, onlineShop.getProductsList().get(0).getQuantity());
        assertEquals(2000.0, onlineShop.getBalance());

        onlineShop.getCustomers().values().forEach(customer -> {
            assertEquals(800.0, customer.getBalance());
        });
    }

    @Test
    public void buyStressTest2() throws ExecutionException, InterruptedException {
        class BuyRunnable implements Runnable {

            private final String customerName;
            private final String goodName;
            private final int quantity;
            private final int repeats;
            private final OnlineShop onlineShop;

            public BuyRunnable(OnlineShop shop, String customerName, String goodName, int quantity, int repeats) {
                this.quantity = quantity;
                this.goodName = goodName;
                this.customerName = customerName;
                this.onlineShop = shop;
                this.repeats = repeats;
            }

            @Override
            public void run() {
                for (int i = 0; i < repeats; i++) {
                    this.onlineShop.buy(customerName, goodName, quantity);
                }
            }
        }
        String goodName = "Orange";
        double goodPrice = 2.0;
        OnlineShop onlineShop = new OnlineShop();
        onlineShop.addProduct(goodName, 1_000_000_000, goodPrice);

        for (int i = 0; i < 1000; i++) {
            onlineShop.createCustomer("Customer " + i, 1000000.0);
        }
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            tasks.add(new BuyRunnable(onlineShop, "Customer " + i, goodName, 1, 100000));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<?>> futures = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (Runnable task : tasks) {
            futures.add(executorService.submit(task));
        }

        for (Future<?> future : futures) {
            future.get();
        }

        while(!onlineShop.getBlockingQueue().isEmpty()){}
        long stop = System.currentTimeMillis();
        System.out.println(100000000.0/((stop-start)/1000.0));
    }
}
