/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmpd;

import control.DeviceManagementController;
import data.DataManager;
import java.util.ArrayList;
import org.soulwing.snmp.SnmpCallback;
import org.soulwing.snmp.SnmpEvent;
import org.soulwing.snmp.SnmpResponse;
import org.soulwing.snmp.VarbindCollection;

/**
 *
 * @author cloud
 */
public class ObjectGettingCallback implements SnmpCallback<VarbindCollection> {
    private String deviceId;
    private String[] itemIds;
    private String[] normalizedObjectNames;
    
    public ObjectGettingCallback(String deviceId, String[] itemIds, String[] normalizedObjectNames) {
        this.deviceId = deviceId;
        this.itemIds = itemIds;
        this.normalizedObjectNames = normalizedObjectNames;
    }
    
    @Override
    public void onSnmpResponse(SnmpEvent<VarbindCollection> se) {
        try {
            SnmpResponse<VarbindCollection> response = se.getResponse();            
            VarbindCollection varbindCollection = response.get();
            DeviceManagementController controller = new DeviceManagementController();
            controller.processReceivedDeviceData(deviceId, itemIds, normalizedObjectNames, varbindCollection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            se.getContext().close();
        }
    }
    
}
