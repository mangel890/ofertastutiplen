<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.ofertastutiplen.*"%>
<%@ page import="com.ofertastutiplen.util.*"%>
<%@ page import="com.ofertastutiplen.entities.*"%>
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
	<jsp:param name="selector" value="1" />
</jsp:include> <%
    String pageNumber = request.getParameter("page");
 %> <jsp:include page="/listOffers.jsp" flush="true">
	<jsp:param name="recentMode" value="0" />
	<jsp:param name="page" value="<%=pageNumber%>" />
</jsp:include> <jsp:include page="/footer.jsp" flush="true" />
</body>
</html>