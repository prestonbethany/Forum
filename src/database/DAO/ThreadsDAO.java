package database.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import database.models.Threads;

public class ThreadsDAO {
    private SessionFactory sessionFactory;
    
    public ThreadsDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Threads findById(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        Threads threads = currentSession.get(Threads.class, id);
        transaction.commit();
        return threads;
    }
}