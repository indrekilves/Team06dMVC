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
	
	

	@PersistenceContext
    private EntityManager em;
	
	
		
	// Find all
	
	
	
    @Transactional(readOnly = true)
    public List<TypeAssociation> findAll() {
        TypedQuery<TypeAssociation> query = em.createQuery("FROM TypeAssociation WHERE opened <= NOW() AND closed >= NOW()", TypeAssociation.class);
        return query.getResultList();
    }
}
