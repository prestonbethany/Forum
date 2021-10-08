package database.DAO;

import database.SessionManager;

public class DAOFactory {
    private static ThreadsDAO threadsDao;
    private static PostsDAO postsDao;

    public static ThreadsDAO getThreadsDao() {
        if (threadsDao == null) {
            threadsDao = new ThreadsDAO(SessionManager.getSessionFactory().getCurrentSession());
        } 
        return threadsDao;
    }

    public static PostsDAO getPostsDao() {
        if (postsDao == null) {
            postsDao = new PostsDAO(SessionManager.getSessionFactory().getCurrentSession());
        } 
        return postsDao;
    }
}
