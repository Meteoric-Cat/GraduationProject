/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmpd;

/**
 *
 * @author danh.nguyentranbao
 */
public class ObjectNameHelper {
    public String normalizeNameToQuery(String obj) {
        int lastChar = (int) obj.charAt(obj.length() - 1);
        String result = null;
        
        if (48 > lastChar || lastChar > 57) {
            result = obj + ".0";
        } else {
            result = obj;
        }
        
        return result;
    }
    
    //result is an array of 2 strings, the first one is 
    public String[] denormalizeQueryName(String obj) {
        String[] result = new String[2];
        int dotLocation = obj.lastIndexOf(".");
        
        if (dotLocation > -1) {
            result[0] = obj.substring(0, dotLocation);
            result[1] = obj.substring(dotLocation + 1);
        }
        
        return result;
    }
}
