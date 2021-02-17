import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Employee implements Login {
    Scanner sc = new Scanner(System.in);
    List<Customer> customers = new ArrayList<>();

    public void login() {
        //opret medarbejdere i SQL og derefter skab forbindelse mellem
        //employee class og SQL, derefter oprettes customer, men tabellen for dem i SQL skal være oprettet
        //på forhånd
    }

    public void employee(){
        System.out.println("Velkommen til Ebberød Bank");
        System.out.println("Vælg fra menuen");
        employeeMenu();

    }

    public void employeeMenu(){
        int choice = 0;
        while (choice != 5){
            choice = sc.nextInt();
            switch (choice){
                case 1: Customer customer = newCostumer(); commitCustomer(customer); break;
                case 2: removeCostumer(); break;
                case 3: customerChanges(); break;
                case 4: moveMoney(); break;

            }
        }
    }
    //case 1a
    private Customer newCostumer(){
        String name =  " ";
        int phoneNumber = 0;
        Customer customer = new Customer(name, phoneNumber;
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
        //Fjern en kunde
        //brug kunde nummer og eller navn
    }

    private void  customerChanges(){
        //indtast navn og nummer på kunde
        //indsæt, hæv
    }

    private void moveMoney(){

    }


}
