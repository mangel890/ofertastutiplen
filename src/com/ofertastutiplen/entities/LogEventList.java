package com.ofertastutiplen.entities;

import java.io.Serializable;
import java.util.LinkedList;

public class LogEventList implements Serializable
{

   
   private static final long serialVersionUID = -5326196581537778822L;

   public LinkedList<LogEvent> logEventList;

	public LogEventList()
	{
		logEventList = new LinkedList<LogEvent>();
	}

	public void addFirst(LogEvent evt)
	{
		logEventList.addFirst(evt);
	}

	public int size()
	{
		return logEventList.size();
	}

	public void removeLast()
	{
		logEventList.removeLast();
	}
}
