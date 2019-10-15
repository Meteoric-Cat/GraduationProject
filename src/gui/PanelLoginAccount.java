/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionEvent;

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
    private javax.swing.JTextField tfieldAccount;
    private javax.swing.JPasswordField tfieldPassword;
    
    private java.awt.event.ActionListener listenerButton;
    
    public PanelLoginAccount() {
        initComponents();
        initEventListeners();
    }

    private void initComponents() {

        labelAccount = new javax.swing.JLabel();
        labelPassword = new javax.swing.JLabel();
        tfieldAccount = new javax.swing.JTextField();
        tfieldPassword = new javax.swing.JPasswordField();
        cboxRemember = new javax.swing.JCheckBox();
        buttonLogin = new javax.swing.JButton();
        buttonCreate = new javax.swing.JButton();
        labelForgot = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1600, 900));
        setLayout(null);

        labelAccount.setFont(new java.awt.Font("Ubuntu", 1, 18)); 
        labelAccount.setText("Account");
        add(labelAccount);
        labelAccount.setBounds(630, 350, 120, 20);

        labelPassword.setFont(new java.awt.Font("Ubuntu", 1, 18)); 
        labelPassword.setText("Password");
        add(labelPassword);
        labelPassword.setBounds(630, 390, 120, 20);

        tfieldAccount.setFont(new java.awt.Font("Ubuntu", 0, 18)); 
        add(tfieldAccount);
        tfieldAccount.setBounds(750, 340, 280, 31);

        tfieldPassword.setFont(new java.awt.Font("Ubuntu", 0, 18)); 
        add(tfieldPassword);
        tfieldPassword.setBounds(750, 380, 280, 31);

        cboxRemember.setFont(new java.awt.Font("Ubuntu", 1, 14));
        cboxRemember.setText("Remeber password");
        add(cboxRemember);
        cboxRemember.setBounds(730, 430, 210, 25);

        buttonLogin.setFont(new java.awt.Font("Ubuntu", 1, 16)); 
        buttonLogin.setText("Login");
        buttonLogin.setMaximumSize(new java.awt.Dimension(100, 40));
        buttonLogin.setMinimumSize(new java.awt.Dimension(100, 40));
        buttonLogin.setPreferredSize(new java.awt.Dimension(100, 40));
        add(buttonLogin);
        buttonLogin.setBounds(690, 470, 100, 40);

        buttonCreate.setFont(new java.awt.Font("Ubuntu", 1, 16)); 
        buttonCreate.setText("Create");
        buttonCreate.setMaximumSize(new java.awt.Dimension(100, 40));
        buttonCreate.setMinimumSize(new java.awt.Dimension(100, 40));
        buttonCreate.setPreferredSize(new java.awt.Dimension(100, 40));
        add(buttonCreate);
        buttonCreate.setBounds(810, 470, 100, 40);

        labelForgot.setFont(new java.awt.Font("Ubuntu", 0, 14));
        labelForgot.setForeground(new java.awt.Color(34, 79, 190));
        labelForgot.setText("Forgot password?");
        add(labelForgot);
        labelForgot.setBounds(750, 520, 140, 17);

    }                      

    private void initEventListeners() {
        listenerButton = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JButton source = (javax.swing.JButton) e.getSource();
                if (source == buttonCreate)
                {
                    ApplicationWindow.getInstance().switchPanel(ApplicationWindow.PANEL_ID.CREATE_ACCOUNT);
                } 
                if (source == buttonLogin)
                {
                    ApplicationWindow.getInstance().switchPanel(ApplicationWindow.PANEL_ID.MAIN);
                }
            }
        };
        
        buttonLogin.addActionListener(listenerButton);
        buttonCreate.addActionListener(listenerButton);
    }
}
