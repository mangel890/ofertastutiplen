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
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.ofertastutiplen.entities.User;
import com.ofertastutiplen.util.PMFContainer;

public class ValidateAccount extends HttpServlet
{

   /**
    * 
    */
   private static final long serialVersionUID = -5877413108693483555L;

   /**
    * 
    */

   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {

      String name = request.getParameter("name");
      String code = request.getParameter("code");
      
      if (name==null || code==null || name.isEmpty() || code.isEmpty())
      {
         request.getSession(true).setAttribute("error","Validación de cuenta fallida, asegúrate de que la URL es correcta");
         response.sendRedirect("/error.jsp");
         return;
      }
      
      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

      Query query = pm.newQuery(User.class);
      query.setFilter("id==myName && randomVerificationString==myCode");
      query.declareParameters("String id1, String myCode");
      List<User> userList = (List<User>) query.execute(name, code);

      if (userList == null || userList.size()!=1)
      {
         request.getSession(true).setAttribute("error","Validación de cuenta fallida, asegúrate de que la URL es correcta");
         response.sendRedirect("/error.jsp");
         pm.close();
         return;
      }
      else
      {
         User user = (User)userList.get(0);
         user.setStatus(User.Status.active);
         request.getSession(true).setAttribute("user",user);
         response.sendRedirect("/validationOk.jsp");
         try
         {
            pm.makePersistent(user);
         }
         finally
         {
            pm.close();
         }
      }
   }
}
