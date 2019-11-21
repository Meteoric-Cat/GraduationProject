/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmpd;

import control.DeviceManagementController;
import java.util.ArrayList;
import java.util.Set;
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
    private int tableId;
    private String deviceId;
    private String[] itemIds;
    private String[] queryObjects;

    public ObjectWalkingCallback(int tableId, String deviceId, String[] itemIds, String[] queryObjects) {
        this.tableId = tableId;
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
            VarbindCollection temp = walker.next().get();
//            Set<String> keySet = temp.get().keySet();
//            for (String value: keySet) {
//                System.out.println(value);
//            }
            while (temp != null) {
                //System.out.println(1);
                varbindCollectionList.add(temp);
                //System.out.println(temp.get().keySet())
                try {
                    temp = walker.next().get();
                } catch (Exception e) {
                    //e.printStackTrace();
                    walker.invoke();
                    temp = walker.next().get();
                }             
            }            
            
            DeviceManagementController controller = new DeviceManagementController();
            controller.processReceivedDeviceData(tableId, deviceId, itemIds, queryObjects, varbindCollectionList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            se.getContext().close();
        }
    }
    
}
