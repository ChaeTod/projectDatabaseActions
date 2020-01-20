package it.sovy.Artem.projectClients;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Request {
    public static void insertNewUser(Connection connection, String firstName, String lastName, String email, Date date) {
        //Scanner in = new Scanner(System.in);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        //     java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());
        try {
            String query = " insert into clients (firstname, lastname, email, date)"
                    + " values (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setDate(4, sqlDate);
            ps.executeUpdate();

            System.out.println("New line added successfully!");

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
    public static void findByDate(Connection connection, String findBy){
        try{
            PreparedStatement preparedStatement = connection.prepareCall("SELECT firstName, lastName FROM clients WHERE firstName LIKE ? OR lastName LIKE ?");
            preparedStatement.setString(1, '%' + findBy + '%');
            preparedStatement.setString(2, '%' + findBy + '%');
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                System.out.println(firstName + " " + lastName + "\n");
            }
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

*/
    public static void findByName(Connection connection, String findBy){
        try{
            PreparedStatement preparedStatement = connection.prepareCall("SELECT firstName, lastName FROM clients WHERE firstName LIKE ? OR lastName LIKE ?");
            preparedStatement.setString(1, '%' + findBy + '%');
            preparedStatement.setString(2, '%' + findBy + '%');
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                System.out.println(firstName + " " + lastName + "\n");
            }
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
