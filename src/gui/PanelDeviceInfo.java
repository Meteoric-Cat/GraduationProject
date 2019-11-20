/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.DeviceManagementController;
import data.models.Devices;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author cloud
 */
public class PanelDeviceInfo extends JPanel {

    private final String[] DEVICE_TYPE_LIST = new String[]{Devices.DeviceType.SWITCH, Devices.DeviceType.ROUTER, Devices.DeviceType.PC};

    private JButton buttonAddTemplates;
    private JButton buttonCancelInfo;
    private JButton buttonCancelTemplates;
    private JButton buttonMonitor;
    private JButton buttonSaveInfo;
    private JComboBox<String> cboxType;
    private JLabel labelCommunity;
    private JLabel labelDescription;
    private JLabel labelIPAddress;
    private JLabel labelImportedTime;
    private JLabel labelImportedTimeValue;
    private JLabel labelLabel;
    private JLabel labelLastAccess;
    private JLabel labelLastAccessValue;
    private JLabel labelName;
    private JLabel labelSNMPVersion;
    private JLabel labelState;
    private JLabel labelStateValue;
    private JLabel labelTemplates;
    private JLabel labelType;
    private JList<String> listTemplates;
    private JScrollPane scrollpaneDescription;
    private JScrollPane scrollpaneTemplates;
    private JTextArea tareaDescription;
    private JTextField tfieldCommunity;
    private JTextField tfieldIPAddress;
    private JTextField tfieldLabel;
    private JTextField tfieldName;
    private JTextField tfieldSNMPVersion;

    private JPopupMenu pmenuTemplates;
    private JMenuItem mitemDelete;

    private DialogAddTemplates dialogAddTemplates;

    private ActionListener listenerButton;
    private ActionListener listenerMenuItem;

    private String currentDeviceId;
    private ArrayList<String> addedTemplateIds;

    public PanelDeviceInfo() {
        initComponents();
        initListeners();

        this.addedTemplateIds = new ArrayList<String>();
    }

