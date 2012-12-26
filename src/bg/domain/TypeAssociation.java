package bg.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;



@Entity
@IdClass(TypeAssociationId.class)
public class TypeAssociation extends BaseEntity {

	
	// Properties
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="boss_id")
	private Integer	bossId;
    
	@Id
	@Column(name="subordinate_id")
    private Integer subOrdinateId;


	@ManyToOne
	@PrimaryKeyJoinColumn(name="BOSS_ID", referencedColumnName="TYPE_ID")
	private Type boss;
	
		
	@ManyToOne
	@PrimaryKeyJoinColumn(name="SUBORDINATE_ID", referencedColumnName="TYPE_ID")
	private Type subOrdinate;
 
    
    // Constructors 
    
    
    public TypeAssociation(){}
    
    
    // Getters / Setters
    

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
	

	
	
	public Type getBoss() {
		return boss;
	}




	public void setBoss(Type boss) {
		this.boss = boss;
	}




	public Type getSubOrdinate() {
		return subOrdinate;
	}




	public void setSubOrdinate(Type subOrdinate) {
		this.subOrdinate = subOrdinate;
	}


	

	// Helpers

	
	

	@Override
	public String toString() {
		String bossName 		= boss 			!= null ? boss.getName() 		: "NO BOSS";
		String subOrdinateName 	= subOrdinate 	!= null ? subOrdinate.getName() : "NO SUBORDINATE";
		
		return "TypeAssociation [boss=" + bossName
				+ ", subOrdinate=" + subOrdinateName + "]";
	}
	
	
}
