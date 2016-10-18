package com.ofertastutiplen;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.google.appengine.api.datastore.KeyFactory;
import com.ofertastutiplen.entities.Comment;
import com.ofertastutiplen.entities.LogEvent;
import com.ofertastutiplen.entities.Offer;
import com.ofertastutiplen.entities.User;
import com.ofertastutiplen.util.PMFContainer;
import com.ofertastutiplen.util.TraceLogger;
import com.ofertastutiplen.util.Util;

public class SendComment extends HttpServlet
{
	/**
    * 
    */
	private static final long serialVersionUID = 4959835802570295623L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException
	{
		String content = "", key = "", challenge = "", answer = "";

		
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
					String value = Streams.asString(stream, "UTF-8");
					response.getWriter().println(
					      "Got a form field: " + item.getFieldName());
					if (item.getFieldName().compareTo("content") == 0)
						content = value;
					if (item.getFieldName().compareTo("key") == 0)
						key = value;
					if (item.getFieldName().compareTo("recaptcha_challenge_field") == 0)
						challenge = value;
					if (item.getFieldName().compareTo("recaptcha_response_field") == 0)
						answer = value;
				}
			}
		} catch (Exception ex)
		{
			request.getSession(true).setAttribute("error", "Error general");
			response.sendRedirect("error.jsp");
			return;
		}
		if (key.isEmpty() || content.isEmpty())
		{
			request.getSession(true).setAttribute("error",
			      "Contenido no debe ser nulo y debe pertenecer a una oferta");
			response.sendRedirect("error.jsp");
			return;
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


		Comment comment = new Comment();
		comment.setDate(new Date());
		comment.setContent(content);
		User user = (User) request.getSession(true).getAttribute("user");
		comment.setUser(user != null ? user.getId() : null);
		comment.setOffer(KeyFactory.stringToKey(key));
		comment.setIp(request.getRemoteAddr());

		PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

		Query query = pm.newQuery(Offer.class);
		query.declareParameters("String myOffer");
		query.setFilter("keyM==myOffer");
		query.setUnique(true);
		Offer off = (Offer) query.execute(KeyFactory.stringToKey(key));
		String url = "";
		if (off != null)
			url = "/unfoldOffer.jsp?uri=" + off.getUri();
		else
		{
			request.getSession(true).setAttribute("error", "Error finding offer");
			response.sendRedirect("error.jsp");
			return;
		}

		try
		{
			pm.makePersistent(comment);
		} finally
		{
			pm.close();
		}
		
		TraceLogger.saveEvent(new LogEvent(
      		new java.util.Date(),
      		"Nuevo comentario",
      		(content.length()>70)?content.substring(0,70)+"...":content,
      		off.getTitle()+":"+(user!=null?user.getId():"anonimo")));
		response.sendRedirect(url);

	}
}
