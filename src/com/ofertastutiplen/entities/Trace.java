package com.ofertastutiplen.entities;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Trace
{

	@Persistent
   @PrimaryKey
   private String name = "SingletonKeyForTrace";
   

	@Persistent(serialized = "true")
   private LogEventList logEventList;
   
   public LogEventList getEventList()
   {
		if (logEventList == null)
			logEventList = new LogEventList();

   	return logEventList;
   }

	public void setEventList(LogEventList list)
   {
   	this.logEventList = list;
   }

	public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }
 
}
