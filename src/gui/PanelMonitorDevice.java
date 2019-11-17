/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import control.DeviceManagementController;
import control.TemplateManagementController;
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
public class PanelMonitorDevice extends JPanel{
    //object is item id + instance id
    private String[] defaultColNames = {"Object Name", "Object Id", "Value", "Updated Time"};    
    private ArrayList<ArrayList<String[]>> uniqueItems;     //each array is a list of similar items.
    //the first array is an array of unique items that will be displayed in default table model
    private ArrayList<ArrayList<String[]>> tableModelItems;
    private String community;
    
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
    }

    private void initComponents() {
        
        labelDevice = new JLabel();
        labelDeviceValue = new JLabel();
        labelIPAddress = new JLabel();
        labelIPAddressValue = new JLabel();
        labelSNMPVersion = new JLabel();
        labelPeriods = new JLabel();
        labelSNMPVersionValue = new JLabel();
        tfieldPeriod = new JTextField();
        scrollpaneItemValues = new JScrollPane();
        tableItemValues = new JTable();
        buttonStart = new JButton();
        buttonStop = new JButton();

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

        tableItemValues.setModel(new DefaultTableModel(
            new Object [][] {
            },
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
    
    }
    
    public void initViewData(String device, String ipAddress, String snmpVersion, String community, String[] templateIds) {
        this.labelDeviceValue.setText(device);
        this.labelIPAddressValue.setText(ipAddress);
        this.labelSNMPVersionValue.setText(snmpVersion);
        this.community = community;
        
        TemplateManagementController templateController = new TemplateManagementController();        
        this.uniqueItems = templateController.processBuildingUniqueItemList(templateIds);
        this.tableModelItems = templateController.processGroupingItemsIntoTable(this.uniqueItems);        
        
        DeviceManagementController deviceController = new DeviceManagementController();
        
    }
}
