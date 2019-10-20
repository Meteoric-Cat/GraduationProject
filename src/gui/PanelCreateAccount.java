/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.UserManagementController;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author cloud
 */
public class PanelCreateAccount extends javax.swing.JPanel {

    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonCreate;
    private javax.swing.JCheckBox cboxTerm;
    private javax.swing.JLabel labelAccount;
    private javax.swing.JLabel labelAge;
    private javax.swing.JLabel labelConfirm;
    private javax.swing.JLabel labelContactNumber;
    private javax.swing.JLabel labelFullname;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelPosition;
    private javax.swing.JTextField tfieldAccount;
    private javax.swing.JTextField tfieldAge;
    private javax.swing.JPasswordField tfieldConfirm;
    private javax.swing.JTextField tfieldContactNumber;
    private javax.swing.JTextField tfieldFullname;
    private javax.swing.JPasswordField tfieldPassword;
    private javax.swing.JTextField tfieldPosition;

    private java.awt.event.ActionListener listenerButton;

    public PanelCreateAccount() {
        initComponents();
        initEventListeners();
        
        renewUI();
    }

    private void initComponents() {

        tfieldAccount = new javax.swing.JTextField();
        tfieldPassword = new javax.swing.JPasswordField();
        labelFullname = new javax.swing.JLabel();
        tfieldFullname = new javax.swing.JTextField();
        labelAge = new javax.swing.JLabel();
        tfieldAge = new javax.swing.JTextField();
        labelPosition = new javax.swing.JLabel();
        tfieldPosition = new javax.swing.JTextField();
        labelContactNumber = new javax.swing.JLabel();
        tfieldContactNumber = new javax.swing.JTextField();
        labelAccount = new javax.swing.JLabel();
        labelPassword = new javax.swing.JLabel();
        labelConfirm = new javax.swing.JLabel();
        tfieldConfirm = new javax.swing.JPasswordField();
        cboxTerm = new javax.swing.JCheckBox();
        buttonCreate = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1500, 900));
        setLayout(null);

        tfieldAccount.setMaximumSize(new java.awt.Dimension(300, 50));
        tfieldAccount.setMinimumSize(new java.awt.Dimension(300, 50));
        tfieldAccount.setPreferredSize(new java.awt.Dimension(300, 50));
        add(tfieldAccount);
        tfieldAccount.setBounds(660, 450, 300, 50);

        tfieldPassword.setMaximumSize(new java.awt.Dimension(300, 50));
        tfieldPassword.setMinimumSize(new java.awt.Dimension(300, 50));
        tfieldPassword.setPreferredSize(new java.awt.Dimension(300, 50));
        add(tfieldPassword);
        tfieldPassword.setBounds(660, 520, 300, 50);

        labelFullname.setFont(new java.awt.Font("Ubuntu", 1, 15)); 
        labelFullname.setText("Full Name");
        labelFullname.setMaximumSize(new java.awt.Dimension(120, 50));
        labelFullname.setMinimumSize(new java.awt.Dimension(120, 50));
        labelFullname.setPreferredSize(new java.awt.Dimension(120, 50));
        add(labelFullname);
        labelFullname.setBounds(540, 120, 120, 50);

        tfieldFullname.setMaximumSize(new java.awt.Dimension(300, 50));
        tfieldFullname.setMinimumSize(new java.awt.Dimension(300, 50));
        tfieldFullname.setPreferredSize(new java.awt.Dimension(300, 50));        
        add(tfieldFullname);
        tfieldFullname.setBounds(660, 120, 300, 50);

        labelAge.setFont(new java.awt.Font("Ubuntu", 1, 15)); 
        labelAge.setText("Age");
        labelAge.setMaximumSize(new java.awt.Dimension(120, 50));
        labelAge.setMinimumSize(new java.awt.Dimension(120, 50));
        labelAge.setPreferredSize(new java.awt.Dimension(120, 50));
        add(labelAge);
        labelAge.setBounds(540, 190, 120, 50);

        tfieldAge.setMaximumSize(new java.awt.Dimension(300, 50));
        tfieldAge.setMinimumSize(new java.awt.Dimension(300, 50));
        tfieldAge.setPreferredSize(new java.awt.Dimension(300, 50));
        add(tfieldAge);
        tfieldAge.setBounds(660, 190, 300, 50);

        labelPosition.setFont(new java.awt.Font("Ubuntu", 1, 15)); 
        labelPosition.setText("Position");
        labelPosition.setMaximumSize(new java.awt.Dimension(120, 50));
        labelPosition.setMinimumSize(new java.awt.Dimension(120, 50));
        labelPosition.setPreferredSize(new java.awt.Dimension(120, 50));
        add(labelPosition);
        labelPosition.setBounds(540, 260, 120, 50);

        tfieldPosition.setMaximumSize(new java.awt.Dimension(300, 50));
        tfieldPosition.setMinimumSize(new java.awt.Dimension(300, 50));
        tfieldPosition.setPreferredSize(new java.awt.Dimension(300, 50));
        add(tfieldPosition);
        tfieldPosition.setBounds(660, 260, 300, 50);

        labelContactNumber.setFont(new java.awt.Font("Ubuntu", 1, 15)); 
        labelContactNumber.setText("Contact Number");
        labelContactNumber.setMaximumSize(new java.awt.Dimension(120, 50));
        labelContactNumber.setMinimumSize(new java.awt.Dimension(120, 50));
        labelContactNumber.setPreferredSize(new java.awt.Dimension(120, 50));
        add(labelContactNumber);
        labelContactNumber.setBounds(540, 330, 120, 50);

        tfieldContactNumber.setMaximumSize(new java.awt.Dimension(300, 50));
        tfieldContactNumber.setMinimumSize(new java.awt.Dimension(300, 50));
        tfieldContactNumber.setPreferredSize(new java.awt.Dimension(300, 50));
        add(tfieldContactNumber);
        tfieldContactNumber.setBounds(660, 330, 300, 50);

        labelAccount.setFont(new java.awt.Font("Ubuntu", 1, 15)); 
        labelAccount.setText("Account");
        labelAccount.setMaximumSize(new java.awt.Dimension(120, 50));
        labelAccount.setMinimumSize(new java.awt.Dimension(120, 50));
        labelAccount.setPreferredSize(new java.awt.Dimension(120, 50));
        add(labelAccount);
        labelAccount.setBounds(540, 450, 120, 50);

        labelPassword.setFont(new java.awt.Font("Ubuntu", 1, 15)); 
        labelPassword.setText("Password");
        labelPassword.setMaximumSize(new java.awt.Dimension(120, 50));
        labelPassword.setMinimumSize(new java.awt.Dimension(120, 50));
        labelPassword.setPreferredSize(new java.awt.Dimension(120, 50));
        add(labelPassword);
        labelPassword.setBounds(540, 520, 120, 50);

        labelConfirm.setFont(new java.awt.Font("Ubuntu", 1, 15)); 
        labelConfirm.setText("Confirm");
        labelConfirm.setMaximumSize(new java.awt.Dimension(120, 50));
        labelConfirm.setMinimumSize(new java.awt.Dimension(120, 50));
        labelConfirm.setPreferredSize(new java.awt.Dimension(120, 50));
        add(labelConfirm);
        labelConfirm.setBounds(540, 590, 120, 50);

        tfieldConfirm.setMaximumSize(new java.awt.Dimension(300, 50));
        tfieldConfirm.setMinimumSize(new java.awt.Dimension(300, 50));
        tfieldConfirm.setPreferredSize(new java.awt.Dimension(300, 50));
        add(tfieldConfirm);
        tfieldConfirm.setBounds(660, 590, 300, 50);

        cboxTerm.setFont(new java.awt.Font("Ubuntu", 1, 15)); 
        cboxTerm.setText("I have read and agree to the term of service");
        add(cboxTerm);
        cboxTerm.setBounds(570, 670, 350, 24);

        buttonCreate.setFont(new java.awt.Font("Ubuntu", 1, 15)); 
        buttonCreate.setText("Create");
        buttonCreate.setMaximumSize(new java.awt.Dimension(100, 50));
        buttonCreate.setMinimumSize(new java.awt.Dimension(100, 50));
        buttonCreate.setPreferredSize(new java.awt.Dimension(100, 50));
        add(buttonCreate);
        buttonCreate.setBounds(750, 710, 100, 50);

        buttonCancel.setFont(new java.awt.Font("Ubuntu", 1, 15)); 
        buttonCancel.setText("Cancel");
        buttonCancel.setMaximumSize(new java.awt.Dimension(100, 50));
        buttonCancel.setMinimumSize(new java.awt.Dimension(100, 50));
        buttonCancel.setPreferredSize(new java.awt.Dimension(100, 50));
        add(buttonCancel);
        buttonCancel.setBounds(860, 710, 100, 50);
    }

    private void initEventListeners() {
        listenerButton = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JButton source = (javax.swing.JButton) e.getSource();
                if (source == buttonCreate) {
                    UserManagementController controller = new UserManagementController();
                    String[] accountInfo = {
                            tfieldAccount.getText(),
                            tfieldPassword.getText(),
                            tfieldConfirm.getText(),
                            String.valueOf(cboxTerm.isSelected())};
                    String[] otherInfo = {
                            tfieldFullname.getText(),
                            tfieldAge.getText(),
                            tfieldPosition.getText(),
                            tfieldContactNumber.getText()};                                            
                    boolean creationResult = controller.processAccountCreation(accountInfo, otherInfo);
                    if (creationResult) {                        
                        ApplicationWindow.getInstance().switchPanel(ApplicationWindow.PANEL_ID.LOGIN_ACCOUNT);
                    } 
                    JOptionPane.showMessageDialog(ApplicationWindow.getInstance(), controller.getResultMessage());
                }
                if (source == buttonCancel) {
                    ApplicationWindow.getInstance().switchPanel(ApplicationWindow.PANEL_ID.LOGIN_ACCOUNT);
                }
            }
        };

        buttonCreate.addActionListener(listenerButton);
        buttonCancel.addActionListener(listenerButton);
    };

    private void renewUI() {
        this.tfieldAccount.setText("");
        this.tfieldAge.setText("");
        this.tfieldConfirm.setText("");
        this.tfieldContactNumber.setText("");
        this.tfieldFullname.setText("");
        this.tfieldPassword.setText("");
        this.tfieldPosition.setText("");
        this.cboxTerm.setSelected(false);
    }
}
