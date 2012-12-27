package bg.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.domain.UnitAssociation;

@Repository
public class UnitAssociationDao {

	
	
	
	// Properties
	

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private UnitDao unitDao;
	
	
	
	
	
	// Constructors
	
	
	
	
	public UnitAssociationDao(){}
	
	
	
	
	// Find all 
	

	
	
    @Transactional(readOnly = true)
	public List<UnitAssociation> getAllUnitAccociations() {
		TypedQuery<UnitAssociation> query = em.createQuery("FROM UnitAssociation WHERE opened <= NOW() AND closed >= NOW()", UnitAssociation.class);
    	return query.getResultList();
	}


    
    // Find bosses

    

    @Transactional(readOnly=true)
	public List<UnitAssociation> getBossAssociationsById(Integer id) {
    	if (id == null) return null;
    	
    	String sql = 	"FROM  UnitAssociation "  		+
     					"WHERE subordinate_id = :id "	+
     					"  AND opened <= NOW() " 		+
     					"  AND closed >= NOW() " 		;
        
    	TypedQuery<UnitAssociation> query = em.createQuery(sql, UnitAssociation.class).setParameter("id", id);
        return query.getResultList();
	}

    
    
    
    // Find subOrdinates


    

    @Transactional(readOnly=true)
	public List<UnitAssociation> getSubOrdinateAssociationsById(Integer id) {
    	if (id == null) return null;
    	
    	String sql = 	"FROM  UnitAssociation " +
     					"WHERE boss_id = :id "	 +
     					"  AND opened <= NOW() " +
     					"  AND closed >= NOW() " ;
        
    	TypedQuery<UnitAssociation> query = em.createQuery(sql, UnitAssociation.class).setParameter("id", id);
        return query.getResultList();
	}

}
