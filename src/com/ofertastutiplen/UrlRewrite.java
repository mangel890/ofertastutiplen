package com.ofertastutiplen;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;

import com.google.appengine.api.datastore.Blob;
import com.ofertastutiplen.entities.Offer;
import com.ofertastutiplen.entities.Offer.Status;
import com.ofertastutiplen.entities.User;
import com.ofertastutiplen.util.PMFContainer;

public class UrlRewrite extends HttpServlet
{

   /**
    * 
    */
   private static final long serialVersionUID = 1355451805352670625L;

   /**
	 * 
	 */

   public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException
   {
   	String req = request.getPathInfo();
   	
   	req = req.replaceAll("/","");
//   	response.sendRedirect("/unfoldOffer.jsp?uri="+req);
      RequestDispatcher rd = request.getRequestDispatcher("/unfoldOffer.jsp?uri="+req);
      rd.forward(request, response);
   }
   
   
   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {
   	String req = request.getPathInfo();
   	
   	req = req.replaceAll("/","");
//   	response.sendRedirect("/unfoldOffer.jsp?uri="+req);
      RequestDispatcher rd = request.getRequestDispatcher("/unfoldOffer.jsp?uri="+req);
      rd.forward(request, response);
   	
   }
}
