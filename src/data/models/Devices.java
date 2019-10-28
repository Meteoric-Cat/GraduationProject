/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import other.DatabaseSyntaxHelper;
import other.DatabaseSyntaxHelper.ColumnDefinition;
import other.DatabaseSyntaxHelper.DataType;
import other.DatabaseSyntaxHelper.RecordValue;

/**
 *
 * @author cloud
 */
public class Devices {
    
    private final String tableName = "Devices";
    private final String primaryKey = "id";
    private ArrayList<ColumnDefinition> columnDefs;
    
    private final String DEFAULT_DEVICE_STATE = DeviceState.NOT_CHECKED; 
    private final String DEFAULT_LAST_ACCESS = " ";
    
    public Devices() {
        this.columnDefs = new ArrayList<ColumnDefinition>();
        this.columnDefs.add(new ColumnDefinition("name", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("type", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("label", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("snmp_version", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("state", DataType.BOOLEAN_TYPE));
        this.columnDefs.add(new ColumnDefinition("imported_time", DataType.DATE_TYPE));
        this.columnDefs.add(new ColumnDefinition("last_access", DataType.DATE_TYPE));
    }    

    public void createTable(Connection con) {
        Statement creationStatement = null;
        try {
            creationStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            creationStatement.executeUpdate(helper.createTable(tableName, columnDefs, primaryKey, null));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (creationStatement != null) {
                try {
                    creationStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public String[] importDevice(Connection con, String[] info) {
        //use default order as of column definitions
        Statement insertStatement = null;
        String[] result = null;
        
        try {
            insertStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            
            RecordValue[] values = new RecordValue[this.columnDefs.size()];
            for (int i = 0; i < info.length; i++) {
                values[i] = new RecordValue(this.columnDefs.get(i).name, info[i], this.columnDefs.get(i).type);
            }
            values[4] = new RecordValue(this.columnDefs.get(4).name, this.DEFAULT_DEVICE_STATE, this.columnDefs.get(4).type);            
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DatabaseSyntaxHelper.DataFormat.DATE_FORMAT);            
            values[5] = new RecordValue(this.columnDefs.get(5).name,
                    dateFormatter.format(new Date()), this.columnDefs.get(5).type);            
            values[6] = new RecordValue(this.columnDefs.get(6).name,
                    DEFAULT_LAST_ACCESS, this.columnDefs.get(6).type);            
            
            insertStatement.executeUpdate(helper.insertRecord(tableName, values));
            
            result = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                result[i] = values[i].getValue();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return result;
    }
    
    public class DeviceState {
        public static final String NOT_CHECKED = "Not checked";
        public static final String CHECKED = "Checked";
        public static final String ENABLED = "Enabled";
        public static final String DISABLED = "Disabled";
    }
}
