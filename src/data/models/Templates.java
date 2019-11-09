/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.models;

import data.DataManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import other.DatabaseSyntaxHelper;
import other.DatabaseSyntaxHelper.ColumnDefinition;
import other.DatabaseSyntaxHelper.RecordValue;

/**
 *
 * @author danh.nguyentranbao
 */
public class Templates {

    private final String TABLE_NAME = "MIB_templates";
    private final ColumnDefinition PRIMARY_KEY = new ColumnDefinition("id", DatabaseSyntaxHelper.DataType.NUMERIC_TYPE);
    private ArrayList<ColumnDefinition> columnDefs;

    private final int IMPORT_COL_ID = 3;
    private final int NAME_COL_ID = 0;

    public Templates() {
        this.columnDefs = new ArrayList<ColumnDefinition>();
        this.columnDefs.add(new ColumnDefinition("name", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("description", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("snmp_version", DatabaseSyntaxHelper.DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("imported_time", DatabaseSyntaxHelper.DataType.DATE_TYPE));
    }

    public void createTable(Connection con) {
        Statement creationStatement = null;
        try {
            creationStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            creationStatement.executeUpdate(helper.createTable(this.TABLE_NAME, columnDefs, this.PRIMARY_KEY.name, null));
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

    public String[] importTemplate(Connection con, String[] info, ArrayList<String[]> itemList) {
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

            SimpleDateFormat dateFormatter = new SimpleDateFormat(DatabaseSyntaxHelper.DataFormat.DATE_FORMAT);
            values[IMPORT_COL_ID] = new RecordValue(this.columnDefs.get(IMPORT_COL_ID).name,
                    dateFormatter.format(new Date()), this.columnDefs.get(IMPORT_COL_ID).type);

            int temp = insertStatement.executeUpdate(helper.insertRecord(this.TABLE_NAME, values));

            if (temp > 0) {
                Statement selectStatement = con.createStatement();
                ResultSet res = selectStatement.executeQuery(helper.selectInsertedId());
                String templateId = null;

                if (res.next()) {
                    templateId = res.getString(1);
                }

                result = new String[values.length + 1];
                for (int i = 1; i <= values.length; i++) {
                    result[i] = values[i - 1].getValue();
                }
                result[0] = templateId;

                if (templateId != null) {
                    int itemListSize = itemList.size();
                    for (int i = 0; i < itemListSize; i++) {
                        DataManager.getInstance().getTemplateItems().importTemplateItem(con, templateId, itemList.get(i));
                    }
                }
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

    public ArrayList<String[]> getTemplateList(Connection con, String[] orders) {
        Statement selectStatement = null;
        String select = "";
        ArrayList<String[]> result = new ArrayList<>();

        try {
            selectStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            int size = 0;

            if (orders == null) {
                select = PRIMARY_KEY.name + ",";
                size = this.columnDefs.size() - 1;
                for (int i = 0; i < size; i++) {
                    select += this.columnDefs.get(i).name + ",";
                }
                select += this.columnDefs.get(size).name;
            } else {
                size = orders.length - 1;
                for (int i = 0; i < size; i++) {
                    select += orders[i] + ",";
                }
                select += orders[size];
            }

            ResultSet res = selectStatement.executeQuery(helper.selectRecord(this.TABLE_NAME, select, null));
            size += 2;
            String[] temp = null;

            while (res.next()) {
                temp = new String[size];
                for (int i = 0; i < size; i++) {
                    temp[i] = res.getString(1 + i);
                }
                result.add(temp);
            }
            //result.add(temp);
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

    public int deleteTemplates(Connection con, String[] templates) {
        Statement deleteStatement = null;
        int result = 0;

        try {
            deleteStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();

            RecordValue[] templateConditionValues = new RecordValue[templates.length];
            for (int i = 0; i < templates.length; i++) {
                templateConditionValues[i] = new RecordValue(this.PRIMARY_KEY.name, templates[i], this.PRIMARY_KEY.type);
                DataManager.getInstance().getTemplateItems().deleteItemsOfTemplate(con, templates[i]);
            }

            result = deleteStatement.executeUpdate(helper.deleteRecord(this.TABLE_NAME, templateConditionValues));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (deleteStatement != null) {
                try {
                    deleteStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return result;
    }

    public ArrayList<String[]> getTemplateInfo(Connection con, String templateId, String[] orders) {
        //this method only return the data of a template without its id
        ArrayList<String[]> result = new ArrayList<String[]>();
        Statement selectStatement = null;
        String select = "";

        try {
            selectStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            int size = -1;

            if (orders == null) { 
                size = this.columnDefs.size() - 1;
                for (int i = 0; i < size; i++) {
                    select += this.columnDefs.get(i).name + ",";
                }
                select += this.columnDefs.get(size).name;
            } else {
                size = orders.length - 1;
                for (int i = 0; i < size; i++) {
                    select += orders[i] + ",";
                }
                select += orders[size];
            }

            //System.out.println(select);
            //System.out.println(size);
            size++;
            String[] temp = new String[size];

            ResultSet res = selectStatement.executeQuery(helper.selectRecord(this.TABLE_NAME, select,
                    new RecordValue(this.PRIMARY_KEY.name, templateId, this.PRIMARY_KEY.type)));

            if (res.next()) {
                for (int i = 0; i < size; i++) {
                    temp[i] = res.getString(i + 1);
                }
                result.add(temp);
            }
            
            result.add(DataManager.getInstance().getTemplateItems().getItemIdsOfTemplate(con, templateId));
            result.addAll(DataManager.getInstance().getTemplateItems().getItemsOfTemplate(con, templateId, null));
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

    public int updateTemplateInfo(Connection con, String templateId, ArrayList<String[]> templateInfo) {
        Statement updateStatement = null;
        int result = -1;
        
        try {
            updateStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            
            RecordValue[] setValues = new RecordValue[this.columnDefs.size()];
            for (int i = 0; i < this.columnDefs.size(); i++) {
                setValues[i] = new RecordValue(this.columnDefs.get(i).name, templateInfo.get(0)[i], this.columnDefs.get(i).type);
            }
            
            RecordValue[] conditionValues = {
                new RecordValue(this.PRIMARY_KEY.name, templateId, this.PRIMARY_KEY.type)
            };
            
            result = updateStatement.executeUpdate(helper.updateRecord(TABLE_NAME, setValues, conditionValues));
            
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
    
    public ArrayList<ColumnDefinition> getColumnDefinitions() {
        return this.columnDefs;
    }
    
    public ColumnDefinition getPrimaryKey() {
        return this.PRIMARY_KEY;
    }
    
    public ColumnDefinition getNameColumn() {
        return this.columnDefs.get(NAME_COL_ID);
    }

}
