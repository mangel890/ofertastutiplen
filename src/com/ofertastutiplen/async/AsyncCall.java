package com.ofertastutiplen.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.ofertastutiplen.entities.User;
import com.ofertastutiplen.entities.Vote;
import com.ofertastutiplen.util.OfferHelper;
import com.ofertastutiplen.util.PMFContainer;

public class AsyncCall extends HttpServlet
{

   /**
    * 
    */
   private static final long serialVersionUID = 7424440872935252105L;

   protected HttpServletRequest requestM;

   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {
      requestM = request;
      try
      {
         StringBuffer jb = new StringBuffer();
         String line = null;
         BufferedReader reader = request.getReader();
         while ((line = reader.readLine()) != null)
            jb.append(line);

         JSONArray myArray = new JSONArray(jb.toString());
         String func = (String) myArray.get(0);

         if (func.compareTo("addVote") == 0)
         {
            String offer = (String) myArray.get(1);
            int value = (Integer) myArray.get(2);
            response.getWriter().print(addVote(offer, value));
         }
      } catch (Exception ex)
      {
         return;
      }
      return;
   }

   public String addVote(String offer, int value)
   {
      String str = "";
      HttpSession session = requestM.getSession(true);
      User user = (User) session.getAttribute("user");

      boolean ipAlreadyVoted = false;
      boolean userAlreadyVoted = false;

      // First, check vote is valid for this ip
      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();
      Query query = pm.newQuery(Vote.class);
      query.setFilter("offer==myOffer && ip==myIp");
      query.declareParameters("String myOffer, String myIp");
      List<Vote> myList = (List<Vote>) query.execute(
            KeyFactory.stringToKey(offer), requestM.getRemoteAddr());

      ipAlreadyVoted = myList.size() > 0;

      // Second, check vote is valid for this user
      // Done in two queries because of limitation of JDOQL (I can't believe
      // it!!)
      if (user != null)
      {
         Query query2 = pm.newQuery(Vote.class);
         query2.setFilter("offer==myOffer && user==myUser");
         query2.declareParameters("String myOffer, String myUser");
         List<Vote> myList2 = (List<Vote>) query.execute(
               KeyFactory.stringToKey(offer), user.getId());

         userAlreadyVoted = myList2.size() > 0;
      }

      if ((ipAlreadyVoted || userAlreadyVoted)
            && (user == null || (user != null && user.getStatus() != User.Status.admin)))
      {
         JSONArray arr = new JSONArray();
         arr.put(0);
         arr.put(0);
         arr.put("Solo se puede votar una vez y esta noticia ya se ha votado desde"
               + " este usuario o ip");
         str += arr.toString();
         pm.close();
         return str;
      }

      Vote vote = new Vote();
      if (user != null)
      {
         vote.setUser(user.getId());
      } else
      {
         vote.setUser("");
      }
      vote.setIp(requestM.getRemoteAddr());
      vote.setOffer(KeyFactory.stringToKey(offer));
      vote.setValue(value);

      pm = PMFContainer.getPMF().getPersistenceManager();

      try
      {
         pm.makePersistent(vote);
      } finally
      {
         pm.close();
      }

      JSONArray arr = new JSONArray();
      arr.put(OfferHelper.getPositiveVotesForOffer(KeyFactory
            .stringToKey(offer)));
      arr.put(OfferHelper.getNegativeVotesForOffer(KeyFactory
            .stringToKey(offer)));

      str += arr.toString();
      return str;
   }
}
