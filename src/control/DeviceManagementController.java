/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.opencsv.CSVReader;
import data.DataManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.soulwing.snmp.SimpleSnmpV2cTarget;
import org.soulwing.snmp.SnmpCallback;
import org.soulwing.snmp.SnmpContext;
import org.soulwing.snmp.SnmpFactory;
import org.soulwing.snmp.SnmpTarget;
import org.soulwing.snmp.VarbindCollection;
import snmpd.ObjectGettingCallback;
import snmpd.SnmpManager;
import snmpd.SnmpManager.SNMPVersion;

/**
 *
 * @author danh.nguyentranbao
 */
public class DeviceManagementController {

    private String resultMessage;

    public ArrayList<String[]> processImportDevices(String fileName) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        CSVReader reader = null;
        ImportResultMessenger resultMessenger = new ImportResultMessenger();

        try {
            reader = new CSVReader(new FileReader(fileName));
            String[] line = reader.readNext();

            while ((line = reader.readNext()) != null) {
                String[] deviceInfo = DataManager.getInstance().getDevices()
                        .importDevice(DataManager.getInstance().getDatabaseConnection(), line);
                if (deviceInfo != null) {
                    result.add(deviceInfo);
                }
            }

            this.resultMessage = resultMessenger.IMPORT_SUCCESS;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            this.resultMessage = resultMessenger.ERROR_FILE_NOT_FOUND;
        } catch (IOException ex) {
            ex.printStackTrace();
            this.resultMessage = resultMessenger.ERROR_IO;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (result.size() == 0) {
            this.resultMessage = resultMessenger.ERROR_NO_CONTENT;
        }
        return result;
    }

    public ArrayList<String[]> processInitDeviceList() {
        return DataManager.getInstance().getDevices().getDeviceList(DataManager.getInstance().getDatabaseConnection(), null);
    }

    public boolean processDevicesDeletion(String[] devices) {
        DeletionResultMessenger resultMessenger = new DeletionResultMessenger();
        int queryResult = DataManager.getInstance().getDevices().deleteDevices(DataManager.getInstance().getDatabaseConnection(), devices);

        if (queryResult == devices.length) {
            this.resultMessage = resultMessenger.SUCCESS;
            return true;
        }

        this.resultMessage = resultMessenger.FAILED;
        return false;
    }

    public String[] proccessGettingDeviceInfo(String deviceId) {
        String[] result = DataManager.getInstance().getDevices().getDeviceInfo(DataManager.getInstance().getDatabaseConnection(), deviceId, null);
        ResultMessenger resultMessenger = new ResultMessenger();

        if (result == null) {
            this.resultMessage = resultMessenger.GETTING_FAILED;
        } else {
            this.resultMessage = resultMessenger.GETTING_SUCCESS;
        }

        return result;
    }

    public boolean saveDeviceInfo(String deviceId, String[] deviceInfo) {
        int result = DataManager.getInstance().getDevices().updateDeviceInfo(DataManager.getInstance().getDatabaseConnection(), deviceId, deviceInfo);
        ResultMessenger resultMessenger = new ResultMessenger();

        if (result == 0) {
            this.resultMessage = resultMessenger.SETTING_FAILED;
            return false;
        }

        this.resultMessage = resultMessenger.SETTING_SUCCESS;
        return true;
    }

    public boolean proccessAddingTemplatesToDevice(String deviceId, String[] templateIds) {
        boolean result = true;
        ResultMessenger resultMessenger = new ResultMessenger();

        for (String templateId : templateIds) {
            if (!DataManager.getInstance().getDevicesAndTemplates().addRelationship(DataManager.getInstance().getDatabaseConnection(), deviceId, templateId)) {
                this.resultMessage = resultMessenger.SETTING_RELATIONSHIP_QUERY_FAILED;
                result = false;
            }
        }
        if (result) {
            this.resultMessage = resultMessenger.SETTING_RELATIONSHIP_SUCCESS;
        }
        
        return result;
    }
    
