import java.sql.*;
import java.util.ArrayList;

public class MYSQL {

    public boolean createUserMYSQL(String username, String password, String name, String adresse, String phone) {
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
            con.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean userLoginCheck(String username, String password) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        String sql = "SELECT CusUsername, CusPassword FROM customer WHERE CusUsername = ? AND CusPassword = ?";
        try{
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                rs.close();
                preparedStatement.close();
                con.close();
                return true;
            }

            rs.close();
            preparedStatement.close();
            con.close();
            return false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Customer loadUser(String username) {
        String customerName = null, customerAdress = null;
        int customerPhone = 0, customerNumber = 0, money = 0;

        Connection con = null;
        PreparedStatement preparedStatement = null;
        String sql = "SELECT cusNumber, cusName, cusPhone, cusAdress FROM customer WHERE CusUsername = ?";
        try{
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                customerNumber = rs.getInt("cusNumber");
                customerName = rs.getString("cusName");
                customerPhone = rs.getInt("cusPhone");
                customerAdress = rs.getString("cusAdress");
            }

            sql = "SELECT MoneyInAccount FROM cusaccount WHERE AccountNumber = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, customerNumber);

            rs = preparedStatement.executeQuery();

            if(rs.next()) {
                money = rs.getInt("MoneyInAccount");
            }
            rs.close();
            preparedStatement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Customer customer = new Customer(customerName, customerAdress, customerPhone, customerNumber, money);
        return customer;
    }
}
