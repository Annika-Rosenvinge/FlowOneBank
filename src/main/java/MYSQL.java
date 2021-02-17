import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
