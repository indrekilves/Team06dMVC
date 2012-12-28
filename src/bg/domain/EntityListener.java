package bg.domain;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class EntityListener {
	
	
	@PrePersist
	public void onPrePersistAction(final Creatable entity) {
		String userName = BaseEntity.getLoggedUserName();
		Date today 		= BaseEntity.getToday();
		
		entity.setOpenedBy(userName);
		if (entity.getOpened() == null)
		{
			entity.setOpened(today);
		}
		
		entity.setChangedBy(userName);
		entity.setChanged(today);
	
		entity.setClosed(BaseEntity.getEndOfTimeDate()); 
	}

	
	
	@PreUpdate
	public void onPreUpdateAction(Updatable entity) {
		entity.setChangedBy(BaseEntity.getLoggedUserName());
		entity.setChanged(	BaseEntity.getToday());	    
	}
}
