/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.UserManagementController;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author cloud
 */
public class PanelAccountInfo extends javax.swing.JPanel {

    private javax.swing.JButton buttonCancelInfo;
    private javax.swing.JButton buttonCancelPassword;
    private javax.swing.JButton buttonChangePassword;
    private javax.swing.JButton buttonSaveInfo;
    private javax.swing.JLabel labelAccount;
    private javax.swing.JLabel labelAccountValue;
    private javax.swing.JLabel labelAge;
    private javax.swing.JLabel labelConfirm;
    private javax.swing.JLabel labelContactNumber;
    private javax.swing.JLabel labelFullname;
    private javax.swing.JLabel labelImage;
    private javax.swing.JLabel labelNewPassword;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelPasswordValue;
    private javax.swing.JLabel labelPosition;
    private javax.swing.JPanel panelChangePassword;
    private javax.swing.JPanel panelImage;
    private javax.swing.JTextField tfieldAge;
    private javax.swing.JPasswordField tfieldConfirm;
    private javax.swing.JTextField tfieldContactNumber;
    private javax.swing.JTextField tfieldFullname;
    private javax.swing.JPasswordField tfieldNewPassword;
    private javax.swing.JTextField tfieldPosition;
    
    private java.awt.event.ActionListener listenerButton;
    private java.awt.event.MouseAdapter listenerPanel;

    public PanelAccountInfo() {
        initComponents();
        initListeners();
    }
    
