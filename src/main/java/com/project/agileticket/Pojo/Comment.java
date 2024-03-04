package com.project.agileticket.Pojo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
public class Comment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentid;
    private String text;
    
    @ManyToOne
	@JoinColumn(name="user_userid", referencedColumnName="userid")
    private User user;
    
    private LocalDateTime creationTime;
    
    private LocalDateTime modificationTime;
    
    @ManyToOne
    @JoinColumn(name = "ticket_ticketNumber")
    private Ticket ticket;
    
    
    public int getCommentid()
    {
    	return this.commentid;
    }
    public void setCommentid(int commentid)
    {
    	this.commentid = commentid;
    }
    public String getText()
    {
    	return this.text;
    }
    public void setText(String text)
    {
    	this.text = text;
    }
    public User getUser()
    {
    	return this.user;
    }
    public void setUser(User user)
    {
    	this.user=user;
    }
    public LocalDateTime getCreationTime()
    {
    	return this.creationTime;
    }
    public void setCreationTime(LocalDateTime creationTime)
    {
    	this.creationTime=creationTime;
    }
    public LocalDateTime getModificationTime()
    {
    	return this.modificationTime;
    }
    public void setModificationTime(LocalDateTime modificationTime)
    {
    	this.modificationTime=modificationTime;
    }
    public Ticket getTicket()
    {
    	return this.ticket;
    }
    public void setTicket(Ticket ticket)   
    {
    	this.ticket=ticket;
    }
    
}
