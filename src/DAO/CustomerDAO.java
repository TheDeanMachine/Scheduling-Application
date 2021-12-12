package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;
import utilities.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO implements DataAccessObject<Customers> {
    ObservableList<Customers> listOfCustomers = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query = "SELECT c.customer_ID, c.customer_name, c.address, c.postal_code, c.phone, fld.division_Id, con.country_Id \n" +
            "FROM customers c \n" +
            "JOIN first_level_divisions fld ON c.division_Id = fld.division_Id \n" +
            "JOIN countries con ON fld.country_Id = con.country_Id;";

    @Override
    public void create() {

    }

    @Override
    public ObservableList<Customers> read() {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int customerId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String code = resultSet.getString(4);
                String phone = resultSet.getString(5);

                int divisionId = resultSet.getInt(6);
                int countryId = resultSet.getInt(7);


                Customers customer = new Customers(customerId, name, address, code, phone, divisionId, countryId);
                listOfCustomers.add(customer);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfCustomers;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
