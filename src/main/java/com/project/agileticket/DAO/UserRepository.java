package com.project.agileticket.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.project.agileticket.Pojo.*;
import com.project.agileticket.Util.HibernateUtil;

import jakarta.transaction.Transactional;

@Component
public class UserRepository extends DAORepository implements IUserRepository {
	
	public UserRepository() {
        this.sf = HibernateUtil.getSessionFactory();
    }
	
	@Transactional
    public List<User> getUsers() {
        try (Session session = sf.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve users: " + e.getMessage());
        }
    }

    @Transactional
    public User findUserById(int userId) {
        try (Session session = sf.openSession()) {
            return session.get(User.class, userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user by ID: " + e.getMessage());
        }
    }

    @Transactional
    public User getUserByUsername(String name) {
        try (Session session = sf.openSession()) {
            return session.createQuery("FROM User WHERE name = :name", User.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user by username: " + e.getMessage());
        }
    }

    @Transactional
    public void save(User user) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user: " + e.getMessage());
        }
    }

    @Transactional
    public void update(User user) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user: " + e.getMessage());
        }
    }

    @Transactional
    public void merge(User user) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to merge user: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(User user) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage());
        }
    }

	@Override
	public void update(int userid, User user) {
		// TODO Auto-generated method stub
		
	}
	
}
