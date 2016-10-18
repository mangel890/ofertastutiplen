package com.ofertastutiplen;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogOut extends HttpServlet
{

   /**
    * 
    */
   private static final long serialVersionUID = -8695576309917122195L;

   /**
    * 
    */


   
   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {
      
      HttpSession session = request.getSession(true);
      session.setAttribute("user",null);
      
      response.sendRedirect("index.jsp");

   }
}
