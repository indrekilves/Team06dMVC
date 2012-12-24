package bg.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@EntityListeners({EntityListener.class})
public class BaseEntity implements Serializable, Creatable, Updatable {

	
	// Properties
	
	
	private static final long serialVersionUID = 1L;

	protected String 	openedBy;
	protected Date		opened;
	
	protected String 	changedBy;
	protected Date 		changed;

	protected String	closedBy;
	protected Date		closed;

	protected String 	comment;

	
	// Getters / Setters 

	
	public String getOpenedBy() {
		return openedBy;
	}

	public void setOpenedBy(String openedBy) {
		this.openedBy = openedBy;
	}


	
	
	public Date getOpened() {
		return opened;
	}

	public void setOpened(Date opened) {
		this.opened = opened;
	}

	
	

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}


	
	public Date getChanged() {
		return changed;
	}

	public void setChanged(Date changed) {
		this.changed = changed;
	}


	
	public String getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	

	public Date getClosed() {
		return closed;
	}

	public void setClosed(Date closed) {
		this.closed = closed;
	}


	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	
	
	
	// Helpers 
	
	
	

	public static Date getToday() {
		return new Date();
		
	}
	
	
	
	
	public static Date getEndOfTimeDate() {
		return new Date(253402207200000L); //9999-12-31
	}
	
	
	
	
	public static String getLoggedUserName(){
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//if (auth != null){
		//	return auth.getName(); 
		//} 
		
	    return "anonymus";		
	}


	


}
