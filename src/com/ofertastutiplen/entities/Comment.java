package com.ofertastutiplen.entities;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.ofertastutiplen.entities.Offer.Status;

@PersistenceCapable
public class Comment
{
   public enum Status {pending, published, deleted}
   
   @PrimaryKey
   @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
   private Key key;

   @Persistent
   private String user;

   @Persistent
   private String content;

	@Persistent
   private String ip;

   @Persistent
   private Key offer;

   @Persistent
   private Date date = new Date();

   @Persistent
   private int value = 0;
   
   @Persistent
   private Status statusM = Status.pending;

   public Key getKey()
   {
      return key;
   }

   public void setKey(Key key)
   {
      this.key = key;
   }

   public String getUser()
   {
      return user;
   }

   public void setUser(String user)
   {
      this.user = user;
   }

   public String getContent()
   {
   	return content;
   }

	public void setContent(String content)
   {
   	this.content = content;
   }

	public String getIp()
   {
      return ip;
   }

   public void setIp(String ip)
   {
      this.ip = ip;
   }

   public Key getOffer()
   {
      return offer;
   }

   public void setOffer(Key offer)
   {
      this.offer = offer;
   }

   public Date getDate()
   {
      return date;
   }

   public void setDate(Date date)
   {
      this.date = date;
   }

   public int getValue()
   {
      return value;
   }

   public void setValue(int value)
   {
      this.value = value;
   }
}
