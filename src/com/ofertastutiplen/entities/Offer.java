package com.ofertastutiplen.entities;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Offer {
    public enum Status {pending, published, deleted}
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key keyM;

    @Persistent
    private String titleM;

    @Persistent
    private String contentM;
    
    
    @Persistent
    private Date publishedDateM;
    
    @Persistent
    private Status statusM = Status.pending;
    
    @Persistent
    private Date submittedDateM = new Date();
    
    @Persistent
    private String urlM;

	@Persistent
   private String userM;
	
	@Persistent
	private Blob imageM;
	
	@Persistent
	private String tags;
	

   @Persistent
	private String uri;
	
   @Persistent
	private int clicks = 2;
   
   @Persistent
   private String ip="";
   

   @Persistent
   private String category="";

   //TODO Activate this before final deployment
/*   @Persistent
   private int posVotes;  //To be used just in case there are many vote instances in the db (for closed offers)

   @Persistent
   private int negVotes;
*/
   
   public String getIp()
   {
      return ip;
   }

   public void setIp(String ip)
   {
      this.ip = ip;
   }

   public Offer(String title)
   {
      this.titleM = title;
      this.uri = formatUri(title);
      
   }
    
	public Offer(String contentM, Key keyM, Date publishedDateM,
			Status statusM, Date submittedDateM, String titleM, String urlM,
			String userM) {
		super();
		this.contentM = contentM;
		this.keyM = keyM;
		this.publishedDateM = publishedDateM;
		this.statusM = statusM;
		this.submittedDateM = submittedDateM;
		this.titleM = titleM;
		this.urlM = urlM;
		this.userM = userM;
		this.uri = formatUri(titleM);
	}

	public String formatUri(String title)
	{
	   String str = "";
	   
	   //A minúsculas
	   title=title.toLowerCase();
	   
	   //Quitar diacríticos y ñ

	    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	    for (int i=0; i<original.length(); i++)
	    {
	        title = title.replace(original.charAt(i), ascii.charAt(i));
	    }
	   
	   for (int k=0;k<title.length();k++)
	   {
	      char c = title.charAt(k);
	      if ((c >= 97 && c <= 122) ||
	          (c >= 48 && c <= 57))
	      {
	             str+=title.charAt(k);
	      }
	      else
	      {
            str+="_";
	      }
	   }
	   java.text.SimpleDateFormat sdf = new 
	         java.text.SimpleDateFormat("dd_MM_yyyy_HH_mm");
	   
	   return str+"_"+sdf.format(submittedDateM);
	}

	@Override
	public String toString() {
		return "Offer [contentM=" + contentM + ", keyM=" + keyM
				+ ", publishedDateM=" + publishedDateM + ", statusM=" + statusM
				+ ", submittedDateM=" + submittedDateM + ", titleM=" + titleM
				+ ", urlM=" + urlM + ", userM=" + userM
				+ ", getPublishedDate()=" + getPublishedDate()
				+ ", getStatus()=" + getStatus() + ", getSubmittedDate()="
				+ getSubmittedDate() + ", getUrl()=" + getUrl()
				+ ", getContent()=" + getContent() + ", getKey()=" + getKey()
				+ ", getTitle()=" + getTitle() + ", getUser()=" + getUser()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}


    public Date getPublishedDate() {
		return publishedDateM;
	}

    public void setPublishedDate(Date publishedDateM) {
		this.publishedDateM = publishedDateM;
	}


	public Status getStatus() {
		return statusM;
	}


	public void setStatus(Status statusM) {
		this.statusM = statusM;
	}


	public Date getSubmittedDate() {
		return submittedDateM;
	}


	public void setSubmittedDate(Date submittedDateM) {
		this.submittedDateM = submittedDateM;
	}


	public String getUrl() {
		return urlM;
	}


	public void setUrl(String urlM) {
		this.urlM = urlM;
	}

    
    
    public String getContent()
    {
    	return contentM;
    }
    
    public Key getKey()
    {
    	return keyM;
    }
    
    public String getTitle()
    {
    	return titleM;
    }
    
    public String getUser()
    {
    	return userM;
    }

    public void setContent(String theContent)
    {
    	contentM = theContent;
    }

    public void setTitle(String theTitle)
    {
    	titleM = theTitle;
    }

    public void setUser(String theUser)
    {
    	userM = theUser;
    }

    public Blob getImage() {
		return imageM;
	}

	public void setImage(Blob imageM) {
		this.imageM = imageM;
	}

   public String getTags()
   {
      return tags;
   }

   public void setTags(String tags)
   {
      this.tags = tags;
   }
   public String getUri()
   {
      if (uri==null)
         uri = formatUri(titleM);
      return uri;
   }

   public void setUri(String uri)
   {
      this.uri = uri;
   }

   public int getClicks()
   {
      return clicks;
   }

   public void setClicks(int clicks)
   {
      this.clicks = clicks;
   }

   public String getCategory()
   {
      return category;
   }

   public void setCategory(String category)
   {
      this.category = category;
   }
	
}