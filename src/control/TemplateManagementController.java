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
import snmpd.TableGroupingHelper;

/**
 *
 * @author danh.nguyentranbao
 */
public class TemplateManagementController {

    private final int DEFAULT_CSV_VALUE_POS = 1;
    private final int DEFAULT_CSV_TEMPLATE_INFO_COUNT = 3;
    private String resultMessage;

    public String[] processImportTemplate(String fileName) {
        String[] result = null;
        CSVReader reader = null;
        ImportResultMessenger resultMessenger = new ImportResultMessenger();

        try {
            reader = new CSVReader(new FileReader(fileName));
            String[] templateInfo = new String[DEFAULT_CSV_TEMPLATE_INFO_COUNT];

            for (int i = 0; i < DEFAULT_CSV_TEMPLATE_INFO_COUNT; i++) {
                templateInfo[i] = reader.readNext()[DEFAULT_CSV_VALUE_POS];
            }

            ArrayList<String[]> itemList = new ArrayList<String[]>();
            String[] temp = reader.readNext();

            while ((temp = reader.readNext()) != null) {
                itemList.add(temp);
            }

            result = DataManager.getInstance().getTemplates().importTemplate(DataManager.getInstance().getDatabaseConnection(), templateInfo, itemList);
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

        return result;
    }

    public ArrayList<String[]> processInitTemplateList(String[] orders) {
        return DataManager.getInstance().getTemplates().getTemplateList(DataManager.getInstance().getDatabaseConnection(), orders);
    }

    public boolean processTemplatesDeletion(String[] templateIds) {
        DeletionResultMessenger resultMessenger = new DeletionResultMessenger();
        int queryResult = DataManager.getInstance().getTemplates().deleteTemplates(DataManager.getInstance().getDatabaseConnection(), templateIds);

        if (queryResult == templateIds.length) {
            this.resultMessage = resultMessenger.SUCCESS;
            return true;
        }

        this.resultMessage = resultMessenger.FAILED;
        return false;
    }

    public ArrayList<String[]> proccessGettingTemplateInfo(String templateId) {
        //String[] result = DataManager.getInstance().getTemplates().getTemplateInfo(DataManager.getInstance().getDatabaseConnection(), templateLabel, null);
        ArrayList<String[]> result = DataManager.getInstance().getTemplates().getTemplateInfo(DataManager.getInstance().getDatabaseConnection(), templateId, null, true);
        ResultMessenger resultMessenger = new ResultMessenger();

        if (result == null) {
            this.resultMessage = resultMessenger.GETTING_FAILED;
        } else {
            this.resultMessage = resultMessenger.GETTING_SUCCESS;
        }

        return result;
    }

    public boolean saveTemplateInfo(String templateId, ArrayList<String[]> templateInfo) {
        int result = DataManager.getInstance().getTemplates().updateTemplateInfo(DataManager.getInstance().getDatabaseConnection(), templateId, templateInfo);
        ResultMessenger resultMessenger = new ResultMessenger();

        if (result == 0) {
            this.resultMessage = resultMessenger.SETTING_FAILED;
            return false;
        }

        this.resultMessage = resultMessenger.SETTING_SUCCESS;
        return true;
    }

    public ArrayList<ArrayList<String[]>> processBuildingUniqueItemList(String[] templateIds) {
        ArrayList<ArrayList<String[]>> result = new ArrayList<ArrayList<String[]>>();
        int tempSize, resultSize;
        boolean isFound = false;

        for (String templateId : templateIds) {
            ArrayList<String[]> temp = DataManager.getInstance().getTemplateItems().getItemsOfTemplate(
                    DataManager.getInstance().getDatabaseConnection(), templateId,
                    new String[]{"id", "name", "object_id"});

//            System.out.println(temp.get(0)[1]);
            tempSize = temp.size();
            for (int i = 0; i < tempSize; i++) {
                resultSize = result.size();
                isFound = false;
                for (int j = 0; j < resultSize; j++) {
                    if (temp.get(i)[1].equalsIgnoreCase(result.get(j).get(0)[1])
                            && temp.get(i)[2].equalsIgnoreCase(result.get(j).get(0)[2])) {
                        result.get(j).add(temp.get(i));
                        isFound = true;
                        break;
                    }
                }

                if (!isFound) {
                    ArrayList<String[]> newList = new ArrayList<String[]>();
                    newList.add(temp.get(i));
                    result.add(newList);
                }
            }
        }

        return result;
    }

    public ArrayList<ArrayList<String[]>> processGroupingItemsIntoTable(ArrayList<ArrayList<String[]>> itemList) {
        ArrayList<ArrayList<String[]>> result = new ArrayList<ArrayList<String[]>>();
        result.add(new ArrayList<String[]>());
        
        TableGroupingHelper groupingHelper = new TableGroupingHelper();
        ArrayList<String> tableNames = new ArrayList<String>();
        int itemListSize = itemList.size(), tempId;
        String curTableName;

        for (int i = 0; i < itemListSize; i++) {
            curTableName = groupingHelper.getTableNameForItem(itemList.get(i).get(0)[1]);
            if (curTableName != null) {
                tempId = tableNames.indexOf(curTableName);
                if (tempId != -1) {
                    result.get(tempId + 1).add(itemList.get(i).get(0));
                } else {
                    ArrayList<String[]> newTable = new ArrayList<String[]>();
                    newTable.add(itemList.get(i).get(0));
                    result.add(newTable);
                    tableNames.add(curTableName);
                }
            } else {
                result.get(0).add(itemList.get(i).get(0));
            }
        }

        return result;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    public class ImportResultMessenger {

        public final String IMPORT_SUCCESS = "Templates were imported successfully";
        public final String ERROR_FILE_NOT_FOUND = "The input file is not found";
        public final String ERROR_IO = "An error happened when importing template info";
        public final String ERROR_NO_CONTENT = "File doesn't contains any information";
        public final String ERROR_DATABASE = "An error happened when insert data into database";
    }

    public class DeletionResultMessenger {

        public final String SUCCESS = "Selected templates were removed successfully";
        public final String FAILED = "Some templates could not be removed";
    }

    public class ResultMessenger {

        public final String GETTING_SUCCESS = "Getting info of the template is successed";
        public final String GETTING_FAILED = "Some errors happened when getting info of the template";
        public final String SETTING_FAILED = "Some errors happened when updating template info";
        public final String SETTING_SUCCESS = "Template info was updated successfully";
    }

}
