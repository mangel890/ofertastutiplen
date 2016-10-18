<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.ofertastutiplen.util.*"%>
<div class="footer">
<table align="center">
	<td class="tabList">
	<div class="li"><a href="/whatsthis.jsp">¿Qué es esto?</a></div>
	<div class="li"><a href="/contact.jsp">Contactar</a></div>
	<div class="li"><a href="/termsofuse.jsp">Condiciones de uso</a></div>
	<div class="li"><a href="/">Licencias</a></div>
	</td>
</table>
<br />
<br />
<table align="center">
	<td class="tabList">
	<div class="li"><a href="<%=Config.serverAddress%>/rss"><img
		src="/img/rss.png"
		style="vertical-align: middle; padding-right: 15px; border: 0px" />Portada</a></div>
	<div class="li"><a
		href="<%=Config.serverAddress%>/rss?type=recent"><img
		src="/img/rss.png"
		style="vertical-align: middle; padding-right: 15px; border: 0px" />Recientes</a></div>
	<div class="li"><a href="<%=Config.serverAddress%>/rss?type="><img
		src="/img/rss.png"
		style="vertical-align: middle; padding-right: 15px; border: 0px;" />Comentarios</a></div>

	</td>
</table>
<br />
<table align="center">
<td>
<img
	src="http://code.google.com/appengine/images/appengine-noborder-120x30.gif"
	alt="Con la tecnología de Google App Engine" />
	</td></table></div>
