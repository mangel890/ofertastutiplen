package com.ofertastutiplen.util;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.ofertastutiplen.entities.Offer;
import com.ofertastutiplen.entities.Vote;

public class OfferHelper
{
   public static int getPositiveVotesForOffer(Key offer)
   {
      //TODO: Change by an aggregate function
      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();
      Query query = pm.newQuery(Vote.class);
      query.setFilter("offer==myKeyOffer && value>=0");
      query.declareParameters("String myKeyOffer");
      List<Vote> myList = (List<Vote>) query.execute(offer);
      return myList.size();
   }

   public static int getNegativeVotesForOffer(Key offer)
   {
      //TODO: Change by an aggregate function
      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();
      Query query = pm.newQuery(Vote.class);
      query.setFilter("offer==myKeyOffer && value <0");
      query.declareParameters("String myKeyOffer");
      List<Vote> myList = (List<Vote>) query.execute(offer);
      return myList.size();
   }
   
   public static int getTotalNumberOfOffers(boolean recentMode)
   {
      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();
      Query query = pm.newQuery(Offer.class);
      if (recentMode)
      {
         query.setFilter("statusM=='pending'");
      }
      else
      {
         query.setFilter("statusM=='published'");
      }
      query.setResult("count(keyM)");
      return (Integer)query.execute();
   }
   
   public static List<Offer> selectOffers(boolean recentMode,
         String pageSelector)
   {
         PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

         Query query = pm.newQuery(Offer.class);
         if (recentMode)
         {
            query.setFilter("statusM=='pending'");
            query.setOrdering("submittedDateM descending");
         }
         else
         {
            query.setFilter("statusM=='published'");
            query.setOrdering("publishedDateM descending");
         }
         long selector = 1;
         try 
         {
            selector = Integer.parseInt(pageSelector);
            selector --;
         }
         catch (Exception e) {};
         
         
         
         long init = selector * Config.ItemsPerPage;
         long end = init + Config.ItemsPerPage;
         query.setRange(init,end);
         return (List<Offer>) query.execute();

   }

   
}
