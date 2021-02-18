import org.junit.Test;

public class CustomerTest {

    @Test
    public void testCustomerInterface() {
        Customer customer = new Customer("Karl Jensen", "splinkervej 21", 73628291, 10, 4230);
        customer.customerInterface();
    }
}
