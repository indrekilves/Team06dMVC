package bg.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.domain.TypeSubordinate;

@Repository
public class TypeSubordinateDao {

	@PersistenceContext
    private EntityManager em;

	
	
	
	// Find all
	
	
	
    @Transactional(readOnly = true)
    public List<TypeSubordinate> findAll() {
        TypedQuery<TypeSubordinate> query = em.createQuery("from TypeSubordinate", TypeSubordinate.class);
        return query.getResultList();
    }
	
}
