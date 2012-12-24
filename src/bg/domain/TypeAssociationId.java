package bg.domain;

import java.io.Serializable;

public class TypeAssociationId  implements Serializable {

	
	// Properties 
	
	
	private static final long serialVersionUID = 1L;
	private Integer bossId;
	private Integer subOrdinateId;

	  
	// Constructors 
	
	  
	public TypeAssociationId(){}
	
	
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

	  
	
	// Helpers   
	
	
	public int hashCode() {
	    return (int)(bossId + subOrdinateId);
	}
	
	
	
	
	public boolean equals(Object object) {
		if (object instanceof TypeAssociationId) {
			TypeAssociationId otherId = (TypeAssociationId) object;
			return (otherId.bossId == this.bossId) && (otherId.subOrdinateId == this.subOrdinateId);
	    }
	    return false;
	}

	
	

	@Override
	public String toString() {
		return "TypeAssociationId [bossId=" + bossId + ", subOrdinateId="
				+ subOrdinateId + "]";
	}
	 
	  
	  
	  
	}