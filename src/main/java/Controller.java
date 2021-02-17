import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Controller {
    private Scanner in = new Scanner (System.in);
    private int userInput;

    public void runInitProgram(){
        System.out.print("Velkommen til Ebberød Bank!\nTast 1 for kunde/Tast 2 for medarbejder: ");
        userInput = in.nextInt();

        switch(userInput) {
            case 1:
                startCustomerUserInterface();
                break;
            case 2:
                break;
            default:
                System.out.println("Invalid indtastet kommando");
                runInitProgram();
                break;
        }
    }


    private void startCustomerUserInterface() {
        System.out.print("Tast 1 for logge ind/Tast 2 for at oprette en bruger: ");
        userInput = in.nextInt();
        in.nextLine();
        switch(userInput) {
            case 1:
                break;
            case 2:
                createUserInterface();
                break;
            default:
                System.out.println("Invalid indtastet kommando");
                startCustomerUserInterface();
                break;
        }
    }

    private void createUserInterface() {
        String username, password = null, name, adresse, phone;

        System.out.print("Indtast dit fuldenavn: ");
        name = in.nextLine();

        System.out.print("Indtast din adresse: ");
        adresse = in.nextLine();

        System.out.print("Indtast dit telefon nummer: ");
        phone = in.nextLine();

        System.out.print("Vælg et brugernavn: ");
        username = in.nextLine();

        while(password == null) {
            password = createPassword();
        }

        if(createUserMYSQL(username, password, name, adresse, phone)) {
            System.out.println("Kontoen er oprettet");
        } else {
            System.out.println("FEJL (MYSQL)");
        }

    }

    private String createPassword() {
        String code1, code2;
        System.out.print("Vælg en kode: ");
        code1 = in.nextLine();
        System.out.print("Gentag koden: ");
        code2 = in.nextLine();

        if(code1.equals(code2)) {
            return code1;
        } else {
            return null;
        }
    }

    private boolean createUserMYSQL(String username, String password, String name, String adresse, String phone) {
        int customerNumber = 0;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO customer (cusName, cusPhone, cusAdress, cusUsername, cusPassword) VALUES (?,?,?,?,?)";
        try{
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, adresse);
            preparedStatement.setString(4, username);
            preparedStatement.setString(5, password);

            preparedStatement.execute();

            sql = "SELECT CusNumber FROM customer WHERE cusPhone = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, phone);
            ResultSet rs = preparedStatement.executeQuery();

            while ( rs.next() )
            {
                customerNumber = rs.getInt("CusNumber");
            }

            if(customerNumber == 0) {
                return false;
            }

            sql = "INSERT INTO cusaccount (MoneyInAccount, CusNumber, CusName) VALUES (?,?,?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, customerNumber);
            preparedStatement.setString(3, name);

            preparedStatement.execute();

            rs.close();
            preparedStatement.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}

