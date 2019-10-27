/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.UserManagementController;
import data.DataManager;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author cloud
 */
public class PanelLoginAccount extends javax.swing.JPanel {

    private javax.swing.JButton buttonCreate;
    private javax.swing.JButton buttonLogin;
    private javax.swing.JCheckBox cboxRemember;
    private javax.swing.JLabel labelAccount;
    private javax.swing.JLabel labelForgot;
    private javax.swing.JLabel labelPassword;
    private javax.swing.DefaultComboBoxModel<String> tfieldAccountModel;
    private javax.swing.JComboBox<String> tfieldAccount;
    private javax.swing.JPasswordField tfieldPassword;

    private java.awt.event.ActionListener listenerButton;
    private java.awt.event.KeyListener listenerBox;
    private java.awt.event.ItemListener listenerBoxItem;

    public PanelLoginAccount() {
        initComponents();
        initEventListeners();
        renewUI();
    }

    private void initComponents() {

        labelAccount = new javax.swing.JLabel();
        labelPassword = new javax.swing.JLabel();
        tfieldPassword = new javax.swing.JPasswordField();
        cboxRemember = new javax.swing.JCheckBox();
        buttonLogin = new javax.swing.JButton();
        buttonCreate = new javax.swing.JButton();
        labelForgot = new javax.swing.JLabel();
        tfieldAccount = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(1600, 900));
        setLayout(null);

        labelAccount.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        labelAccount.setText("Account");
        add(labelAccount);
        labelAccount.setBounds(630, 340, 120, 30);

        labelPassword.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        labelPassword.setText("Password");
        add(labelPassword);
        labelPassword.setBounds(630, 380, 120, 30);

        tfieldPassword.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        tfieldPassword.setPreferredSize(new java.awt.Dimension(280, 31));
        add(tfieldPassword);
        tfieldPassword.setBounds(750, 380, 280, 31);

        cboxRemember.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        cboxRemember.setText("Remeber password");
        add(cboxRemember);
        cboxRemember.setBounds(730, 430, 210, 25);

        buttonLogin.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        buttonLogin.setText("Login");
        buttonLogin.setMaximumSize(new java.awt.Dimension(100, 40));
        buttonLogin.setMinimumSize(new java.awt.Dimension(100, 40));
        buttonLogin.setPreferredSize(new java.awt.Dimension(100, 40));
        add(buttonLogin);
        buttonLogin.setBounds(690, 470, 100, 40);

        buttonCreate.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        buttonCreate.setText("Create");
        buttonCreate.setMaximumSize(new java.awt.Dimension(100, 40));
        buttonCreate.setMinimumSize(new java.awt.Dimension(100, 40));
        buttonCreate.setPreferredSize(new java.awt.Dimension(100, 40));
        add(buttonCreate);
        buttonCreate.setBounds(810, 470, 100, 40);

        labelForgot.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        labelForgot.setForeground(new java.awt.Color(34, 79, 190));
        labelForgot.setText("Forgot password?");
        add(labelForgot);
        labelForgot.setBounds(750, 520, 140, 17);

        tfieldAccount.setEditable(true);
        tfieldAccount.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        tfieldAccountModel = new javax.swing.DefaultComboBoxModel<String>(new String[]{" "});
        tfieldAccount.setModel(this.tfieldAccountModel);
        tfieldAccount.setMinimumSize(new java.awt.Dimension(280, 31));
        tfieldAccount.setPreferredSize(new java.awt.Dimension(280, 31));
        add(tfieldAccount);
        tfieldAccount.setBounds(750, 340, 280, 31);

    }

    private void initEventListeners() {
        listenerButton = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JButton source = (javax.swing.JButton) e.getSource();
                if (source == buttonCreate) {
                    ApplicationWindow.getInstance().switchPanel(ApplicationWindow.PANEL_ID.CREATE_ACCOUNT);
                }
                if (source == buttonLogin) {
                    UserManagementController controller = new UserManagementController();
                    if (!controller.processLogin(
                            tfieldAccount.getSelectedItem().toString(),
                            String.valueOf(tfieldPassword.getPassword()),
                            cboxRemember.isSelected())) {
                        JOptionPane.showMessageDialog(ApplicationWindow.getInstance(), controller.getResultMessage());
                    } else {
                        //controller.saveAccountId(tfieldAccount.getSelectedItem().toString(), String.valueOf(tfieldPassword.getPassword()));
                        ApplicationWindow.getInstance().switchPanel(ApplicationWindow.PANEL_ID.MAIN);
                        ApplicationWindow.getInstance().getPanelMain().getPanelAccountInfo().populateData();
                    };
                }
            }
        };

        buttonLogin.addActionListener(listenerButton);
        buttonCreate.addActionListener(listenerButton);

        listenerBox = new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                javax.swing.JComboBox<String> source = (javax.swing.JComboBox<String>) e.getSource();
                if (source == tfieldAccount) {
                    UserManagementController controller = new UserManagementController();
                    PanelLoginAccount.this.updateFieldAccount(controller.processPreLogin(tfieldAccount.getSelectedItem().toString()));
                }
            }
        };
        listenerBoxItem = new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    javax.swing.JComboBox<String> source = (javax.swing.JComboBox<String>) e.getSource();
                    if (source == tfieldAccount) {
                        UserManagementController controller = new UserManagementController();
                        String password = "";
                        if (tfieldAccount.getSelectedItem() != null) {
                            password = controller.processPasswordRetrieval(tfieldAccount.getSelectedItem().toString());
                            tfieldPassword.setText(password);
                        }
                        cboxRemember.setSelected(!password.isEmpty());
                    }
                }
            }
        };

        tfieldAccount.addKeyListener(listenerBox);
        tfieldAccount.addItemListener(listenerBoxItem);
    }

    private void renewUI() {
        this.tfieldAccount.setSelectedIndex(0);
        this.tfieldPassword.setText("");
        this.cboxRemember.setSelected(false);

        UserManagementController controller = new UserManagementController();
        this.updateFieldAccount(controller.processPreLogin(null));
        this.tfieldAccount.setSelectedIndex(0);
    }

    private void updateFieldAccount(String[] accounts) {
        this.tfieldAccountModel.removeAllElements();
        for (String account : accounts) {
            this.tfieldAccountModel.addElement(account);
        }
        this.tfieldAccount.repaint();
        this.tfieldAccount.revalidate();
    }
}
