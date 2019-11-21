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
import helper.DatabaseSyntaxHelper;
import helper.DatabaseSyntaxHelper.ColumnDefinition;
import helper.DatabaseSyntaxHelper.DataType;
import helper.DatabaseSyntaxHelper.RecordValue;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final int TIME_COL_ID = 4;

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

    //return importTime
    public String insertTemplateItemValue(Connection con, String... data) {
        Statement insertStatement = null;
        String result = null;

        try {
            insertStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            RecordValue[] valueSet = null;
            if (data.length < this.columnDefs.size()) {
                valueSet = new RecordValue[data.length + 1];
            } else {
                valueSet = new RecordValue[data.length];
            }

            for (int i = 0; i < data.length; i++) {
                valueSet[i] = new RecordValue(this.columnDefs.get(i).name, data[i], this.columnDefs.get(i).type);
            }
            if (data.length < this.columnDefs.size()) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(DatabaseSyntaxHelper.DataFormat.DATE_FORMAT);
                valueSet[data.length] = new RecordValue(this.columnDefs.get(TIME_COL_ID).name, dateFormatter.format(new Date()),
                        this.columnDefs.get(TIME_COL_ID).type);
            }

            insertStatement.executeUpdate(helper.insertRecord(TABLE_NAME, valueSet));
            if (data.length < this.columnDefs.size()) {
                result = valueSet[data.length].getValue();
            } else {
                result = valueSet[data.length - 1].getValue();
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

}
