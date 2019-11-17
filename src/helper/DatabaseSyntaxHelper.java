/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author cloud
 */
public class DatabaseSyntaxHelper {

    //foreignKeys values should have the form of table.key
    public String createTable(String tableName, ArrayList<ColumnDefinition> columns, String primaryKey, HashMap foreignKeys) {
        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(tableName);

        builder.append(" (");
        builder.append(primaryKey);
        builder.append(" INT NOT NULL AUTO_INCREMENT,");
        for (ColumnDefinition column : columns) {
            builder.append(column.name);
            builder.append(" ");
            builder.append(column.type);
            builder.append(",");
        }

        builder.append(" PRIMARY KEY (" + primaryKey + ")");
        if (foreignKeys != null) {
            Set<String> keyNames = foreignKeys.keySet();
            for (String keyName : keyNames) {
                builder.append(",FOREIGN KEY (");
                builder.append(keyName);
                builder.append(") REFERENCES ");
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

    public String selectRecord(String tableName, String selectClause, RecordValue... values) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ");
        builder.append(selectClause);
        if (tableName != null) {
            builder.append(" FROM ");
            builder.append(tableName);
        }
        if (values != null) {
            builder.append(" WHERE ");
            for (RecordValue value : values) {
                builder.append(value.toEquationString());
                builder.append(" AND ");
            }
            builder.append("TRUE");
        }
        builder.append(";");
        return builder.toString();
    }

    public String selectInsertedId() {
        return this.selectRecord(null, "LAST_INSERT_ID()", null);
    }

    public String updateRecord(String tableName, RecordValue[] setValues, RecordValue[] conditionValues) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ");
        builder.append(tableName);
        builder.append(" SET ");

        for (int i = 0; i < setValues.length - 1; i++) {
            builder.append(setValues[i].toEquationString());
            builder.append(",");
        }
        builder.append(setValues[setValues.length - 1].toEquationString());

        if (conditionValues != null) {
            builder.append(" WHERE ");
            for (int i = 0; i < conditionValues.length; i++) {
                builder.append(conditionValues[i].toEquationString());
                builder.append(" AND ");
            }
            builder.append("TRUE");
        }
        builder.append(";");

        return builder.toString();
    }

    public String deleteRecord(String tableName, RecordValue[] conditionValues, boolean useOr) {
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM ");
        builder.append(tableName);
        builder.append(" WHERE ");

        for (int i = 0; i < conditionValues.length - 1; i++) {
            builder.append(conditionValues[i].toEquationString());
            if (useOr) {
                builder.append(" OR ");
            } else {
                builder.append(" AND ");
            }
        }
        builder.append(conditionValues[conditionValues.length - 1].toEquationString());

        return builder.toString();
    }

    public class DataFormat {

        public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    }

    public class DataType {

        public static final String NUMERIC_TYPE = "INT";
        public static final String CHAR_TYPE = "MEDIUMTEXT";
        public static final String BOOLEAN_TYPE = "TINYINT";
        public static final String DATE_TYPE = "MEDIUMTEXT";
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
            if (this.columnDef.type.equals(DataType.BOOLEAN_TYPE)) {
                return (Boolean.valueOf(this.value) ? "1" : "0");
            }
            return this.value;
        }

        public String getName() {
            return this.columnDef.name;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public String toEquationString() {
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
