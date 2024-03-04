package com.project.agileticket.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.agileticket.DAO.ITicketRepository;
import com.project.agileticket.Pojo.Ticket;

@Service
public class TicketService implements ITicketService{

	@Autowired
	private ITicketRepository ticketRepository;

	@Override
	public Ticket createTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		System.out.println(ticket.getName());
		ticketRepository.save(ticket);
		return ticket;
	}

	@Override
	public void updateTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ticket deleteTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		try {
			return ticketRepository.delete(ticket);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			
			return null;
		}
	}

	@Override
	public Ticket getTicketById(String ticketId)
	{
		Ticket ticket = ticketRepository.getTicketById(ticketId);
		return ticket;
	}

	@Override
	public List<Ticket> getTicketsCreatedBytManager(int userid) {
		// TODO Auto-generated method stub
		try {
			return ticketRepository.managerRelatedTickets(userid);
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace().toString());
			return null;
		}
	}

	@Override
	public List<Ticket> getTicketsUnderUser(int userid) {
		// TODO Auto-generated method stub
		try {
			return ticketRepository.userRelatedTickets(userid);
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace().toString());
			return null;
		}
	}
	
	@Override
	public Ticket editTicketByManager(Ticket ticket)
	{
		return ticketRepository.merge(ticket);
	}
	
	@Override
	public Ticket editTicketByDeveloper(Ticket tickets)
	{
		return new Ticket();
	}
}
