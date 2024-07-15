package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DbConnection implements ITransaction {
    Connection conn;


    public DbConnection() {
        try {
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fxRate", "root", "milanp25");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fx", "root", "milanp25");
            System.out.println("Connected successful");
        } catch (SQLException ex) {
            System.out.println("Database connection failed: " + ex.getMessage());
        }
    }

    public void insertCurrency(String name, double rate) throws SQLException {


        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = today.format(formatter);


        String insertRate = "INSERT INTO rate (price, date , currencyID) VALUES (?, ?, ?)";


        var toReturn = new ArrayList<Integer>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id FROM currency WHERE currencyName = '" + name + "'");
            while (rs.next()) {
                var currencyID = rs.getInt("id");
                toReturn.add(currencyID);
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
        /*
         */


        try (PreparedStatement pstmt1 = conn.prepareStatement(insertRate))
        {
            for (Integer cid : toReturn) {
                pstmt1.setInt(3, cid);
            }
            pstmt1.setDouble(1, rate);
            pstmt1.setString(2, todayDate);
            pstmt1.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }

        /*  */


    }

    public void updateCurrency(double rate,String name) throws SQLException {

        String updateQuery = "UPDATE rate INNER JOIN currency ON rate.currencyID = currency.id SET rate.price = ? WHERE currency.currencyName = ?";
       // String updateQuery = "UPDATE rate  SET rate.price = ? WHERE currency.currencyName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setDouble(1, rate);
            stmt.setString(2, name);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        }
    }


}
