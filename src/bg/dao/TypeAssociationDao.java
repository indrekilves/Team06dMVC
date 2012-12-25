package bg.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.domain.TypeAssociation;

@Repository
public class TypeAssociationDao {
	
	
	// Properties

	
	@PersistenceContext
    private EntityManager em;
	
	
	
	
	// Find all
	
	
	
	
    @Transactional(readOnly = true)
    public List<TypeAssociation> getAllTypeAccociations() {
    	TypedQuery<TypeAssociation> query = em.createQuery("FROM TypeAssociation WHERE opened <= NOW() AND closed >= NOW()", TypeAssociation.class);
    	return query.getResultList();
    }

    
    

    // Find all bosses
    
    
    
    
    @Transactional(readOnly = true)
	public List<TypeAssociation> getBossAssociationsById(Integer id) {
    	if (id == null) return null;
    	
    	String sql = 	"FROM  TypeAssociation "  		+
     					"WHERE subordinate_id = :id "	+
     					"  AND opened <= NOW() " 		+
     					"  AND closed >= NOW() " 		;
        
    	TypedQuery<TypeAssociation> query = em.createQuery(sql, TypeAssociation.class).setParameter("id", id);
        return query.getResultList();
	}

    
    

    // Find all subordinates
    
    
    

    @Transactional(readOnly = true)
	public List<TypeAssociation> getSubOrdinateAssociations(Integer id) {
   	if (id == null) return null;
    	
    	String sql = 	"FROM  TypeAssociation " +
     					"WHERE boss_id = :id "	 +
     					"  AND opened <= NOW() " +
     					"  AND closed >= NOW() " ;
        
    	TypedQuery<TypeAssociation> query = em.createQuery(sql, TypeAssociation.class).setParameter("id", id);
        return query.getResultList();
	}

}
