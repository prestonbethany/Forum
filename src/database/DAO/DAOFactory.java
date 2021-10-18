package database.DAO;

import database.SessionManager;

public class DAOFactory {
    private static ThreadDAO threadDao;
    private static PostDAO postDao;

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
}
