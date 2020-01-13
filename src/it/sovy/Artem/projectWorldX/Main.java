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

                    break;
                default:
                    System.out.println("Have a nice day!");
                    break;
            }

            //Statement stmt = connection.createStatement(); //create a statement

        } catch (Exception e){
            e.printStackTrace();
        }






        /*
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");  //register the driver in the project
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to a selected database...");
            Connection connetion = DriverManager.getConnection("jdbc:mysql://localhost:3308/world_x", "root", "");
            System.out.println("Connected database successfully...");

            Statement stmt = connetion.createStatement(); //create a statement

            PreparedStatement pStmnt = null;

            String sql1 = "SELECT * FROM city";
            ResultSet rs = stmt.executeQuery(sql1);

            Scanner in = new Scanner(System.in);

            String preQueryStatement = "SELECT * FROM city WHERE city.Name = ?";
            pStmnt = connetion.prepareStatement(preQueryStatement);
            System.out.println("Input the name of the city in database: ");
            pStmnt.setString(1, in.nextLine());

            ResultSet rs1 = pStmnt.executeQuery();
            if (!rs1.next()){

                String query = " insert into city (ID, Name, CountryCode, District, Info)"
                        + " values (?, ?, ?, ?, ?)";
                PreparedStatement preparedStmt = connetion.prepareStatement(query);

                preparedStmt.setInt(1,4082);
                preparedStmt.setString (2, "Rootang");
                preparedStmt.setString (3, "ROT");
                preparedStmt.setString(4, "Sabol");
                preparedStmt.setString(5, "{\"Population\":1885055}");  // correct shown the json
                preparedStmt.executeUpdate();

                System.out.println("New line added");
            } else {
                System.out.println("That line is already in data base");

            }
// Input the select for cities
            System.out.println("Input the city: ");
            String cityName = in.nextLine();
            PreparedStatement preparedStatement = connetion.prepareCall("SELECT city.Name, country.Name AS Country_Name, json_extract(Info, '$.Population') AS Population FROM city JOIN country ON country.code = city.CountryCode WHERE city.Name = ?");
            preparedStatement.setString(1, cityName);
            ResultSet result = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String lastName = rs.getString("Name");
                String countryCode = rs.getString("CountryCode");
                String district = rs.getString("District");
                String info = rs.getString("Info");
                System.out.println(id + " " + lastName + " " + countryCode+ " "+ district+ " " + info+ "\n");
            }


            System.out.println("--------------------------------------------");
            while (result.next()) {
                String cityName2 = result.getString("city.Name");
                String countryName = result.getString("Country_Name");
                String population = result.getString("Population");
                System.out.println(cityName2 + " " + countryName + " " + population + "\n");
            }



            // The biggest cities in the world

            preparedStatement = connetion.prepareCall("SELECT city.Name, country.Name AS Country_Name, json_extract(Info, '$.Population') AS Population FROM city JOIN country ON country.code = city.CountryCode ORDER BY Population DESC Limit 20");
            //preparedStatement.setString(1, cityName);
            ResultSet result1 = preparedStatement.executeQuery();

            int i = 0;

            System.out.println("--------------------------------------------");
            while (result1.next()) {
                i++;
                System.out.print(i+ " ");
                String cityName2 = result1.getString("city.Name");
                String countryName = result1.getString("Country_Name");
                String population = result1.getString("Population");
                System.out.println(cityName2 + " " + countryName + " " + population + "\n");
            }


            rs.close();
            result.close();
            result1.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

         */
    }
}
