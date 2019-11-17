/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmpd;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author cloud
 */
public class TableGroupingHelper {
    private String[] ifTable = {"ifIndex", "ifDescr", "ifType", "ifMtu", 
        "ifSpeed", "ifPhysAddress", "ifAdminStatus", "ifOperStatus", "ifLastChange",
        "ifInOctets", "ifInUcastPkts", "ifInNUcastPkts", "ifInDiscards", "ifInErrors",
        "ifInUnknownProtos", "ifOutOctets", "ifOutUcastPkts", "ifOutNUcastPkts",
        "ifOutDiscards", "ifOutErrors", "ifOutQLen", "ifSpecific"};
    
    public TableGroupingHelper() {
    }
    
    public String getTableNameForItem(String itemName) {
        for (String ifItem : ifTable) {
            if (ifItem.equalsIgnoreCase(itemName)) {
                return "ifTable";
            }
        }
        
        return null;
    }
}
