package view.actions;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import database.DAO.DAOFactory;
import database.models.User;

public class LoginAction extends ActionSupport implements SessionAware {
    private String userName;
    private SessionMap<String,Object> sessionMap;  
    private String errorMessage;
//getters and setters  

    @Override  
    public void setSession(Map<String, Object> map) {  
        sessionMap = (SessionMap<String, Object>) map;  
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    //Controller Actions

    @Override
    public String execute() {
        //TODO(Preston): Check if username is already logged in so as not to make duplicates in the session map.
        String name = ServletActionContext.getRequest().getParameter("userName");
        String password = ServletActionContext.getRequest().getParameter("password");
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return INPUT;
        }
        userName = new String(name);
        User dbUser = DAOFactory.getUserDao().findByName(userName);
        if (dbUser != null && dbUser.getRoleID().isLoginToApp()) {
            try {
                // hash the userpassword and compare it with the DB entry
                MessageDigest digest = MessageDigest.getInstance("SHA3-256");
                final byte[] hashbytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexString = new StringBuilder(2 * hashbytes.length);
                for (int i = 0; i < hashbytes.length; i++) {
                    String hex = Integer.toHexString(0xff & hashbytes[i]);
                    if(hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
                String sha3Hex = hexString.toString();
    
                if (sha3Hex.equals(dbUser.getPassword())) {
                    sessionMap.put("userName", userName);
                    return SUCCESS;
                }
            } catch (NoSuchAlgorithmException nsae) {
                nsae.printStackTrace();
            }
            errorMessage = "Password or username is incorrect.";
            return ERROR;
        } else {
            errorMessage = "Something went catastrophically wrong. Please send a report of the exact steps used to get to this point to the system administrator.";
            return ERROR;
        }
    }
}