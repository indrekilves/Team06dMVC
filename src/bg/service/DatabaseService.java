package bg.service;

import java.io.File;

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
	    typeDao.addBossToType(tRI, tMA);

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


	

	// Delete database / Clear DB lock
	
	
	
	
	public String deleteDatabase() {
		String result = "";
		
		try {
			
			File scriptfile	 	= new File("/usr/share/tomcat7/i377/Team06dMVC/db.script");
			File propertiesfile = new File("/usr/share/tomcat7/i377/Team06dMVC/db.properties");
			
			
			result = clearDbLock();
			
			if (scriptfile.delete()) {
				result = result + " " + scriptfile.getName() + " is deleted.";
			} else {
				result = result + " " + "Script file delete failed: "+scriptfile.getAbsolutePath();
			}
			
			if (propertiesfile.delete()) {
				result = result + " " + propertiesfile.getName() + " is deleted.";
			} else {
				result = result + " " + "Properties file delete failed: "+propertiesfile.getAbsolutePath();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	
	

	public String clearDbLock() {
		String clearResult = "";
		
		try {
			
			File lockfile 		= new File("/usr/share/tomcat7/i377/Team06dMVC/db.lck");
			
			if (lockfile.delete()) {
				clearResult = lockfile.getName() + " is deleted.";
			} else {
				clearResult = "Lock file delete failed: "+lockfile.getAbsolutePath();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return clearResult;
	}
	
}

