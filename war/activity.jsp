<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.ofertastutiplen.*"%>
<%@ page import="com.ofertastutiplen.util.*"%>
<%@ page import="com.ofertastutiplen.entities.*"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.TimeZone"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="ofertasTutiplen.css">
<link rel="shortcut icon" href="img/tut.ico" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Ofertas Tutiplen</title>
</head>
<body>
<div id="body"><jsp:include page="/header.jsp" flush="true">
	<jsp:param name="selector" value="0" />
</jsp:include> 
<br/>
<style>
.cell {
padding:10px;
}
.row {
background:#ddd;
}

.headerRow {
font-weight:bold;
color:white;
background:#444;
}
.tableActivity {
text-align: center;
padding:10px;
}
</style>

<table border="0" cellspacing="0" celpadding="0" align="center" class="tableActivity">
<tr class="headerRow">
<td >
Cuándo
</td>
<td>
Qué
</td>
<td>
Cómo/Dónde
</td>
<td>
Quién</td>
</tr>
<tr></tr>

<%
boolean striped = false;
for (LogEvent l:TraceLogger.getEvents().logEventList) 
{
	if (striped)
	{
		out.write("<tr>");
	}
	else
	{
		out.write("<tr class=\"row\">");
	}
	out.write("<td class=\"cell\">");
    Locale dateLocale = new Locale("ES");
    java.text.SimpleDateFormat sdf = 
      new java.text.SimpleDateFormat("EEE, dd MMM yyyy HH:mm", dateLocale);
    sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
   
    out.write(sdf.format(l.when));
	out.write("</td><td class=\"cell\">");
	out.write(l.what);
	out.write("</td><td class=\"cell\">");
	out.write(l.where);
	out.write("</td><td class=\"cell\">");
	out.write(l.who);
	out.write("</td>");
	out.write("</tr>");
	striped = !striped;
}
%>
</td></table>
<jsp:include page="/footer.jsp" flush="true" />
</body>
</html>