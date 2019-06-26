package com.illuha.netty1.repository;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.illuha.netty1.dao.ApplicationDao;
import com.illuha.netty1.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerRepository {
    private ApplicationDao applicationDao;

    @Inject
    public CustomerRepository(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }

    public void addCustomer(Customer customer) {
        Statement stmt = null;
        try {
            stmt = applicationDao.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO CUSTOMER (");
        sb.append("    CUSTOMER_ID,");
        sb.append("    CUSTOMER_NAME");
        sb.append(") VALUES ('");
        sb.append(customer.getId()).append("','");
        sb.append(customer.getName()).append("')");

        try {
            assert stmt != null;
            stmt.executeUpdate(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sb = new StringBuilder();
        sb.append("SELECT * FROM CUSTOMER");

        ResultSet rs;
        try {
            rs = stmt.executeQuery(sb.toString());

            while (rs.next()) {
                System.out.println(rs.getString("CUSTOMER_ID"));
                System.out.println(rs.getString("CUSTOMER_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
