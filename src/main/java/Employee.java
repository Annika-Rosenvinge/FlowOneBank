import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Employee {
    Scanner in = new Scanner(System.in);
    List<Customer> customers = new ArrayList<>();
    String employeeName;
    int userInput;
    Controller controller = new Controller();

    public Employee(String employeeName) {
        this.employeeName = employeeName;
    }

    public void employeeInterface() {
        System.out.println("Velkommen " + employeeName + "!");

        System.out.println("Menu:\n" +
                "1. Opret konti\n" +
                "2. Fjern konti\n" +
                "3. Redigere konti\n" +
                "4. Flyt penge\n" +
                "5. Se alle kontier\n" +
                "6. QUIT");

        System.out.print("Indtast menu nummer: ");
        userInput = in.nextInt();
        in.nextLine();

        System.out.println("\n");

        switch (userInput) {
            case 1:
                controller.createUserInterface();
                employeeInterface();
                break;
            case 2:

                break;
            case 3:

                break;
            default:
                break;
        }
    }
/*


    //case 1a

    private Customer newCostumer() {
        String name = " ";
        int phoneNumber = 0;
        String address = "";
        Customer customer = new Customer(name, phoneNumber, address);
        System.out.println("Indtast kundens navn: ");
        name = sc.next();
        customer.setName(name);
        System.out.println("Indtast kundens telefon nummer; ");
        phoneNumber = sc.nextInt();
        customer.setPhoneNumber(phoneNumber);
        System.out.println("Indtast kundens adresse; ");
        phoneNumber = sc.nextInt();
        customer.setAddress(address);

        return customer;
    }

    //cas 1b
    private void commitCustomer(Customer customer) {
        //INSERT INTO customer (cusID, CusName, phone) values....
        Connection con = null;
        PreparedStatement prepStat = null;

        String sql = "INSERT INTO customer (CusNumber, CusName, CusPhone, CusAdress) VALUES (?,?,?,?)";
        con = JDBConnector.getConnection();

        try {
            prepStat = con.prepareStatement(sql);
            prepStat.setInt(1, customer.getCustomerId());
            prepStat.setString(2, customer.getName());
            prepStat.setInt(3, customer.getPhoneNumber());
            prepStat.setString(4, customer.getAddress());

            int update = prepStat.executeUpdate();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        customers.add(customer);

    }

    private void removeCostumer() {
        JDBConnector connector = new JDBConnector();
        Connection conn = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to selected database...");
            // mangler vi login + url til databasen?
            // conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected database succesfully...");
            System.out.println("Deleting table in given database....");
            st = conn.createStatement();
            String sql = "DROP TABLE REGRISTRATION";
            st.executeUpdate(sql);
            System.out.println("Tabnle deleted in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        try {
            if (conn != null)
                conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Good bye!");
    }
*/

    private void customerChanges() {
        //indtast navn og nummer på kunde
        //indsæt, hæv
    }

    private void moveMoney() {

    }

    private void seeAllCustomers() {
        JDBConnector connector = new JDBConnector();

        try {
            Statement statement = connector.getConnection().createStatement();
            String sql = "SELECT * FROM ebberodbank.customer";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(" ");
                System.out.println(resultSet.getString("CusNumber"));
                System.out.println(" ");
                System.out.println(resultSet.getString("CusName"));
                System.out.println(" ");
                System.out.println(resultSet.getString("CusPhone"));
                System.out.println(" ");
                System.out.println(resultSet.getString("CusAddress"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