    private void initComponents() {

        labelAccount = new javax.swing.JLabel();
        labelPassword = new javax.swing.JLabel();
        labelFullname = new javax.swing.JLabel();
        labelAge = new javax.swing.JLabel();
        labelPosition = new javax.swing.JLabel();
        labelContactNumber = new javax.swing.JLabel();
        labelAccountValue = new javax.swing.JLabel();
        tfieldFullname = new javax.swing.JTextField();
        tfieldAge = new javax.swing.JTextField();
        tfieldPosition = new javax.swing.JTextField();
        tfieldContactNumber = new javax.swing.JTextField();
        buttonSaveInfo = new javax.swing.JButton();
        buttonCancelInfo = new javax.swing.JButton();
        buttonChangePassword = new javax.swing.JButton();
        panelImage = new javax.swing.JPanel();
        labelImage = new javax.swing.JLabel();
        labelPasswordValue = new javax.swing.JLabel();
        panelChangePassword = new javax.swing.JPanel();
        labelNewPassword = new javax.swing.JLabel();
        labelConfirm = new javax.swing.JLabel();
        tfieldConfirm = new javax.swing.JPasswordField();
        tfieldNewPassword = new javax.swing.JPasswordField();
        buttonCancelPassword = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1500, 900));
        setMinimumSize(new java.awt.Dimension(1500, 900));
        setPreferredSize(new java.awt.Dimension(1500, 900));
        setLayout(null);

        labelAccount.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelAccount.setText("Account:");
        labelAccount.setMaximumSize(new java.awt.Dimension(100, 30));
        labelAccount.setMinimumSize(new java.awt.Dimension(100, 30));
        labelAccount.setPreferredSize(new java.awt.Dimension(100, 30));
        add(labelAccount);
        labelAccount.setBounds(320, 570, 100, 30);

        labelPassword.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelPassword.setText("Password:");
        labelPassword.setMaximumSize(new java.awt.Dimension(100, 30));
        labelPassword.setMinimumSize(new java.awt.Dimension(100, 30));
        labelPassword.setPreferredSize(new java.awt.Dimension(100, 30));
        add(labelPassword);
        labelPassword.setBounds(320, 610, 100, 30);

        labelFullname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelFullname.setText("Full name:");
        labelFullname.setMaximumSize(new java.awt.Dimension(150, 30));
        labelFullname.setMinimumSize(new java.awt.Dimension(150, 30));
        labelFullname.setPreferredSize(new java.awt.Dimension(150, 30));
        add(labelFullname);
        labelFullname.setBounds(880, 190, 150, 30);

        labelAge.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelAge.setText("Age:");
        labelAge.setMaximumSize(new java.awt.Dimension(150, 30));
        labelAge.setMinimumSize(new java.awt.Dimension(150, 30));
        labelAge.setPreferredSize(new java.awt.Dimension(150, 30));
        add(labelAge);
        labelAge.setBounds(880, 230, 150, 30);

        labelPosition.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelPosition.setText("Position:");
        labelPosition.setMaximumSize(new java.awt.Dimension(150, 30));
        labelPosition.setMinimumSize(new java.awt.Dimension(150, 30));
        labelPosition.setPreferredSize(new java.awt.Dimension(150, 30));
        add(labelPosition);
        labelPosition.setBounds(880, 270, 150, 30);

        labelContactNumber.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelContactNumber.setText("Contact number:");
        labelContactNumber.setMaximumSize(new java.awt.Dimension(150, 30));
        labelContactNumber.setMinimumSize(new java.awt.Dimension(150, 30));
        labelContactNumber.setPreferredSize(new java.awt.Dimension(150, 30));
        add(labelContactNumber);
        labelContactNumber.setBounds(880, 310, 150, 30);

        labelAccountValue.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelAccountValue.setText(". . .");
        labelAccountValue.setMaximumSize(new java.awt.Dimension(200, 30));
        labelAccountValue.setMinimumSize(new java.awt.Dimension(200, 30));
        labelAccountValue.setPreferredSize(new java.awt.Dimension(200, 30));
        add(labelAccountValue);
        labelAccountValue.setBounds(470, 570, 200, 30);

        tfieldFullname.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        tfieldFullname.setMaximumSize(new java.awt.Dimension(200, 30));
        tfieldFullname.setMinimumSize(new java.awt.Dimension(200, 30));
        tfieldFullname.setPreferredSize(new java.awt.Dimension(200, 30));
        add(tfieldFullname);
        tfieldFullname.setBounds(1030, 190, 200, 30);

        tfieldAge.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        tfieldAge.setMaximumSize(new java.awt.Dimension(200, 30));
        tfieldAge.setMinimumSize(new java.awt.Dimension(200, 30));
        tfieldAge.setPreferredSize(new java.awt.Dimension(200, 30));
        add(tfieldAge);
        tfieldAge.setBounds(1030, 230, 200, 30);

        tfieldPosition.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        tfieldPosition.setMaximumSize(new java.awt.Dimension(200, 30));
        tfieldPosition.setMinimumSize(new java.awt.Dimension(200, 30));
        tfieldPosition.setPreferredSize(new java.awt.Dimension(200, 30));
        add(tfieldPosition);
        tfieldPosition.setBounds(1030, 270, 200, 30);

        tfieldContactNumber.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        tfieldContactNumber.setMaximumSize(new java.awt.Dimension(200, 30));
        tfieldContactNumber.setMinimumSize(new java.awt.Dimension(200, 30));
        tfieldContactNumber.setPreferredSize(new java.awt.Dimension(200, 30));
        add(tfieldContactNumber);
        tfieldContactNumber.setBounds(1030, 310, 200, 30);

        buttonSaveInfo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonSaveInfo.setText("Save");
        buttonSaveInfo.setMaximumSize(new java.awt.Dimension(80, 30));
        buttonSaveInfo.setMinimumSize(new java.awt.Dimension(80, 30));
        buttonSaveInfo.setPreferredSize(new java.awt.Dimension(80, 30));
        add(buttonSaveInfo);
        buttonSaveInfo.setBounds(1050, 370, 80, 30);

        buttonCancelInfo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonCancelInfo.setText("Cancel");
        buttonCancelInfo.setMaximumSize(new java.awt.Dimension(80, 30));
        buttonCancelInfo.setMinimumSize(new java.awt.Dimension(80, 30));
        buttonCancelInfo.setPreferredSize(new java.awt.Dimension(80, 30));
        add(buttonCancelInfo);
        buttonCancelInfo.setBounds(1140, 370, 80, 30);

        buttonChangePassword.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonChangePassword.setText("Change");
        buttonChangePassword.setMaximumSize(new java.awt.Dimension(100, 30));
        buttonChangePassword.setMinimumSize(new java.awt.Dimension(100, 30));
        buttonChangePassword.setPreferredSize(new java.awt.Dimension(100, 30));
        add(buttonChangePassword);
        buttonChangePassword.setBounds(690, 610, 100, 30);

        panelImage.setLayout(null);
        panelImage.setSize(290, 350);
        add(panelImage);
        panelImage.setBounds(400, 130, panelImage.getWidth(), panelImage.getHeight());
        
        labelImage.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelImage.setText("");
        labelImage.setSize(this.panelImage.getPreferredSize());
        panelImage.add(labelImage);
        labelImage.setBounds(0, 0, this.labelImage.getSize().width, this.labelImage.getSize().height);
        
        labelPasswordValue.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelPasswordValue.setText(". . .");
        labelPasswordValue.setMaximumSize(new java.awt.Dimension(200, 30));
        labelPasswordValue.setMinimumSize(new java.awt.Dimension(200, 30));
        labelPasswordValue.setPreferredSize(new java.awt.Dimension(200, 30));
        add(labelPasswordValue);
        labelPasswordValue.setBounds(470, 610, 200, 30);

        panelChangePassword.setMaximumSize(new java.awt.Dimension(470, 120));
        panelChangePassword.setMinimumSize(new java.awt.Dimension(470, 120));
        panelChangePassword.setPreferredSize(new java.awt.Dimension(470, 120));
        panelChangePassword.setLayout(null);

        labelNewPassword.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelNewPassword.setText("New password:");
        labelNewPassword.setMaximumSize(new java.awt.Dimension(100, 30));
        labelNewPassword.setMinimumSize(new java.awt.Dimension(100, 30));
        labelNewPassword.setPreferredSize(new java.awt.Dimension(140, 30));
        panelChangePassword.add(labelNewPassword);
        labelNewPassword.setBounds(10, 10, 140, 30);

        labelConfirm.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelConfirm.setText("Confirm:");
        labelConfirm.setMaximumSize(new java.awt.Dimension(100, 30));
        labelConfirm.setMinimumSize(new java.awt.Dimension(100, 30));
        labelConfirm.setPreferredSize(new java.awt.Dimension(100, 30));
        panelChangePassword.add(labelConfirm);
        labelConfirm.setBounds(10, 60, 100, 30);

        tfieldConfirm.setFont(new java.awt.Font("SansSerif", 0, 18));
        tfieldConfirm.setMaximumSize(new java.awt.Dimension(200, 30));
        tfieldConfirm.setMinimumSize(new java.awt.Dimension(200, 30));
        tfieldConfirm.setPreferredSize(new java.awt.Dimension(200, 30));
        panelChangePassword.add(tfieldConfirm);
        tfieldConfirm.setBounds(160, 60, 200, 30);

        tfieldNewPassword.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        tfieldNewPassword.setMaximumSize(new java.awt.Dimension(200, 30));
        tfieldNewPassword.setMinimumSize(new java.awt.Dimension(200, 30));
        tfieldNewPassword.setPreferredSize(new java.awt.Dimension(200, 30));
        panelChangePassword.add(tfieldNewPassword);
        tfieldNewPassword.setBounds(160, 10, 200, 30);

        buttonCancelPassword.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonCancelPassword.setText("Cancel");
        buttonCancelPassword.setMaximumSize(new java.awt.Dimension(100, 30));
        buttonCancelPassword.setMinimumSize(new java.awt.Dimension(100, 30));
        buttonCancelPassword.setPreferredSize(new java.awt.Dimension(100, 30));
        panelChangePassword.add(buttonCancelPassword);
        buttonCancelPassword.setBounds(380, 10, 100, 30);

        add(panelChangePassword);
        panelChangePassword.setBounds(310, 650, 470, 120);    
        panelChangePassword.setVisible(false);
        panelChangePassword.setEnabled(false);
    }

    private void initListeners() {
        this.listenerButton = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JButton source = (javax.swing.JButton) e.getSource();
                UserManagementController controller = new UserManagementController();
                if (source == buttonCancelInfo) {
                    PanelAccountInfo.this.revertChangeUserInfo(controller.getUserInfo(null));
                }
                if (source == buttonSaveInfo) {
                    String[] userInfo = new String[] {
                        tfieldFullname.getText(),
                        tfieldAge.getText(),
                        tfieldPosition.getText(),
                        tfieldContactNumber.getText()
                    };
                    controller.saveUserInfo(userInfo);
                    JOptionPane.showMessageDialog(null, controller.getResultMessage());
                }
                if (source == buttonCancelPassword) {
                    PanelAccountInfo.this.revertChangePassword();
                }
                if (source == buttonChangePassword) {
                    if (panelChangePassword.isVisible()) {
                        controller.processChangePassword(String.valueOf(tfieldNewPassword.getPassword()),
                            String.valueOf(tfieldConfirm.getPassword()));
                        JOptionPane.showMessageDialog(null, controller.getResultMessage());
                    } else {
                        //panelChangePassword.setEnabled(true);
                        panelChangePassword.setVisible(true);
                    }
                }
            }         
        };
        
        this.buttonCancelInfo.addActionListener(listenerButton);
        this.buttonSaveInfo.addActionListener(listenerButton);
        this.buttonChangePassword.addActionListener(listenerButton);
        this.buttonCancelPassword.addActionListener(listenerButton);
        
        this.listenerPanel = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                javax.swing.JPanel source = (javax.swing.JPanel) e.getSource();
                if (source == panelImage) {
                    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("images", "jpg", "bmp", "png");
                    fileChooser.setFileFilter(filter);                    
                    int result = fileChooser.showOpenDialog(null);
                    
                    if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                        UserManagementController controller = new UserManagementController();                        
                        String imageFile = controller.proccessChangeAccountImage(fileChooser.getSelectedFile());
                        if (imageFile != null) {
                            PanelAccountInfo.this.displayAccountImage(imageFile);
                        }
                    }
                }
            }
        };
        this.panelImage.addMouseListener(this.listenerPanel);
    }

    private void revertChangeUserInfo(String[] info) {
        this.tfieldFullname.setText(info[0]);
        this.tfieldAge.setText(info[1]);
        this.tfieldPosition.setText(info[2]);
        this.tfieldContactNumber.setText(info[3]);
    }
    
    private void revertChangePassword() {
        this.tfieldNewPassword.setText("");
        this.tfieldConfirm.setText("");
        this.panelChangePassword.setVisible(false);
        //this.panelChangePassword.setEnabled(false);
    }

    public void populateData() {
        UserManagementController controller = new UserManagementController();
        String[] accountData = controller.getLoggedAccount();
        this.labelAccountValue.setText(accountData[0]);
        this.labelPasswordValue.setText(accountData[1]);
        
        String[] userInfo = controller.getUserInfo(null);
        this.tfieldFullname.setText(userInfo[0]);
        this.tfieldAge.setText(userInfo[1]);
        this.tfieldPosition.setText(userInfo[2]);
        this.tfieldContactNumber.setText(userInfo[3]);
        
        String[] image = controller.getUserInfo(new String[]{"image_dir"});
        this.displayAccountImage(image[0]);
    }
    
    private void displayAccountImage(String imagePath) {
        try {
            File file = new File(imagePath);
            if (file.exists()) {
                BufferedImage image = ImageIO.read(new File(imagePath));
                this.labelImage.setIcon(new ImageIcon(image));            
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
    }
}
