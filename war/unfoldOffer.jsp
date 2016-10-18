<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.ofertastutiplen.*"%>
<%@ page import="com.ofertastutiplen.util.*"%>
<%@ page import="com.ofertastutiplen.entities.*"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.jdo.Query"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="java.net.URLEncoder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="/ofertasTutiplen.css">
<link rel="shortcut icon" href="/img/tut.ico" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
   Offer offer = null;

   String uri = request.getParameter("uri");
   if (uri != null && !uri.isEmpty())
   {
      PersistenceManager pm = PMFContainer.getPMF()
            .getPersistenceManager();

      Query query = pm.newQuery(Offer.class);
      query.setFilter("uri==generatedUri");
      query.declareParameters("String generatedUri");
      List<Offer> myList = (List<Offer>) query.execute(uri);
      if (myList.size() == 1)
         offer = myList.get(0);
      
      offer.setClicks(offer.getClicks()+1);
      pm.makePersistent(offer);
      pm.close();
   }
%>
<title><%= offer.getTitle()%> | Ofertas Tutiplen</title>
</head>
<body>
<div id="body" align="center"><jsp:include page="/header.jsp" flush="true">
   <jsp:param name="selector" value="0" />
</jsp:include> <br />
<div id="content" align="center"><br />
<br />
<% 

       String title = URLEncoder.encode(offer.getTitle(),"UTF-8");
       String content = URLEncoder.encode(offer.getContent(),"UTF-8");
       String submittedDate = 
          URLEncoder.encode(Util.formatHumanTime(offer.getSubmittedDate()),"UTF-8");
       String publishedDate =
          URLEncoder.encode(Util.formatHumanTime(offer.getPublishedDate()),"UTF-8");
       String cat = offer.getCategory();
       if (cat==null)
          cat="";
       String category = URLEncoder.encode(
             cat, "UTF-8");

%> <jsp:include page="/displayOffer.jsp" flush="true">
   <jsp:param name="title" value="<%= title%>" />
   <jsp:param name="uri" value="<%= offer.getUri() %>" />
   <jsp:param name="url" value="<%= offer.getUrl() %>" />
   <jsp:param name="submittedDate" value="<%=submittedDate%>" />
   <jsp:param name="publishedDate" value="<%=publishedDate%>" />
   <jsp:param name="content" value="<%= content %>" />
   <jsp:param name="user" value="<%= offer.getUser()%>" />
   <jsp:param name="key" value="<%= KeyFactory.keyToString(offer.getKey())%>" />
   <jsp:param name="positiveVotes"
      value="<%= OfferHelper.getPositiveVotesForOffer(offer.getKey())%>" />
   <jsp:param name="negativeVotes"
      value="<%= OfferHelper.getNegativeVotesForOffer(offer.getKey())%>" />
   <jsp:param name="clicks" value="<%= offer.getClicks() %>" />
   <jsp:param name="unfolded" value="1" />
   <jsp:param name="category" value="<%= category%>" />
   <jsp:param name="comments" value="<%= CommentHelper.countComments(offer.getKey())%>" />
</jsp:include></div>
<% for (Comment c:CommentHelper.getComments(offer.getKey()))
	{%>
<div id="submitOffer">
<span id="datesValue"><%= Util.formatHumanTime(c.getDate()) %></span> 
<span id="author"><%= c.getUser()!=null?c.getUser():"anónimo" %></span> 
<br/>
<span style="font-weight:normal;">
<%= c.getContent() %>
</span>
</div>
<%} %>
<div id="submitOffer">
Añade un comentario:<br/><br/>
<form action="/sendComment" method="post" enctype="multipart/form-data">
<textarea class="textarea"name="content" rows="7" cols="100"></textarea> 
	</br>
	 
	<%= Util.generateCaptcha()%>
	
	<input class="button" type="submit" value="Enviar comentario"/>
	
	<input type="hidden" name="key"
	value="<%if (offer != null)
            out.println(KeyFactory.keyToString(offer.getKey()));%>"/>
</form>
</div>
<jsp:include page="/footer.jsp" flush="true" />
</body>
</html>