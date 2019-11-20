/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.opencsv.CSVReader;
import data.DataManager;
import gui.ApplicationWindow;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.soulwing.snmp.SimpleSnmpV2cTarget;
import org.soulwing.snmp.SnmpAsyncWalker;
import org.soulwing.snmp.SnmpCallback;
import org.soulwing.snmp.SnmpContext;
import org.soulwing.snmp.SnmpFactory;
import org.soulwing.snmp.SnmpTarget;
import org.soulwing.snmp.VarbindCollection;
import snmpd.ObjectGettingCallback;
import snmpd.ObjectNameHelper;
import snmpd.ObjectWalkingCallback;
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

        String[] orders = new String[]{
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

    public void processGettingSnmpObjectValues(int tableId, String deviceId, String ipAddress, String snmpVersion, String community, boolean inTable, ArrayList<String[]> objects) {
        SnmpTarget target = null;
        if (snmpVersion.equalsIgnoreCase(SNMPVersion.VERSION_2_COMMUNITY)) {
            target = new SimpleSnmpV2cTarget();
            ((SimpleSnmpV2cTarget) target).setAddress(ipAddress);
            ((SimpleSnmpV2cTarget) target).setCommunity(community);
        }

        SnmpContext context = SnmpFactory.getInstance().newContext(target, SnmpManager.getInstance().getSystemMib());

        //preprocess getting list to add instance id
        int objListSize = objects.size();
        String[] queryObjects = new String[objListSize];
        String[] itemIds = new String[objListSize];
        ObjectNameHelper helper = new ObjectNameHelper();

        if (!inTable) {
            for (int i = 0; i < objListSize; i++) {
                queryObjects[i] = helper.normalizeNameToQuery(objects.get(i)[1]);
                itemIds[i] = objects.get(i)[0];
            }

            SnmpCallback<VarbindCollection> getCallback = new ObjectGettingCallback(
                    tableId, deviceId, itemIds, queryObjects);
            context.asyncGet(getCallback, queryObjects);
        } else {
            for (int i = 0; i < objListSize; i++) {
                queryObjects[i] = objects.get(i)[1];
                itemIds[i] = objects.get(i)[0];
            }

            SnmpCallback<SnmpAsyncWalker<VarbindCollection>> walkCallback = new ObjectWalkingCallback(
                    tableId, deviceId, itemIds, queryObjects);
            context.asyncWalk(walkCallback, queryObjects);
        }
    }

    public synchronized void processReceivedDeviceData(int tableId, String deviceId, String[] itemIds, String[] queryObjects, VarbindCollection varbinds) {
        int itemListSize = itemIds.length;
        ObjectNameHelper helper = new ObjectNameHelper();
        ArrayList<ArrayList<String[]>> uniqueItems = ApplicationWindow
                .getInstance().getPanelMain().getPanelMonitorDevice().getUniqueItems();
        int uniqueItemsSize = uniqueItems.size();
        ArrayList<String[]> viewDataList = new ArrayList<String[]>();

        for (int i = 0; i < itemListSize; i++) {
            String[] dataToView = new String[4];
            dataToView[0] = queryObjects[i];
            dataToView[2] = varbinds.get(queryObjects[i]).toString();

            for (int j = 0; j < uniqueItemsSize; j++) {
                if (uniqueItems.get(j).get(0)[0].equalsIgnoreCase(itemIds[i])) {
                    String instanceId = "";
                    if (uniqueItems.get(j).get(0)[1].equalsIgnoreCase(queryObjects[i])) {
                        dataToView[1] = uniqueItems.get(j).get(0)[2];
                    } else {
                        instanceId = helper.denormalizeQueryName(queryObjects[i])[1];
                        dataToView[1] = uniqueItems.get(j).get(0)[2] + "." + instanceId;
                    }
                    dataToView[3] = DataManager.getInstance().getTemplateItemValues().insertTemplateItemValue(
                            DataManager.getInstance().getDatabaseConnection(), itemIds[i], instanceId, deviceId, dataToView[2]);

                    int tempSize = uniqueItems.get(j).size();
                    for (int k = 1; k < tempSize; k++) {
                        DataManager.getInstance().getTemplateItemValues().insertTemplateItemValue(
                                DataManager.getInstance().getDatabaseConnection(),
                                uniqueItems.get(j).get(k)[0],
                                instanceId,
                                deviceId,
                                dataToView[2],
                                dataToView[3]);
                    }
                    break;
                }
            }

            viewDataList.add(dataToView);
        }
        ApplicationWindow.getInstance().getPanelMain().getPanelMonitorDevice().updateDataToTable(tableId, viewDataList);
    }

    public synchronized void processReceivedDeviceData(int tableId, String deviceId, String[] itemIds, String[] queryObjects, ArrayList<VarbindCollection> varbindColList) {
        //int itemListSize = itemIds.length;
        ObjectNameHelper helper = new ObjectNameHelper();
        ArrayList<ArrayList<String[]>> uniqueItems = ApplicationWindow
                .getInstance().getPanelMain().getPanelMonitorDevice().getUniqueItems();
        int uniqueItemsSize = uniqueItems.size();
        int varbindListSize = varbindColList.size();
        ArrayList<String[]> viewDataList = new ArrayList<String[]>();

        for (int i = 0; i < varbindListSize; i++) {
            String[] dataToView = new String[itemIds.length + 2];
            dataToView[0] = String.valueOf(i + 1);
            SimpleDateFormat dateFormatter = new SimpleDateFormat();
            dataToView[itemIds.length + 1] = dateFormatter.format(new Date());

            for (int j = 0; j < itemIds.length; j++) {
                dataToView[j + 1] = varbindColList.get(i).get(queryObjects[j]).asString();
                for (int k = 0; k < uniqueItemsSize; k++) {
                    if (uniqueItems.get(k).get(0)[0].equalsIgnoreCase(itemIds[j])) {
                        int tempSize = uniqueItems.get(k).size();
                        for (int m = 0; m < tempSize; m++) {
                            DataManager.getInstance().getTemplateItemValues().insertTemplateItemValue(
                                    DataManager.getInstance().getDatabaseConnection(),
                                    uniqueItems.get(k).get(m)[0],
                                    dataToView[0],
                                    deviceId,
                                    dataToView[j + 1],
                                    dataToView[itemIds.length + 1]);
                        }
                        break;
                    }                    
                }
            }
            
            viewDataList.add(dataToView);
        }

        ApplicationWindow.getInstance().getPanelMain().getPanelMonitorDevice().updateDataToTable(tableId, viewDataList);
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
