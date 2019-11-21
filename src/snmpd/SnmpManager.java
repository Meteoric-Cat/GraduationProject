/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snmpd;

import java.io.IOException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.soulwing.snmp.Mib;
import org.soulwing.snmp.MibFactory;
import org.soulwing.snmp.ModuleParseException;

/**
 *
 * @author cloud
 */
public class SnmpManager {
    private Mib systemMib;
    
    private static SnmpManager instance = null;
    private Timer queryTimer;
    
    private SnmpManager() {        
        initSystemMib();
        initOtherComponents();
    }
    
    private void initSystemMib() {
        this.systemMib = MibFactory.getInstance().newMib();
        String[] mibModules = {"SNMPv2-MIB", "IF-MIB", "IP-MIB"};
        for (String module : mibModules) {
            try {
                this.systemMib.load(module);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void initOtherComponents() {
        this.queryTimer = new Timer();
    }
    
    public static SnmpManager getInstance() {
        if (instance == null) {
            instance = new SnmpManager();            
        }
        return instance;
    }
    
    public Mib getSystemMib() {
        return this.systemMib;
    }
        
    public Timer getQueryTimer() {
        return this.queryTimer;
    }
    
    public void cancelQueryTimerTasks() {
        this.queryTimer.cancel();
        this.queryTimer.purge();
        
        this.queryTimer = new Timer();        
    }
    
    public class SNMPVersion {
        public static final String VERSION_1 = "v1";
        public static final String VERSION_2 = "v2";
        public static final String VERSION_2_COMMUNITY = "v2c";
        public static final String VERSION_3 = "v3";
    }    
}