    public ArrayList<String[]> proccessGettingAddedTemplates(String deviceId) {
        ArrayList<String> addedTemplateIds = DataManager.getInstance().getDevicesAndTemplates().getAddedTemplateIdsOfDevice(DataManager.getInstance().getDatabaseConnection(), deviceId);
        ArrayList<String[]> result = new ArrayList<String[]>();
        
        String[] orders = new String[] {
            DataManager.getInstance().getTemplates().getPrimaryKey().name,
            DataManager.getInstance().getTemplates().getNameColumn().name
        };
        
        int tempSize = addedTemplateIds.size();
        for (int i = 0; i < tempSize; i++) {
            result.addAll(DataManager.getInstance().getTemplates().getTemplateInfo(DataManager.getInstance().getDatabaseConnection(), addedTemplateIds.get(i), orders, false));
        }
        
        if (result.size() != addedTemplateIds.size()) {
            this.resultMessage = new ResultMessenger().GETTING_TEMPLATES_FAILED;
        }
       
        return result;
    }
    
    public boolean proccessDeletingAddedTemplates(String deviceId, String[] templateIds) {
        boolean result = true;
        ResultMessenger resultMessenger = new ResultMessenger();
        
        for (String templateId : templateIds) {
            if (DataManager.getInstance().getDevicesAndTemplates().deleteRelationship(DataManager.getInstance().getDatabaseConnection(), deviceId, templateId) < 0) {
                result = false;
                this.resultMessage = resultMessenger.DELETING_RELATIONSHIP_QUERY_FAILED;
            }
        } 
        
        return result;
    }

    public void processGettingSnmpObjectValues(String ipAddress, String snmpVersion, String community, boolean inTable, ArrayList<String[]> objects) {
        SnmpTarget target = null;        
        if (snmpVersion.equalsIgnoreCase(SNMPVersion.VERSION_2_COMMUNITY)) {
            target = new SimpleSnmpV2cTarget();
            ((SimpleSnmpV2cTarget) target).setAddress(ipAddress);
            ((SimpleSnmpV2cTarget) target).setCommunity(community);
        }
        
        SnmpContext context = SnmpFactory.getInstance().newContext(target, SnmpManager.getInstance().getSystemMib());
        
        //preprocess getting list to add instance id
        int objListSize = objects.size();
        String[] queryObjects = null;
        if (!inTable) {
            queryObjects = new String[objListSize];
                    
            for (int i = 0; i < objListSize; i++) {
                int lastCharCode = (int)objects.get(i)[1].charAt(objects.get(i)[1].length() - 1);
                if (48 > lastCharCode || lastCharCode > 57) {
                    queryObjects[i] = objects.get(i)[1] + ".0";
                } else {
                    queryObjects[i] = objects.get(i)[1];
                }
            }
            
            SnmpCallback<VarbindCollection> callback = new ObjectGettingCallback();
            context.asyncGet(callback, queryObjects);
        }        
    }
    
    public String getResultMessage() {
        return this.resultMessage;
    }

    public class ImportResultMessenger {

        public final String IMPORT_SUCCESS = "Devices were imported successfully";
        public final String ERROR_FILE_NOT_FOUND = "The input file is not found";
        public final String ERROR_IO = "An error happened when importing device info";
        public final String ERROR_NO_CONTENT = "File doesn't contains any information";
    }

    public class DeletionResultMessenger {

        public final String SUCCESS = "Selected devices were removed successfully";
        public final String FAILED = "Some devices could not be removed";
    }

    public class ResultMessenger {

        public final String GETTING_SUCCESS = "Getting info of the device is successed";
        public final String GETTING_FAILED = "Some errors happened when getting info of the device";
        public final String SETTING_FAILED = "Some errors happened when updating device info";
        public final String SETTING_SUCCESS = "Device info was updated successfully";

        public final String SETTING_RELATIONSHIP_QUERY_FAILED = "Some errors happened when saving device-templates relationship to database";
        public final String SETTING_RELATIONSHIP_SUCCESS = "Adding templates to this device was successfull";
        
        public final String GETTING_TEMPLATES_FAILED = "Some errors happened when getting added template information";
        
        public final String DELETING_RELATIONSHIP_QUERY_FAILED = "Some errors happend when deleting device-templates relationship in database";
    }
}
