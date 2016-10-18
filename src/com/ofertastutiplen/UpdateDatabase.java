package com.ofertastutiplen;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.KeyFactory;
import com.ofertastutiplen.entities.Offer;
import com.ofertastutiplen.entities.Vote;
import com.ofertastutiplen.util.Config;
import com.ofertastutiplen.util.PMFContainer;

public class UpdateDatabase extends HttpServlet
{
   /**
    * 
    */
   private static final long serialVersionUID = 5494978605346997998L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {
      response.setContentType("text/plain");
   	
      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();
      Query query = pm.newQuery(Offer.class);
      query.setOrdering("submittedDateM descending");
      
      List<Offer> myList = (List<Offer>) query.execute();
      if (myList==null || myList.size()==0 )
      {
         return;
      }
      
      
   	int size = myList.size();

   	int k = 0;
   	for (Offer offer:myList)
      {
      	response.getOutputStream().print("Titulo: "+offer.getTitle()+" Clicks:" +offer.getClicks());
      	
      	
      	double probability = ((double)(size-k)/(double)size)*Config.maximumProbabilityOfUpdatingOffer;
      	response.getOutputStream().print(" Probability: "+probability);
      	
      	if (Math.random()<probability)
      	{
         	offer.setClicks(offer.getClicks()+1);
         	response.getOutputStream().print(" Added one ");
      	}
      	
      	if (Math.random()<probability*0.2) //Only vote 20% of times
      	{
            Vote vote = new Vote();
            vote.setUser("bot");
            vote.setIp("127.0.0.1");
            vote.setOffer(offer.getKey());
            vote.setValue(1);
         	response.getOutputStream().print(" This one was voted ");
           	try
         	{
         		pm.makePersistent(vote);
         		
         	}
         	catch (Exception e)
         	{
         		System.out.println(e.toString());
         		pm.close();
         		
         	}
         	}
      	
      	response.getOutputStream().println("");      	
      	k++;
      	try
      	{
      		pm.makePersistent(offer);
      		
      	}
      	catch (Exception e)
      	{
      		System.out.println(e.toString());
      		pm.close();
      		
      	}
      	
      }
      pm.close();

//      Iterator<Offer> iter =  myList.iterator();
      
      //while (iter.hasNext())
      {
      	//response.getOutputStream().println(iter.
      }

   	
   	
   	
   
   }
   

}
