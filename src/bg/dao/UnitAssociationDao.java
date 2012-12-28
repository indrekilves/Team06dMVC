package bg.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.domain.BaseEntity;
import bg.domain.Unit;
import bg.domain.UnitAssociation;
import bg.service.GenericService;

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
    	return getSubOrdinateAssociationsByIdAndDate(id, new Date());
	}

    
	public List<UnitAssociation> getSubOrdinateAssociationsByIdAndDate(Integer id, Date date) {
    	if (id == null || date == null) return null;
    	
    	String sql = 	"FROM  UnitAssociation " +
     					"WHERE boss_id = :id   " +
     					"  AND opened <= :date " +
     					"  AND closed >= :date " ;
        
    	TypedQuery<UnitAssociation> query = em.createQuery(sql, UnitAssociation.class);
    	query.setParameter("id",   id);
    	query.setParameter("date", date);
        return query.getResultList();
	}
	
    
    
    // Replace boss

    
    

	public void replaceBossAssociation(Integer oldBossId, Integer newBossId, Integer subOrdinateId) {
		if (subOrdinateId == null) return;
		
		UnitAssociation oldAssociation = getUnitAssociationByIDs(oldBossId, subOrdinateId);
		
		if (oldAssociation != null){
			closeUnitAssociation(oldAssociation);
		}
		
		if (newBossId != null){
			addUnitAssociationByIDs(newBossId, subOrdinateId);
		}			
	}


	
	
	// Find one

	
	
	
	private UnitAssociation getUnitAssociationByIDs(Integer bossId, Integer subOrdinateId) {
		if (bossId == null || subOrdinateId == null) return null;
    	
    	String sql = 	"FROM  UnitAssociation " 				+
     					"WHERE boss_id        = :bossId " 		+
     					"  AND subordinate_id = :subOrdinateId" +
     					"  AND opened        <= NOW() " 		+
     					"  AND closed        >= NOW() " 		;
        
    	TypedQuery<UnitAssociation> query = em.createQuery(sql, UnitAssociation.class);
    	query.setParameter("bossId", 		bossId);
    	query.setParameter("subOrdinateId", subOrdinateId);
    	
        return query.getResultList().get(0);		        
	}


	

	// Close
	
	
	

	private void closeUnitAssociation(UnitAssociation unitAssociation) {
		unitAssociation.setClosed(BaseEntity.getToday());
		unitAssociation.setClosedBy(BaseEntity.getLoggedUserName());
		save(unitAssociation);	
	}


	

	// Add

	
	
	
	private void addUnitAssociationByIDs(Integer bossId, Integer subOrdinateId) {
		if (bossId == null || subOrdinateId == null) return;

		
		Unit boss 			= unitDao.getUnitById(bossId);
		Unit subOrdinate 	= unitDao.getUnitById(subOrdinateId);
		if (boss == null || subOrdinate == null) return;
		
		
		UnitAssociation unitAssociation = new UnitAssociation();
		unitAssociation.setBoss(boss);
		unitAssociation.setSubOrdinate(subOrdinate);
		  
		unitAssociation.setBossId(bossId);
		unitAssociation.setSubOrdinateId(subOrdinateId);
		
		save(unitAssociation);	
	}


	
	// Save 


	private UnitAssociation save(UnitAssociation unitAssociation) {
		EntityManagerFactory emf = GenericService.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
        unitAssociation = em.merge(unitAssociation);
        
		em.getTransaction().commit();
		em.close();
		emf.close();
		
		return unitAssociation;		
	}




    // Remove subOrdinate

    
    
    
	public void removeSubOrdinateByIds(Integer id, Integer subOrdinateId) {
		if (id == null || subOrdinateId == null) return;

		UnitAssociation oldAssociation = getUnitAssociationByIDs(id, subOrdinateId);
		
		if (oldAssociation != null){
			closeUnitAssociation(oldAssociation);
		}	
	}


	

	// Add subOrdinate
	
	
	

	public void addSubOrdinateByIds(Integer bossId, Integer subOrdinateId) {
		if (bossId == null || subOrdinateId == null) return;
		
		addUnitAssociationByIDs(bossId, subOrdinateId);				
	}


	

	// Close all units

	
	
	
	public void closeAllTypeAssociationsById(Integer id) {
		if (id == null) return;
		
		List <UnitAssociation> unitAssociations = new ArrayList<UnitAssociation>();
		unitAssociations.addAll(getBossAssociationsById(id));
		unitAssociations.addAll(getSubOrdinateAssociationsById(id));
		
		if (unitAssociations.isEmpty()) return;
		
		for (UnitAssociation unitAssociation : unitAssociations) {
			closeUnitAssociation(unitAssociation);
		}		
	}






}
