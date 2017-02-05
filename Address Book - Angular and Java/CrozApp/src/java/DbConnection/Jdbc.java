/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import oracle.jdbc.OracleConnection;

/**
 *
 * @author Deamon
 */
public class Jdbc {

    public static Jdbc instance;
    public OracleConnection connection;

    public Jdbc() {

        //Loading of java jdbc driver/interface
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (Exception e) {
            System.out.println("Where is your Oracle jdbc driver");
            e.printStackTrace();
            return;
        }
        System.out.println("Oracle jdbc driver registerd");

        //Try-catch block for connection on Oracle Database with defined parameters
        try {
            connection = (OracleConnection) DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Database", "Croz", "Croz");
        } catch (Exception e) {
            System.out.println("Something went wrong, connection can not be established");
            e.printStackTrace();
            return;
        }

        //Check for connection instance
        if (connection != null) {
            System.out.println("Connection has been established");
        } else {
            System.out.println("Connection error");
        }

    }

    //Method that will allow us to use already defined connection. In case that we don't have already created connection, 
    //the instance of new one will be created. 
    public static Jdbc getInstance() {
        if (instance == null) {
            instance = new Jdbc();
        }
        return instance;
    }

    //connection getter
    public OracleConnection getConnection() {
        return connection;
    }

    public void setConnection(OracleConnection connection) {
        this.connection = connection;
    }

}
