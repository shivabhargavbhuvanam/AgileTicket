package com.project.agileticket.DAO;

import java.util.List;

import org.springframework.stereotype.Component;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.project.agileticket.Pojo.Comment;
import com.project.agileticket.Util.HibernateUtil;

import jakarta.transaction.Transactional;

@Component
public class CommentRepository extends DAORepository implements ICommentRepository{
	
	public CommentRepository()
	{
		this.sf = HibernateUtil.getSessionFactory();
	}
	
	@Transactional
	public List<Comment> getCommentsByTicketId(int ticket_ticketNumber)
	{
		try(Session session = sf.openSession())
		{
			Query query = getSession().createQuery("FROM Comment WHERE ticket_ticketNumber = :ticket_ticketNumber");
			query.setParameter("ticket_ticketNumber", ticket_ticketNumber);
			if(query != null)
			{
				return query.list();
			}
			return null;
			}
			catch(Exception e)
			{
				System.out.print(e.getMessage());
				return null;
			}
	}
		
		
	@Transactional
	public Comment addCommentByTicket(Comment comment)
	{
		try
		{
			begin();
			session = getSession();
	        session.persist(comment);
	        System.out.println("comment : " + comment.getText());
	        Query<Comment> query = session.createQuery("FROM Comment ORDER BY creationTime DESC", Comment.class);
	        query.setMaxResults(1);
	        Comment latestComment = query.uniqueResult();
	        
	        
	        System.out.println(latestComment.getCommentid());
		    commit();
		    return latestComment;
		}
		catch(Exception e)
		{
			System.out.println("Exception message: " + e.getMessage());
			return null;
		}
	}
	
	@Transactional
	public void editCommentInTicket(Comment comment)
	{
		try {
			begin();
			session = getSession();
			session.merge(comment);
			commit();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
}
