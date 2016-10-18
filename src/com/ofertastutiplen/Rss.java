package com.ofertastutiplen;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.ofertastutiplen.entities.Offer;
import com.ofertastutiplen.util.Config;
import com.ofertastutiplen.util.OfferHelper;

public class Rss extends HttpServlet
{
   /**
    * 
    */
   private static final long serialVersionUID = -3637078944336170806L;

   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException
   {
      try
      {
         String type = request.getParameter("type");
         if (type!=null && type.equals("recent"))
         {
            
            generateXML(new OutputStreamWriter(response.getOutputStream(),"UTF-8"),
                  true);
         }
         else
         {
            generateXML(new OutputStreamWriter(response.getOutputStream(),"UTF-8"),
                  false);
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
   
   public void generateXML(OutputStreamWriter sos, boolean recentMode)
   throws XMLStreamException
   {
      // Create a XMLOutputFactory
      XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

      // Create XMLEventWriter
      XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(sos);
      //outputFactory.createXMLEventWriter(System.out);

      // Create a EventFactory

      XMLEventFactory eventFactory = XMLEventFactory.newInstance();
      XMLEvent end = eventFactory.createDTD("\n");

      // Create and write Start Tag

      StartDocument startDocument = eventFactory.createStartDocument("UTF-8","1.0");

      eventWriter.add(startDocument);

      // Create open tag
      eventWriter.add(end);

      StartElement rssStart = eventFactory.createStartElement("", "", "rss");
      eventWriter.add(rssStart);
      eventWriter.add(eventFactory.createAttribute("version", "2.0"));
      eventWriter.add(eventFactory.createAttribute("xmlns:atom", "http://www.w3.org/2005/Atom"));
      eventWriter.add(end);

      eventWriter.add(eventFactory.createStartElement("", "", "channel"));
      eventWriter.add(end);
      
      eventWriter.add(eventFactory.createStartElement("", "", "atom:link"));
      eventWriter.add(eventFactory.createAttribute("href", Config.serverAddress+
            "/rss"+(recentMode?"?type=recent":"")));
      eventWriter.add(eventFactory.createAttribute("rel", "self"));
      eventWriter.add(eventFactory.createAttribute("type", "application/rss+xml"));
      eventWriter.add(eventFactory.createEndElement("", "", "atom:link"));
      
      
      // Write the different nodes

      String title = "Ofertas Tutiplén: ";
      title+=recentMode?"Recientes":"Portada";
         
      createNode(eventWriter, "title", title);

      createNode(eventWriter, "link", Config.serverAddress);

      createNode(eventWriter, "description", "Las mejores ofertas publicadas de "+
            "comercios y tiendas en Internet, Alcampo, Saturn, MediaMarkt,"+
            "Carrefour, PcCity, Pixmania, etc.");

      createNode(eventWriter, "language", "es");

      createNode(eventWriter, "copyright", "Todas las marcas son "+
            "propiedad de sus respectivos fabricantes");

      createNode(eventWriter, "pubDate", convertDate(new Date()));

      List<Offer> list = new ArrayList<Offer>();
      for (int k=0;k<(20/Config.ItemsPerPage);k++)
         list.addAll(OfferHelper.selectOffers(recentMode,Integer.toString(k)));
      
      for (Offer o: list) {
         eventWriter.add(eventFactory.createStartElement("", "", "item"));
         eventWriter.add(end);
         createNode(eventWriter, "title", o.getTitle());
         createNode(eventWriter, "description", createDescription(o.getUri(),
               o.getContent()));
         createNode(eventWriter, "link", Config.serverAddress+"/unfoldOffer.jsp?uri="+o.getUri());
         //createNode(eventWriter, "author", o.getUser());
         createNode(eventWriter, "guid", Config.serverAddress+"/unfoldOffer.jsp?uri="+o.getUri());
         createNode(eventWriter, "pubDate", convertDate(
               recentMode?o.getSubmittedDate():o.getPublishedDate()));
         eventWriter.add(end);
         eventWriter.add(eventFactory.createEndElement("", "", "item"));
         eventWriter.add(end);

      }
      eventWriter.add(end);
      eventWriter.add(eventFactory.createEndElement("", "", "channel"));
      eventWriter.add(end);
      eventWriter.add(eventFactory.createEndElement("", "", "rss"));

      eventWriter.add(end);

      eventWriter.add(eventFactory.createEndDocument());

      eventWriter.flush();
      eventWriter.close();
}

private void createNode(XMLEventWriter eventWriter, String name,String value) 
throws XMLStreamException
{
   XMLEventFactory eventFactory = XMLEventFactory.newInstance();
   XMLEvent end = eventFactory.createDTD("\n");
   XMLEvent tab = eventFactory.createDTD("\t");
   // Create Start node
   StartElement sElement = eventFactory.createStartElement("", "", name);
   eventWriter.add(tab);
   eventWriter.add(sElement);
   // Create Content
   Characters characters = eventFactory.createCharacters(value);
   eventWriter.add(characters);
   // Create End node
   EndElement eElement = eventFactory.createEndElement("", "", name);
   eventWriter.add(eElement);
   eventWriter.add(end);

}
   
private String createDescription(String uri,String content)
{
   String str = "";
   str+="<img src=\""+Config.serverAddress+
   "/imageServer?uri="+uri+"\" style=\"max-height: 200px; max-width: 200px;"+
   "float:left;margin-left:3px\" align=\"left\" "+
   "hspace=\"3\"/><p>"+content +"</p><p><a href=\""+Config.serverAddress+
   "/unfoldOffer.jsp?uri="+uri+"\">Enlace</a> a la oferta</p>";
   return str;
   
}

private String convertDate(Date date)
{
   if (date==null)
      return "";
   Locale dateLocale = new Locale("en_GB");
   java.text.SimpleDateFormat sdf = 
      new java.text.SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZ", dateLocale);
   sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
   return sdf.format(date);
}
   
}
