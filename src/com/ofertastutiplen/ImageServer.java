package com.ofertastutiplen;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.rmi.transport.proxy.HttpReceiveSocket;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.ofertastutiplen.entities.Offer;
import com.ofertastutiplen.entities.User;
import com.ofertastutiplen.util.PMFContainer;

public class ImageServer extends HttpServlet
{
   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {

      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

      String uri = request.getParameter("uri");
      String avatar = request.getParameter("avatar");

      if ((uri==null || uri.isEmpty())&&
           ( avatar==null || avatar.isEmpty()) )
      {
         return;
      }
      if (uri!=null)
      {
         Query query = pm.newQuery(Offer.class);
         query.setFilter("uri==uriSelected");
         query.declareParameters("String uriSelected");
         List<Offer> myList = (List<Offer>) query.execute(uri);
         if (myList==null || myList.size()!=1 || myList.get(0).getImage() == null)
         {
            sendDefaultImage("img/empty.png", response);
            return;
         }
         
         if (myList.get(0).getImage()!=null)
            response.getOutputStream().write(myList.get(0).getImage().getBytes());
      }
      if (avatar!=null)
      {
         Query query = pm.newQuery(User.class);
         query.setFilter("id==myId");
         query.declareParameters("String myId");
         List<User> myList = (List<User>) query.execute(avatar);
         if (myList==null || myList.size()!=1 || myList.get(0).getAvatar()==null)
         {
            sendDefaultImage("img/dfltAvtr.jpg", response);
            return;
         }
         
         if (myList.get(0).getAvatar()!=null)
            response.getOutputStream().write(myList.get(0).getAvatar().getBytes());
      }
   }
   
   public void sendDefaultImage(String img, HttpServletResponse response)
      throws IOException
   {
      FileInputStream in = new FileInputStream(img);
      response.setContentType("image/png");

      byte[] bytes = new byte[256];
      while (in.read(bytes)!=-1)
      {
         response.getOutputStream().write(bytes);
      }
   }
}
