/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.DeviceManagementController;
import control.TemplateManagementController;
import data.DataManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author cloud
 */
public class DialogAddTemplates extends JDialog {

    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JList<String> listTemplates;
    private javax.swing.JScrollPane scrollpaneTemplates;

    private ActionListener listenerButton;

    private ArrayList<String> templateIds;

    public DialogAddTemplates(java.awt.Frame parent, String title) {
        super(parent, title);
        initComponents();
        initListeners();
        
        this.templateIds = new ArrayList<String>();
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
        DefaultListModel<String> model = new DefaultListModel<String>();
        model.clear();
        listTemplates.setModel(model);
        listTemplates.setLayoutOrientation(javax.swing.JList.VERTICAL_WRAP);
        listTemplates.setMaximumSize(new java.awt.Dimension(372, 600));
        listTemplates.setMinimumSize(new java.awt.Dimension(372, 600));
        listTemplates.setPreferredSize(new java.awt.Dimension(372, 600));
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

    public void initTemplateList(ArrayList<String> addedTemplateIds) {
        TemplateManagementController controller = new TemplateManagementController();
        ArrayList<String[]> templatesInfo = controller.processInitTemplateList(new String[]{
            DataManager.getInstance().getTemplates().getPrimaryKey().name,
            DataManager.getInstance().getTemplates().getNameColumn().name}
        );

        //if (addedTemplateIds.size() > 0) {
        int listSize1 = addedTemplateIds.size();
        int listSize2 = templatesInfo.size();

        DefaultListModel<String> model = (DefaultListModel) this.listTemplates.getModel();
        model.clear();        
        this.templateIds.clear();

        for (int i = 0; i < listSize2; i++) {
            if (addedTemplateIds.indexOf(templatesInfo.get(i)[0]) == -1) {
                this.templateIds.add(templatesInfo.get(i)[0]);
                model.addElement(templatesInfo.get(i)[1]);
            }
        }
        //}

    }

    private void initListeners() {
        this.listenerButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();

                if (source == buttonAdd) {
                    DeviceManagementController controller = new DeviceManagementController();

                    int[] selectedIndices = listTemplates.getSelectedIndices();
                    String[] selectedTemplateIds = new String[selectedIndices.length];
                    for (int i = 0; i < selectedIndices.length; i++) {
                        selectedTemplateIds[i] = templateIds.get(selectedIndices[i]);
                    }

                    if (controller.proccessAddingTemplatesToDevice(
                            ApplicationWindow.getInstance().getPanelMain().getPanelDeviceInfo().getCurrentDeviceId(), selectedTemplateIds)) {
                        ApplicationWindow.getInstance().getPanelMain().getPanelDeviceInfo().initListTemplates();
                        DialogAddTemplates.this.setVisible(false);
                        DialogAddTemplates.this.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(null, controller.getResultMessage());
                    }
                }
                if (source == buttonCancel) {
                    DialogAddTemplates.this.setVisible(false);
                    DialogAddTemplates.this.setEnabled(false);
                }
            }

        };
        
        this.buttonAdd.addActionListener(this.listenerButton);
        this.buttonCancel.addActionListener(this.listenerButton);
    }
}
