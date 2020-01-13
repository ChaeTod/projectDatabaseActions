package it.sovy.Artem.projectWorldX;

import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws SQLException {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //register the jdbc driver in the project
            System.out.println("Connecting to a selected database...");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/world_x", "root", ""); // that is strange, but I got port for MySql - 3308 instead of 3306!
            System.out.println("Connected database successfully...");
            System.out.println("Ready to work with it!");

            Scanner in = new Scanner(System.in);
            int choice = 0, tableNum = 0;
            System.out.println("------------------------------");
            System.out.println("1 - Show city database");
            System.out.println("2 - Add new line to the database");
            System.out.println("3 - Find the country and a population by inputted city");
            System.out.println("4 - Find top 20 cities and countries by the population");
            System.out.println("0 - Exit");
            System.out.println("------------------------------");
            choice = in.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Input the number of table to show (1-4): ");
                    tableNum = in.nextInt();
                    Request.showTable(tableNum, connection);  // print the database statements into the console
                    break;
                case 2:
                    Request.addNewLine(connection);  // add new city and an info about it to the table city if there are no duplicates in it.
                    break;
                case 3:
                    Request.makeSelectByCity(connection);
                    break;
                case 4:
                    Request.makeSelectByPopulation(connection);
                    break;
                default:
                    System.out.println("Have a nice day!");
                    break;
            }

            //Statement stmt = connection.createStatement(); //create a statement

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
