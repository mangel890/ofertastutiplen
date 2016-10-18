package com.ofertastutiplen.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import com.ofertastutiplen.entities.Category;
import com.ofertastutiplen.entities.User;

public class StaticHtmlGenerator
{

	// TODO: Change += by StringBuilders, they are much faster

	public static String doCategorySelector(String selectedOffer)
	{
		String result = "<table><td><br/>";

		List<Category> parents = CategoryHelper.getParentCategories();

		for (Category p : parents)
		{
			result+="<div class=\"singleCategoryBox\">";
			result+="<div style=\"text-align:center;font-weight:bold\">"
			      + p.getName() + "</div><br/>";
			List<Category> children = CategoryHelper.getCategories(p.getName());
			for (Category c : children)
			{
				String checked = "";

				if (selectedOffer != null && c.getName().equals(selectedOffer))
				{
					checked = "checked";
				}
				result+="<input type=\"radio\" name=\"category\" value=\""
				      + c.getName() + "\"" + checked + "/>" + c.getName()
				      + "<br />";
			}
			result+="</div>";
		}
		result += "</td></table>";
		return result;
	}

	public static String doPageSelector(int totalOffers, int currentPage,
	      String basePage)
	{
		String result = "<table align=\"center\"><td>";

		int totalNumberOfPages = (int) Math.ceil((float) totalOffers
		      / Config.ItemsPerPage);
		{
			int upperPage = currentPage + 2;
			int lowerPage = currentPage - 2;
			if (totalNumberOfPages <= 5)
			{
				lowerPage = 1;
				upperPage = totalNumberOfPages;
			} else if (lowerPage < 1)
			{
				lowerPage = 1;
				upperPage = lowerPage + 5 - 1;
			} else if (upperPage > totalNumberOfPages)
			{
				upperPage = totalNumberOfPages;
				lowerPage = upperPage - 5 + 1;
			}
			// Show prev
			if (currentPage > 1 && lowerPage > 1)
			{
				result += "<div class=\"prev\">" + "<a href=\"" + basePage
				      + "?page=" + (lowerPage - 1) + "\">ant</a></div>";
			}

			// Show 5 inner elements
			for (int k = lowerPage; k <= upperPage; k++)
			{
				String classSel = "";
				if (k != currentPage)
					classSel = "page";
				else
					classSel = "pageSelected";

				result += "<div class=\"" + classSel + "\">" + "<a href=\""
				      + basePage + "?page=" + k + "\">" + +k + "</a></div>";
			}

			// Show next
			if (currentPage < totalNumberOfPages && upperPage < totalNumberOfPages)
			{
				result += "<div class=\"prev\">" + "<a href=\"" + basePage
				      + "?page=" + (upperPage + 1) + "\">sig</a></div>";
			}
		}

		result += "</td></table>";
		return result;
	}

	public static String doHeader_old(int selector, User user)
	{
		int length = 0;
		String myString = new String();
		byte b[] = new byte[256];
		try
		{
			FileInputStream fis = new FileInputStream("static/header.html");

			while ((length = fis.read(b)) != -1)
			{
				myString += new String(b, 0, length);
			}
		} catch (Exception e)
		{
			return e.toString();
		}

		int counter = 1;
		while (counter <= 5)
		{
			if (counter == selector)
			{
				myString = myString.replaceFirst("\\{%\\$.%\\}", "id=\"selected\"");
			} else
			{
				myString = myString.replaceFirst("\\{%\\$.%\\}", "");
			}
			counter++;
		}

		String str;

		if (user != null)
		{
			str = "<div id=\"logInBox\">"
			      + "<a href=\"modifyUser.jsp\" style=\"text-decoration: none;\">"
			      + user.getId() + "<img src=\"\"/>"
			      + "<label class=\"button\">Modificar Perfil</label> </a>"
			      + "<a href=\"/logOut\" style=\"text-decoration: none;\">"
			      + "<label class=\"button\">Cerrar Sesión</label></a>" + "</div>";
		} else
		{
			str = "<div id=\"logInBox\">"
			      + "<form action=\"/logIn\" method=\"post\" enctype=\"multipart/form-data\">"
			      + "Usuario: <input type=\"text\" name=\"idLogin\" class=\"input\"/><br/>"
			      + "Clave: <input type=\"password\" name=\"passwordLogin\" class=\"input\"/><br>"
			      + "<label class=\"button\">Registrarse</label>"
			      + "<input type=\"submit\" name=\"submit\" class=\"buttonDisabled\" value=\"Acceder\"/>"
			      + "</form></div>";

		}

		myString = myString.replaceFirst("\\{%userControl%\\}", str);

		return myString;

	}

	public static String doFooter_old()
	{
		int length = 0;
		String myString = new String();
		byte b[] = new byte[256];
		try
		{
			FileInputStream fis = new FileInputStream("static/footer.html");

			while ((length = fis.read(b)) != -1)
			{
				myString += new String(b, 0, length);
			}
		} catch (Exception e)
		{
			return e.toString();
		}
		return myString;

	}

}
