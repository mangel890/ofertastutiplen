<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.net.URLEncoder"%>
<%@ page import="com.ofertastutiplen.*"%>
<%@ page import="com.ofertastutiplen.util.*"%>
<%@ page import="com.ofertastutiplen.entities.*"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="java.util.List"%>
<div id="content" align="center"><br />
<br />
<%
   String pageNumber = (String) request.getParameter("page");

   if (pageNumber == null || pageNumber.equals("null"))
   {
      pageNumber = "1";
   }
   boolean recentMode = request.getParameter("recentMode").equals("1");
   List<Offer> myList = OfferHelper
         .selectOffers(recentMode, pageNumber);
   if (myList != null)
   {
      for (Offer g : myList)
      {
         String title = URLEncoder.encode(g.getTitle(), "UTF-8");
         String content = URLEncoder.encode(g.getContent(), "UTF-8");
         String submittedDate = URLEncoder.encode(
               Util.formatHumanTime(g.getSubmittedDate()), "UTF-8");
         String publishedDate = URLEncoder.encode(
               Util.formatHumanTime(g.getPublishedDate()), "UTF-8");
         String cat = g.getCategory();
         if (cat==null)
            cat="";
         String category = URLEncoder.encode(
               cat, "UTF-8");
         
%> <jsp:include page="/displayOffer.jsp" flush="true">
	<jsp:param name="title" value="<%= title%>" />
	<jsp:param name="uri" value="<%= g.getUri() %>" />
	<jsp:param name="url" value="<%= g.getUrl() %>" />
	<jsp:param name="submittedDate" value="<%=submittedDate%>" />
	<jsp:param name="publishedDate" value="<%=publishedDate%>" />
	<jsp:param name="content" value="<%= content %>" />
	<jsp:param name="user" value="<%= g.getUser()%>" />
	<jsp:param name="key" value="<%= KeyFactory.keyToString(g.getKey())%>" />
	<jsp:param name="positiveVotes"
		value="<%= OfferHelper.getPositiveVotesForOffer(g.getKey())%>" />
	<jsp:param name="negativeVotes"
		value="<%= OfferHelper.getNegativeVotesForOffer(g.getKey())%>" />
	<jsp:param name="clicks" value="<%= g.getClicks()%>" />
	<jsp:param name="category" value="<%= category%>" />
    <jsp:param name="comments" value="<%= CommentHelper.countComments(g.getKey())%>" />
   </jsp:include> <%
    }
       if (myList == null || myList.size() == 0)
       {
          out.println("<h2>No hay ofertas para mostrar</h2>");
       }

    }
 %>
</div>

				      
<%=StaticHtmlGenerator.doPageSelector(
               OfferHelper.getTotalNumberOfOffers(recentMode),
               Integer.parseInt(pageNumber),
               recentMode?"recent.jsp":"index.jsp")%>
