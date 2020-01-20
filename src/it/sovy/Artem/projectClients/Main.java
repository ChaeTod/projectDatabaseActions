package it.sovy.Artem.projectClients;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;
//import java.sql.Date;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner in = new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //register the jdbc driver in the project
            System.out.println("Connecting to a selected database...");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/userf", "root", ""); // that is strange, but I got port for MySql - 3308 instead of 3306!
            System.out.println("Connected database successfully...");
            System.out.println("Ready to work with it!");

            //System.out.println("Input new number ID: "); //in the future, ID number should be updated automatically
            //int newID = in.nextInt();
            //in.nextLine();

            //Date date = ;

            int choice = 0, tableNum = 0;
            System.out.println("------------------------------");
            System.out.println("1 - Insert new user to database");
            System.out.println("2 - Find by name in database");
            System.out.println("3 - Find by date in database");
            System.out.println("4 - Find top 20 cities and countries by the population");
            System.out.println("5 - Find population by country name");
            System.out.println("6 - Find date by month");
            System.out.println("0 - Exit");
            System.out.println("------------------------------");
            choice = in.nextInt();
            switch (choice){
                case 1:
                 //   System.out.println("Input the number of table to show (1-4): ");
                 //   tableNum = in.nextInt();
                    in.nextLine();
                    System.out.println("Input first name for the new user: ");
                    String firstName = in.nextLine();
                    String abc = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
                    System.out.println("Input last name for the new user: ");
                    String lastName = in.nextLine();
                    String abcd = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
                    System.out.println("Input email for the new user: ");
                    String email = in.nextLine();
                    System.out.println("Input the date of birth: ");
                    String sDate1 = in.nextLine(); //"31/12/1998";
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                    Request.insertNewUser(connection, firstName, lastName, email, date);  // print the database statements into the console

                    System.out.println(abc); // check the output
                    System.out.println(abcd);
                    break;
                case 2:
                    in.nextLine();
                    System.out.println("Input values of the user: ");
                    String findBy = in.nextLine();
                    Request.findByName(connection, findBy);
                    break;
                case 3:
                    /*
                    in.nextLine();
                    System.out.println("Input the date to find: ");
                    findBy = in.nextLine();
                    Request.findByDate(connection, findBy);
                    */
                    break;
                case 4:
                 //   Request.makeSelectByPopulation(connection);
                    break;
                case 5:
                 //   Request.makeSelectByCountryName(connection);
                    break;
                case 6:
                 //   Request.makeSelctByDate(connection);
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
