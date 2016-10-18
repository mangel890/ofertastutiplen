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
<title>Ofertas Tutiplen | Enviar Oferta</title>
</head>
<script type="text/javascript">
function checkFields()
{
	if (document.getElementsByName("name")[0].value!="")
	   document.getElementsByName("name")[0].style.backgroundColor='#AFA';
	else
	   document.getElementsByName("name")[0].style.backgroundColor='#FFF';

	if (document.getElementsByName("email")[0].value.indexOf("@")!=-1)
      document.getElementsByName("email")[0].style.backgroundColor='#AFA';
   else
	   document.getElementsByName("email")[0].style.backgroundColor='#FFF';

   if (document.getElementsByName("password")[0].value!="")
      document.getElementsByName("password")[0].style.backgroundColor='#AFA';
	else
	   document.getElementsByName("password")[0].style.backgroundColor='#FFF';

   if (document.getElementsByName("password")[0].value ==
	   document.getElementsByName("passwordVerify")[0].value &&
	   document.getElementsByName("passwordVerify")[0].value!= "")
	   document.getElementsByName("passwordVerify")[0].style.backgroundColor='#AFA';
	else
	   document.getElementsByName("passwordVerify")[0].style.backgroundColor='#FFF';

	
   if ((document.getElementsByName("password")[0].value==
        document.getElementsByName("passwordVerify")[0].value) &&
       (document.getElementsByName("name")[0].value!="") &&
       (document.getElementsByName("email")[0].value.indexOf("@")!=-1) &&
       (document.getElementsByName("password")[0].value!=""))
      
   {
         document.getElementsByName("button")[0].disabled=false;  
         document.getElementsByName("button")[0].className="button";  
   }
   else
   {
        document.getElementsByName("button")[0].disabled=true;  
        document.getElementsByName("button")[0].className="buttonDisabled";  
      
   }
}
</script>
<body>
<div id="body" align="center">

<jsp:include page="/header.jsp" flush="true" >
<jsp:param name="selector" value="0"/>
</jsp:include>

 <br />
<div id="submitOffer">
<div id="submitTittle">Alta Nuevo Usuario</div>
<br />
<br />
<form action="/signUser" method="post" enctype="multipart/form-data">
<div>Nombre de usuario:<br />
<input type="text" class="input" name="name" size="50" onkeyup="checkFields()" /></div>
<br />
<br />
<div>E-mail:<br> <input type="text" class="input" name="email"
   onkeyup="checkFields()" size="50" /><br />
<br />
</div>
<br />
<br />
<div>Clave:<br> <input type="password" class="input" name="password"
   size="25" onkeyup="checkFields()" />
</div>
<br />
<br />
<div>Verificación de clave:<br> <input type="password" class="input"
   name="passwordVerify" size="25" onkeyup="checkFields()" /> <br />
</div>
<br />
<br />
<div>

<%= Util.generateCaptcha()%>
<br />
<br />
<input name="button" class="buttonDisabled" type="submit" disabled="disabled"
   value="Alta de usuario" /></div>
</form>
</div>
<jsp:include page="/footer.jsp" flush="true" />
</body>
</html>