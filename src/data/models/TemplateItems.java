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
import other.DatabaseSyntaxHelper;
import other.DatabaseSyntaxHelper.ColumnDefinition;

/**
 *
 * @author danh.nguyentranbao
 */
public class TemplateItems {

    private final String TABLE_NAME = "MIB_template_items";
    private final String PRIMARY_KEY = "id";
    private ArrayList<ColumnDefinition> columnDefs;

    private HashMap<String, String> foreignKeys;
    private final int FOREIGN_KEY_COL_ID = 4;
    
    public TemplateItems() {
        this.columnDefs = new ArrayList<ColumnDefinition>();
        this.columnDefs.add(new ColumnDefinition("name", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("object_id", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("value_type", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("access_type", DatabaseSyntaxHelper.DataType.DATE_TYPE));        
        this.columnDefs.add(new ColumnDefinition("owned_template", DatabaseSyntaxHelper.DataType.NUMERIC_TYPE));
        
        this.foreignKeys = new HashMap<String, String>();
        this.foreignKeys.put("template_id", "MIB_templates(id)");
    }

    public void createTable(Connection con) {
        Statement creationStatement = null;
        try {
            creationStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            creationStatement.executeUpdate(helper.createTable(this.TABLE_NAME, columnDefs, this.PRIMARY_KEY, this.foreignKeys));
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

    public int importTemplate(Connection con, String templateId, String[] info) {
        //use default order as of column definitions
        Statement insertStatement = null;
        int result = -1;

        try {
            insertStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();

            DatabaseSyntaxHelper.RecordValue[] values = new DatabaseSyntaxHelper.RecordValue[this.columnDefs.size()];
            for (int i = 0; i < info.length; i++) {
                values[i] = new DatabaseSyntaxHelper.RecordValue(this.columnDefs.get(i).name, info[i], this.columnDefs.get(i).type);
            }
            values[4] = new DatabaseSyntaxHelper.RecordValue(this.columnDefs.get(this.FOREIGN_KEY_COL_ID).name, templateId, this.columnDefs.get(this.FOREIGN_KEY_COL_ID).type);
            
            result = insertStatement.executeUpdate(helper.insertRecord(this.TABLE_NAME, values));
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
