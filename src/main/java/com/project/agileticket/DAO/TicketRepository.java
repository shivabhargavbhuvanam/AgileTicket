package com.project.agileticket.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.project.agileticket.Pojo.*;
import com.project.agileticket.Util.HibernateUtil;

@Component
public class TicketRepository extends DAORepository implements ITicketRepository {
	
	public TicketRepository() {
		this.sf = HibernateUtil.getSessionFactory();
	}
	
	public List<Ticket> managerRelatedTickets(int manager_userid) {
		try(Session session = sf.openSession()) {
		Query query = getSession().createQuery("FROM Ticket WHERE manager_userid = :manager_userid");
		query.setParameter("manager_userid", manager_userid);
		List<Ticket> list = query.list();
		return list;
		}
		catch(Exception e)
		{
			System.out.print(e.getMessage());
			return null;
		}
	}
	
	public List<Ticket> userRelatedTickets(int ticketUnder_userid)
	{
		try(Session session = sf.openSession()){
		Query query = getSession().createQuery("FROM Ticket WHERE ticketUnder_userid = :ticketUnder_userid");
		query.setParameter("ticketUnder_userid", ticketUnder_userid);
		List<Ticket> list = query.list();
		return list;
		}
		catch(Exception e)
		{
			System.out.print(e.getMessage());
			return null;
		}
	}
	
	public Ticket ticketByNumber(int ticketNumber)
	{
		try(Session session = sf.openSession()) {
			return session.get(Ticket.class, ticketNumber);
		}
		catch(Exception e) {
			throw new RuntimeException("Failed to retrieve ticket by Number: " + e.getMessage());
		}
	}
	
	public Ticket getTicketById(String ticketid)
	{
		try (Session session = sf.openSession()) {
            return session.createQuery("FROM Ticket WHERE ticket_id = :ticketid", Ticket.class)
                    .setParameter("ticketid", ticketid)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user by ID: " + e.getMessage());
        }
	}
	
	public void save(Ticket ticket) {
		try{
			begin();
			session = getSession();
	        session.persist(ticket);
	        Query<Ticket> query = session.createQuery("FROM Ticket ORDER BY creationTime DESC", Ticket.class);
	        query.setMaxResults(1);
	        Ticket latestTicket = query.uniqueResult();
	        latestTicket.setId("TCKT-"+latestTicket.getTicketNumber());
	        session.merge(latestTicket);
	        System.out.println(latestTicket.getId());
		    commit();
		} catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        throw e;
		} 
	}


	public Ticket merge(Ticket ticket) {
		try{
			begin();
	        getSession().merge(ticket);
		    commit();
		    return ticket;
		} catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        throw e;
		} 
	}

	public Ticket delete(Ticket ticket) {
		try{
			begin();
	        getSession().delete(ticket);
		    commit();
		    return new Ticket();
		} catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        return null;
		}
	}
}
