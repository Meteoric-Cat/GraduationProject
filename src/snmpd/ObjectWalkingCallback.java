/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmpd;

import org.soulwing.snmp.SnmpAsyncWalker;
import org.soulwing.snmp.SnmpCallback;
import org.soulwing.snmp.SnmpEvent;
import org.soulwing.snmp.SnmpResponse;
import org.soulwing.snmp.VarbindCollection;

/**
 *
 * @author danh.nguyentranbao
 */
public class ObjectWalkingCallback implements SnmpCallback<SnmpAsyncWalker<VarbindCollection>>{

    @Override
    public void onSnmpResponse(SnmpEvent se) {
        try {
            SnmpResponse<SnmpAsyncWalker<VarbindCollection>> response = se.getResponse();
            SnmpAsyncWalker<VarbindCollection> varbinds = response.get();
                        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            se.getContext().close();
        }
    }
    
}
