package com.ofertastutiplen.util;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ofertastutiplen.entities.Category;

public class CategoryHelper
{
   public static List<Category> getParentCategories()
   {
      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();
      Query query = pm.newQuery(Category.class);
      query.setFilter("parentCategory==null");
      return (List<Category>)query.execute();
   }
   
   public static List<Category> getCategories(String parent)
   {
      PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();
      Query query = pm.newQuery(Category.class);
      query.setFilter("parentCategory==myParent");
      query.declareParameters("String myParent");
      return (List<Category>)query.execute(parent);     
   }
}
