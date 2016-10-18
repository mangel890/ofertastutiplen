<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.ofertastutiplen.*"%>
<%@ page import="com.ofertastutiplen.util.*"%>
<%@ page import="com.ofertastutiplen.entities.*"%>

<%@ page import="javax.jdo.PersistenceManager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="ofertasTutiplen.css">
<link rel="shortcut icon" href="img/tut.ico" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Ofertas Tutiplen | Bienvenido </title>
</head>
<body>
<div id="body" align="center">
<jsp:include page="/header.jsp" flush="true" >
<jsp:param name="selector" value="0"/>
</jsp:include>
<br />
<div id="submitOffer">
<img src="img/ok.png"/><span style="font-size:20px;vertical-align: top;padding-left:10px;">
Bienvenido 
<%if (session.getAttribute("user") != null)
   {
      out.write(((User)session.getAttribute("user")).getId()); 
   }
   else
      out.write("Error Interno: Código 14030");
%>
</span><br/>
</div>
<jsp:include page="/footer.jsp" flush="true" />
</body>
</html>