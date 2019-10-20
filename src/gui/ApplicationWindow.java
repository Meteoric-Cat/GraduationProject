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
public class ApplicationWindow extends javax.swing.JFrame {
    
    public static int WINDOW_HEIGHT = 900;
    public static int WINDOW_WIDTH = 1600;
    public static String WINDOW_TITLE = "SNMP Manager";
    
    public static enum PANEL_ID {
        CREATE_ACCOUNT, 
        LOGIN_ACCOUNT,
        MAIN
    }; 
    
    private javax.swing.JPanel panelCurrent;
    private PanelLoginAccount panelLoginAccount;
    private PanelCreateAccount panelCreateAccount;    
    private PanelMain panelMain;
    
    private static ApplicationWindow instance = null;
    
    private ApplicationWindow() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setResizable(false);
        setTitle(WINDOW_TITLE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(null);
        pack();
        
        panelLoginAccount = new PanelLoginAccount();
        getContentPane().add(panelLoginAccount);
        panelLoginAccount.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        panelCurrent = panelLoginAccount;
        
        panelCreateAccount = new PanelCreateAccount();
        panelCreateAccount.setEnabled(false);
        
        panelMain = new PanelMain();
        panelMain.setEnabled(false);
    }        
    
    public static ApplicationWindow getInstance()
    {
        if (instance == null)
        {
            instance = new ApplicationWindow();
        }
        return instance;
    }
    
    public void switchPanel(PANEL_ID id)
    {
        this.disablePanel(panelCurrent);
        
        switch(id)
        {
            case LOGIN_ACCOUNT:
                this.enablePanel(panelLoginAccount);
                break;
            case CREATE_ACCOUNT:
                this.enablePanel(panelCreateAccount);
                break;
            case MAIN:
                this.enablePanel(panelMain);
                break;
        }
        
        this.repaint();
        this.revalidate();
    }
    
    public void disablePanel(javax.swing.JPanel panel)
    {
        this.getContentPane().remove(panel);
        panel.setVisible(false);
        panel.setEnabled(false);
    }
    
    public void enablePanel(javax.swing.JPanel panel)
    {        
        panel.setEnabled(true);
        this.getContentPane().add(panel);
        panel.setBounds(0, 0, panel.getPreferredSize().width, panel.getPreferredSize().height);
        panel.setVisible(true);
        this.panelCurrent = panel;
    }
}
