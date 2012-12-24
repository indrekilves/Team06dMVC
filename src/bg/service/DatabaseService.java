package bg.service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Service;
import bg.dao.TypeDao;
import bg.domain.BaseEntity;
import bg.domain.Type;


@Service
public class DatabaseService extends GenericService {

	
	// Properties

	
	@Resource
	private TypeDao 		typeDao;
    private EntityManager 	em;
	
	
    // Insert test data
    
	
	public void insertTestData() {
		deleteExistingData();
		insertTestTypes();
	}

	
	
	public void deleteExistingData() {
		EntityManagerFactory emf = GenericService.getEntityManagerFactory();
    	em = emf.createEntityManager();
		em.getTransaction().begin();
	
		em.createQuery("DELETE FROM TypeAssociation").executeUpdate(); 
	    em.createQuery("DELETE FROM Type").executeUpdate();
	    
	    em.getTransaction().commit();
		em.close();
		emf.close();
	}



	private void insertTestTypes() {
		EntityManagerFactory emf = GenericService.getEntityManagerFactory();
    	em = emf.createEntityManager();
		em.getTransaction().begin();

	    Type tKU = createType("KU", "Kula");
	    Type tVA = createType("VA", "Vald");
	    Type tKI = createType("KI", "Kihelkond");
	    Type tMA = createType("MA", "Maakond");
	    Type tRI = createType("RI", "Riik");

	    em.getTransaction().commit();
		em.close();
		emf.close();

	    
	    typeDao.addBossToType(tVA, tKU);
	    typeDao.addBossToType(tKI, tVA);
	    typeDao.addBossToType(tMA, tKI);
	    typeDao.addBossToType(tRI, tRI);

	}
	
	
	
	private Type createType(String code, String name) {
		Type type = new Type();
		
		type.setCode(code);
		type.setName(name);
		type.setFromDate(BaseEntity.getToday());
		type.setToDate(BaseEntity.getEndOfTimeDate());
		
		em.persist(type);
	    return type;
	}
	
}