    private void initComponents() {

        labelName = new JLabel();
        labelType = new JLabel();
        labelDescription = new JLabel();
        labelLabel = new JLabel();
        labelSNMPVersion = new JLabel();
        labelIPAddress = new JLabel();
        labelCommunity = new JLabel();
        labelState = new JLabel();
        labelImportedTime = new JLabel();
        labelLastAccess = new JLabel();
        tfieldName = new JTextField();
        cboxType = new JComboBox<>();
        tfieldLabel = new JTextField();
        tfieldSNMPVersion = new JTextField();
        tfieldIPAddress = new JTextField();
        tfieldCommunity = new JTextField();
        labelStateValue = new JLabel();
        labelImportedTimeValue = new JLabel();
        labelLastAccessValue = new JLabel();
        buttonSaveInfo = new JButton();
        buttonCancelInfo = new JButton();
        scrollpaneDescription = new JScrollPane();
        tareaDescription = new JTextArea();
        scrollpaneTemplates = new JScrollPane();
        listTemplates = new JList<>();
        labelTemplates = new JLabel();
        buttonAddTemplates = new JButton();
        buttonCancelTemplates = new JButton();
        buttonMonitor = new JButton();

        setMaximumSize(new java.awt.Dimension(1500, 900));
        setMinimumSize(new java.awt.Dimension(1500, 900));
        setLayout(null);

        labelName.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        labelName.setText("Name:");
        labelName.setMaximumSize(new java.awt.Dimension(120, 20));
        labelName.setMinimumSize(new java.awt.Dimension(120, 20));
        labelName.setPreferredSize(new java.awt.Dimension(120, 20));
        add(labelName);
        labelName.setBounds(220, 180, 120, 20);

        labelType.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        labelType.setText("Type:");
        labelType.setMaximumSize(new java.awt.Dimension(120, 20));
        labelType.setMinimumSize(new java.awt.Dimension(120, 20));
        labelType.setPreferredSize(new java.awt.Dimension(120, 20));
        add(labelType);
        labelType.setBounds(220, 230, 120, 20);

        labelDescription.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        labelDescription.setText("Description:");
        labelDescription.setMaximumSize(new java.awt.Dimension(120, 20));
        labelDescription.setMinimumSize(new java.awt.Dimension(120, 20));
        labelDescription.setPreferredSize(new java.awt.Dimension(120, 20));
        add(labelDescription);
        labelDescription.setBounds(220, 280, 120, 20);

        labelLabel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        labelLabel.setText("Label:");
        labelLabel.setMaximumSize(new java.awt.Dimension(120, 20));
        labelLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        labelLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        add(labelLabel);
        labelLabel.setBounds(220, 420, 120, 20);

        labelSNMPVersion.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        labelSNMPVersion.setText("SNMP Version:");
        labelSNMPVersion.setMaximumSize(new java.awt.Dimension(120, 20));
        labelSNMPVersion.setMinimumSize(new java.awt.Dimension(120, 20));
        labelSNMPVersion.setPreferredSize(new java.awt.Dimension(120, 20));
        add(labelSNMPVersion);
        labelSNMPVersion.setBounds(220, 470, 120, 20);

        labelIPAddress.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        labelIPAddress.setText("IP Address:");
        labelIPAddress.setMaximumSize(new java.awt.Dimension(120, 20));
        labelIPAddress.setMinimumSize(new java.awt.Dimension(120, 20));
        labelIPAddress.setPreferredSize(new java.awt.Dimension(120, 20));
        add(labelIPAddress);
        labelIPAddress.setBounds(220, 520, 120, 20);

        labelCommunity.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        labelCommunity.setText("Community:");
        labelCommunity.setMaximumSize(new java.awt.Dimension(120, 20));
        labelCommunity.setMinimumSize(new java.awt.Dimension(120, 20));
        labelCommunity.setPreferredSize(new java.awt.Dimension(120, 20));
        add(labelCommunity);
        labelCommunity.setBounds(220, 570, 120, 20);

        labelState.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        labelState.setText("State:");
        labelState.setMaximumSize(new java.awt.Dimension(120, 20));
        labelState.setMinimumSize(new java.awt.Dimension(120, 20));
        labelState.setPreferredSize(new java.awt.Dimension(120, 20));
        add(labelState);
        labelState.setBounds(220, 620, 120, 20);

        labelImportedTime.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        labelImportedTime.setText("Imported Time:");
        labelImportedTime.setMaximumSize(new java.awt.Dimension(120, 20));
        labelImportedTime.setMinimumSize(new java.awt.Dimension(120, 20));
        labelImportedTime.setPreferredSize(new java.awt.Dimension(120, 20));
        add(labelImportedTime);
        labelImportedTime.setBounds(220, 670, 120, 20);

        labelLastAccess.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        labelLastAccess.setText("Last Access:");
        labelLastAccess.setMaximumSize(new java.awt.Dimension(120, 20));
        labelLastAccess.setMinimumSize(new java.awt.Dimension(120, 20));
        labelLastAccess.setPreferredSize(new java.awt.Dimension(120, 20));
        add(labelLastAccess);
        labelLastAccess.setBounds(220, 710, 120, 20);

        tfieldName.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tfieldName.setMaximumSize(new java.awt.Dimension(300, 30));
        tfieldName.setMinimumSize(new java.awt.Dimension(300, 30));
        tfieldName.setPreferredSize(new java.awt.Dimension(300, 30));
        add(tfieldName);
        tfieldName.setBounds(360, 180, 300, 30);

        cboxType.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cboxType.setModel(new DefaultComboBoxModel<>(this.DEVICE_TYPE_LIST));
        add(cboxType);
        cboxType.setBounds(360, 230, 150, 27);

        tfieldLabel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tfieldLabel.setMaximumSize(new java.awt.Dimension(300, 30));
        tfieldLabel.setMinimumSize(new java.awt.Dimension(300, 30));
        tfieldLabel.setPreferredSize(new java.awt.Dimension(300, 30));
        add(tfieldLabel);
        tfieldLabel.setBounds(360, 420, 300, 30);

        tfieldSNMPVersion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tfieldSNMPVersion.setMaximumSize(new java.awt.Dimension(300, 30));
        tfieldSNMPVersion.setMinimumSize(new java.awt.Dimension(300, 30));
        tfieldSNMPVersion.setPreferredSize(new java.awt.Dimension(300, 30));
        add(tfieldSNMPVersion);
        tfieldSNMPVersion.setBounds(360, 470, 300, 30);

        tfieldIPAddress.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tfieldIPAddress.setMaximumSize(new java.awt.Dimension(300, 30));
        tfieldIPAddress.setMinimumSize(new java.awt.Dimension(300, 30));
        tfieldIPAddress.setPreferredSize(new java.awt.Dimension(300, 30));
        add(tfieldIPAddress);
        tfieldIPAddress.setBounds(360, 520, 300, 30);

        tfieldCommunity.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tfieldCommunity.setMaximumSize(new java.awt.Dimension(300, 30));
        tfieldCommunity.setMinimumSize(new java.awt.Dimension(300, 30));
        tfieldCommunity.setPreferredSize(new java.awt.Dimension(300, 30));
        add(tfieldCommunity);
        tfieldCommunity.setBounds(360, 570, 300, 30);

        labelStateValue.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelStateValue.setText(". . .");
        labelStateValue.setMaximumSize(new java.awt.Dimension(300, 30));
        labelStateValue.setMinimumSize(new java.awt.Dimension(300, 30));
        labelStateValue.setPreferredSize(new java.awt.Dimension(300, 30));
        add(labelStateValue);
        labelStateValue.setBounds(360, 620, 300, 30);

        labelImportedTimeValue.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelImportedTimeValue.setText(". . .");
        labelImportedTimeValue.setMaximumSize(new java.awt.Dimension(300, 30));
        labelImportedTimeValue.setMinimumSize(new java.awt.Dimension(300, 30));
        labelImportedTimeValue.setPreferredSize(new java.awt.Dimension(300, 30));
        add(labelImportedTimeValue);
        labelImportedTimeValue.setBounds(360, 670, 300, 30);

        labelLastAccessValue.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelLastAccessValue.setText(". . .");
        labelLastAccessValue.setMaximumSize(new java.awt.Dimension(300, 30));
        labelLastAccessValue.setMinimumSize(new java.awt.Dimension(300, 30));
        labelLastAccessValue.setPreferredSize(new java.awt.Dimension(300, 30));
        add(labelLastAccessValue);
        labelLastAccessValue.setBounds(360, 710, 300, 30);

        buttonSaveInfo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonSaveInfo.setText("Save");
        buttonSaveInfo.setMaximumSize(new java.awt.Dimension(100, 40));
        buttonSaveInfo.setMinimumSize(new java.awt.Dimension(100, 40));
        buttonSaveInfo.setPreferredSize(new java.awt.Dimension(100, 40));
        add(buttonSaveInfo);
        buttonSaveInfo.setBounds(440, 760, 100, 40);

        buttonCancelInfo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonCancelInfo.setText("Cancel");
        buttonCancelInfo.setMaximumSize(new java.awt.Dimension(100, 40));
        buttonCancelInfo.setMinimumSize(new java.awt.Dimension(100, 40));
        buttonCancelInfo.setPreferredSize(new java.awt.Dimension(100, 40));
        add(buttonCancelInfo);
        buttonCancelInfo.setBounds(550, 760, 100, 40);

        tareaDescription.setColumns(20);
        tareaDescription.setRows(5);
        scrollpaneDescription.setViewportView(tareaDescription);

        add(scrollpaneDescription);
        scrollpaneDescription.setBounds(360, 280, 300, 120);

        listTemplates.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        DefaultListModel model = new DefaultListModel();
        model.clear();
        listTemplates.setModel(model);
        scrollpaneTemplates.setViewportView(listTemplates);

        add(scrollpaneTemplates);
        scrollpaneTemplates.setBounds(880, 220, 410, 430);

        labelTemplates.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        labelTemplates.setText("Templates:");
        add(labelTemplates);
        labelTemplates.setBounds(880, 180, 100, 30);

        buttonAddTemplates.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonAddTemplates.setText("Add");
        buttonAddTemplates.setMaximumSize(new java.awt.Dimension(100, 40));
        buttonAddTemplates.setMinimumSize(new java.awt.Dimension(100, 40));
        buttonAddTemplates.setPreferredSize(new java.awt.Dimension(100, 40));
        add(buttonAddTemplates);
        buttonAddTemplates.setBounds(1070, 680, 100, 40);

        buttonCancelTemplates.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonCancelTemplates.setText("Cancel");
        buttonCancelTemplates.setMaximumSize(new java.awt.Dimension(100, 40));
        buttonCancelTemplates.setMinimumSize(new java.awt.Dimension(100, 40));
        buttonCancelTemplates.setPreferredSize(new java.awt.Dimension(100, 40));
        add(buttonCancelTemplates);
        buttonCancelTemplates.setBounds(1190, 680, 100, 40);

        buttonMonitor.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonMonitor.setText("Monitor");
        buttonMonitor.setMaximumSize(new java.awt.Dimension(100, 40));
        buttonMonitor.setMinimumSize(new java.awt.Dimension(100, 40));
        buttonMonitor.setPreferredSize(new java.awt.Dimension(100, 40));
        add(buttonMonitor);
        buttonMonitor.setBounds(880, 680, 100, 40);

        this.dialogAddTemplates = new DialogAddTemplates(null, "Templates");
        this.dialogAddTemplates.setVisible(false);
        this.dialogAddTemplates.setEnabled(false);

        initMenus();
    }

