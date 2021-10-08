package database.DAO;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.models.Posts;

public class PostsDAO {
    private Session currentSession;
    
    public PostsDAO(Session currentSession) {
        this.currentSession = currentSession;
    }

    public List<Posts> findAllByThreadId(long threadId) {
        //TODO(Preston 7/10/2021): See why Posts is Null with Correct threadID
        //Hibernate requires a transaction to interact with the database.
        Transaction transaction = null;
        List<Posts> posts = null;
        try {
            transaction = currentSession.beginTransaction();
            CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
            CriteriaQuery<Posts> criteriaQuery = criteriaBuilder.createQuery(Posts.class);
            //Root selects the columns from the model that is passed in.
            Root<Posts> root = criteriaQuery.from(Posts.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("ThreadsID"), threadId));
            posts = currentSession.createQuery(criteriaQuery).getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return posts;
    }
}
