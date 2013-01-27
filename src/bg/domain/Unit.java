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
public class Unit extends BaseEntity{
	
	
	// Properties
	
	
	private static final long serialVersionUID = 1L;

	
	@Id
    @GeneratedValue
    @Column(name="unit_id")
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
	private List<UnitAssociation> 		subOrdinateAssociations;

	@OneToMany(mappedBy="subOrdinate")
	private List<UnitAssociation> 		bossAssociations;

	@Column(name="type_id")
	private Integer          			typeId;
	
	@Transient
	private Type						type;
	
	@Transient	
	private Unit 						boss;
	
	@Transient
	private List<Unit>					subOrdinates;

	
	
	
	// Constructors 
	
	
	
	
	public Unit(){}
	
	
	
	
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

	
	
	
	public List<UnitAssociation> getSubOrdinateAssociations() {
		return subOrdinateAssociations;
	}

	public void setSubOrdinateAssociations(List<UnitAssociation> subOrdinateAssociations) {
		this.subOrdinateAssociations = subOrdinateAssociations;
	}

	
	
	
	public List<UnitAssociation> getBossAssociations() {
		return bossAssociations;
	}

	public void setBossAssociations(List<UnitAssociation> bossAssociations) {
		this.bossAssociations = bossAssociations;
	}

	
	
	
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	
	
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	
	
	
	public Unit getBoss() {
		return boss;
	}

	public void setBoss(Unit boss) {
		this.boss = boss;
	}

	
	
	
	public List<Unit> getSubOrdinates() {
		return subOrdinates;
	}

	public void setSubOrdinates(List<Unit> subOrdinates) {
		this.subOrdinates = subOrdinates;
	}


	
	
	// Helpers 

	
	

	@Override
	public String toString() {
		return "Unit [id=" + id + ", code=" + code + ", name=" + name
				+ ", typeId=" + typeId + "]";
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
		
		
		Unit otherUnit = (Unit) obj;
		if (this.id == null || otherUnit.getId() == null) 
		{
			return false;
		} 
		
		
		if (this.id == otherUnit.getId()){
			return true;
		}
		else
		{
			return false;
		}
	}


}
