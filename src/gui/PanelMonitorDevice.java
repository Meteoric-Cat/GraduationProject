/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.DeviceManagementController;
import control.TemplateManagementController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author danh.nguyentranbao
 */
public class PanelMonitorDevice extends JPanel {

    //object id is item id + instance id, object name equals to object name + instance id
    private String[] defaultColNames = {"Object Name", "Object Id", "Value", "Updated Time"};
    private String[] currentColNames = null;
    private ArrayList<ArrayList<String[]>> uniqueItems;     //each array is a list of similar items.
    //the first array is an array of unique items that will be displayed in default table model
    private ArrayList<ArrayList<String[]>> tableModelItems;
    private String community;
    private String deviceId;
    private int currentTable;

    private JButton buttonNextTable;
    private JButton buttonPreviousTable;
    private JButton buttonStart;
    private JButton buttonStop;
    private JLabel labelDevice;
    private JLabel labelDeviceValue;
    private JLabel labelIPAddress;
    private JLabel labelIPAddressValue;
    private JLabel labelPeriods;
    private JLabel labelSNMPVersion;
    private JLabel labelSNMPVersionValue;
    private JScrollPane scrollpaneItemValues;
    private JTable tableItemValues;
    private JTextField tfieldPeriod;

    private ActionListener listenerButton;

    public PanelMonitorDevice() {
        initComponents();
        initListeners();
    }

