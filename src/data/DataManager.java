/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import data.models.Devices;
import data.models.TemplateItems;
import data.models.Templates;
import data.models.Users;
import java.io.File;
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

    private final String FILE_PATH = System.getProperty("user.home").concat("/SNMPManager");
    private final String USER_IMAGES_PATH = FILE_PATH.concat("/user_images/");
    private final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";
    private final String DEFAULT_URL = "jdbc:mysql://localhost/";
    private final String DEFAULT_USER = "root";
    private final String DEFAULT_PASSWORD = "caothanhhuyen123";
    private final String DEFAULT_DATABASE = "legacy";

    private Users users;
    private Devices devices;
    private Templates templates;
    private TemplateItems templateItems;
    
    private Connection connection;

    private static DataManager instance = null;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private DataManager() {
        initConnection();
        initDatabase();
        initTables();
        initFileStorage();
    }

    private void initConnection() {
        try {
            Class.forName(DEFAULT_DRIVER);
            this.connection = DriverManager.getConnection(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanUp() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initDatabase() {
        Statement creationStatement = null;
        try {
            creationStatement = this.connection.createStatement();
            creationStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DEFAULT_DATABASE + ";");
            creationStatement.executeQuery("USE " + DEFAULT_DATABASE);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (creationStatement != null) {
                try {
                    creationStatement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    private void initTables() {
        this.users = new Users();
        users.createTable(this.connection);

        this.devices = new Devices();
        devices.createTable(this.connection);

        this.templates = new Templates();
        templates.createTable(this.connection);
    }

    private void initFileStorage() {
        File file = new File(this.FILE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(this.USER_IMAGES_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public Connection getDatabaseConnection() {
        return this.connection;
    }

    public Users getUsers() {
        return this.users;
    }

    public Devices getDevices() {
        return this.devices;
    }

    public Templates getTemplates() {
        return this.templates;
    }
    
    public TemplateItems getTemplateItems() {
        return this.templateItems;
    }
    
    public String getFilePath() {
        return this.FILE_PATH;
    }

    public String getUserImagesPath() {
        return this.USER_IMAGES_PATH;
    }

}
