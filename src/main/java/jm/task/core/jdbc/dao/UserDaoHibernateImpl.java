package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        String hql = "create table if not exists users (" +
                "id bigserial," +
                "name varchar(100)," +
                "last_name varchar(100)," +
                "age smallint);";
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(hql);
            query.executeUpdate();
            transaction.commit();


        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to .createUsersTable", ex);
        }

    }

    @Override
    public void dropUsersTable() {

        String hql = "drop table if exists users;";
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<User> query = session.createSQLQuery(hql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to .dropUsersTable", ex);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to .saveUser name = " + name, ex);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user == null) {
                throw new RuntimeException("User not found!");
            }
            session.delete(user);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to .removeUserById id = " + id, ex);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery("from User", User.class);
            users = query.getResultList();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction == null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to .getAllUsers", ex);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();

    }
}
