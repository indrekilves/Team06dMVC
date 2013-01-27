package bg.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;



@Entity
public class Type extends BaseEntity {

	
	// Properties 
	
	
	private static final long 			serialVersionUID = 1L;	
	
	@Id
    @GeneratedValue
    @Column(name="type_id")
	private Integer 					id;
	
	@NotEmpty(message="{error.code.empty}")
	@Size(min = 1, max = 10, message="{error.code.size}")
	private String 						code;
	
	@NotEmpty(message="{error.name.empty}")
	@Size(min = 1, max = 100, message="{error.name.size}")
	private String 						name;	

	@DateTimeFormat(style="M-")
    @Temporal( TemporalType.DATE)
	private Date   						fromDate;

    @DateTimeFormat(style="M-")
    @Temporal( TemporalType.DATE)
    private Date   						toDate;	
	
	@OneToMany(mappedBy="boss")
	private List<TypeAssociation> 		subOrdinateAssociations;

	@OneToMany(mappedBy="subOrdinate")
	private List<TypeAssociation> 		bossAssociations;

	@Transient	
	private Type 						boss;
	
	@Transient
	private List<Type>					subOrdinates;
	
	
	// Constructors 
	
	
	public Type() {}
	
	
	// Getters / Setters
	
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	
	
	
	public List<TypeAssociation> getBossAssociations() {
		return bossAssociations;
	}

	public void setBossAssociations(List<TypeAssociation> bossAssociations) {
		this.bossAssociations = bossAssociations;
	}

	

	
	public List<TypeAssociation> getSubOrdinateAssociations() {
		return subOrdinateAssociations;
	}

	public void setSubOrdinateAssociations(List<TypeAssociation> subOrdinateAssociations) {
		this.subOrdinateAssociations = subOrdinateAssociations;
	}


	  

	public Type getBoss() {
		return boss;
	}

	public void setBoss(Type boss) {
		this.boss = boss;
	}


	
	
	public List<Type> getSubOrdinates() {
		return subOrdinates;
	}

	public void setSubOrdinates(List<Type> subOrdinates) {
		this.subOrdinates = subOrdinates;
	}
	
	
	
	
	// Helpers 

	


	@Override
	public String toString() {
		return "Type [id=" + id + ", code=" + code + ", name=" + name + "]";
	}


	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
		{
			return true;
		}
		
		if (obj == null)
		{
			return false;
		}
		
		if (getClass() != obj.getClass())
		{
			return false;
		}
		
		
		Type otherType = (Type) obj;
		if (this.id == null || otherType.getId() == null) 
		{
			return false;
		} 
		
		
		if (this.id == otherType.getId()){
			return true;
		}
		else
		{
			return false;
		}
	}




	
	
	
}
