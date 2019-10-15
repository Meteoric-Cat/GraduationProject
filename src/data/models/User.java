/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.models;

import java.util.HashMap;
import other.DatabaseSyntaxHelper;

/**
 *
 * @author cloud
 */
public class User {
    private String tableName;
    private HashMap columnDefinitions;
    
    public User() {
        this.tableName = "Users";
        
        this.columnDefinitions = new HashMap();
        this.columnDefinitions.put("id", DatabaseSyntaxHelper.DataType.NUMERIC_TYPE);
        this.columnDefinitions.put("account", DatabaseSyntaxHelper.DataType.CHAR_TYPE);
        this.columnDefinitions.put("password", DatabaseSyntaxHelper.DataType.CHAR_TYPE);
        this.columnDefinitions.put("fullname", DatabaseSyntaxHelper.DataType.CHAR_TYPE);
        this.columnDefinitions.put("age", DatabaseSyntaxHelper.DataType.NUMERIC_TYPE);
        this.columnDefinitions.put("contact_number", DatabaseSyntaxHelper.DataType.CHAR_TYPE);
        this.columnDefinitions.put("position", DatabaseSyntaxHelper.DataType.CHAR_TYPE);
        this.columnDefinitions.put("auto_login", DatabaseSyntaxHelper.DataType.BOOLEAN_TYPE);
        this.columnDefinitions.put("language", DatabaseSyntaxHelper.DataType.CHAR_TYPE);
        
    }
}
