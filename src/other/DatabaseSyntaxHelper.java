/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author cloud
 */
public class DatabaseSyntaxHelper {
    
    //foreignKeys values should have the form of table.key
    public String createTable(String tableName, ArrayList<ColumnDefinition> columns, String primaryKey, HashMap foreignKeys )
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(tableName);
        
        builder.append(" (");
        builder.append(primaryKey);
        builder.append(" INT NOT NULL AUTO_INCREMENT,");
        for (ColumnDefinition column : columns)
        {
            builder.append(column.name);
            builder.append(" ");
            builder.append(column.type);
            builder.append(",");
        }
        
        builder.append(" PRIMARY KEY (" + primaryKey + ")");
        if (foreignKeys != null)
        {
            Set<String> keyNames = foreignKeys.keySet();            
            for (String keyName : keyNames)
            {                
                builder.append(",FOREIGN KEY ");
                builder.append(keyName);
                builder.append(" REFERENCES ");
                builder.append(foreignKeys.get(keyName));
            }
        }
        builder.append(");");
        
        return builder.toString();
    }

    public String insertRecord(String tableName, RecordValue[] values) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append(tableName);
        
        builder.append("(");
        for (int i = 0; i < values.length - 1; i++) {
            builder.append(values[i].getName());
            builder.append(",");            
        }
        builder.append(values[values.length - 1].getName());
        builder.append(") VALUES (");

        for (int i = 0; i < values.length - 1; i++) {
            builder.append(values[i].getSQLNormalizedValue());
            builder.append(",");
        }
        builder.append(values[values.length - 1].getSQLNormalizedValue());
        
        builder.append(");");
        return builder.toString();
    }
    
    public String countRecord(String tableName, RecordValue... values) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(*) FROM ");
        builder.append(tableName);
        if (values != null) {
            builder.append(" WHERE ");
            for (RecordValue value : values) {
                builder.append(value.toWhereCondition());
                builder.append(" AND ");
            }
            builder.append("TRUE");
        }
        builder.append(";");
        return builder.toString();
    }     
   
    public class DataType
    {
        public static final String NUMERIC_TYPE = "INT";
        public static final String CHAR_TYPE = "MEDIUMTEXT";
        public static final String BOOLEAN_TYPE = "TINYINT";
    }
    
    public static class RecordValue {
        private ColumnDefinition columnDef;
        private String value;
        //private String type;
        
        public RecordValue(String name, String value, String type) {
            this.value = value;
            this.columnDef = new ColumnDefinition(name, type);
        }
        
        public String getSQLNormalizedValue() {
            if (this.columnDef.type.equals(DataType.CHAR_TYPE)) {
                return "\'" + this.value + "\'";
            }                
            return this.value;
        }
        
        public String getName() {
            return this.columnDef.name;
        }
        
        public String toWhereCondition() {
            if (this.columnDef.name != null) {
                return this.columnDef.name + "=" + this.getSQLNormalizedValue();
            }
            return "";
        }
    }
    
    public static class ColumnDefinition {
        public String name;
        public String type;
        
        public ColumnDefinition(String name, String type) {
            this.name = name;
            this.type = type;
        }
        
    }
}
