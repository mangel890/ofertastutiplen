package com.ofertastutiplen.entities;

import java.io.Serializable;
import java.util.Date;

public class LogEvent implements Serializable
{

   
   private static final long serialVersionUID = 4048794087145784614L;

   public Date when;
	public String what;
	public String who;
	public String where;

	public LogEvent(Date when, String what, String where, String who)
	{
		super();
		this.when = when;
		this.what = what;
		this.who = who;
		this.where = where;
	}
}
