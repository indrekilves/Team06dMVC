package bg.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class Type {

	@Id
    @GeneratedValue
    @Column(name="type_id")
	private Integer 	id;
	
	private String 		openedBy;
	private Date   		opened;
	private String 		changedBy;
	private Date   		changed;
	private String		closedBy;
	private Date  		closed;
	private String 		code;
	private String 		name;
	private String 		comment;
	private Date   		fromDate;
	private Date   		toDate;
	//private Type		bossType;
	
	@ManyToMany
    @JoinTable(
            name="typeSubordinate",
            joinColumns={@JoinColumn(name="boss_id", referencedColumnName="type_id")},
            inverseJoinColumns={@JoinColumn(name="subOrdinate_id", referencedColumnName="type_id")})
	private List<Type> 	subordinateTypes;
	
	
	
	
	public Type() {}
	
	

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
	
	
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	public Date getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	
	
	public Date getToDate() {
		return toDate;
	}
	
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	
	
//	public Type getBossType() {
//		return bossType;
//	}
//	
//	public void setBossType(Type bossType) {
//		this.bossType = bossType;
//	}
//	
	
	
	public List<Type> getSubordinateTypes() {
		return subordinateTypes;
	}
	
	public void setSubordinateTypes(List<Type> subordinateTypes) {
		this.subordinateTypes = subordinateTypes;
	}



	@Override
	public String toString() {
		return "Type [id=" + id + ", code=" + code + ", name=" + name + "]";
	}
	
	
}
