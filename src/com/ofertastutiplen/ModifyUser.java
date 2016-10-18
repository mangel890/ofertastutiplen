package com.ofertastutiplen;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
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

public class ModifyUser extends HttpServlet
{

   /**
    * 
    */
   private static final long serialVersionUID = -8839723972307392393L;

   /**
	 * 
	 */

   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {
      String url="", email="";
      Blob imgBlob = null;


      try
      {
         ServletFileUpload upload = new ServletFileUpload();
         response.setContentType("text/plain");

         FileItemIterator iterator = upload.getItemIterator(request);
         while (iterator.hasNext())
         {
            FileItemStream item = iterator.next();
            InputStream stream = item.openStream();
            
            if (item.isFormField())
            {
               String value = Streams.asString(stream,"UTF-8");
               response.getWriter().println(
                     "Got a form field: " + item.getFieldName());
               if (item.getFieldName().compareTo("url")==0)
                  url = value;
               if (item.getFieldName().compareTo("email")==0)
                  email = value;
            } else
            {
               String f = item.getFieldName();
               f = item.getName();
               response.getWriter().println(
                     "Got an uploaded file: " + item.getFieldName()
                           + ", name = " + item.getName());

               if (!item.getName().isEmpty())
                  imgBlob = new Blob(IOUtils.toByteArray(stream));
            }
         }
      } catch (Exception ex)
      {
         request.getSession(true).setAttribute("error", "Error general");
         response.sendRedirect("error.jsp");
         return;
      }

      User user = (User) request.getSession(true).getAttribute("user");
      if (user == null)
      {
         request.getSession(true).setAttribute("error", "Para usar esta pagina has de acceder al sistema");
         response.sendRedirect("error.jsp");
         return;
      }
      
      user.setUrl(url);
      user.setEmail(email);
      if (imgBlob != null)
         user.setAvatar(imgBlob);

      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();
      
      try
      {
         pm.makePersistent(user);
      }
      finally
      {
         pm.close();
      }
      request.getSession(true).setAttribute("success","Usuario modificado correctamente");
      response.sendRedirect("/success.jsp");
   }
}
