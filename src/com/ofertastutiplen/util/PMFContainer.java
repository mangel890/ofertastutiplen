package com.ofertastutiplen.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class PMFContainer
{
   public static PersistenceManagerFactory pmfInstanceM;

   public static PersistenceManagerFactory getPMF()
   {
      if (pmfInstanceM == null)
      {
         pmfInstanceM = JDOHelper
               .getPersistenceManagerFactory("transactions-optional");
      }

      return pmfInstanceM;
   }
}
