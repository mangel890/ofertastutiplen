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
<title>Ofertas Tutiplen | Enviar Oferta</title>
</head>
<body>
<div id="body" align="center"><jsp:include page="/header.jsp"
	flush="true">
	<jsp:param name="selector" value="2" />
</jsp:include> <br />
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
      pm.close();
   }

   User user = null;
   session = request.getSession(true);
   if (session != null)
      user = (User) session.getAttribute("user");
%>
<div id="submitOffer">
<div id="submitTittle">
<%
   if (offer == null)
      out.println("Enviar nueva oferta");
   else
      out.println("Modificar oferta");
%>
</div>
<%
   if (user == null)
   {
%>
<div style="text-align: center;"><br />
<strong> <img src="img/error.png" style="max-width: 30px;"></img><br />
<br />
Atención: </strong> Estás enviando esta oferta de forma anónima. <br />
Puedes continuar o acceder al sistema si deseas enviarla con tu usuario.
<br />
<br />
</div>
<%
   }
%> <br />
<br />
<%
   if (offer != null)
   {
%> <img src="/imageServer?uri=<%=offer.getUri()%>"
	style="float: right; max-height: 200px; max-width: 200px; -webkit-box-shadow: rgba(0, 0, 0, 0.199219) 5px 5px 5px; -moz-box-shadow: rgba(0, 0, 0, 0.199219) 5px 5px 5px; margin-right: 20px;" />
<%
   }
%>
<form action="/submitOffer" method="post" enctype="multipart/form-data">
<div>Título de la oferta:<br />
<input type="text" class="input" name="title" size="70"
	value="<%if (offer != null)
            out.println(offer.getTitle());%>" /><br />
<span style="font-weight: normal; font-size: smaller;">Ej: Disco
duro Western Digital 1TB por 53 Euros!!!<br />
(max 120 caracteres)</span></div>
<br />
<br />
<div>Url o dirección web:<br> <input type="text"
	class="input" name="url"
	value="<%if (offer != null)
            out.println(offer.getUrl());
         else
            out.println("http://");%>"
	onblur="if(this.value=='') this.value='http://'"
	onclick="if(this.value=='http://') this.value=''" size="70" /><br />
<span style="font-weight: normal; font-size: smaller;">Pega aquí
el enlace a la oferta.</span><br />
</div>
<br />
<br />
<div>Descripción:<br> <textarea class="textarea"
	name="content" rows="7" cols="100">
<%
   if (offer != null)
      out.println(offer.getContent());
%>
</textarea> <span style="font-weight: normal; font-size: smaller;"><br />
Destaca las características más importantes</span>
</div>
<br />
<br />
<div>Etiquetas:<br> <input type="text" class="input"
	name="tags"
	value="<%if (offer != null)
            out.println(offer.getTags());%>"
	size="35" /><br />
<span style="font-weight: normal; font-size: smaller;">Pocas
palabras, separadas por ","</span>
</div>
<br />
<br />
Categoría: <%=StaticHtmlGenerator.doCategorySelector(offer!=null?offer.getCategory():null) %>
<br />
<br />
<label for="title" accesskey="5">¿Quieres subir una imagen?:</label><br />
<div class="upload">
<div class="fakeupload"><input type="text" name="fakeupload"
	onchange="this.form.upload.value=this.form.fakeupload.value" /> <!-- browse button is here as background -->
<label>Examinar...</label></div>
<input type="file" name="upload" id="realupload" class="realupload"
	onchange="this.form.fakeupload.value = this.value;" /></div>
<span style="font-weight: normal; font-size: smaller;"> Mejora el
aspecto de la oferta ¡y los votos!</span>
<div><br />
<br />
<input type="hidden" name="uri"
	value="<%if (offer != null)
            out.println(offer.getUri());%>"/></div>
<%= Util.generateCaptcha()%>            
<input class="button" type="submit"
	value="<%if (offer != null)
            out.println("Modificar");
         else
            out.println("Enviar");%>"/>
</div>
</form>
</div>
<jsp:include page="/footer.jsp" flush="true" />
</body>
</html>