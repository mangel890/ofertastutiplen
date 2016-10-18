package com.ofertastutiplen;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ofertastutiplen.entities.LogEvent;
import com.ofertastutiplen.entities.Offer;
import com.ofertastutiplen.entities.Offer.Status;
import com.ofertastutiplen.util.PMFContainer;
import com.ofertastutiplen.util.TraceLogger;

public class ApproveOffer extends HttpServlet
{

   /**
    * 
    */
   private static final long serialVersionUID = 8604234679315571327L;

	/**
	 * 
	 */
   public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException
   {
   	doPost(req, resp);
   }
   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {
      String uri = "";

      try
      {
         ServletFileUpload upload = new ServletFileUpload();
         response.setContentType("text/plain");
         
         uri = request.getParameter("uri");

/*         FileItemIterator iterator = upload.getItemIterator(request);
         while (iterator.hasNext())
         {
            FileItemStream item = iterator.next();
            InputStream stream = item.openStream();

            if (item.isFormField())
            {
               String value = Streams.asString(stream, "UTF-8");
               response.getWriter().println(
                     "Got a form field: " + item.getFieldName());
          
               if (item.getFieldName().compareTo("uri") == 0)
                  uri = value;
             
            }
         }*/
      } catch (Exception ex)
      {
         request.getSession(true).setAttribute("error", "Error general");
         response.sendRedirect("error.jsp");
         return;
      }
      uri = uri.replaceAll("\\r\\n", "");

      

      
      
      Offer offer = null;
      if (!uri.isEmpty())
      {

         PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

         Query query = pm.newQuery(Offer.class);
         query.setFilter("uri==generatedUri");
         query.declareParameters("String generatedUri");
         List<Offer> myList = (List<Offer>) query.execute(uri);
         if (myList.size() == 1)
            offer = myList.get(0);
         pm.close();
         
         offer.setStatus(Status.published);
         offer.setPublishedDate(new Date());

      }

      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

      try
      {
         pm.makePersistent(offer);
      } finally
      {
         pm.close();
      }
      TraceLogger.saveEvent(new LogEvent(
      		new java.util.Date(),
      		"Oferta aprobada!!!",
      		offer.getTitle(),
      		offer.getUser()));
      
      response.sendRedirect("submitOfferSuccess.jsp");
   }
}
