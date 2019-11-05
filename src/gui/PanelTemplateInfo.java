/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionListener;

/**
 *
 * @author cloud
 */
public class PanelTemplateInfo extends javax.swing.JPanel {

    private String[] colNames = {"Name", "Object ID", "Value Type", "Access Type", "Description"};
    
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonSave;
    private javax.swing.JLabel labelDescription;
    private javax.swing.JLabel labelImportedTime;
    private javax.swing.JLabel labelImportedTimeValue;
    private javax.swing.JLabel labelItems;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelSNMPVersion;
    private javax.swing.JScrollPane scrollpaneDescription;
    private javax.swing.JScrollPane scrollpaneItems;
    private javax.swing.JTable tableItems;
    private javax.swing.JTextArea tareaDescription;
    private javax.swing.JTextField tfieldName;
    private javax.swing.JTextField tfieldSNMPVersion;

    private ActionListener listenerButton;
    
    public PanelTemplateInfo() {
        initComponents();
    }

    private void initComponents() {

        labelName = new javax.swing.JLabel();
        labelDescription = new javax.swing.JLabel();
        labelSNMPVersion = new javax.swing.JLabel();
        tfieldName = new javax.swing.JTextField();
        tfieldSNMPVersion = new javax.swing.JTextField();
        labelImportedTime = new javax.swing.JLabel();
        labelImportedTimeValue = new javax.swing.JLabel();
        scrollpaneItems = new javax.swing.JScrollPane();
        tableItems = new javax.swing.JTable();
        scrollpaneDescription = new javax.swing.JScrollPane();
        tareaDescription = new javax.swing.JTextArea();
        buttonSave = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        labelItems = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1500, 900));
        setMinimumSize(new java.awt.Dimension(1500, 900));
        setPreferredSize(new java.awt.Dimension(1500, 900));
        setLayout(null);

        labelName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelName.setText("Name:");
        add(labelName);
        labelName.setBounds(440, 150, 90, 14);

        labelDescription.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelDescription.setText("Description:");
        add(labelDescription);
        labelDescription.setBounds(440, 180, 90, 14);

        labelSNMPVersion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelSNMPVersion.setText("SNMP Version:");
        add(labelSNMPVersion);
        labelSNMPVersion.setBounds(440, 280, 90, 14);

        tfieldName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        add(tfieldName);
        tfieldName.setBounds(550, 150, 300, 20);

        tfieldSNMPVersion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        add(tfieldSNMPVersion);
        tfieldSNMPVersion.setBounds(550, 280, 300, 20);

        labelImportedTime.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelImportedTime.setText("Imported Time:");
        add(labelImportedTime);
        labelImportedTime.setBounds(440, 320, 90, 14);

        labelImportedTimeValue.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelImportedTimeValue.setText(". . .");
        add(labelImportedTimeValue);
        labelImportedTimeValue.setBounds(550, 320, 300, 14);

        tableItems.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tableItems.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                this.colNames
        ));
        scrollpaneItems.setViewportView(tableItems);

        add(scrollpaneItems);
        scrollpaneItems.setBounds(130, 400, 1255, 402);

        tareaDescription.setColumns(20);
        tareaDescription.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tareaDescription.setRows(5);
        scrollpaneDescription.setViewportView(tareaDescription);

        add(scrollpaneDescription);
        scrollpaneDescription.setBounds(550, 180, 300, 90);

        buttonSave.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        buttonSave.setText("Save");
        buttonSave.setMaximumSize(new java.awt.Dimension(100, 30));
        buttonSave.setMinimumSize(new java.awt.Dimension(100, 30));
        buttonSave.setPreferredSize(new java.awt.Dimension(100, 30));
        add(buttonSave);
        buttonSave.setBounds(900, 150, 100, 30);

        buttonCancel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        buttonCancel.setText("Cancel");
        buttonCancel.setMaximumSize(new java.awt.Dimension(100, 30));
        buttonCancel.setMinimumSize(new java.awt.Dimension(100, 30));
        buttonCancel.setPreferredSize(new java.awt.Dimension(100, 30));
        add(buttonCancel);
        buttonCancel.setBounds(900, 190, 100, 30);

        labelItems.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        labelItems.setText("Items:");
        add(labelItems);
        labelItems.setBounds(130, 370, 34, 16);
    }
}
