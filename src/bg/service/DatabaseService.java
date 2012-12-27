package bg.service;

import java.io.File;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Service;
import bg.dao.TypeDao;
import bg.dao.UnitDao;
import bg.domain.BaseEntity;
import bg.domain.Type;
import bg.domain.Unit;


@Service
public class DatabaseService extends GenericService {

	
	
	
	// Properties

	
	
    private EntityManager 	em;

	@Resource
	private TypeDao 		typeDao;

	@Resource
	private UnitDao 		unitDao;
	
	
	
	
    // Insert test data
    
	
	
	
	public void insertTestData() {
		deleteExistingData();
		insertTestTypes();
		insertTestUnits();
	}




	public void deleteExistingData() {
		EntityManagerFactory emf = GenericService.getEntityManagerFactory();
    	em = emf.createEntityManager();
		em.getTransaction().begin();
	
		em.createQuery("DELETE FROM TypeAssociation").executeUpdate(); 
	    em.createQuery("DELETE FROM Type").executeUpdate();

	    em.createQuery("DELETE FROM UnitAssociation").executeUpdate();
	    em.createQuery("DELETE FROM Unit").executeUpdate();
	   
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


	
	
	
	
	private void insertTestUnits() {
		EntityManagerFactory emf = GenericService.getEntityManagerFactory();
    	em = emf.createEntityManager();
		em.getTransaction().begin();

	    Unit uKU01 = createUnit("KU01", "Kassi kula", "KU");
	    Unit uKU02 = createUnit("KU02", "Toku kula",  "KU");
	    Unit uKU03 = createUnit("KU03", "Londi kula", "KU");
	    Unit uKU04 = createUnit("KU04", "Lusti kula", "KU");
	    
	    Unit uVA01 = createUnit("VA01", "Urvaste vald", "VA");
	    Unit uVA02 = createUnit("VA02", "Karula vald",  "VA");

	    Unit uKI01 = createUnit("KI01", "Urvaste kihelkond", "KI");
	    Unit uKI02 = createUnit("KI02", "Karula kihelkond",  "KI");

	    Unit uMA01 = createUnit("MA01", "Voru maakond", "MA");

	    Unit uRI01 = createUnit("RI01", "Eesti riik", "RI");

	    em.getTransaction().commit();
		em.close();
		emf.close();

	    
	    unitDao.addBossToUnit(uVA01, uKU01);
	    unitDao.addBossToUnit(uVA01, uKU02);
	    unitDao.addBossToUnit(uVA02, uKU03);
	    unitDao.addBossToUnit(uVA02, uKU04);

	    unitDao.addBossToUnit(uKI01, uVA01);
	    unitDao.addBossToUnit(uKI02, uVA02);

	    unitDao.addBossToUnit(uMA01, uKI01);
	    unitDao.addBossToUnit(uMA01, uKI02);

	    unitDao.addBossToUnit(uRI01, uMA01);
	    
	}

	
	

	// Delete database / Clear DB lock
	
	
	
	
	private Unit createUnit(String code, String name, String typeCode) {
		Integer typeId = typeDao.getTypeIdByCode(typeCode); 
		
		Unit unit = new Unit();
		
		unit.setCode(code);
		unit.setName(name);
		unit.setFromDate(BaseEntity.getToday());
		unit.setToDate(BaseEntity.getEndOfTimeDate());
		unit.setTypeId(typeId);
		
		em.persist(unit);
	    return unit;
	}




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

