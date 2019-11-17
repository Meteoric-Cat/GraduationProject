/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmpd;

import org.soulwing.snmp.SnmpCallback;
import org.soulwing.snmp.SnmpEvent;
import org.soulwing.snmp.SnmpResponse;
import org.soulwing.snmp.VarbindCollection;

/**
 *
 * @author cloud
 */
public class ObjectGettingCallback implements SnmpCallback<VarbindCollection> {

    @Override
    public void onSnmpResponse(SnmpEvent<VarbindCollection> se) {
        try {
            SnmpResponse<VarbindCollection> response = se.getResponse();            
            VarbindCollection varbindCollection = response.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            se.getContext().close();
        }
    }
    
}