    private void initComponents() {

        labelDevice = new javax.swing.JLabel();
        labelDeviceValue = new javax.swing.JLabel();
        labelIPAddress = new javax.swing.JLabel();
        labelIPAddressValue = new javax.swing.JLabel();
        labelSNMPVersion = new javax.swing.JLabel();
        labelPeriods = new javax.swing.JLabel();
        labelSNMPVersionValue = new javax.swing.JLabel();
        tfieldPeriod = new javax.swing.JTextField();
        scrollpaneItemValues = new javax.swing.JScrollPane();
        tableItemValues = new javax.swing.JTable();
        buttonStart = new javax.swing.JButton();
        buttonStop = new javax.swing.JButton();
        buttonPreviousTable = new javax.swing.JButton();
        buttonNextTable = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1500, 900));
        setLayout(null);

        labelDevice.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelDevice.setText("Device:");
        add(labelDevice);
        labelDevice.setBounds(70, 60, 40, 16);

        labelDeviceValue.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelDeviceValue.setText(". . .");
        add(labelDeviceValue);
        labelDeviceValue.setBounds(120, 60, 18, 14);

        labelIPAddress.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelIPAddress.setText("IP Address:");
        add(labelIPAddress);
        labelIPAddress.setBounds(240, 60, 63, 16);

        labelIPAddressValue.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelIPAddressValue.setText(". . .");
        add(labelIPAddressValue);
        labelIPAddressValue.setBounds(310, 60, 18, 14);

        labelSNMPVersion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelSNMPVersion.setText("SNMP Version:");
        add(labelSNMPVersion);
        labelSNMPVersion.setBounds(410, 60, 82, 16);

        labelPeriods.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelPeriods.setText("Period (s):");
        add(labelPeriods);
        labelPeriods.setBounds(730, 60, 57, 16);

        labelSNMPVersionValue.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelSNMPVersionValue.setText(". . .");
        add(labelSNMPVersionValue);
        labelSNMPVersionValue.setBounds(500, 60, 18, 14);

        tfieldPeriod.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        add(tfieldPeriod);
        tfieldPeriod.setBounds(790, 60, 64, 22);

        tableItemValues.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                this.defaultColNames
        ));
        scrollpaneItemValues.setViewportView(tableItemValues);

        add(scrollpaneItemValues);
        scrollpaneItemValues.setBounds(72, 111, 1347, 716);

        buttonStart.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        buttonStart.setText("Start");
        buttonStart.setMaximumSize(new java.awt.Dimension(60, 30));
        buttonStart.setMinimumSize(new java.awt.Dimension(60, 30));
        buttonStart.setPreferredSize(new java.awt.Dimension(60, 30));
        add(buttonStart);
        buttonStart.setBounds(870, 55, 60, 30);

        buttonStop.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        buttonStop.setText("Stop");
        buttonStop.setMaximumSize(new java.awt.Dimension(60, 30));
        buttonStop.setMinimumSize(new java.awt.Dimension(60, 30));
        buttonStop.setPreferredSize(new java.awt.Dimension(60, 30));
        add(buttonStop);
        buttonStop.setBounds(940, 55, 60, 30);

        buttonPreviousTable.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        buttonPreviousTable.setText("Previous");
        buttonPreviousTable.setMaximumSize(new java.awt.Dimension(75, 25));
        buttonPreviousTable.setMinimumSize(new java.awt.Dimension(75, 25));
        buttonPreviousTable.setPreferredSize(new java.awt.Dimension(75, 25));
        add(buttonPreviousTable);
        buttonPreviousTable.setBounds(1250, 850, 75, 25);

        buttonNextTable.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        buttonNextTable.setText("Next");
        buttonNextTable.setMaximumSize(new java.awt.Dimension(75, 25));
        buttonNextTable.setMinimumSize(new java.awt.Dimension(75, 25));
        buttonNextTable.setPreferredSize(new java.awt.Dimension(75, 25));
        add(buttonNextTable);
        buttonNextTable.setBounds(1340, 850, 75, 25);
    }

    public void initViewData(String deviceId, String device, String ipAddress, String snmpVersion, String community, String[] templateIds) {
        this.labelDeviceValue.setText(device);
        this.labelIPAddressValue.setText(ipAddress);
        this.labelSNMPVersionValue.setText(snmpVersion);

        this.deviceId = deviceId;
        this.community = community;

        TemplateManagementController templateController = new TemplateManagementController();
        this.uniqueItems = templateController.processBuildingUniqueItemList(templateIds);
        this.tableModelItems = templateController.processGroupingItemsIntoTable(this.uniqueItems);

        this.currentTable = 0;
        this.currentColNames = this.defaultColNames;
        DeviceManagementController deviceController = new DeviceManagementController();
        deviceController.processGettingSnmpObjectValues(
                this.currentTable, deviceId, ipAddress, snmpVersion, community, false, this.tableModelItems.get(this.currentTable));
    }

    private void initListeners() {
        this.listenerButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                if (source == buttonStart) {
                    DeviceManagementController controller = new DeviceManagementController();
                    boolean inTable = (currentTable == 0) ? false : true;

                    controller.processStartingTimerToGetDeviceData(Integer.parseInt(tfieldPeriod.getText()),
                            currentTable,
                            deviceId,
                            labelIPAddressValue.getText(),
                            labelSNMPVersionValue.getText(),
                            community,
                            inTable,
                            tableModelItems.get(currentTable));
                }
                if (source == buttonStop) {
                    DeviceManagementController controller = new DeviceManagementController();;
                    controller.processCancelingGettingTimer();
                }
                if (source == buttonPreviousTable) {
                    PanelMonitorDevice.this.switchTable(true);
                }
                if (source == buttonNextTable) {
                    PanelMonitorDevice.this.switchTable(false);
                }
            }
        };

        this.buttonStart.addActionListener(this.listenerButton);
        this.buttonStop.addActionListener(this.listenerButton);
        this.buttonNextTable.addActionListener(this.listenerButton);
        this.buttonPreviousTable.addActionListener(this.listenerButton);
    }

    public synchronized void updateDataToTable(int tableId, ArrayList<String[]> deviceData) {
        if (tableId != this.currentTable) {
            //System.out.println(1);
            return;
        }

        if (deviceData == null || deviceData.size() == 0) {
            //System.out.println(2);
            return;
        }

//        if (deviceData != null && deviceData.get(0) != null) {
//            System.out.println(deviceData.get(0).length);
//        }
//        if (deviceData.get(0).length != this.currentColNames.length) {
//            System.out.println(3);
//            return;
//        }
        DefaultTableModel tableModel = (DefaultTableModel) this.tableItemValues.getModel();
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount; i >= 1; i--) {
            tableModel.removeRow(i - 1);
        }
        
        int dataSize = deviceData.size();
        for (int i = 0; i < dataSize; i++) {
            tableModel.addRow(deviceData.get(i));
        }

        this.revalidate();
        this.repaint();
    }

    public void switchTable(boolean previous) {
        //System.out.println(previous);
        if (previous && this.currentTable > 0) {
            this.currentTable--;
        } else if (this.currentTable < this.tableModelItems.size() - 1) {
            this.currentTable++;
        }

        int tempSize = this.tableModelItems.get(this.currentTable).size();
        String[] newHeaders = new String[tempSize + 2];
        newHeaders[0] = "Id";
        for (int i = 0; i < tempSize; i++) {
            newHeaders[i + 1] = this.tableModelItems.get(this.currentTable).get(i)[1];
        }
        newHeaders[tempSize + 1] = "Updated Time";
        this.currentColNames = newHeaders;

        DefaultTableModel tableModel = new DefaultTableModel(
                new String[][]{},
                newHeaders
        );
        this.tableItemValues.setModel(tableModel);

        boolean inTable = (this.currentTable == 0) ? false : true;
        DeviceManagementController deviceController = new DeviceManagementController();
        deviceController.processCancelingGettingTimer();
        deviceController.processGettingSnmpObjectValues(this.currentTable,
                this.deviceId,
                this.labelIPAddressValue.getText(),
                this.labelSNMPVersionValue.getText(),
                this.community,
                inTable,
                this.tableModelItems.get(this.currentTable));

        this.repaint();
        this.revalidate();
    }

    public ArrayList<ArrayList<String[]>> getUniqueItems() {
        return this.uniqueItems;
    }

    public int getCurrentTable() {
        return this.currentTable;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (!enabled) {
            DeviceManagementController controller = new DeviceManagementController();
            controller.processCancelingGettingTimer();
        }
    }
}
