package it.sovy.Artem.projectTwoTables;

import javax.sound.midi.Soundbank;
import java.sql.*;
import java.util.Scanner;

public class Request {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver"); //register the jdbc driver in the project
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/userf", "root", ""); // that is strange, but I got port for MySql - 3308 instead of 3306!
        return connection;
    }

    public static void insertNewInput() throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);

        System.out.println("Input the name: ");
        String name = in.nextLine();
        System.out.println("Input the A, B and C sides: ");
        int a = in.nextInt(), b = in.nextInt(), c = in.nextInt();
        String query = " insert into input (name, a, b, c)"
                + " values (?, ?, ?, ?)";
        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.setString(1, name);
        ps.setInt(2, a);
        ps.setInt(3, b);
        ps.setInt(4, c);
        ps.executeUpdate();

        System.out.println("New line added successfully!");

        ps.close();
    }

    public int calculateSurface(int a, int b, int c) {
        return (a * b) * 2 + (b * c) * 2 + (c * a) * 2;
    }

    public int calculateVolume(int a, int b, int c){
        return a * b * c;
    }

    public void getValues() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM input";
        PreparedStatement ps = getConnection().prepareStatement(query);
        //stmt = connection.createStatement();
        ResultSet rs = ps.executeQuery(query);
        //String name = null;
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int a = rs.getInt("A");
            int b = rs.getInt("B");
            int c = rs.getInt("C");  //json
            System.out.println(id + " " + name + " " + a + " " + b + " " + c + "\n");
            updateOutput(name, calculateSurface(a, b, c), calculateVolume(a, b, c));
            System.out.println("The surface of this values: " + calculateSurface(a, b, c));
            System.out.println("The volume of this values: " + calculateVolume(a, b, c));
        }
        rs.close();
        // delete from the input table after the calculations
        deleteFromInput();
    }

    public void startCheck() throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        String query = "SELECT * FROM input";
        PreparedStatement ps = getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);
        if (rs.next()){
            getValues();
        } else {
            System.out.println("There are no data inside the input table!");
            System.out.println("You must input something first!");
            System.out.println("Do you want to input value now? (Y/N)");
            String answer = in.nextLine();
            if (answer.equals("Y")){
                insertNewInput();
            } else {
                System.out.println("Have a nice day!");
                System.exit(0);
            }
        }
    }

    public void updateOutput(String name, int a, int b) throws SQLException, ClassNotFoundException {
        String query = "insert into output (name, Surface, Volume) values (?, ?, ?)";
        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.setString(1, name);
        ps.setInt(2, a);
        ps.setInt(3, b);
        ps.executeUpdate();

        System.out.println("Output table updated successfully!");
    }

    public void deleteFromInput() throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM input";
        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.executeUpdate();
    }
}
