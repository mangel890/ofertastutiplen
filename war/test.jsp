<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ofertastutiplen.*" %>
<%@ page import="com.ofertastutiplen.util.*" %>
<%@ page import="com.ofertastutiplen.entities.*" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="javax.jdo.Query" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" rel="stylesheet" href="ofertasTutiplen.css">
<link rel="shortcut icon" href="img/tut.ico"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Ofertas Tutiplen</title>
</head>
<body>
<div id="body">
<jsp:include page="/header.jsp" flush="true" >
<jsp:param name="selector" value="1"/>
</jsp:include>

<%
PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();
Query query = pm.newQuery(Category.class);
long lg = query.deletePersistentAll();
//List<Category> list = (List<Category>) query.deletePersistentAll();
pm.close();

pm = PMFContainer.getPMF().getPersistenceManager();
String[] parents = {"Audio","Electrodomésticos","Varios",
      "PC","Video"};

for (String g:parents)
{
   Category cat = new Category();
   cat.setName(g);
   cat.setParentCategory(null);
   try 
   {
      pm.makePersistent(cat);
   }
   catch (Exception e)
   {
      e.printStackTrace();
   }
}

String[] audio = {"Altavoces", "Auriculares","Home Cinema",
      "MP3", "Autorradio","Otros audio"};

for (String g:audio)
{
   Category cat = new Category();
   cat.setName(g);
   cat.setParentCategory("Audio");
   try 
   {
      pm.makePersistent(cat);
   }
   catch (Exception e)
   {
      e.printStackTrace();
   }
}

String[] electrodomésticos = {"Frigoríficos", "Lavadoras/Secadoras","Hornos/Placas",
      "Calefacción", "Otros electrodomésticos"};

for (String g:electrodomésticos)
{
   Category cat = new Category();
   cat.setName(g);
   cat.setParentCategory("Electrodomésticos");
   try 
   {
      pm.makePersistent(cat);
   }
   catch (Exception e)
   {
      e.printStackTrace();
   }
}

String[] pc = {"CD/DVD", "BluRay","Impresoras",
      "Discos duros", "Memorias", "Portatiles","Procesadores",
      "Placas base", "Tarjetas gráficas", "Otros PC"};

for (String g:pc)
{
   Category cat = new Category();
   cat.setName(g);
   cat.setParentCategory("PC");
   try 
   {
      pm.makePersistent(cat);
   }
   catch (Exception e)
   {
      e.printStackTrace();
   }
}

String[] varios = {"Automóvil", "Consolas","Navegador GPS",
      "Mobiliario", "Telefonía","Software", "Viajes", "Otros misceláneo"};

for (String g:varios)
{
   Category cat = new Category();
   cat.setName(g);
   cat.setParentCategory("Varios");
   try 
   {
      pm.makePersistent(cat);
   }
   catch (Exception e)
   {
      e.printStackTrace();
   }
}

String[] video = {"DVD / Blue-ray", "E-books", "Fotografía",
      "Marcos digitales", "Monitores", "MP4", "Otros Video",
      "Proyectores","TDT","TV","Videocámaras"};

for (String g:video)
{
   Category cat = new Category();
   cat.setName(g);
   cat.setParentCategory("Video");
   try 
   {
      pm.makePersistent(cat);
   }
   catch (Exception e)
   {
      e.printStackTrace();
   }
}



pm.close();


%>

   
<jsp:include page="/footer.jsp" flush="true" />
</body>
</html>