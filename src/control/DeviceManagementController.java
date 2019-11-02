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

    public ArrayList<String[]> processInitData() {
        return DataManager.getInstance().getDevices().getDeviceList(DataManager.getInstance().getDatabaseConnection(), null);
    }

    public boolean processDevicesDeletion(String[] devices) {
        DeletionResultMessenger resultMessenger = new DeletionResultMessenger();
        int queryResult = DataManager.getInstance().getDevices().deleteDeivices(DataManager.getInstance().getDatabaseConnection(), devices);

        if (queryResult == devices.length) {
            this.resultMessage = resultMessenger.SUCCESS;
            return true;
        }
        
        this.resultMessage = resultMessenger.FAILED;
        return false;
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
}
