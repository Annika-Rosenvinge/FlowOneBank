import org.junit.Test;

public class CustomerTest {

    // Customer interface test (For at mindske tid med at login)
    @Test
    public void testCustomerInterface() {
        MYSQL mysql = new MYSQL();
        Customer customer = new Customer("Karl Jensen", "splinkervej 21", 73628291, 10, 4230, mysql);
        customer.customerInterface();
    }
}