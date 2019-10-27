/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import data.DataManager;
import data.models.Users;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        this.saveAccountId(account, password);        
        this.resultMessage = resultMessenger.LOGIN_SUCCESS;
        return true;
    }
    
    public void saveAccountId(String account, String password) {
        Users.setLoggedAccountId(DataManager.getInstance().getUsers().getAccountId(DataManager.getInstance().getDatabaseConnection(), account, password));
    }
    
    public String[] getUserInfo(String[] orders) {
        return DataManager.getInstance().getUsers().getUserInfo(DataManager.getInstance().getDatabaseConnection(), orders);
    }
    
    public boolean saveUserInfo(String[] info) {
        SaveUserInfoResultMessenger resultMessenger = new SaveUserInfoResultMessenger();
        if (DataManager.getInstance().getUsers().updateUserInfo(DataManager.getInstance().getDatabaseConnection(), info)) {
            resultMessage = resultMessenger.UPDATE_SUCCESS;
            return true;
        }
        resultMessage = resultMessenger.UPDATE_FAILED;
        return false;
    }   
    
    public String[] getLoggedAccount() {
        return DataManager.getInstance().getUsers().getAccountData(DataManager.getInstance().getDatabaseConnection());
    }
    
    public boolean processChangePassword(String password, String confirm) {
        ChangePasswordResultMessenger resultMessenger = new ChangePasswordResultMessenger();
        if (!password.equals(confirm)) {
            this.resultMessage = resultMessenger.CONFIRM_MISMATCH;
            return false;
        }
        
        if (DataManager.getInstance().getUsers().savePassword(DataManager.getInstance().getDatabaseConnection(), password)) {
            this.resultMessage = resultMessenger.CHANGE_SUCCESS;
            return true;
        }
        this.resultMessage = resultMessenger.CHANGE_FAILED;
        return false;
    }
    
    public String proccessChangeAccountImage(File file) {
        String result = null;
        
        if (file != null) {
            File systemFile = new File(DataManager.getInstance().getUserImagesPath() + file.getName());
            try {
                Files.copy(file.toPath(), systemFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                DataManager.getInstance().getUsers().saveAccountImage(DataManager.getInstance().getDatabaseConnection(), systemFile.getAbsolutePath());
                result = file.getAbsolutePath();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return result;
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
    
    public class SaveUserInfoResultMessenger {
        public final String UPDATE_SUCCESS = "User information was updated successfully";
        public final String UPDATE_FAILED = "Update user information is failed, do it again";
    }
    
    public class ChangePasswordResultMessenger {
        public final String CHANGE_SUCCESS = "Your password was changed successfully";        
        public final String CHANGE_FAILED = "Couldn't change your password, do it again";
        public final String CONFIRM_MISMATCH = "Your password confirmation is incorrect, input again";
    }    
}
