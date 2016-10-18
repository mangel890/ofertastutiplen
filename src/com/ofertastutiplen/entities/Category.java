package com.ofertastutiplen.entities;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Category
{

   @Persistent
   @PrimaryKey
   private String name;
   
   @Persistent
   private String parentCategory;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getParentCategory()
   {
      return parentCategory;
   }

   public void setParentCategory(String parentCategory)
   {
      this.parentCategory = parentCategory;
   }
 
}
