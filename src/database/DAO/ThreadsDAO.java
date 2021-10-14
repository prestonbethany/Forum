package database.DAO;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

    public List<Threads> findAll(boolean isArchived) {
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.beginTransaction();
        CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Threads> criteriaQuery = criteriaBuilder.createQuery(Threads.class);
        //Root selects all columns from the model that is passed in.
        Root<Threads> root = criteriaQuery.from(Threads.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("archivedFlag"), isArchived));
        List<Threads> threadList = currentSession.createQuery(criteriaQuery).getResultList();
        transaction.commit();
        return threadList;
    }

    public long save(Threads threads) {
        Transaction transaction = null;
        long threadId = -1;
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            threadId = (Long)currentSession.save(threads);
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