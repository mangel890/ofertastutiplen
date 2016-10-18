<%@page import="javax.swing.text.DateFormatter"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.ofertastutiplen.*"%>
<%@ page import="com.ofertastutiplen.util.*"%>
<%@ page import="com.ofertastutiplen.entities.*"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.jdo.Query"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="ofertasTutiplen.css">
<link rel="shortcut icon" href="img/tut.ico" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
   User user = null;
   session = request.getSession(true);
   if (session != null)
      user = (User) session.getAttribute("user");

   if (session == null || user==null )
   {
      request.setAttribute("error","Ha de acceder al sistema para ver esta página");
      response.sendRedirect("error.jsp");
   }
%>
<title>Ofertas Tutiplen | Perfil usuario <%=user.getId()%></title>
</head>
<body>
<div id="body" align="center"><jsp:include page="/header.jsp" flush="true">
   <jsp:param name="selector" value="0" />
</jsp:include> <br />
<div id="submitOffer">
<div id="submitTittle">
Perfil Usuario <%=user.getId()%>
</div>
<br />
<br />

<img src="/imageServer?avatar=<%=user.getId() %>" 
style="float:right;max-height: 200px; max-width: 200px;
-webkit-box-shadow: rgba(0, 0, 0, 0.199219) 5px 5px 5px;
-moz-box-shadow: rgba(0, 0, 0, 0.199219) 5px 5px 5px;
margin-right:20px;"/>


<form action="/modifyUser" method="post" enctype="multipart/form-data">
<div>Nombre: <span style="font-weight: normal;"><%=user.getId() %></span>
</div>
<br />
<br />
<div>Fecha de alta: <span style="font-weight: normal;">
<%= Util.formatHumanTime(user.getSignDate()) %></span>
</div>
<br />
<br />
<div>Pagina web:<br> <input type="text" class="input"
   name="url"
   value="<%String str; if ((str = user.getUrl()) != null) out.write(user.getUrl());
   else out.write("http://");%>"
   onblur="if(this.value=='') this.value='http://'"
   onclick="if(this.value=='http://') this.value=''" size="80" /><br />
<br />
</div>
<br />
<br />
<div>Email:<br> <input type="text" class="input"
   name="email"
   value="<%=user.getEmail()%>"
    size="80" /><br />
<br />
</div>
<br />
<br />
<label for="title" accesskey="5">Imagen, icono o avatar personal:</label><br />
<div class="upload">
<div class="fakeupload"><input type="text" name="fakeupload"
   onchange="this.form.upload.value=this.form.fakeupload.value" /> <!-- browse button is here as background -->
<label>Examinar...</label></div>
<input type="file" name="upload" id="realupload" class="realupload"
   onchange="this.form.fakeupload.value = this.value;" /></div>

<div><br />
<br />

<input class="button" type="submit"
   value="Modificar"/>
   <a href="changePassword.jsp"><span class="button">Cambiar clave</span></a>
</div>
</form>
</div>
<jsp:include page="/footer.jsp" flush="true" />
</body>
</html>