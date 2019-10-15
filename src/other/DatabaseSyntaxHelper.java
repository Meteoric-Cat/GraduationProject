/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author cloud
 */
public class DatabaseSyntaxHelper {
    
    public String createTable(String tableName, HashMap columns, String primaryKey, HashMap foreignKeys )
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append("CREATE TABLE IF NOT EXISTS ");
        builder.append(tableName);
        
        builder.append(" (");
        Set<String> columnNames = columns.keySet();
        for (String columnName : columnNames)
        {
            builder.append(columnName);
            builder.append(" ");
            builder.append(columns.get(columnName));
            builder.append(",");
        }
        
        builder.append(" PRIMARY KEY (" + primaryKey + ")");
        if (foreignKeys != null)
        {
            Set<String> keyNames = foreignKeys.keySet();            
            for (String keyName : keyNames)
            {
                builder.append(",");
                builder.append("FOREIGN KEY ");
                builder.append(keyName);
                builder.append(" REFERENCES ");
                builder.append(foreignKeys.get(keyName));
            }
        }
        builder.append(");");
        
        return builder.toString();
    }

    public class DataType
    {
        public static final String NUMERIC_TYPE = "INT";
        public static final String CHAR_TYPE = "MEDIUMTEXT";
        public static final String BOOLEAN_TYPE = "TINYINT";
    }
}
