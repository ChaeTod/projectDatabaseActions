package it.sovy.Artem.projectWorldX;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Request {
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
