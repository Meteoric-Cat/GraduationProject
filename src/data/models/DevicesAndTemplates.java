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
import other.DatabaseSyntaxHelper;
import other.DatabaseSyntaxHelper.ColumnDefinition;
import other.DatabaseSyntaxHelper.DataType;

/**
 *
 * @author cloud
 */
public class DevicesAndTemplates {

    private final String TABLE_NAME = "Devices_and_templates";
    private final ColumnDefinition PRIMARY_KEY = new ColumnDefinition("id", DataType.NUMERIC_TYPE);
    private ArrayList<ColumnDefinition> columnDefs;

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

}
