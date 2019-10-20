/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import other.DatabaseSyntaxHelper;
import other.DatabaseSyntaxHelper.ColumnDefinition;
import other.DatabaseSyntaxHelper.RecordValue;

/**
 *
 * @author cloud
 */
public class Users {
    private String tableName;
    private String primaryKey;
    //private HashMap columnDefinitions;
    private ArrayList<ColumnDefinition> columnDefs;
    
    private final int DEFAULT_ACCOUNT_COL_NUMBER = 2;
    private final int DEFAULT_USER_COL_NUMBER = 4;
    
    private final String DEFAULT_LANGUAGE = "en";
    private final String DEFAULT_AUTO_LOGIN = "0";
    
    public Users() {
        this.tableName = "Users";
        this.primaryKey = "id";
        
        this.columnDefs = new ArrayList<ColumnDefinition>();
        //this.columnDefs.put("id", DatabaseSyntaxHelper.DataType.NUMERIC_TYPE);
        this.columnDefs.add(new ColumnDefinition("account", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("password", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("fullname", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("age", DatabaseSyntaxHelper.DataType.NUMERIC_TYPE));
        this.columnDefs.add(new ColumnDefinition("position", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("contact_number", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("auto_login", DatabaseSyntaxHelper.DataType.BOOLEAN_TYPE));
        this.columnDefs.add(new ColumnDefinition("language", DatabaseSyntaxHelper.DataType.CHAR_TYPE));        
    }
    
    public void createTable(Connection con) {
        Statement creationStatement = null;
        try {
            creationStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            creationStatement.executeUpdate(helper.createTable(tableName, this.columnDefs, primaryKey, null));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (creationStatement != null)
            {
                try {
                    creationStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public boolean createUser(Connection con, String[] accountInfo, String[] otherInfo) {
        Statement creationStatement = null;
        boolean result = false;
        try {
            creationStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();           
            
            RecordValue[] recordValues = new RecordValue[this.columnDefs.size()];
            for (int i = 0; i < this.DEFAULT_ACCOUNT_COL_NUMBER; i++)
                recordValues[i] = new RecordValue(this.columnDefs.get(i).name, accountInfo[i], this.columnDefs.get(i).type);
            for (int i = 0; i < this.DEFAULT_USER_COL_NUMBER; i++)
                recordValues[this.DEFAULT_ACCOUNT_COL_NUMBER + i] = new RecordValue(
                        this.columnDefs.get(this.DEFAULT_ACCOUNT_COL_NUMBER + i).name, otherInfo[i], 
                        this.columnDefs.get(this.DEFAULT_ACCOUNT_COL_NUMBER + i).type);
            recordValues[6] = new RecordValue(this.columnDefs.get(6).name, DEFAULT_AUTO_LOGIN, this.columnDefs.get(6).type);
            recordValues[7] = new RecordValue(this.columnDefs.get(7).name, DEFAULT_LANGUAGE, this.columnDefs.get(7).type);

            //System.out.println(helper.insertRecord(tableName, recordValues));
            creationStatement.executeUpdate(helper.insertRecord(tableName, recordValues));
            result = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            result = false;
        } finally {
            if (creationStatement != null) {
                try {
                    creationStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();                    
                }
            }
        }
        return result;
    }
    
    public int countAccount(Connection con, String account) {
        Statement checkStatement = null;
        //String colName = "account";
        
        try {
            checkStatement = con.createStatement();            
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            ResultSet res = checkStatement.executeQuery(
                    helper.countRecord(tableName, new DatabaseSyntaxHelper.RecordValue(this.columnDefs.get(0).name, account, this.columnDefs.get(0).type)));
            while (res.next()) {
                return res.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (checkStatement != null) {
                try {
                    checkStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return 0;
    }
}
