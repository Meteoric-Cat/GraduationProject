/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author cloud
 */
public class PanelMain extends javax.swing.JPanel{
    private javax.swing.JLabel labelDevices;
    private javax.swing.JLabel labelSettings;
    private javax.swing.JLabel labelTemplates;
    private javax.swing.JLabel labelUser;
    private javax.swing.JPanel panelOptions;
    
    public PanelMain() {
        initComponents();
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

        add(panelOptions);
    }    
    
}
