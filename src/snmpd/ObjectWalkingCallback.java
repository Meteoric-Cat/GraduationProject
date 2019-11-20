/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmpd;

import control.DeviceManagementController;
import java.util.ArrayList;
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
    private String deviceId;
    private String[] itemIds;
    private String[] queryObjects;

    public ObjectWalkingCallback(String deviceId, String[] itemIds, String[] queryObjects) {
        this.deviceId = deviceId;
        this.itemIds = itemIds;
        this.queryObjects = queryObjects;
    }
    
    @Override
    public void onSnmpResponse(SnmpEvent se) {
        try {
            SnmpResponse<SnmpAsyncWalker<VarbindCollection>> response = se.getResponse();
            SnmpAsyncWalker<VarbindCollection> walker = response.get();
            ArrayList<VarbindCollection> varbindCollectionList = new ArrayList<VarbindCollection>();
            SnmpResponse<VarbindCollection> temp = walker.next();
            
            while (temp != null) {
                varbindCollectionList.add(temp.get());
                try {
                    temp = walker.next();
                } catch (Exception e) {
                    //e.printStackTrace();
                    walker.invoke();
                    temp = walker.next();
                }             
            }            
            
            DeviceManagementController controller = new DeviceManagementController();
            controller.processReceivedDeviceData(deviceId, itemIds, queryObjects, varbindCollectionList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            se.getContext().close();
        }
    }
    
}
