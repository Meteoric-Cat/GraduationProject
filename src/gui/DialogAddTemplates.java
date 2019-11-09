/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.TemplateManagementController;
import data.DataManager;
import java.util.ArrayList;
import javax.swing.JDialog;

/**
 *
 * @author cloud
 */
public class DialogAddTemplates extends JDialog {

    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JList<String> listTemplates;
    private javax.swing.JScrollPane scrollpaneTemplates;
    
    public DialogAddTemplates(java.awt.Frame parent, String title) {
        super(parent, title);
        initComponents();
    }

    private void initComponents() {
        scrollpaneTemplates = new javax.swing.JScrollPane();
        listTemplates = new javax.swing.JList<>();
        buttonAdd = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(500, 700));
        setMinimumSize(new java.awt.Dimension(500, 700));
        setPreferredSize(new java.awt.Dimension(500, 700));
        getContentPane().setLayout(null);

        listTemplates.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        listTemplates.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        listTemplates.setLayoutOrientation(javax.swing.JList.VERTICAL_WRAP);
        listTemplates.setMaximumSize(new java.awt.Dimension(52, 400));
        listTemplates.setMinimumSize(new java.awt.Dimension(52, 400));
        listTemplates.setPreferredSize(new java.awt.Dimension(52, 400));
        scrollpaneTemplates.setViewportView(listTemplates);

        getContentPane().add(scrollpaneTemplates);
        scrollpaneTemplates.setBounds(49, 57, 392, 531);

        buttonAdd.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonAdd.setText("Add");
        buttonAdd.setMaximumSize(new java.awt.Dimension(80, 30));
        buttonAdd.setMinimumSize(new java.awt.Dimension(80, 30));
        buttonAdd.setPreferredSize(new java.awt.Dimension(80, 30));
        getContentPane().add(buttonAdd);
        buttonAdd.setBounds(260, 620, 80, 30);

        buttonCancel.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        buttonCancel.setText("Cancel");
        buttonCancel.setMaximumSize(new java.awt.Dimension(80, 30));
        buttonCancel.setMinimumSize(new java.awt.Dimension(80, 30));
        buttonCancel.setPreferredSize(new java.awt.Dimension(80, 30));
        getContentPane().add(buttonCancel);
        buttonCancel.setBounds(360, 620, 80, 30);

        pack();
    }// </editor-fold>                        

    public void initTemplateList() {
        TemplateManagementController controller = new TemplateManagementController();
        ArrayList<String[]> templatesInfo = controller.processInitTemplateList(new String[]{
            DataManager.getInstance().getTemplates().getPrimaryKey().name,
            DataManager.getInstance().getTemplates().getNameColumn().name}
        );
        
        
    }
}
