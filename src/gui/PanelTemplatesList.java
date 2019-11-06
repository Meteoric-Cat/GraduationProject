/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.TemplateManagementController;
import data.models.Templates;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cloud
 */
public class PanelTemplatesList extends javax.swing.JPanel {

    private String[] colNames = {"Name", "Description", "SNMP Version", "Imported Time"};
    private final int STATE_COL_ID = 7;
    private final int LABEL_COL_ID = 3;

    private ArrayList<String> templateIds;

    private javax.swing.JButton buttonImport;
//    private javax.swing.JLabel labelDisabled;
//    private javax.swing.JLabel labelDisabledValue;
//    private javax.swing.JLabel labelEnabled;
//    private javax.swing.JLabel labelEnabledValue;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JLabel labelTotalValue;
    private javax.swing.JScrollPane scrollpanelTemplates;
    private javax.swing.JTable tableTemplates;

    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JMenuItem mitemCheck;
    private javax.swing.JMenuItem mitemDelete;

    private java.awt.event.ActionListener listenerButton;
    private java.awt.event.ActionListener listenerMenuItem;

    private MouseAdapter mlistenerTable;
    private int counterRowClicking;

    public PanelTemplatesList() {
        initComponents();
        initViewData();
        initListeners();

        this.counterRowClicking = 0;
        this.templateIds = new ArrayList<String>();
    }

    private void initComponents() {

        labelTotal = new javax.swing.JLabel();
        labelTotalValue = new javax.swing.JLabel();
//        labelEnabled = new javax.swing.JLabel();
//        labelEnabledValue = new javax.swing.JLabel();
//        labelDisabled = new javax.swing.JLabel();
//        labelDisabledValue = new javax.swing.JLabel();
        buttonImport = new javax.swing.JButton();
        scrollpanelTemplates = new javax.swing.JScrollPane();
        tableTemplates = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(1500, 900));
        setMinimumSize(new java.awt.Dimension(1500, 900));
        setPreferredSize(new java.awt.Dimension(1500, 900));
        setLayout(null);

        labelTotal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelTotal.setText("Total:");
        add(labelTotal);
        labelTotal.setBounds(170, 30, 36, 19);

        labelTotalValue.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        labelTotalValue.setText("...");
        labelTotalValue.setMaximumSize(new java.awt.Dimension(30, 19));
        labelTotalValue.setMinimumSize(new java.awt.Dimension(30, 19));
        labelTotalValue.setPreferredSize(new java.awt.Dimension(30, 19));
        add(labelTotalValue);
        labelTotalValue.setBounds(210, 30, 30, 19);

//        labelEnabled.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
//        labelEnabled.setText("Enabled:");
//        add(labelEnabled);
//        labelEnabled.setBounds(270, 30, 56, 19);
//
//        labelEnabledValue.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
//        labelEnabledValue.setText("...");
//        labelEnabledValue.setMaximumSize(new java.awt.Dimension(30, 19));
//        labelEnabledValue.setMinimumSize(new java.awt.Dimension(30, 19));
//        labelEnabledValue.setPreferredSize(new java.awt.Dimension(30, 19));
//        add(labelEnabledValue);
//        labelEnabledValue.setBounds(330, 30, 30, 19);
//
//        labelDisabled.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
//        labelDisabled.setText("Disabled:");
//        add(labelDisabled);
//        labelDisabled.setBounds(390, 30, 59, 19);
//
//        labelDisabledValue.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
//        labelDisabledValue.setText("...");
//        labelDisabledValue.setMaximumSize(new java.awt.Dimension(30, 19));
//        labelDisabledValue.setMinimumSize(new java.awt.Dimension(30, 19));
//        labelDisabledValue.setPreferredSize(new java.awt.Dimension(30, 19));
//        add(labelDisabledValue);
//        labelDisabledValue.setBounds(450, 30, 30, 19);
        buttonImport.setText("Import");
        buttonImport.setMaximumSize(new java.awt.Dimension(80, 30));
        buttonImport.setMinimumSize(new java.awt.Dimension(80, 30));
        buttonImport.setPreferredSize(new java.awt.Dimension(80, 30));
        add(buttonImport);
        buttonImport.setBounds(1210, 20, 80, 30);

