/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.DeviceManagementController;
import data.models.Devices;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cloud
 */
public class PanelDevicesList extends javax.swing.JPanel {

    private String[] colNames = {"Name", "Type", "Description", "Label", "SNMP Version", "IP Address", "Community", "State", "Imported Date", "Last Access"};
    private final int STATE_COL_ID = 7;
    private final int LABEL_COL_ID = 3;

    private javax.swing.JButton buttonImport;
    private javax.swing.JLabel labelDisabled;
    private javax.swing.JLabel labelDisabledValue;
    private javax.swing.JLabel labelEnabled;
    private javax.swing.JLabel labelEnabledValue;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel labelTotalValue;
    private javax.swing.JScrollPane scrollpanelDevices;
    private javax.swing.JTable tableDevices;

    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem mitemCheck;
    private javax.swing.JMenuItem mitemDelete;

    private java.awt.event.ActionListener listenerButton;
    private java.awt.event.ActionListener listenerMenuItem;

    private MouseAdapter mlistenerTable;
    private int counterRowClicking;

    public PanelDevicesList() {
        initComponents();
        initViewData();
        initListeners();

        this.counterRowClicking = 0;
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
        labelTotalValue.setText("0");
        add(labelTotalValue);
        labelTotalValue.setBounds(210, 30, 30, 19);

        labelEnabled.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelEnabled.setText("Enabled:");
        add(labelEnabled);
        labelEnabled.setBounds(270, 30, 60, 19);

        labelEnabledValue.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelEnabledValue.setText("0");
        add(labelEnabledValue);
        labelEnabledValue.setBounds(330, 30, 40, 19);

        labelDisabled.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelDisabled.setText("Disabled:");
        add(labelDisabled);
        labelDisabled.setBounds(380, 30, 60, 19);

        labelDisabledValue.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelDisabledValue.setText("0");
        add(labelDisabledValue);
        labelDisabledValue.setBounds(440, 30, 40, 19);

        buttonImport.setText("Import");
        buttonImport.setMaximumSize(new java.awt.Dimension(80, 30));
        buttonImport.setMinimumSize(new java.awt.Dimension(80, 30));
        buttonImport.setPreferredSize(new java.awt.Dimension(80, 30));
        add(buttonImport);
        buttonImport.setBounds(1060, 30, 65, 23);

        tableDevices.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                this.colNames
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });
        scrollpanelDevices.setViewportView(tableDevices);

        add(scrollpanelDevices);
        scrollpanelDevices.setBounds(60, 70, 1380, 770);

        initMenus();
    }

    private void initMenus() {
        this.popupMenu = new javax.swing.JPopupMenu();
        this.mitemCheck = new javax.swing.JMenuItem("Check");
        this.mitemDelete = new javax.swing.JMenuItem("Delete");

        this.popupMenu.add(mitemCheck);
        this.popupMenu.add(mitemDelete);

        this.tableDevices.setComponentPopupMenu(this.popupMenu);
    }

    private void initListeners() {
        this.listenerButton = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JButton source = (javax.swing.JButton) e.getSource();
                if (source == buttonImport) {
                    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "csv");
                    int result = fileChooser.showOpenDialog(null);

                    if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                        DeviceManagementController controller = new DeviceManagementController();
                        ArrayList<String[]> deviceInfo = controller.processImportDevices(fileChooser.getSelectedFile().getAbsolutePath());
                        PanelDevicesList.this.updateViewData(deviceInfo);
                    }
                }
            }
        };
        this.buttonImport.addActionListener(this.listenerButton);

        this.listenerMenuItem = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JMenuItem source = (javax.swing.JMenuItem) e.getSource();
                if (source == mitemDelete) {
                    int[] selectedRows = tableDevices.getSelectedRows();
                    if (selectedRows != null) {
                        String[] selectedDevices = new String[selectedRows.length];
                        for (int i = 0; i < selectedRows.length; i++) {
                            selectedDevices[i] = (String) tableDevices.getValueAt(selectedRows[i], LABEL_COL_ID);
                        }

                        DeviceManagementController controller = new DeviceManagementController();
                        controller.processDevicesDeletion(selectedDevices);

                        PanelDevicesList.this.initViewData();
                        JOptionPane.showMessageDialog(null, controller.getResultMessage());
                    }
                }
                if (source == mitemCheck) {

                }
            }
        };
        this.mitemCheck.addActionListener(listenerMenuItem);
        this.mitemDelete.addActionListener(listenerMenuItem);

        this.mlistenerTable = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                counterRowClicking++;
                if (counterRowClicking > 2) {
                    counterRowClicking = 0;
                    ApplicationWindow.getInstance().getPanelMain().switchPanelForDisplayDetail(PanelMain.PANEL_ID.PANEL_DEVICE_INFO);
                    ApplicationWindow.getInstance().getPanelMain()
                            .getPanelDeviceInfo().initViewData(String.valueOf(tableDevices.getValueAt(tableDevices.getSelectedRow(), LABEL_COL_ID)));
                }
            }
        };
        this.tableDevices.addMouseListener(this.mlistenerTable);
    }
    
    private void initViewData() {
        DefaultTableModel tableModel = (DefaultTableModel) this.tableDevices.getModel();
        for (int i = tableModel.getRowCount(); i > 0; i--) {
            tableModel.removeRow(i - 1);
        }

        this.labelDisabledValue.setText("0");
        this.labelEnabledValue.setText("0");
        this.labelTotalValue.setText("0");

        DeviceManagementController controller = new DeviceManagementController();
        ArrayList<String[]> deviceList = controller.processInitDeviceList();

        this.updateViewData(deviceList);
    }

    public void refreshViewData() {
        this.initViewData();
    }
    
    private void showPopup(int x, int y) {
        this.popupMenu.show(this, x, y);
    }

    private void updateViewData(ArrayList<String[]> deviceInfo) {
        int listSize = deviceInfo.size();
        DefaultTableModel tableModel = (DefaultTableModel) this.tableDevices.getModel();
        for (int i = 0; i < listSize; i++) {
            tableModel.addRow(deviceInfo.get(i));
        }

        this.labelTotalValue.setText(String.valueOf(Integer.parseInt(this.labelTotalValue.getText()) + deviceInfo.size()));

        int enabledCount = Integer.parseInt(this.labelEnabledValue.getText());
        int disabledCount = Integer.parseInt(this.labelDisabledValue.getText());

        for (int i = 0; i < listSize; i++) {
            if (deviceInfo.get(i)[STATE_COL_ID].equals(Devices.DeviceState.DISABLED)) {
                disabledCount++;
            } else {
                enabledCount++;
            }
        }

        this.labelEnabledValue.setText(String.valueOf(enabledCount));
        this.labelDisabledValue.setText(String.valueOf(disabledCount));

        this.revalidate();
        this.repaint();
    }

}