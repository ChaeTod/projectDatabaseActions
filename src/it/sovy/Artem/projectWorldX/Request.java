package it.sovy.Artem.projectWorldX;

import java.sql.*;
import java.util.Scanner;

/*  // check it how to import the JSON's
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
or
import net.sf.json.*;
but for using it, we suppose to download and add to the project a JSON-lib
*/

public class Request {
    public static void addNewLine(Connection connection) throws SQLException {
        //JSONObject jsonObject = new JSONObject();
        try {
            Scanner in = new Scanner(System.in);
            PreparedStatement pStmnt = null;

            String preQueryStatement = "SELECT * FROM city WHERE city.Name = ?";
            pStmnt = connection.prepareStatement(preQueryStatement);
            System.out.println("Input the name of the city in database to check for duplicates: ");
            pStmnt.setString(1, in.nextLine());

            ResultSet rs1 = pStmnt.executeQuery();
            if (!rs1.next()) {

                System.out.println("Input new number ID: "); //in the future, ID number should be updated automatically
                int newID = in.nextInt();
                in.nextLine();
                System.out.println("Input the name for the new city: ");
                String newCityName = in.nextLine();
                System.out.println("Input the CountryCode for the new city: ");
                String newCountryCode = in.nextLine();
                System.out.println("Input the District for the new city: ");
                String newDistrict = in.nextLine();
                System.out.println("Input the information about the population for the new city: ");
                String newInfo = in.nextLine();

                //JSONArray ja = new JSONArray();

                String query = " insert into city (ID, Name, CountryCode, District, Info)"
                        + " values (?, ?, ?, ?, ?)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                preparedStmt.setInt(1, newID);
                preparedStmt.setString(2, newCityName);
                preparedStmt.setString(3, newCountryCode);
                preparedStmt.setString(4, newDistrict);
                preparedStmt.setString(5, newInfo);  // check out how to add properly new info to the json
                //preparedStmt.setString(5, "{\"Population\":1885055}");  // correct shown for json
                preparedStmt.executeUpdate();

                System.out.println("New line added");
            } else {
                System.out.println("That city is already in data base! No need to add the same one!");
            }
            rs1.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void makeSelectByCity(Connection connection) throws SQLException {
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Input the city: ");
            String cityName = in.nextLine();
            PreparedStatement preparedStatement = connection.prepareCall("SELECT city.Name, country.Name AS Country_Name, json_extract(Info, '$.Population') AS Population FROM city JOIN country ON country.code = city.CountryCode WHERE city.Name = ?");
            preparedStatement.setString(1, cityName);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                String cityName2 = result.getString("city.Name");
                String countryName = result.getString("Country_Name");
                String population = result.getString("Population");
                System.out.println(cityName2 + " " + countryName + " " + population + "\n");
            }
            result.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void makeSelectByPopulation(Connection connection){
        try {
            PreparedStatement preparedStatement = connection.prepareCall("SELECT city.Name, country.Name AS Country_Name, json_extract(Info, '$.Population') AS Population FROM city JOIN country ON country.code = city.CountryCode ORDER BY Population DESC Limit 20");
            ResultSet result1 = preparedStatement.executeQuery();

            int i = 0;

            while (result1.next()) {
                i++;
                System.out.print(i + " ");
                String cityName2 = result1.getString("city.Name");
                String countryName = result1.getString("Country_Name");
                String population = result1.getString("Population");
                System.out.println(cityName2 + " " + countryName + " " + population + "\n");
            }
            result1.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showTable(int tableNum, Connection connection) throws SQLException {
        Statement stmt = null;
        String query = null;
        switch (tableNum) {
            case 1:
                query = "SELECT * FROM city";
                break;
            case 2:
                query = "SELECT * FROM country";
                break;
            case 3:
                query = "SELECT * FROM countryinfo";
                break;
            case 4:
                query = "SELECT * FROM countrylanguage";
                break;
            default:
                System.out.println("Incorrect table number! Waited for 1-4, but got: " + tableNum + ". Try one more time!");
                break;
        }
        try {  // NOTE: stupid solution, but for now it must be enough. For the future - user must input the name of the table which he wants to see!
            if (tableNum > 4) {
                System.exit(0);  // Exit - do not continue with an empty query!
            } else {
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (tableNum == 1) {  // city
                    while (rs.next()) {
                        int id = rs.getInt("ID");
                        String lastName = rs.getString("Name");
                        String countryCode = rs.getString("CountryCode");
                        String district = rs.getString("District");
                        String info = rs.getString("Info");  //json
                        System.out.println(id + " " + lastName + " " + countryCode + " " + district + " " + info + "\n");
                    }
                } else {
                    if (tableNum == 2) { // country
                        while (rs.next()) {
                            String countryCode = rs.getString("Code");
                            String countryName = rs.getString("Name");
                            String capital = rs.getString("Capital");
                            String code = rs.getString("Code2");
                            System.out.println(countryCode + " " + countryName + " " + capital + " " + code + "\n");
                        }
                    } else {
                        if (tableNum == 3) {
                            while (rs.next()) { //countryinfo
                                String doc = rs.getString("doc"); // json
                                String _id = rs.getString("_id");
                                System.out.println(doc + " " + _id + "\n");
                            }
                        } else {
                            while (rs.next()) { //countrylanguage
                                String countryCode = rs.getString("CountryCode");
                                String language = rs.getString("Language");
                                String isOfficial = rs.getString("IsOfficial");  //enum
                                String percentage = rs.getString("Percentage");
                                System.out.println(countryCode + " " + language + " " + isOfficial + " " + percentage + "\n");
                            }
                        }
                    }
                }
                rs.close();  // When you are finished using a Statement, call the method Statement.close to immediately release the resources it is using.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
