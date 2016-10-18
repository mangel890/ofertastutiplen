<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="com.ofertastutiplen.*"%>
<%@ page import="com.ofertastutiplen.entities.*"%>
<%@ page import="java.util.List"%>
<script src="/js/ajax.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="/js/json2.js"></script>
<div id="offer">
<table >
<tr>
<td>
<img src="img/upArrow.png"
   onclick="addVote(&quot;<%=request.getParameter("key")%>&quot;,1)" /> <br />
<label id="positiveVotes<%=request.getParameter("key")%>"> <%=request.getParameter("positiveVotes")%></label>
<br />
<label id="negativeVotes<%=request.getParameter("key")%>"
   style="color: #EE8562;"> <%=request.getParameter("negativeVotes")%></label> <br />
<img src="img/downArrow.png"
   onclick="addVote(&quot;<%=request.getParameter("key")%>&quot;,-1)" /> <br />


</td>
<td width="100%">


<div style="display:block;float:left">
<%
   if (request.getParameter("unfolded") == null)
   {
%> <a href="/unfoldOffer.jsp?uri=<%=request.getParameter("uri")%>"> <%
   } else
   {
%> <a href="<%=request.getParameter("url")%>"> <%
    }
 %> <img src="/imageServer?uri=<%=request.getParameter("uri")%>"
   style="max-height: 200px; max-width: 200px;" /> </a>
   </div>
   
 <div style="display:block;">
   
<div >
<%
   if (request.getParameter("unfolded") == null)
   {
%> <a href="/unfoldOffer.jsp?uri=<%=request.getParameter("uri")%>"> <%
   } else
   {
%> <a href="<%=request.getParameter("url")%>"> <%
    }
 %> <%=URLDecoder.decode(request.getParameter("title"), "UTF-8")%> </a></div>
<div><a href="<%=request.getParameter("url")%>"> <%=request.getParameter("url")%>
</a></div>
<div >Enviado: <span id="datesValue"> <%=URLDecoder.decode(request.getParameter("submittedDate"),
               "UTF-8")%> </span>
                <% String pubDate = request.getParameter("publishedDate");
               if (pubDate!=null && !pubDate.isEmpty()) { %>
| En portada: <span id="datesValue"> <%=URLDecoder.decode(request.getParameter("publishedDate"),
               "UTF-8")%> </span><%} %>
</div>
<div ><%=URLDecoder.decode(request.getParameter("content"), "UTF-8")%>
</div>
<div>vista: <span id="bottomLineOfferValue"> <%=request.getParameter("clicks")%>
veces</span> &nbsp;&nbsp;|&nbsp;&nbsp; comentarios: <span id="bottomLineOfferValue">0</span>
&nbsp;&nbsp;|&nbsp;&nbsp; categoría: <span id="bottomLineOfferValue">
<%=URLDecoder.decode(request.getParameter("category"),"UTF-8")%></span> <br />
<br />
</div>
<div >
<%
   if (request.getParameter("user").equals("null"))
   {
      out.println("oferta anónima");
   } else
   {
%> Por <%=request.getParameter("user")%> <%
    }
 %> <img class="avatar"
   src="imageServer?avatar=<%=request.getParameter("user")%>" /> <br />
<br />
<%
   User user = (User) session.getAttribute("user");

   if ((user != null && user.getId().compareTo(
         request.getParameter("user")) == 0)
         || (user != null && user.getStatus() == User.Status.admin))
   {
%> <a href="/submitOffer.jsp?uri=<%=request.getParameter("uri")%>"
   style="text-decoration: none;"><span class="button"> Modificar
oferta</span></a> <%
    }
 %>
 </div>
</div>
</td>
</tr>
</table>
</div>
