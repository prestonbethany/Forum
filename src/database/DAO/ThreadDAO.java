package database.DAO;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import database.models.Thread;

public class ThreadDAO {
    private SessionFactory sessionFactory;
    
    public ThreadDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Thread findById(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        Thread threads = currentSession.get(Thread.class, id);
        transaction.commit();
        return threads;
    }

    public List<Thread> findAll(boolean isArchived) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Thread> criteriaQuery = criteriaBuilder.createQuery(Thread.class);
        //Root selects all columns from the model that is passed in.
        Root<Thread> root = criteriaQuery.from(Thread.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("archivedFlag"), isArchived));
        List<Thread> threadList = currentSession.createQuery(criteriaQuery).getResultList();
        transaction.commit();
        return threadList;
    }

    public long save(Thread thread) {
        Transaction transaction = null;
        long threadId = -1;
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            threadId = (Long)currentSession.save(thread);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return threadId;
    }
}