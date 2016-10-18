/**
 * 
 */
package com.ofertastutiplen.client;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ofertastutiplen.util.StaticHtmlGenerator;

/**
 * @author Miguel
 *
 */
public class StaticGeneratorTest
{

   /**
    * @throws java.lang.Exception
    */
   @Before
   public void setUp() throws Exception
   {
   }

   /**
    * @throws java.lang.Exception
    */
   @After
   public void tearDown() throws Exception
   {
   }

   /**
    * Test method for {@link com.ofertastutiplen.util.StaticHtmlGenerator#doHeader_old(int, com.ofertastutiplen.entities.User)}.
    */
   @Test
   public void testDoHeader()
   {
      System.out.println(StaticHtmlGenerator.doPageSelector(1, 1,"recent.jsp"));
      System.out.println(StaticHtmlGenerator.doPageSelector(3, 2,"recent.jsp"));
      System.out.println(StaticHtmlGenerator.doPageSelector(10, 1,"recent.jsp"));
      System.out.println(StaticHtmlGenerator.doPageSelector(15, 4,"recent.jsp"));
      System.out.println(StaticHtmlGenerator.doPageSelector(16, 5,"recent.jsp"));
      System.out.println(StaticHtmlGenerator.doPageSelector(17, 7,"recent.jsp"));
      System.out.println(StaticHtmlGenerator.doPageSelector(17, 8,"recent.jsp"));
      System.out.println(StaticHtmlGenerator.doPageSelector(17, 9,"recent.jsp"));
   }

}
