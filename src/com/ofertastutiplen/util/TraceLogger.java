package com.ofertastutiplen.util;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ofertastutiplen.entities.LogEvent;
import com.ofertastutiplen.entities.LogEventList;
import com.ofertastutiplen.entities.Trace;

public class TraceLogger
{

	public static void saveEvent(LogEvent evt)
	{
		PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();
      Query query = pm.newQuery(Trace.class);
      query.setUnique(true);
      Trace trace = (Trace) query.execute();
      if (trace == null)
      {
      	trace = new Trace();
      }

      
		LogEventList list = trace.getEventList();
		list.addFirst(evt);
		if (list.size()>20)
			list.removeLast();
		
		
      LogEventList list2 = new LogEventList();
      list2.logEventList=list.logEventList;
      trace.setEventList(list2);
		    
      try 
      {
   		pm.makePersistent(trace);
      }
      finally
      {
   		pm.close();
      }
	}
	
	
	public static LogEventList getEvents()
	{
		return getSingletonList();
	}
	
	private static LogEventList getSingletonList()
	{
      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

      Query query = pm.newQuery(Trace.class);
      query.setUnique(true);
      Trace trace = (Trace) query.execute();
      if (trace == null)
      {
      	trace = new Trace();
      }
      

      return trace.getEventList();
	}
}
