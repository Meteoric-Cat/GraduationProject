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
import other.DatabaseSyntaxHelper.DataType;
import other.DatabaseSyntaxHelper.RecordValue;

/**
 *
 * @author cloud
 */
public class Devices {
    
    private final String tableName = "Devices";
    //private final String primaryKey = "id";
    private final ColumnDefinition PRIMARY_KEY = new ColumnDefinition("id", DataType.NUMERIC_TYPE);
    private ArrayList<ColumnDefinition> columnDefs;
    
    private final String DEFAULT_DEVICE_STATE = DeviceState.DISABLED;
    private final String DEFAULT_LAST_ACCESS = " ";
    
    private final int STATE_COL_ID = 7;
    private final int IMPORT_COL_ID = 8;
    private final int ACCESS_COL_ID = 9;
    
    public Devices() {
        this.columnDefs = new ArrayList<ColumnDefinition>();
        this.columnDefs.add(new ColumnDefinition("name", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("type", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("description", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("label", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("snmp_version", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("ip_address", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("community", DataType.CHAR_TYPE));
        this.columnDefs.add(new ColumnDefinition("state", DataType.BOOLEAN_TYPE));
        this.columnDefs.add(new ColumnDefinition("imported_time", DataType.DATE_TYPE));
        this.columnDefs.add(new ColumnDefinition("last_access", DataType.DATE_TYPE));
    }
    
    public void createTable(Connection con) {
        Statement creationStatement = null;
        try {
            creationStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            creationStatement.executeUpdate(helper.createTable(tableName, columnDefs, this.PRIMARY_KEY.name, null));
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
            values[STATE_COL_ID] = new RecordValue(this.columnDefs.get(STATE_COL_ID).name, this.normalizeStateValue(this.DEFAULT_DEVICE_STATE), this.columnDefs.get(STATE_COL_ID).type);
            SimpleDateFormat dateFormatter = new SimpleDateFormat(DatabaseSyntaxHelper.DataFormat.DATE_FORMAT);
            values[IMPORT_COL_ID] = new RecordValue(this.columnDefs.get(IMPORT_COL_ID).name,
                    dateFormatter.format(new Date()), this.columnDefs.get(IMPORT_COL_ID).type);
            values[ACCESS_COL_ID] = new RecordValue(this.columnDefs.get(ACCESS_COL_ID).name,
                    DEFAULT_LAST_ACCESS, this.columnDefs.get(ACCESS_COL_ID).type);
            
            insertStatement.executeUpdate(helper.insertRecord(tableName, values));
            
            values[STATE_COL_ID].setValue(this.denormalizeStateValue(values[STATE_COL_ID].getValue()));
            result = new String[values.length + 1];
            
            //get the id of the inserted device
            ResultSet res = insertStatement.executeQuery(helper.selectInsertedId());
            if (res.next()) {
                result[0] = res.getString(1);
            }
            
            for (int i = 1; i < values.length; i++) {
                result[i] = values[i - 1].getValue();
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
    
    public ArrayList<String[]> getDeviceList(Connection con, String[] orders) {
        Statement selectStatement = null;
        String select = "";
        ArrayList<String[]> result = new ArrayList<>();
        
        try {
            selectStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            int size = 0;
            
            if (orders == null) {
                select = this.PRIMARY_KEY.name + ",";
                size = this.columnDefs.size() - 1;
                for (int i = 0; i < size; i++) {
                    select += this.columnDefs.get(i).name + ",";
                }
                select += this.columnDefs.get(size).name;
                size += 2;
            } else {
                size = orders.length - 1;
                for (int i = 0; i < size; i++) {
                    select += orders[i] + ",";
                }
                select += orders[size];
                size ++;
            }
            
            ResultSet res = selectStatement.executeQuery(helper.selectRecord(tableName, select, null));            
            String[] temp = null;
            
            while (res.next()) {
                temp = new String[size];
                for (int i = 0; i < size; i++) {
                    if (i == STATE_COL_ID + 1) {
                        temp[i] = this.denormalizeStateValue(res.getString(i + 1));
                    } else {
                        temp[i] = res.getString(1 + i);
                    }
                    
                }
                result.add(temp);
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
    
    public String[] getDeviceInfo(Connection con, String id, String[] orders) {
        Statement selectStatement = null;
        String[] result = null;
        String select = "";

        try {
            selectStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            int size = 0;
            
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
            
            ResultSet res = selectStatement.executeQuery(helper.selectRecord(tableName, select,
                    new RecordValue(this.PRIMARY_KEY.name, id, this.PRIMARY_KEY.type)));
            size++;
            result = new String[size];
            
            if (res.next()) {
                for (int i = 0; i < size; i++) {
                    if (i == STATE_COL_ID) {
                        result[i] = this.denormalizeStateValue(res.getString(i + 1));
                    } else {
                        result[i] = res.getString(1 + i);
                    }
                    
                }
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
    
    public int updateDeviceInfo(Connection con, String deviceId, String[] info) {
        Statement updateStatement = null;
        int result = 0;       
        
        try {
            updateStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            
            RecordValue[] setValues = new RecordValue[info.length];
            for (int i = 0; i < info.length; i++) 
                setValues[i] = new RecordValue(this.columnDefs.get(i).name, info[i], this.columnDefs.get(i).type);
            if (info.length > STATE_COL_ID) {
                setValues[STATE_COL_ID].setValue(this.normalizeStateValue(info[STATE_COL_ID]));
            }
            
            RecordValue[] conditionValues = {
                new RecordValue(this.PRIMARY_KEY.name, deviceId, this.PRIMARY_KEY.type)
            };
            
            result = updateStatement.executeUpdate(helper.updateRecord(tableName, setValues, conditionValues));            
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
    
    public int deleteDevices(Connection con, String[] devices) {
        Statement deleteStatement = null;
        int result = 0;
        
        try {
            deleteStatement = con.createStatement();
            DatabaseSyntaxHelper helper = new DatabaseSyntaxHelper();
            
            RecordValue[] conditionValues = new RecordValue[devices.length];
            for (int i = 0; i < devices.length; i++) {
                conditionValues[i] = new RecordValue(this.PRIMARY_KEY.name, devices[i], this.PRIMARY_KEY.type);
                DataManager.getInstance().getDevicesAndTemplates().deleteRelationship(con, devices[i], null);
            }                        
            
            result = deleteStatement.executeUpdate( helper.deleteRecord(tableName, conditionValues, true));
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
    
    public String normalizeStateValue(String type) {
        if (type.equals(DeviceState.ENABLED)) {
            return String.valueOf(true);
        }
        return String.valueOf(false);
    }
    
    public String denormalizeStateValue(String stateValue) {
        if (stateValue.equals(String.valueOf(false)) || stateValue.equals("0")) {
            return DeviceState.DISABLED;
        }
        return DeviceState.ENABLED;
    }
    
    public class DeviceType {
        public static final String ROUTER = "Router";
        public static final String PC = "PC";
        public static final String SWITCH = "Switch";
    }
    
    public class DeviceState {

        //public static final String NOT_CHECKED = "Not checked";
        public static final String ENABLED = "Enabled";
        public static final String DISABLED = "Disabled";
        
    }
}
