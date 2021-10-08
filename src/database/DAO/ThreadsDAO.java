package database.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.models.Threads;

public class ThreadsDAO {
    private Session currentSession;
    
    public ThreadsDAO(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Threads findById(long id) {
        Transaction transaction = currentSession.beginTransaction();
        Threads threads = currentSession.get(Threads.class, id);
        transaction.commit();
        return threads;
    }
}