package database.DAO;

import database.SessionManager;

public class DAOFactory {
    private static ThreadDAO threadDao;
    private static PostDAO postDao;
    private static UserDAO userDao;
    private static RoleDAO roleDao;

    public static ThreadDAO getThreadDao() {
        if (threadDao == null) {
            threadDao = new ThreadDAO(SessionManager.getSessionFactory());
        } 
        return threadDao;
    }

    public static PostDAO getPostDao() {
        if (postDao == null) {
            postDao = new PostDAO(SessionManager.getSessionFactory());
        } 
        return postDao;
    }

    public static UserDAO getUserDao() {
        if (userDao == null) {
            userDao = new UserDAO(SessionManager.getSessionFactory());
        } 
        return userDao;
    }

    public static RoleDAO getRoleDao() {
        if (roleDao == null) {
            roleDao = new RoleDAO(SessionManager.getSessionFactory());
        } 
        return roleDao;
    }
}
