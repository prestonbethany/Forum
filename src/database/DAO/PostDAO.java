package database.DAO;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import database.models.Post;

public class PostDAO {
    private SessionFactory sessionFactory;
    
    public PostDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Post> findAllByThreadId(long threadId) {
        //Hibernate requires a transaction to interact with the database.
        Transaction transaction = null;
        List<Post> posts = null;
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
            CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
            //Root selects the columns from the model that is passed in.
            Root<Post> root = criteriaQuery.from(Post.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("threadID"), threadId));
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

    public long save(Post post) {
        Transaction transaction = null;
        long postId = -1;
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            transaction = currentSession.beginTransaction();
            postId = (Long)currentSession.save(post);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return postId;
    }
}
