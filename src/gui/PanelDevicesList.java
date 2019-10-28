/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.DeviceManagementController;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author cloud
 */
public class PanelDevicesList extends javax.swing.JPanel {
    private String[] colNames = {"Name", "Type", "Description", "Label", "State", "Imported Date", "Last Access"};
    
    private javax.swing.JButton buttonImport;
    private javax.swing.JLabel labelDisabled;
    private javax.swing.JLabel labelDisabledValue;
    private javax.swing.JLabel labelEnabled;
    private javax.swing.JLabel labelEnabledValue;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel labelTotalValue;
    private javax.swing.JScrollPane scrollpanelDevices;
    private javax.swing.JTable tableDevices;
    
    private java.awt.event.ActionListener listenerButton;
    
    public PanelDevicesList() {
        initComponents();
        initListeners();
    }

    private void initComponents() {

        labelTotal = new javax.swing.JLabel();
        labelTotalValue = new javax.swing.JLabel();
        labelEnabled = new javax.swing.JLabel();
        labelEnabledValue = new javax.swing.JLabel();
        labelDisabled = new javax.swing.JLabel();
        labelDisabledValue = new javax.swing.JLabel();
        buttonImport = new javax.swing.JButton();
        scrollpanelDevices = new javax.swing.JScrollPane();
        tableDevices = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(1500, 900));
        setMinimumSize(new java.awt.Dimension(1500, 900));
        setPreferredSize(new java.awt.Dimension(1500, 900));
        setLayout(null);

        labelTotal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelTotal.setText("Total:");
        add(labelTotal);
        labelTotal.setBounds(170, 30, 40, 19);

        labelTotalValue.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelTotalValue.setText("...");
        add(labelTotalValue);
        labelTotalValue.setBounds(210, 30, 30, 19);

        labelEnabled.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelEnabled.setText("Enabled:");
        add(labelEnabled);
        labelEnabled.setBounds(270, 30, 60, 19);

        labelEnabledValue.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelEnabledValue.setText("...");
        add(labelEnabledValue);
        labelEnabledValue.setBounds(330, 30, 40, 19);

        labelDisabled.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelDisabled.setText("Disabled:");
        add(labelDisabled);
        labelDisabled.setBounds(380, 30, 60, 19);

        labelDisabledValue.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelDisabledValue.setText("...");
        add(labelDisabledValue);
        labelDisabledValue.setBounds(440, 30, 40, 19);

        buttonImport.setText("Import");
        buttonImport.setMaximumSize(new java.awt.Dimension(80, 30));
        buttonImport.setMinimumSize(new java.awt.Dimension(80, 30));
        buttonImport.setPreferredSize(new java.awt.Dimension(80, 30));
        add(buttonImport);
        buttonImport.setBounds(1060, 30, 65, 23);

        tableDevices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            this.colNames
        ));
        scrollpanelDevices.setViewportView(tableDevices);

        add(scrollpanelDevices);
        scrollpanelDevices.setBounds(60, 70, 1380, 770);
    }
    
    private void initListeners() {
        this.listenerButton = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JButton source = (javax.swing.JButton) e.getSource();
                if (source == buttonImport) {
                    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("csv");
                    int result = fileChooser.showOpenDialog(null);
                    
                    if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                        DeviceManagementController controller = new DeviceManagementController();
                        ArrayList<String[]> deviceInfo = controller.processImportDevices(fileChooser.getSelectedFile().getAbsolutePath());
                        PanelDevicesList.this.updateDeviceTable(deviceInfo);
                    }
                }
            }
        };
        
        this.buttonImport.addActionListener(this.listenerButton);
    }
    
    private void updateDeviceTable(ArrayList<String[]> deviceInfo) {
        
    }
}
