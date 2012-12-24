package bg.domain;

import java.util.Date;

public interface Updatable {

	public void setChangedBy(	String 	changedBy);
	public void setChanged(		Date 	changed);
	
}
