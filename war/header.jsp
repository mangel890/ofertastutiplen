<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.ofertastutiplen.*"%>
<%@ page import="com.ofertastutiplen.util.*"%>
<%@ page import="com.ofertastutiplen.entities.*"%>
<link rel="alternate" type="application/rss+xml" title="portada"
	href="<%=Config.serverAddress %>/rss" />
<link rel="alternate" type="application/rss+xml" title="recientes"
	href="<%=Config.serverAddress %>/rss?type=recent" />
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-20014479-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

<script type="text/javascript">
function enterPressed(e) {
  key = (document.all) ? e.keyCode : e.which;
  if (key==13) return true;
  else return false;
}

	function checkFieldsLogIn()
	{
		if ((document.getElementsByName("idLogin")[0].value != "")
				&& (document.getElementsByName("passwordLogin")[0].value != ""))

		{
			document.getElementsById("submit")[0].disabled = false;
			document.getElementsById("submit")[0].className = "button";
		} else
		{
			document.getElementsById("submit")[0].disabled = true;
			document.getElementsById("submit")[0].className = "buttonDisabled";

		}
	}
</script>
<div id="header">
<%
   if (session.getAttribute("user") != null)
   {
%>
<div id="logInBox"><a href="/modifyUser.jsp"
	style="text-decoration: none;"> <%User user = (User) session.getAttribute("user"); %>
<%=user.getId()%> <img class="avatar" class="avatar"
	src="/imageServer?avatar=<%=user.getId()%>" /> <span class="button">Modificar
Perfil</span> </a> <a href="/logOut" style="text-decoration: none;"> <span
	class="button">Cerrar Sesión</span></a></div>
<%
   } else
   {
%>
<div id="logInBox">
<form action="/logIn" method="post" name="form1" enctype="multipart/form-data">
Usuario: <input type="text" name="idLogin" class="input"
	onkeyup="checkFieldsLogIn()" size="15"><br />
Clave: <input type="password" name="passwordLogin" class="input"
	onkeyup="checkFieldsLogIn()" onkeypress="if (enterPressed(event)) form1.submit()"size="15" /><br>
<a href="/signUser.jsp" style="text-decoration: none;"> <span
	class="button" >Registrarse</span></a> <a href="#" onClick="form1.submit()" style="text-decoration: none;"> <span
	class="button" id="submit" >Acceder</span></a>
	</form>
</div>
<%
   }
%>
<div id="logo" style="text-align: center"><img src="/img/logo.png" /></div>

<table align="center">
	<td class="tabList">
	<div
		<%if (request.getParameter("selector").compareTo("1") == 0)
            out.print("class=\"selected\"");%>
		class="li"><a href="/index.jsp">Inicio</a></div>
	<div
		<%if (request.getParameter("selector").compareTo("2") == 0)
            out.print("class=\"selected\"");%>
		class="li"><a href="/submitOffer.jsp">Enviar Oferta</a></div>
	<div
		<%if (request.getParameter("selector").compareTo("3") == 0)
            out.print("class=\"selected\"");%>
		class="li"><a href="/recent.jsp">Recientes</a></div>
	<div
		<%if (request.getParameter("selector").compareTo("4") == 0)
            out.print("class=\"selected\"");%>
		class="li"><a href="/activity.jsp">Actividad</a></div>
	<div class="li"><a href="http://ofertastutiplen.blogspot.com">El
	Blog</a></div>


	</td>
</table>
</div>