    private void initMenus() {
        this.pmenuTemplates = new JPopupMenu();

        this.mitemDelete = new JMenuItem("Delete");
        this.pmenuTemplates.add(this.mitemDelete);

        this.listTemplates.setComponentPopupMenu(this.pmenuTemplates);
    }

    private void initListeners() {
        this.listenerButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();

                if (source == buttonSaveInfo) {
                    DeviceManagementController controller = new DeviceManagementController();
                    if (!controller.saveDeviceInfo(currentDeviceId,
                            PanelDeviceInfo.this.getDeviceInfoFromViews())) {
                        JOptionPane.showMessageDialog(null, controller.getResultMessage());
                    }

                }
                if (source == buttonCancelInfo) {
                    PanelDeviceInfo.this.initViewData(currentDeviceId);
                }

                if (source == buttonAddTemplates) {
                    dialogAddTemplates.initTemplateList(PanelDeviceInfo.this.addedTemplateIds);
                    dialogAddTemplates.setEnabled(true);
                    dialogAddTemplates.setVisible(true);
                }

                if (source == buttonMonitor) {
                    int[] selectedIds = PanelDeviceInfo.this.listTemplates.getSelectedIndices();
                    if (selectedIds.length > 0) {
                        String[] templateIds = new String[selectedIds.length];
                        for (int i = 0; i < selectedIds.length; i++) {
                            templateIds[i] = addedTemplateIds.get(selectedIds[i]);
                        }

                        ApplicationWindow.getInstance().getPanelMain().switchPanelForDisplayDetail(PanelMain.PANEL_ID.PANEL_MONITOR_DEVICE);
                        ApplicationWindow.getInstance().getPanelMain().getPanelMonitorDevice().initViewData(
                                currentDeviceId,
                                tfieldName.getText(),
                                tfieldIPAddress.getText(),
                                tfieldSNMPVersion.getText(),
                                tfieldCommunity.getText(), 
                                templateIds);
                    } 
                }
            }
        };

        this.buttonSaveInfo.addActionListener(this.listenerButton);
        this.buttonCancelInfo.addActionListener(this.listenerButton);
        this.buttonAddTemplates.addActionListener(this.listenerButton);
        this.buttonCancelTemplates.addActionListener(this.listenerButton);
        this.buttonMonitor.addActionListener(this.listenerButton);

        this.listenerMenuItem = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem source = (JMenuItem) e.getSource();

                if (source == mitemDelete) {
                    DeviceManagementController controller = new DeviceManagementController();

                    int[] selectedIndices = listTemplates.getSelectedIndices();
                    String[] selectedTemplateIds = new String[selectedIndices.length];
                    for (int i = 0; i < selectedIndices.length; i++) {
                        selectedTemplateIds[i] = addedTemplateIds.get(selectedIndices[i]);
                    }

                    if (!controller.proccessDeletingAddedTemplates(currentDeviceId, selectedTemplateIds)) {
                        JOptionPane.showConfirmDialog(null, controller.getResultMessage());
                    }

                    PanelDeviceInfo.this.initListTemplates();
                }
            }

        };

        this.mitemDelete.addActionListener(this.listenerMenuItem);
    }

    public void initViewData(String deviceId) {
        this.currentDeviceId = deviceId;

        DeviceManagementController controller = new DeviceManagementController();
        String[] deviceInfo = controller.proccessGettingDeviceInfo(deviceId);

        if (deviceInfo == null) {
            JOptionPane.showMessageDialog(null, controller.getResultMessage());
        } else {
            this.tfieldName.setText(deviceInfo[0]);

            for (int i = 0; i < this.DEVICE_TYPE_LIST.length; i++) {
                if (deviceInfo[1].equalsIgnoreCase(this.DEVICE_TYPE_LIST[i])) {
                    this.cboxType.setSelectedIndex(i);
                    break;
                }
            }

            this.tareaDescription.setText(deviceInfo[2]);
            this.tfieldLabel.setText(deviceInfo[3]);
            this.tfieldSNMPVersion.setText(deviceInfo[4]);
            this.tfieldIPAddress.setText(deviceInfo[5]);
            this.tfieldCommunity.setText(deviceInfo[6]);
            this.labelStateValue.setText(deviceInfo[7]);
            this.labelImportedTimeValue.setText(deviceInfo[8]);
            this.labelLastAccessValue.setText(deviceInfo[9]);

            this.initListTemplates();

            this.revalidate();
            this.repaint();
        }

    }

    public void initListTemplates() {
        this.addedTemplateIds.clear();
        DefaultListModel listTemplatesModel = (DefaultListModel) this.listTemplates.getModel();
        listTemplatesModel.clear();

        DeviceManagementController controller = new DeviceManagementController();
        ArrayList<String[]> templatesInfo = controller.proccessGettingAddedTemplates(currentDeviceId);
        int tempSize = templatesInfo.size();

        for (int i = 0; i < tempSize; i++) {
            addedTemplateIds.add(templatesInfo.get(i)[0]);
            listTemplatesModel.addElement(templatesInfo.get(i)[1]);
        }
    }

    public String[] getDeviceInfoFromViews() {
        String[] result = new String[7];
        result[0] = this.tfieldName.getText();
        result[1] = (String) this.cboxType.getSelectedItem();
        result[2] = this.tareaDescription.getText();
        result[3] = this.tfieldLabel.getText();
        result[4] = this.tfieldSNMPVersion.getText();
        result[5] = this.tfieldIPAddress.getText();
        result[6] = this.tfieldCommunity.getText();
        return result;
    }

    public String getCurrentDeviceId() {
        return this.currentDeviceId;
    }

}
