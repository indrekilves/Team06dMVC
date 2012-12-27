package bg.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;


@Entity
public class UnitAssociation extends BaseEntity{

	
	// Properties 
	
	
	private static final long serialVersionUID = 1L;

	
	@Id
    @GeneratedValue
    @Column(name="unitAssociation_id")
	private Integer id;
	
	@Column(name="boss_id")
	private Integer	bossId;
    
	@Column(name="subordinate_id")
    private Integer subOrdinateId;


	@ManyToOne
	@PrimaryKeyJoinColumn(name="BOSS_ID", referencedColumnName="UNIT_ID")
	private Unit boss;
	
		
	@ManyToOne
	@PrimaryKeyJoinColumn(name="SUBORDINATE_ID", referencedColumnName="UNIT_ID")
	private Unit subOrdinate;
 
	
	
	
	// Constructors
	
	
	
	
	public UnitAssociation(){}



	
	// Getters / Setters

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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




	public Unit getBoss() {
		return boss;
	}

	public void setBoss(Unit boss) {
		this.boss = boss;
	}




	public Unit getSubOrdinate() {
		return subOrdinate;
	}

	public void setSubOrdinate(Unit subOrdinate) {
		this.subOrdinate = subOrdinate;
	}


	

	// Helpers
	
	
	

	@Override
	public String toString() {
		String bossName 		= boss 			!= null ? boss.getName() 		: "NO BOSS";
		String subOrdinateName 	= subOrdinate 	!= null ? subOrdinate.getName() : "NO SUBORDINATE";
		
		return "UnitAssociation [id=" + id + ", boss=" + bossName
				+ ", subOrdinate=" + subOrdinateName + "]";
	}
	
	
	
	
	
	
}
