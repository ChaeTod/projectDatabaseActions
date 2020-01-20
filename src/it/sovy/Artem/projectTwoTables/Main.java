package it.sovy.Artem.projectTwoTables;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        //Connect with the database
        if (Request.getConnection() != null){
            System.out.println("Connecting to a selected database...");
            System.out.println("Connected database successfully...");
            System.out.println("Ready to work!");
        } else {
            System.out.println("There is something go wrong! Check the error log!");
        }

        Request rq = new Request();

        int choice = 0, tableNum = 0;
        System.out.println("------------------------------");
        System.out.println("1 - Insert new data to the Input table");
        System.out.println("2 - Get the data from the Input table and calculate the values");
        //System.out.println("3 - Find by date in");
        choice = in.nextInt();
        switch (choice){
            case 1:
                Request.insertNewInput();
                break;
            case 2:
                rq.startCheck();
                break;
            default:
                System.out.println("Have a nice day! See ya!");
        }
    }
}
