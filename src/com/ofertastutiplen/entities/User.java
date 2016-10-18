package com.ofertastutiplen.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable
public class User implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -3296714708609093570L;

   /**
    * 
    */

   @PrimaryKey
   @Persistent
   private String id;

   @Persistent
   private String email;

   @Persistent
   private String password;

   @Persistent
   private Date signDate = new Date();

   @Persistent
   private Blob avatar;

   public enum Status
   {
      notVerified, active, admin
   }

   @Persistent
   private Status status = Status.notVerified;

   @Persistent
   private Date lastLoginDate = new Date();

   @Persistent
   private String ip;
   
   @Persistent
   private String url;

   public String getRandomVerificationString()
   {
      return randomVerificationString;
   }

   public void setRandomVerificationString(String randomVerificationString)
   {
      this.randomVerificationString = randomVerificationString;
   }

   @Persistent
   private String randomVerificationString = "";

   public User()
   {
      randomVerificationString = "randomGenerated" + signDate.getTime()
            + Double.toString(Math.random());
   }

   public User(String id, String email, String password, Date signDate,
         Blob avatar)
   {
      super();
      this.id = id;
      this.email = email;
      this.password = password;
      this.signDate = signDate;
      this.avatar = avatar;
   }

   @Override
   public String toString()
   {
      return "User [id=" + id + ", email=" + email + ", password=" + password
            + ", signDate=" + signDate + ", avatar=" + avatar + "]";
   }

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }

   public Date getSignDate()
   {
      return signDate;
   }

   public void setSignDate(Date signDate)
   {
      this.signDate = signDate;
   }

   public Blob getAvatar()
   {
      return avatar;
   }

   public void setAvatar(Blob avatar)
   {
      this.avatar = avatar;
   }
   public Status getStatus()
   {
      return status;
   }

   public void setStatus(Status status)
   {
      this.status = status;
   }

   public Date getLastLoginDate()
   {
      return lastLoginDate;
   }

   public void setLastLoginDate(Date lastLoginDate)
   {
      this.lastLoginDate = lastLoginDate;
   }
   public String getIp()
   {
      return ip;
   }

   public void setIp(String ip)
   {
      this.ip = ip;
   }

   public String getUrl()
   {
      return url;
   }

   public void setUrl(String url)
   {
      this.url = url;
   }

}