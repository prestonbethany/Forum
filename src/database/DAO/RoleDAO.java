package database.DAO;

import org.hibernate.SessionFactory;

public class RoleDAO {
    private SessionFactory sessionFactory;
    
    public RoleDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
