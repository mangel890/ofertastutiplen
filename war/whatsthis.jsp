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
	<jsp:param name="selector" value="0" />
</jsp:include> 
<p>
<p>
<div id="body" align="center">
<div id="submitOffer">
¿Qué es Ofertas Tutiplén?
<p>
<span style="font-weight: normal">Es un sitio en el que los usuarios compartimos las buenas ofertas que encontramos en cualquier tipo de comercios, tiendas y supermercados, ya sean establecimientos físicos o de Internet.
</span><p>
¿Cómo funciona?
<p>
<span style="font-weight: normal">Los usuarios envían ofertas que se guardan en una cola de 'recientes', en donde son votadas por el resto de usuarios. Las que consiguen más votos pasan a portada, la página principal del sitio.
</span><p>
¿Es necesario registrarse?
<p>
<span style="font-weight: normal">No es necesario registrarse para participar en ofertas tutiplen, sin embargo darte de alta en Ofertas Tutiplén te permitirá tener tu propio usuario para acumular puntos y recibir reconocimiento en la comunidad.
</span><p>
</div>
</div>
<jsp:include page="/footer.jsp" flush="true" />
</body>
</html>