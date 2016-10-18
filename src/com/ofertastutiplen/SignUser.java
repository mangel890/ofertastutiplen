package com.ofertastutiplen;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.google.appengine.repackaged.com.google.common.base.Escaper;
import com.ofertastutiplen.entities.LogEvent;
import com.ofertastutiplen.entities.User;
import com.ofertastutiplen.util.PMFContainer;
import com.ofertastutiplen.util.TraceLogger;
import com.ofertastutiplen.util.Util;

public class SignUser extends HttpServlet
{

   /**
    * 
    */
   private static final long serialVersionUID = 2231444594056013293L;
   /**
	 * 
	 */

   private static final Logger log = Logger.getLogger(SignUser.class.getName());

   public HttpServletRequest requestM;
   public HttpServletResponse responseM;

   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {
      requestM = request;
      responseM = response;
      String name = "";
      String email = "";
      String password = "", challenge = "", answer = "";

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
               String value = Streams.asString(stream);
               response.getWriter().println(
                     "Got a form field: " + item.getFieldName());
               response.getWriter().println("SCWE");
               if (item.getFieldName().compareTo("name") == 0)
               {
                  name = value;
               }
               if (item.getFieldName().compareTo("email") == 0)
               {
                  email = value;
               }
               if (item.getFieldName().compareTo("password") == 0)
               {
                  password = value;
               }
               if (item.getFieldName().compareTo("passwordVerify") == 0)
               {
                  // offer.setStatus(Offer.Status.valueOf(value));
               }
					if (item.getFieldName().compareTo("recaptcha_challenge_field") == 0)
						challenge = value;
					if (item.getFieldName().compareTo("recaptcha_response_field") == 0)
						answer = value;

            }
         }
      } catch (Exception ex)
      {
         request.getSession(true)
               .setAttribute(
                     "error",
                     "Error interno 10503. Por favor contacta con nosotros para resolver el problema");
         response.sendRedirect("error.jsp");

      }
      

		/*
		 * Recaptcha
		 */
		if (!Util.checkCaptcha(request, challenge, answer))
		{
			request.getSession(true).setAttribute("error",
			      "Código de seguridad incorrecto");
			response.sendRedirect("error.jsp");
			return;

		}	
      
      String errorMsg = "";
      if ((errorMsg = checkUserOk(name, email, password)).compareTo("") == 0)
      {
         User user = new User();
         user.setId(name);
         user.setEmail(email);
         user.setPassword(password);

         PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

         try
         {
            pm.makePersistent(user);
         } finally
         {
            pm.close();
         }

         if (sendEmail(name, email, user.getRandomVerificationString()))
         {
            TraceLogger.saveEvent(new LogEvent(
            		new java.util.Date(),
            		"Nuevo usuario",
            		"-",
            		name));
            response.sendRedirect("signUserSuccess.jsp");
            return;
         } 
         else
         {
             request.getSession(true).setAttribute("error",
               "Error enviando email. Por favor, ponte en contacto "+
               "con nosotros para resolver el problema.");
               response.sendRedirect("/error.jsp");
         }

      } else
      {
         request.getSession(true).setAttribute("error", errorMsg);
         response.sendRedirect("error.jsp");
      }
   }

   public boolean sendEmail(String name, String email,
         String randomVerificationString)
   {
      Properties props = new Properties();
      Session session = Session.getDefaultInstance(props, null);

      String msgBody = "Hola "
            + name
            + ",\r\n"
            + "si te has registrado en Ofertas Tutiplen necesitas activar tu cuenta para \r\n"
            + "poder acceder al sistema. Para ello basta con hacer click en el siguiente enlace:\r\n\r\n"
            + "http://ofertastutiplen.appspot.com/validateAccount?name="
            + name
            + "&&code="
            + randomVerificationString
            + "\r\n\r\nSi, por el contrario, has recibido este email por error, discúlpanos y bórralo de tu cuenta."
            + "\r\n\r\nUn saludo del equipo de Ofertas Tutiplen (http://ofertastutiplen.co.cc)";

      try
      {
         Message msg = new MimeMessage(session);
         msg.setFrom(new InternetAddress("ofertastutiplen@gmail.com",
               "Ofertas Tutiplen"));
         msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email,
               name));
         msg.setSubject("Registro de nuevo usuario en Ofertas Tutiplen");
         msg.setText(msgBody);
         Transport.send(msg);

      } catch (Exception e)
      {
         log.severe("Cannot send email to " + email);
         return false;
      }
      return true;
   }

   public String checkUserOk(String name, String email, String password)
         throws IOException
   {

      if (name.isEmpty() || email.isEmpty() || password.isEmpty())
         return ("Id, Email y Password deben tener algún valor");

      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();
      Query query = pm.newQuery(User.class);
      query.setFilter("id==myName");
      query.declareParameters("String myName");
      List<User> myList = (List<User>) query.execute(name);

      if (myList.size() > 0)
      {
         return "Nombre de usuario utilizado previamente";
      }

      query = pm.newQuery(User.class);
      query.setFilter("email==myEmail");
      query.declareParameters("String myEmail");
      myList = (List<User>) query.execute(email);

      if (myList.size() > 0)
      {
         return "Email utilizado previamente";
      }

      return "";
   }
}
