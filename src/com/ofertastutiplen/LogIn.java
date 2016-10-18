package com.ofertastutiplen;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.ofertastutiplen.entities.User;
import com.ofertastutiplen.util.PMFContainer;

public class LogIn extends HttpServlet
{

   /**
    * 
    */
   private static final long serialVersionUID = 1820707396975625030L;

   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {

      try
      {
         ServletFileUpload upload = new ServletFileUpload();
         response.setContentType("text/plain");

         String id = "";
         String password = "";
         FileItemIterator iterator = upload.getItemIterator(request);
         while (iterator.hasNext())
         {
            FileItemStream item = iterator.next();
            InputStream stream = item.openStream();

            if (item.isFormField())
            {
               String value = Streams.asString(stream);
               response.getWriter().println(
                     "Got a form field: " + item.getFieldName());
               if (item.getFieldName().compareTo("idLogin") == 0)
               {
                  id = value;
               }
               if (item.getFieldName().compareTo("passwordLogin") == 0)
               {
                  password = value;
               }
            }
         }

         PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

         Query query = pm.newQuery(User.class);
         query.setFilter("id==id1 && status!='notVerified'");
         query.declareParameters("String id1");
         List<User> userList = (List<User>) query.execute(id);
         if (userList != null)
         {
            for (User g : userList)
            {
               if (g.getPassword().compareTo(password) == 0)
               {
                  HttpSession session = request.getSession(true);
                  session.setAttribute("user", g);
                  g.setLastLoginDate(new Date());
                  pm.close();
                  response.sendRedirect("welcomeUser.jsp");
                  return;
               }
            }
         } else
         {
            request.getSession(true).setAttribute("error", "Problema en Login");
            pm.close();
            response.sendRedirect("error.jsp");
            return;
         }

      } catch (Exception ex)
      {

         response.getWriter().println(ex.toString());
         request.getSession(true).setAttribute("error", "Problema en Login");
         response.sendRedirect("error.jsp");
         return;
      }

      request.getSession(true).setAttribute("error",
            "Usuario / clave incorrectos");
      response.sendRedirect("error.jsp");
      return;

   }
}
