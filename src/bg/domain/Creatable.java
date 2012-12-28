package bg.domain;

import java.util.Date;

public interface Creatable {
	
	public void setOpenedBy(	String 	openedBy);
	public void setOpened(		Date 	opened);
	public void setChangedBy(	String 	changedBy);
	public void setChanged(		Date 	changed);
	public void setClosed(		Date	closed);
	public Date getOpened();
	
}
