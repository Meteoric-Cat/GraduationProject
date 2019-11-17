/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import helper.DatabaseSyntaxHelper;
import helper.DatabaseSyntaxHelper.ColumnDefinition;
import helper.DatabaseSyntaxHelper.DataType;

/**
 *
 * @author danh.nguyentranbao
 */
public class TemplateItemValues {
    private final String TABLE_NAME = "MIB_object_values";
    private final DatabaseSyntaxHelper.ColumnDefinition PRIMARY_KEY = new DatabaseSyntaxHelper.ColumnDefinition("id", DatabaseSyntaxHelper.DataType.CHAR_TYPE);
    private ArrayList<DatabaseSyntaxHelper.ColumnDefinition> columnDefs;

    private HashMap<String, String> foreignKeys;
    private final int FORKEY_ITEM_COL_ID = 0;
    private final int FORKEY_DEVICE_COL_ID = 2;
    
    public TemplateItemValues() {
        this.columnDefs = new ArrayList<ColumnDefinition>();
        this.columnDefs.add(new ColumnDefinition("item_id", DataType.NUMERIC_TYPE));  
        this.columnDefs.add(new ColumnDefinition("instance_id", DataType.NUMERIC_TYPE));
        this.columnDefs.add(new ColumnDefinition("device_id", DataType.NUMERIC_TYPE));
        this.columnDefs.add(new ColumnDefinition("value", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("updated_time", DataType.DATE_TYPE));
        
        this.foreignKeys = new HashMap<String, String>();
        this.foreignKeys.put("item_id", "MIB_template_items(id)");
        this.foreignKeys.put("device_id", "Devices(id)");
    }
 
    public void createTable(Connection con) {
        Statement creationStatement = null;
        
        try {
            creationStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            
            creationStatement.executeUpdate(helper.createTable(this.TABLE_NAME, columnDefs, PRIMARY_KEY.name, foreignKeys));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }    
    
}
