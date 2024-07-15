package org.example;

import java.sql.SQLException;

public interface ITransaction {
    void insertCurrency(String name,double rate) throws SQLException;
    void updateCurrency(double rate,String name) throws SQLException;
}
