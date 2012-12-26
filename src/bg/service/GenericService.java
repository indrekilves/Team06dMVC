package bg.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GenericService {
	
	
	

	public static EntityManagerFactory getEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("my-hsql-unit");
	}
	
	
}
