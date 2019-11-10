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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class DevicesAndTemplates {

    private final String TABLE_NAME = "Devices_and_templates";
    private final ColumnDefinition PRIMARY_KEY = new ColumnDefinition("id", DataType.NUMERIC_TYPE);
    private ArrayList<ColumnDefinition> columnDefs;
    
    private final int DEVICE_COL_ID = 0;
    private final int TEMPLATE_COL_ID = 1;
    private final int DATE_COL_ID = 2;

    private HashMap<String, String> foreignKeys;    

    public DevicesAndTemplates() {
        this.columnDefs = new ArrayList<ColumnDefinition>();
        this.columnDefs.add(new ColumnDefinition("device_id", DataType.NUMERIC_TYPE));
        this.columnDefs.add(new ColumnDefinition("template_id", DataType.NUMERIC_TYPE));
        this.columnDefs.add(new ColumnDefinition("established_time", DataType.DATE_TYPE));

        this.foreignKeys = new HashMap<String, String>();
        this.foreignKeys.put("device_id", "Devices(id)");
        this.foreignKeys.put("template_id", "MIB_templates(id)");
    }

    public void createTable(Connection con) {
        Statement creationStatement = null;

        try {
            creationStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            creationStatement.executeUpdate(helper.createTable(TABLE_NAME, columnDefs, PRIMARY_KEY.name, foreignKeys));
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
    
    public boolean addDeviceAndTemplate(Connection con, String deviceId, String templateId) {
        Statement insertStatement = null;
        boolean result = false;
        
        try {
            insertStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            
            RecordValue[] values = new RecordValue[this.columnDefs.size()];
            values[DEVICE_COL_ID] = new RecordValue(this.columnDefs.get(DEVICE_COL_ID).name, deviceId, this.columnDefs.get(DEVICE_COL_ID).type);
            values[TEMPLATE_COL_ID] = new RecordValue(this.columnDefs.get(TEMPLATE_COL_ID).name, templateId, this.columnDefs.get(TEMPLATE_COL_ID).type);
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DatabaseSyntaxHelper.DataFormat.DATE_FORMAT);
            values[DATE_COL_ID] = new RecordValue(this.columnDefs.get(DATE_COL_ID).name, dateFormatter.format(new Date()), this.columnDefs.get(DATE_COL_ID).type);
            
            insertStatement.executeUpdate(helper.insertRecord(TABLE_NAME, values));
            result = true;                        
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

    
    public ArrayList<String> getAddedTemplateIds(Connection con, String deviceId) {
        ArrayList<String> result = new ArrayList<String>();
        Statement selectStatement = null;
        
        try {
            selectStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            
            ResultSet res = selectStatement.executeQuery(
            helper.selectRecord(TABLE_NAME, this.columnDefs.get(TEMPLATE_COL_ID).name, 
                    new RecordValue(this.columnDefs.get(DEVICE_COL_ID).name, deviceId, this.columnDefs.get(DEVICE_COL_ID).type)));
            
            while (res.next()) {
                result.add(res.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (selectStatement != null) {
                try {
                    selectStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return result;
    }
}
