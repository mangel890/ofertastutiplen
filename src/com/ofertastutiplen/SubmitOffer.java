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
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.ofertastutiplen.entities.LogEvent;
import com.ofertastutiplen.entities.Offer;
import com.ofertastutiplen.entities.Offer.Status;
import com.ofertastutiplen.entities.User;
import com.ofertastutiplen.util.PMFContainer;
import com.ofertastutiplen.util.TraceLogger;
import com.ofertastutiplen.util.Util;

public class SubmitOffer extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5691124169807635877L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException
	{
		String title = "", url = "", uri = "", content = "", tags = "", category = "", challenge = "", answer = "";
		Offer.Status status = Status.pending;
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
					String value = Streams.asString(stream, "UTF-8");
					response.getWriter().println(
					      "Got a form field: " + item.getFieldName());
					if (item.getFieldName().compareTo("title") == 0)
						title = value;
					if (item.getFieldName().compareTo("url") == 0)
						url = value;
					if (item.getFieldName().compareTo("uri") == 0)
						uri = value;
					if (item.getFieldName().compareTo("content") == 0)
						content = value;
					if (item.getFieldName().compareTo("status") == 0)
						status = Offer.Status.valueOf(value);
					// if (item.getFieldName().compareTo("user")==0)
					// offer.setUser(item.getFieldName());
					if (item.getFieldName().compareTo("tags") == 0)
						tags = value;
					if (item.getFieldName().compareTo("category") == 0)
						category = value;
					if (item.getFieldName().compareTo("recaptcha_challenge_field") == 0)
						challenge = value;
					if (item.getFieldName().compareTo("recaptcha_response_field") == 0)
						answer = value;
					// if (item.getFieldName().compareTo("category")==0)
					// offer.setUser(item.getFieldName());
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
		uri = uri.replaceAll("\\r\\n", "");

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

		Offer offer = null;
		// This means that we are modifying an existing offer
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

		}

		User user = null;

		if (offer == null)
		{
			offer = new Offer(title);
			// Set the user Id in case any.
			if ((user = (User) request.getSession(true).getAttribute("user")) != null)
			{
				offer.setUser(user.getId());
			}
			offer.setStatus(status);
		}

		offer.setUrl(url);
		offer.setContent(content);
		offer.setTitle(title);
		offer.setTags(tags);
		offer.setCategory(category);

		if (imgBlob != null)
			offer.setImage(resizeImage(imgBlob));

		if (offer.getTitle() == null || offer.getTitle().isEmpty())
		{
			request.getSession(true).setAttribute("error",
			      "El título no puede ser nulo");
			response.sendRedirect("error.jsp");
			return;
		}

		if (offer.getUrl() == null || offer.getUrl().isEmpty())
		{
			request.getSession(true).setAttribute("error",
			      "La URL no puede ser nula");
			response.sendRedirect("error.jsp");
			return;
		}

		if (offer.getContent() == null || offer.getContent().isEmpty())
		{
			request.getSession(true).setAttribute("error",
			      "La descripción no puede ser nula");
			response.sendRedirect("error.jsp");
			return;
		}

		if (offer.getTags() == null || offer.getTags().isEmpty())
		{
			request.getSession(true).setAttribute("error",
			      "Añade algunas etiquetas, por favor");
			response.sendRedirect("error.jsp");
			return;
		}

		if (offer.getContent().length() >= 500)
		{
			request.getSession(true).setAttribute("error",
			      "La descripción no puede superar 500 caracteres");
			response.sendRedirect("error.jsp");
			return;
		}
		if (offer.getCategory() == null || offer.getCategory().isEmpty())
		{
			request.getSession(true).setAttribute("error",
			      "Selecciona una categoría para esta oferta, por favor");
			response.sendRedirect("error.jsp");
			return;
		}

		PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

		try
		{
			pm.makePersistent(offer);
		} finally
		{
			pm.close();
		}
		TraceLogger.saveEvent(new LogEvent(new java.util.Date(), "Nueva oferta",
		      title, user != null ? user.getId() : ""));

		response.sendRedirect("submitOfferSuccess.jsp");
	}

	public Blob resizeImage(Blob img)
	{
		ImagesService imgService = ImagesServiceFactory.getImagesService();
		Image oldImage = ImagesServiceFactory.makeImage(img.getBytes());

		// oldImage.getHeight();
		// oldImage.getWidth();
		Transform resize = ImagesServiceFactory.makeResize(200, 200);
		Image newImage = imgService.applyTransform(resize, oldImage);

		Blob newImageBlob = new Blob(newImage.getImageData());
		if (newImageBlob.getBytes().length > img.getBytes().length)
		{
			return img;
		} else
		{
			return newImageBlob;
		}

	}
}
