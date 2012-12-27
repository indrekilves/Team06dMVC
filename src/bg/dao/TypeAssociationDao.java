package bg.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.domain.BaseEntity;
import bg.domain.Type;
import bg.domain.TypeAssociation;
import bg.service.GenericService;

@Repository
public class TypeAssociationDao {
	
	
	
	
	// Properties

	
	
	
	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private TypeDao typeDao;
	

	
	
	// Find all
	
	
	
	
    @Transactional(readOnly = true)
    public List<TypeAssociation> getAllTypeAccociations() {
    	TypedQuery<TypeAssociation> query = em.createQuery("FROM TypeAssociation WHERE opened <= NOW() AND closed >= NOW()", TypeAssociation.class);
    	return query.getResultList();
    }


    
    
	// Remove all by ID

    
    
    
	public void closeAllTypeAssociationsById(Integer id) {
		if (id == null) return;
		
		List <TypeAssociation> typeAssociations = new ArrayList<TypeAssociation>();
		typeAssociations.addAll(getBossAssociationsById(id));
		typeAssociations.addAll(getSubOrdinateAssociationsById(id));
		
		if (typeAssociations.isEmpty()) return;
		
		for (TypeAssociation typeAssociation : typeAssociations) {
			closeTypeAssociation(typeAssociation);
		}
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
	public List<TypeAssociation> getSubOrdinateAssociationsById(Integer id) {
    	if (id == null) return null;
    	
    	String sql = 	"FROM  TypeAssociation " +
     					"WHERE boss_id = :id "	 +
     					"  AND opened <= NOW() " +
     					"  AND closed >= NOW() " ;
        
    	TypedQuery<TypeAssociation> query = em.createQuery(sql, TypeAssociation.class).setParameter("id", id);
        return query.getResultList();
	}


    

    // Remove subOrdinate

    
    
    
	public void removeSubOrdinateByIds(Integer id, Integer subOrdinateId) {
		if (id == null || subOrdinateId == null) return;

		TypeAssociation oldAssociation = getTypeAssociationByIDs(id, subOrdinateId);
		
		if (oldAssociation != null){
			closeTypeAssociation(oldAssociation);
		}	
	}
	
    
    
    
    
    // Replace boss 
    
    
    

	public void replaceBossAssociation(Integer oldBossId, Integer newBossId, Integer subOrdinateId) {
		if (subOrdinateId == null) return;
		
		TypeAssociation oldAssociation = getTypeAssociationByIDs(oldBossId, subOrdinateId);
		
		if (oldAssociation != null){
			closeTypeAssociation(oldAssociation);
		}
		
		if (newBossId != null){
			addTypeAssociationByIDs(newBossId, subOrdinateId);
		}		
	}

	
	
	
	// Add subOrdinate


	

	public void addSubOrdinateByIds(Integer bossId, Integer subOrdinateId) {
		if (bossId == null || subOrdinateId == null) return;
		
		addTypeAssociationByIDs(bossId, subOrdinateId);		
	}

	
	
	
	// Find One
	
	
	

	@Transactional (readOnly=true)
	private TypeAssociation getTypeAssociationByIDs(Integer bossId, Integer subOrdinateId) {
		if (bossId == null || subOrdinateId == null) return null;
    	
    	String sql = 	"FROM  TypeAssociation " 				+
     					"WHERE boss_id        = :bossId " 		+
     					"  AND subordinate_id = :subOrdinateId" +
     					"  AND opened        <= NOW() " 		+
     					"  AND closed        >= NOW() " 		;
        
    	TypedQuery<TypeAssociation> query = em.createQuery(sql, TypeAssociation.class);
    	query.setParameter("bossId", 		bossId);
    	query.setParameter("subOrdinateId", subOrdinateId);
    	
        return query.getResultList().get(0);		
	}


	

	// Close
	
	
	

	private void closeTypeAssociation(TypeAssociation typeAssociation) {
		typeAssociation.setClosed(BaseEntity.getToday());
		typeAssociation.setClosedBy(BaseEntity.getLoggedUserName());
		save(typeAssociation);
	}


	
	
	// Add 
	
	
	
	
	private void addTypeAssociationByIDs(Integer bossId, Integer subOrdinateId) {
		if (bossId == null || subOrdinateId == null) return;

	
		
		Type boss 			= typeDao.getTypeById(bossId);
		Type subOrdinate 	= typeDao.getTypeById(subOrdinateId);
		if (boss == null || subOrdinate == null) return;
		
		
		TypeAssociation typeAssociation = new TypeAssociation();
		typeAssociation.setBoss(boss);
		typeAssociation.setSubOrdinate(subOrdinate);
		  
		typeAssociation.setBossId(bossId);
		typeAssociation.setSubOrdinateId(subOrdinateId);
		
		save(typeAssociation);
	}
	
	
	


	// Save 


	
	
	private TypeAssociation save(TypeAssociation typeAssociation) {
		EntityManagerFactory emf = GenericService.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
        typeAssociation = em.merge(typeAssociation);
        
		em.getTransaction().commit();
		em.close();
		emf.close();
		
		return typeAssociation;
	}



}
