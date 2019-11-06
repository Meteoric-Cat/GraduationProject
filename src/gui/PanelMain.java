/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author cloud
 */
public class PanelMain extends javax.swing.JPanel{
    private javax.swing.JLabel labelDevices;
    private javax.swing.JLabel labelSettings;
    private javax.swing.JLabel labelTemplates;
    private javax.swing.JLabel labelUser;
    
    private java.awt.event.MouseAdapter listenerLabel;
    
    private javax.swing.JPanel panelOptions;    
    private PanelAccountInfo panelAccountInfo;
    private PanelDevicesList panelDevicesList;
    private PanelDeviceInfo panelDeviceInfo;
    private PanelTemplatesList panelTemplatesList;
    private PanelTemplateInfo panelTemplateInfo;
    
    private javax.swing.JPanel curMainChild;
    
    public static enum PANEL_ID {
        PANEL_DEVICES,
        PANEL_TEMPLATES,
        PANEL_ACCOUNT_INFO,
        PANEL_SETTINGS, 
        PANEL_DEVICE_INFO,
        PANEL_TEMPLATE_INFO
    }
    
    public PanelMain() {
        initComponents();
        initListeners();
    }
    
    private void initComponents() {
        panelOptions = new javax.swing.JPanel();
        labelDevices = new javax.swing.JLabel();
        labelTemplates = new javax.swing.JLabel();
        labelUser = new javax.swing.JLabel();
        labelSettings = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1600, 900));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        panelOptions.setMaximumSize(new java.awt.Dimension(100, 900));
        panelOptions.setMinimumSize(new java.awt.Dimension(100, 900));
        panelOptions.setPreferredSize(new java.awt.Dimension(100, 900));
        panelOptions.setLayout(null);

        labelDevices.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDevices.setText("Devices");
        labelDevices.setMaximumSize(new java.awt.Dimension(100, 100));
        labelDevices.setMinimumSize(new java.awt.Dimension(100, 100));
        labelDevices.setPreferredSize(new java.awt.Dimension(100, 100));
        panelOptions.add(labelDevices);
        labelDevices.setBounds(0, 0, 100, 100);

        labelTemplates.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTemplates.setText("Templates");
        labelTemplates.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelTemplates.setMaximumSize(new java.awt.Dimension(100, 100));
        labelTemplates.setMinimumSize(new java.awt.Dimension(100, 100));
        labelTemplates.setPreferredSize(new java.awt.Dimension(100, 100));
        panelOptions.add(labelTemplates);
        labelTemplates.setBounds(0, 100, 100, 100);

        labelUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelUser.setText("User");
        labelUser.setMaximumSize(new java.awt.Dimension(100, 100));
        labelUser.setMinimumSize(new java.awt.Dimension(100, 100));
        labelUser.setPreferredSize(new java.awt.Dimension(100, 100));
        panelOptions.add(labelUser);
        labelUser.setBounds(0, 690, 100, 100);

        labelSettings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSettings.setText("Settings");
        labelSettings.setMaximumSize(new java.awt.Dimension(100, 100));
        labelSettings.setMinimumSize(new java.awt.Dimension(100, 100));
        labelSettings.setPreferredSize(new java.awt.Dimension(100, 100));
        panelOptions.add(labelSettings);
        labelSettings.setBounds(0, 800, 100, 100);

        initChildPanels();
    }    
 
    private void initChildPanels() {
        panelAccountInfo = new PanelAccountInfo();
        add(panelOptions);
        add(panelAccountInfo);        
        this.curMainChild = panelAccountInfo;

        panelDevicesList = new PanelDevicesList();
        panelDevicesList.setVisible(false);
        panelDevicesList.setEnabled(false);
        
        panelDeviceInfo = new PanelDeviceInfo();
        panelDeviceInfo.setVisible(false);
        panelDeviceInfo.setEnabled(false);
        
        panelTemplatesList = new PanelTemplatesList();
        panelTemplatesList.setVisible(false);
        panelTemplatesList.setEnabled(false);
        
        panelTemplateInfo = new PanelTemplateInfo();
        panelTemplateInfo.setVisible(false);
        panelTemplateInfo.setEnabled(false);
    }
    
    private void initListeners() {
        this.listenerLabel = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                javax.swing.JLabel source = (javax.swing.JLabel) e.getSource();
                if (source == labelDevices) {
                    PanelMain.this.switchPanelForOptions(PANEL_ID.PANEL_DEVICES);
                }
                if (source == labelUser) {
                    PanelMain.this.switchPanelForOptions(PANEL_ID.PANEL_ACCOUNT_INFO);
                }
            }
        };
        
        this.labelDevices.addMouseListener(this.listenerLabel);
        this.labelUser.addMouseListener(this.listenerLabel);
        this.labelTemplates.addMouseListener(this.listenerLabel);
        this.labelSettings.addMouseListener(this.listenerLabel);
    }
    
    public PanelAccountInfo getPanelAccountInfo() {
        return this.panelAccountInfo;
    }
    
    private void switchPanelForOptions(PANEL_ID id) {
        switch (id) {
            case PANEL_DEVICES:
                this.enablePanel(this.panelDevicesList);
                this.panelDevicesList.refreshViewData();
                break;
            case PANEL_TEMPLATES:
                this.enablePanel(this.panelTemplatesList);
                this.panelTemplatesList.refreshViewData();
                break;
            case PANEL_ACCOUNT_INFO:
                this.enablePanel(this.panelAccountInfo);
                break;                
        }
        
        this.revalidate();
        this.repaint();
    }
        
    public void switchPanelForDisplayDetail(PANEL_ID id) {
        switch(id) {
            case PANEL_DEVICE_INFO:
                this.enablePanel(this.panelDeviceInfo);
                break;
            case PANEL_TEMPLATE_INFO:
                this.enablePanel(this.panelTemplateInfo);
                break;
        }
        
        this.revalidate();
        this.repaint();
    }
    
    public void enablePanel(javax.swing.JPanel panel) {
        this.remove(this.curMainChild);
        this.curMainChild.setVisible(false);
        this.curMainChild.setEnabled(false);
        
        panel.setEnabled(true);
        panel.setVisible(true);
        this.add(panel);
        this.curMainChild = panel;        
    }
    
    public PanelDeviceInfo getPanelDeviceInfo() {
        return this.panelDeviceInfo;
    }
    
    public PanelTemplateInfo getPanelTemplateInfo() {
        return this.panelTemplateInfo;
    }
}