        tableTemplates.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                this.colNames
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });
        scrollpanelTemplates.setViewportView(tableTemplates);

        add(scrollpanelTemplates);
        scrollpanelTemplates.setBounds(60, 70, 1380, 770);

        initMenus();
    }

    private void initMenus() {
        this.popupMenu = new javax.swing.JPopupMenu();
        this.mitemCheck = new javax.swing.JMenuItem("Check");
        this.mitemDelete = new javax.swing.JMenuItem("Delete");

        this.popupMenu.add(mitemCheck);
        this.popupMenu.add(mitemDelete);

        this.tableTemplates.setComponentPopupMenu(this.popupMenu);
    }

    private void initListeners() {
        this.listenerButton = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JButton source = (javax.swing.JButton) e.getSource();
                if (source == buttonImport) {
                    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "csv");
                    int result = fileChooser.showOpenDialog(null);

                    if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                        TemplateManagementController controller = new TemplateManagementController();
                        String[] templateInfo = controller.processImportTemplate(fileChooser.getSelectedFile().getAbsolutePath());
                        PanelTemplatesList.this.updateViewData(templateInfo);
                    }
                }
            }
        };
        this.buttonImport.addActionListener(this.listenerButton);

        this.listenerMenuItem = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JMenuItem source = (javax.swing.JMenuItem) e.getSource();
                if (source == mitemDelete) {
                    int[] selectedRows = tableTemplates.getSelectedRows();
                    if (selectedRows != null) {
                        String[] selectedTemplates = new String[selectedRows.length];
                        for (int i = 0; i < selectedRows.length; i++) {
                            selectedTemplates[i] = PanelTemplatesList.this.templateIds.get(selectedRows[i]);
                        }

                        TemplateManagementController controller = new TemplateManagementController();
                        controller.processTemplatesDeletion(selectedTemplates);

                        PanelTemplatesList.this.initViewData();
                        JOptionPane.showMessageDialog(null, controller.getResultMessage());
                    }
                }
                if (source == mitemCheck) {

                }
            }
        };
        this.mitemCheck.addActionListener(listenerMenuItem);
        this.mitemDelete.addActionListener(listenerMenuItem);

        this.mlistenerTable = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                counterRowClicking++;
                if (counterRowClicking == 2) {
                    counterRowClicking = 0;
                    if (tableTemplates.getSelectedRowCount() == 1) {
                        ApplicationWindow.getInstance().getPanelMain().switchPanelForDisplayDetail(PanelMain.PANEL_ID.PANEL_DEVICE_INFO);
                        ApplicationWindow.getInstance().getPanelMain()
                                .getPanelTemplateInfo().initViewData(PanelTemplatesList.this.templateIds.get(tableTemplates.getSelectedRow()));
                    }
                }
            }
        };
        this.tableTemplates.addMouseListener(this.mlistenerTable);
    }

    private void initViewData() {
        DefaultTableModel tableModel = (DefaultTableModel) this.tableTemplates.getModel();
        for (int i = tableModel.getRowCount(); i > 0; i--) {
            tableModel.removeRow(i - 1);
        }

//        this.labelDisabledValue.setText("0");
//        this.labelEnabledValue.setText("0");
        this.labelTotalValue.setText("0");

        TemplateManagementController controller = new TemplateManagementController();
        ArrayList<String[]> templateList = controller.processInitTemplateList();

        this.updateViewData(templateList);
    }

    public void refreshViewData() {
        this.initViewData();
    }

    private void showPopup(int x, int y) {
        this.popupMenu.show(this, x, y);
    }

    private void updateViewData(ArrayList<String[]> templatesInfo) {
        int listSize = templatesInfo.size();
        DefaultTableModel tableModel = (DefaultTableModel) this.tableTemplates.getModel();
        for (int i = 0; i < listSize; i++) {
            this.addRowToTable(tableModel, templatesInfo.get(i));
        }

        this.labelTotalValue.setText(String.valueOf(Integer.parseInt(this.labelTotalValue.getText()) + templatesInfo.size()));

//        int enabledCount = Integer.parseInt(this.labelEnabledValue.getText());
//        int disabledCount = Integer.parseInt(this.labelDisabledValue.getText());
//
//        this.labelEnabledValue.setText(String.valueOf(enabledCount));
//        this.labelDisabledValue.setText(String.valueOf(disabledCount));
        this.revalidate();
        this.repaint();
    }

    private void updateViewData(String[] templateInfo) {
        DefaultTableModel tableModel = (DefaultTableModel) this.tableTemplates.getModel();        
        this.addRowToTable(tableModel, templateInfo);
        this.labelTotalValue.setText(String.valueOf(Integer.parseInt(this.labelTotalValue.getText()) + 1));

        this.revalidate();
        this.repaint();
    }
    
    private void addRowToTable(DefaultTableModel tableModel, String[] templateInfo) {
        if (templateInfo.length == this.colNames.length) {
            tableModel.addRow(templateInfo);
            return;
        }        
        
        this.templateIds.add(templateInfo[0]);        
        String[] temp = new String[this.colNames.length];
        for (int i = 0; i < this.colNames.length; i++) {
            temp[i] = templateInfo[i + 1];
        }
        tableModel.addRow(temp);
    }
}
