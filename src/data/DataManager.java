/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cloud
 */
public class DataManager {
    private final String DEFAULT_DRIVER = "com.mysql.jdbc.driver";
    private final String DEFAULT_URL = "jdbc:mysql://localhost/";
    private final String DEFAULT_USER = "root";
    private final String DEFAULT_PASSWORD = "caothanhhuyen123";
    private final String DEFAULT_DATABASE = "legacy";
    
    private Connection connection;
    
    private static DataManager instance;
    
    private DataManager() {
        initConnection();
        initDatabase();
    }
    
    private void initConnection() {
        try {
            Class.forName(DEFAULT_DRIVER);
            this.connection = DriverManager.getConnection(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void initDatabase() {
        Statement creationStatement = null;
        try {           
            creationStatement = this.connection.createStatement();
            creationStatement.executeQuery("CREATE DATABASE IF NOT EXISTS " + DEFAULT_DATABASE + ";");            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (creationStatement != null)
                try {
                    creationStatement.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
    }
}
