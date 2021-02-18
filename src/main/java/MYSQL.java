import java.sql.*;
import java.util.ArrayList;

public class MYSQL {

    // Opretter kunde konto
    public boolean createUserMYSQL(String username, String password, String name, String adresse, String phone) {
        int customerNumber = 0;
        Connection con;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO customer (cusName, cusPhone, cusAdress, cusUsername, cusPassword) VALUES (?,?,?,?,?)";
        try {
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

            while (rs.next()) {
                customerNumber = rs.getInt("CusNumber");
            }

            if (customerNumber == 0) {
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

    // Kunde login check
    public boolean userLoginCheck(String username, String password) {
        String sql = "SELECT CusUsername, CusPassword FROM customer WHERE CusUsername = ? AND CusPassword = ?";
        return checkLoginCorrect(sql, username, password);
    }

    // Henter kunde informationer fra databasen
    public Customer loadUser(String username, MYSQL mysql) {
        String customerName = null, customerAdress = null;
        int customerPhone = 0, customerNumber = 0, money = 0;

        Connection con;
        PreparedStatement preparedStatement;
        String sql = "SELECT cusNumber, cusName, cusPhone, cusAdress FROM customer WHERE CusUsername = ?";
        try {
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                customerNumber = rs.getInt("cusNumber");
                customerName = rs.getString("cusName");
                customerPhone = rs.getInt("cusPhone");
                customerAdress = rs.getString("cusAdress");
            }

            sql = "SELECT MoneyInAccount FROM cusaccount WHERE cusNumber = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, customerNumber);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                money = rs.getInt("MoneyInAccount");
            }
            rs.close();
            preparedStatement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new Customer(customerName, customerAdress, customerPhone, customerNumber, money, mysql);
    }

    // Henter kundes balance
    public int getMoney(int customerNumber) {
        int money = 0;
        Connection con;
        PreparedStatement preparedStatement;
        String sql = "SELECT MoneyInAccount FROM cusaccount WHERE cusNumber = ?";
        try {
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, customerNumber);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                money = rs.getInt("MoneyInAccount");
            }

            rs.close();
            preparedStatement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return money;
    }

    // Opdatere kundens balance
    public void updateMoney(int customerNumber, int money) {
        Connection con;
        PreparedStatement preparedStatement;
        String sql = "UPDATE cusaccount SET MoneyInAccount = ? WHERE cusNumber = ?";
        try {
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, money);
            preparedStatement.setInt(2, customerNumber);

            preparedStatement.execute();
            preparedStatement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Opdaterer transaktionshistorik
    public void transactionUpdate(int customerNumber, String customerName, int balance, int changeMoney, String txt) {
        Connection con;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO TransactionHistory (cusNumber, cusName, TransactionHis) VALUES (?, ?, ?)";
        try {
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, customerNumber);
            preparedStatement.setString(2, customerName);

            String transTxt = txt + " " + changeMoney + " DKK .... Balance: " + (float) balance / 100.00 + " DKK";
            preparedStatement.setString(3, transTxt);

            preparedStatement.execute();
            preparedStatement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Opretter employee konto
    public boolean createEmployeeMYSQL(String username, String password) {
        Connection con;
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO employeeaccount (emName, ePincode) VALUES (?,?)";
        try {
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            preparedStatement.execute();
            preparedStatement.close();
            con.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    // Employee login check
    public boolean employeeLoginCheck(String username, String password) {
        String sql = "SELECT emName, ePincode FROM employeeaccount WHERE emName = ? AND ePincode = ?";
        return checkLoginCorrect(sql, username, password);
    }

    // Checker om employee/kunde login passer med konti fra database
    private boolean checkLoginCorrect(String sql, String username, String password) {
        Connection con;
        PreparedStatement preparedStatement;
        try {
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
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

    // Henter alle kunders informationer fra databasen
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        String customerName, customerAdress;
        int customerPhone, customerNumber, money = 0;

        Connection con;
        PreparedStatement preparedStatement;
        String sql = "SELECT cusNumber, cusName, cusPhone, cusAdress, cusUsername FROM customer";
        try {
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                customerNumber = rs.getInt("cusNumber");
                customerName = rs.getString("cusName");
                customerPhone = rs.getInt("cusPhone");
                customerAdress = rs.getString("cusAdress");

                String sql2 = "SELECT MoneyInAccount FROM cusaccount WHERE cusNumber = ?";
                PreparedStatement preparedStatement2 = con.prepareStatement(sql2);
                preparedStatement2.setInt(1, customerNumber);

                ResultSet rs2 = preparedStatement2.executeQuery();

                if (rs2.next()) {
                    money = rs2.getInt("MoneyInAccount");
                }

                customers.add(new Customer(customerName, customerAdress, customerPhone, customerNumber, money, this));
            }


            rs.close();
            preparedStatement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    // Fjerner kunder fra databasen
    public void removeUser(int cusNummer) {
        Connection con;
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM customer WHERE CusNumber = ?;";
        try {
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, cusNummer);
            preparedStatement.execute();

            sql = "DELETE FROM transactionhistory WHERE CusNumber = ?;";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, cusNummer);
            preparedStatement.execute();

            sql = "DELETE FROM cusaccount WHERE CusNumber = ?;";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, cusNummer);
            preparedStatement.execute();

            preparedStatement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Henter transaktionshistorik fra kunde
    public ArrayList<String[]> getTransactionHistory(int cusNumber) {
        ArrayList<String[]> transactions = new ArrayList<>();
        Connection con;
        PreparedStatement preparedStatement;
        String sql = "SELECT CusName, TimeTransaction, TransactionHis FROM transactionhistory WHERE CusNumber = ?";
        try {
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, cusNumber);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String[] tmpTransaction = new String[3];
                tmpTransaction[0] = rs.getString("CusName");
                tmpTransaction[1] = rs.getString("TimeTransaction");
                tmpTransaction[2] = rs.getString("TransactionHis");

                transactions.add(tmpTransaction);
            }


            rs.close();
            preparedStatement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transactions;
    }

    // Opdaterer kundens indstillinger
    public void updateUserAccount(int cusNumber, String cusName, int phone, String cusAdress) {
        Connection con;
        PreparedStatement preparedStatement;
        String sql = "UPDATE customer SET CusName = ?, CusPhone = ?, CusAdress = ? WHERE cusNumber = ?";
        try {
            con = JDBConnector.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, cusName);
            preparedStatement.setInt(2, phone);
            preparedStatement.setString(3, cusAdress);
            preparedStatement.setInt(4, cusNumber);

            preparedStatement.execute();
            preparedStatement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}