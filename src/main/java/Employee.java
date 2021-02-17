import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//passwords til kunder

public class Employee implements Login {
    Scanner sc = new Scanner(System.in);
    List<Customer> customers = new ArrayList<>();

    public void login() {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM employee (SELECT employee.employeeID)";

        con = JDBConnector.getConnection();
        



        /* if navn = medarbejdernummer && kode = medarbejderkode
        * så logges man ind og kan fortsætte
        * */

    }

    public void employee(){
        login();
        System.out.println("Velkommen til Ebberød Bank");
        System.out.println("Vælg fra menuen");
        employeeMenu();

    }

    public void employeeMenu(){
        int choice = 0;
        while (choice != 6){
            choice = sc.nextInt();
            switch (choice){
                case 1: Customer customer = newCostumer(); commitCustomer(customer); break;
                case 2: removeCostumer(); break;
                case 3: customerChanges(); break;
                case 4: moveMoney(); break;
                case 5: seeAllCustomers(); break;

            }
        }
    }
    //case 1a
    private Customer newCostumer(){
        String name =  " ";
        int phoneNumber = 0;
        Customer customer = new Customer(name, phoneNumber);
        System.out.println("Indtast kundens navn: ");
        name = sc.next();
        customer.setName(name);
        System.out.println("Indtast kundens telefon nummer; ");
        phoneNumber = sc.nextInt();
        customer.setPhoneNumber(phoneNumber);

        return customer;
    }

    //cas 1b
    private void commitCustomer(Customer customer){
        //INSERT INTO customer (cusID, CusName, phone) values....
        Connection con = null;
        PreparedStatement prepStat = null;
        ResultSet res = null;

        String sql = "INSERT INTO customer (cusID, cusName, phone) VALUES (?,?,?)";
        con = JDBConnector.getConnection();

        try{
            prepStat = con.prepareStatement(sql);
            prepStat.setInt(1,customer.getCustomerId() );
            prepStat.setString(2, customer.getName());
            prepStat.setInt(3, customer.getPhoneNumber());

            int update = prepStat.executeUpdate();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        customers.add(customer);

    }

    private void removeCostumer(){
    }

    private void  customerChanges(){
        //indtast navn og nummer på kunde
        //indsæt, hæv
    }

    private void moveMoney(){

    }

    private void seeAllCustomers(){
        JDBConnector connector = new JDBConnector();

        try{
            Statement statement = connector.getConnection().createStatement();
            String sql = "SELECT * FROM bank.customer";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                System.out.println(" ");
                System.out.println(resultSet.getString("cusId"));
                System.out.println(" ");
                System.out.println(resultSet.getString("cusName"));
                System.out.println(" ");
                System.out.println(resultSet.getString("phone"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
