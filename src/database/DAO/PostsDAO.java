package database.DAO;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import database.models.Posts;

public class PostsDAO {
    private SessionFactory sessionFactory;
    
    public PostsDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Posts> findAllByThreadId(long threadId) {
        //Hibernate requires a transaction to interact with the database.
        Transaction transaction = null;
        List<Posts> posts = null;
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
            CriteriaQuery<Posts> criteriaQuery = criteriaBuilder.createQuery(Posts.class);
            //Root selects the columns from the model that is passed in.
            Root<Posts> root = criteriaQuery.from(Posts.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("threadsID"), threadId));
            posts = currentSession.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return posts;
    }

    public void save(Posts posts) {
        Transaction transaction = null;
        
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            currentSession.saveOrUpdate(posts);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
