/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import data.DataManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author cloud
 */
public class UserManagementController {    
    private final int DEFAULT_ACCOUNT_INFO_LENGTH = 4;
    private String resultMessage;
    
    private String[] savedAccounts;
    
    public UserManagementController() {
        this.resultMessage = null;
    }
    
    public boolean processAccountCreation(String[] accountInfo, String[] otherInfo) {
        CreationResultMessenger resultMessenger = new CreationResultMessenger();
        
        for (String fieldValue : accountInfo)
            if (fieldValue.isEmpty()) {
                this.resultMessage = resultMessenger.FIELD_EMPTY_ERROR;
                return false;
            }
        
        for (String fieldValue : otherInfo)
            if (fieldValue.isEmpty()) {
                this.resultMessage = resultMessenger.FIELD_EMPTY_ERROR;
                return false;
            }
        
        if (DEFAULT_ACCOUNT_INFO_LENGTH == accountInfo.length) {
            if (!Boolean.valueOf(accountInfo[accountInfo.length - 1])) {
                this.resultMessage = resultMessenger.CHECKBOX_ERROR;
                return false;
            }
            
            if (!accountInfo[accountInfo.length - 3].equals(accountInfo[accountInfo.length - 2])) {
                this.resultMessage = resultMessenger.CONFIRM_MISMATCH;
                return false;
            }
            
            if (!this.checkAccountAvailability(accountInfo[0])) {
                this.resultMessage = resultMessenger.ACCOUNT_CREATED;
                return false;
            }
        }  
        
        if (!DataManager.getInstance().getUsers().createUser(DataManager.getInstance().getDatabaseConnection(), accountInfo, otherInfo)) {
            this.resultMessage = resultMessenger.SQL_ERROR;
            return false;
        }
        this.resultMessage = resultMessenger.CREATION_SUCCESS;
        return true;
    }

    private boolean checkAccountAvailability(String account) {
        if (DataManager.getInstance().getUsers().countAccount(DataManager.getInstance().getDatabaseConnection(), account) > 0)
            return false;
        return true;
    }
    
    public boolean checkPasswordRemembering(String account)
    {
        return false;
    }
    
    public String[] processPreLogin(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            if (this.savedAccounts == null)
                this.savedAccounts = DataManager.getInstance().getUsers().getSavedAccounts(DataManager.getInstance().getDatabaseConnection());
            return this.savedAccounts;
        } else {
            if (this.savedAccounts != null) {
                List<String> result = new ArrayList<String>();
                for (String account : savedAccounts)
                    if (account.startsWith(prefix)) 
                        result.add(account);
                return Arrays.copyOf(result.toArray(), result.size(), String[].class);
            }            
        }
        return null;
    }
    
    public String processPasswordRetrieval(String account) {
        if (!account.isEmpty()) {
            return DataManager.getInstance().getUsers().getPassword(DataManager.getInstance().getDatabaseConnection(), account);
        }
        return "";
    }
    
    public boolean processLogin(String account, String password, boolean isRemembered)
    {
        LoginResultMessenger resultMessenger = new LoginResultMessenger();
        if (account.isEmpty() || password.isEmpty()) {
            this.resultMessage = resultMessenger.FIELD_EMPTY_ERROR;
            return false;
        }
        
        if (DataManager.getInstance().getUsers().countAccount(DataManager.getInstance().getDatabaseConnection(), account, password) == 0) {
            this.resultMessage = resultMessenger.INVALID_ACCOUNT;
            return false;
        }
        
        DataManager.getInstance().getUsers().updateAccountLogin(DataManager.getInstance().getDatabaseConnection(), account, password, isRemembered);
        this.resultMessage = resultMessenger.LOGIN_SUCCESS;
        return true;
    }
    
    public String getResultMessage() {
        return this.resultMessage;
    }
    
    public class CreationResultMessenger {
        public final String FIELD_EMPTY_ERROR = "Fill all the required fields please";
        public final String CHECKBOX_ERROR = "You must accept the term in order to use this software";
        public final String CONFIRM_MISMATCH = "Your confirmation is incorrect, input again";
        public final String ACCOUNT_CREATED = "Account has been created, create another";
        public final String SQL_ERROR = "An error has happened in interaction with database, try it again";
        
        public final String CREATION_SUCCESS = "Your account has been created successfully";
    }
    
    public class LoginResultMessenger {
        public final String FIELD_EMPTY_ERROR = "Fill all the required fields please";
        public final String INVALID_ACCOUNT = "Your account or password is incorrect, input again";
        
        public final String LOGIN_SUCCESS = "Login success";
    }
}
