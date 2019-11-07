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
import java.util.logging.Level;
import java.util.logging.Logger;
import other.DatabaseSyntaxHelper;
import other.DatabaseSyntaxHelper.ColumnDefinition;
import other.DatabaseSyntaxHelper.RecordValue;

/**
 *
 * @author danh.nguyentranbao
 */
public class TemplateItems {

    private final String TABLE_NAME = "MIB_template_items";
    private final ColumnDefinition PRIMARY_KEY = new ColumnDefinition("id", DatabaseSyntaxHelper.DataType.CHAR_TYPE);
    private ArrayList<ColumnDefinition> columnDefs;

    private HashMap<String, String> foreignKeys;
    private final int FOREIGN_KEY_COL_ID = 5;

    public TemplateItems() {
        this.columnDefs = new ArrayList<ColumnDefinition>();
        this.columnDefs.add(new ColumnDefinition("name", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("object_id", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("value_type", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("access_type", DatabaseSyntaxHelper.DataType.DATE_TYPE));
        this.columnDefs.add(new ColumnDefinition("description", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("template_id", DatabaseSyntaxHelper.DataType.NUMERIC_TYPE));

        this.foreignKeys = new HashMap<String, String>();
        this.foreignKeys.put("template_id", "MIB_templates(id)");
    }

    public void createTable(Connection con) {
        Statement creationStatement = null;
        try {
            creationStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            creationStatement.executeUpdate(helper.createTable(this.TABLE_NAME, columnDefs, this.PRIMARY_KEY.name, this.foreignKeys));
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

    public int importTemplateItem(Connection con, String templateId, String[] info) {
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
            values[this.FOREIGN_KEY_COL_ID] = new DatabaseSyntaxHelper.RecordValue(this.columnDefs.get(this.FOREIGN_KEY_COL_ID).name, templateId, this.columnDefs.get(this.FOREIGN_KEY_COL_ID).type);

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

    public int deleteItemsOfTemplate(Connection con, String templateId) {
        Statement deletionStatement = null;
        int result = -1;

        try {
            deletionStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();

            RecordValue[] conditionValues = {
                new RecordValue(this.columnDefs.get(this.FOREIGN_KEY_COL_ID).name, templateId, this.columnDefs.get(this.FOREIGN_KEY_COL_ID).type)
            };

            result = deletionStatement.executeUpdate(helper.deleteRecord(TABLE_NAME, conditionValues));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (deletionStatement != null) {
                try {
                    deletionStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return result;
    }
    
    public String[] getItemIdsOfTemplate(Connection con, String templateId) {
        Statement selectStatement = null;
        ArrayList<String> result = new ArrayList<String>();
         
        try {
            selectStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            
            ResultSet res = selectStatement.executeQuery(helper.selectRecord(TABLE_NAME, this.PRIMARY_KEY.name, 
                    new RecordValue(this.columnDefs.get(FOREIGN_KEY_COL_ID).name, templateId, this.columnDefs.get(FOREIGN_KEY_COL_ID).type)));
            
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
        
        String[] temp = new String[result.size()];        
        result.toArray(temp);
        return temp;
    }

    public ArrayList<String[]> getItemsOfTemplate(Connection con, String templateId, String[] orders) {
        Statement selectStatement = null;
        ArrayList<String[]> result = new ArrayList<String[]>();
        String select = "";

        try {
            selectStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            int tempSize = 0;
            
            if (orders == null) {
                tempSize = this.columnDefs.size() - 2;
                for (int i = 0; i < tempSize; i++) {
                    select += (this.columnDefs.get(i).name + ",");
                }
                select += this.columnDefs.get(tempSize).name;
            } else {
                tempSize = orders.length - 1;
                for (int i = 0; i < tempSize; i++) {
                    select += (this.columnDefs.get(i).name +",");
                }
                select += this.columnDefs.get(tempSize).name;
            }

            ResultSet res = selectStatement.executeQuery(helper.selectRecord(this.TABLE_NAME, select,
                    new RecordValue(this.columnDefs.get(this.FOREIGN_KEY_COL_ID).name, templateId, this.columnDefs.get(this.FOREIGN_KEY_COL_ID).type)));

            String[] tempResult = null;
            while (res.next()) {
                tempResult = new String[tempSize + 1];
                for (int i = 0; i <= tempSize; i++) {
                    tempResult[i] = res.getString(i + 1);
                }
                result.add(tempResult);
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

    public int updateItemsOfTemplate(Connection con, ArrayList<String[]> templateInfo) {
        Statement updateStatement = null;
        int result = 0;

        try {
            updateStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();

            String[] itemIds = templateInfo.get(1);

            int tempSize = this.columnDefs.size() - 1;
            RecordValue[] setValues = new RecordValue[tempSize];
            RecordValue[] conditionValues = null;

            for (int i = 0; i < itemIds.length; i++) {
                for (int j = 0; j < tempSize; j++) {
                    setValues[j] = new RecordValue(this.columnDefs.get(j).name, templateInfo.get(2 + i)[j], this.columnDefs.get(j).type);
                }

                conditionValues = new RecordValue[]{
                    new RecordValue(this.PRIMARY_KEY.name, itemIds[i], this.PRIMARY_KEY.type)
                };

                if (updateStatement.executeUpdate(helper.updateRecord(TABLE_NAME, setValues, conditionValues)) > 0) {
                    result++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (updateStatement != null) {
                try {
                    updateStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return result;
    }
}
