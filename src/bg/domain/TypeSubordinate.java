package bg.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class TypeSubordinate {
	
	// TODO tee selle kohta ka pojo
	//  
	// vaata /Users/indrekilves/Temp/webJava2012Old/src/lab4/

	
	@Id
    @GeneratedValue
    @Column(name="type_id")
	private Integer 					id;
	
	private String 						openedBy;
	private Date   						opened;
	private String				 		changedBy;
	private Date   						changed;
	private String						closedBy;
	private Date  						closed;
	private String						comment;

    @Column(name="boss_id")
	private Integer						bossId;
    
    @Column(name="subOrdinate_id")
    private Integer 					subOrdinateId;
	
    
    
    
	public void TypeSubordinate() {
		
	}

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
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




	public Integer getBossId() {
		return bossId;
	}




	public void setBossId(Integer bossId) {
		this.bossId = bossId;
	}




	public Integer getSubOrdinateId() {
		return subOrdinateId;
	}




	public void setSubOrdinateId(Integer subOrdinateId) {
		this.subOrdinateId = subOrdinateId;
	}




	@Override
	public String toString() {
		return "TypeSubordinate [id=" + id + ", bossId=" + bossId
				+ ", subOrdinateId=" + subOrdinateId + "]";
	}
	
	
	
	


}
