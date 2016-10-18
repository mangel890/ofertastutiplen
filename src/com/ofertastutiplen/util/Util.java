package com.ofertastutiplen.util;

import java.io.InputStream;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class Util
{

   
   
   public static String formatHumanTime(Date date)
   {

      if (date==null)
         return "";
      
      Date end = new Date();
      
      long diffInSeconds = (end.getTime() - date.getTime()) / 1000;
      long tempSeconds = diffInSeconds;

      long diff[] = new long[]
      {0, 0, 0, 0};
      /* sec */diff[3] = (diffInSeconds >= 60
            ? diffInSeconds % 60
            : diffInSeconds);
      /* min */diff[2] = (diffInSeconds = (diffInSeconds / 60)) >= 60
            ? diffInSeconds % 60
            : diffInSeconds;
      /* hours */diff[1] = (diffInSeconds = (diffInSeconds / 60)) >= 24
            ? diffInSeconds % 24
            : diffInSeconds;
      /* days */diff[0] = (diffInSeconds = (diffInSeconds / 24));

      
      if (diff[0]>=29)
      {
         // TODO: Use local timezone offset (in javascript) to give a proper time
         java.text.SimpleDateFormat sdf = 
            new java.text.SimpleDateFormat("dd/MM/yyyy");
         sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
         String str = "El "+sdf.format(date);
         sdf = new java.text.SimpleDateFormat("HH:mm:ss");
         sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
         str +=" a las "+sdf.format(date);
         return str;
         
      }
      
      String str = "Hace ";
      
      if (diff[0]>0)
         str += String.format(
               "%d día%s ", diff[0],
               diff[0] > 1 ? "s" : "");
      
      if (diff[1]>0)
         str += String.format(
               "%d hora%s ", diff[1],
               diff[1] > 1 ? "s" : "");
      
      if (diff[2]>0 && tempSeconds < 24*3600)
         str += String.format(
               "%d minuto%s ", diff[2],
               diff[2] > 1 ? "s" : "");

      if (diff[3]>0 && tempSeconds < 3600)
         str += String.format(
               "%d segundo%s ", diff[3],
               diff[3] > 1 ? "s" : "");
      
      return str;
   }
   
   public static boolean checkCaptcha(HttpServletRequest request, String challenge, String response)
   {
   	
    	if (!Config.captchaActive)
   		return true;
    	
    	String remoteAddr = request.getRemoteAddr();
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey("6Ld4V8ESAAAAANeL45QDs7h1rO7k3rTD1ybYWwnf");
		
		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr,
		      challenge, response);

		return reCaptchaResponse.isValid();
   }
   
   public static String generateCaptcha()
   {
   	if (!Config.captchaActive)
   		return "";
   	
   	ReCaptcha c = ReCaptchaFactory.newReCaptcha("6Ld4V8ESAAAAAE5yPjn0X6qQ4w3z3SWAATez8TES",
     		 "6Ld4V8ESAAAAANeL45QDs7h1rO7k3rTD1ybYWwnf", false);
   	String str = "<script type=\"text/javascript\"> var RecaptchaOptions = {theme : 'white',lang : 'es'};</script> ";
      return str + c.createRecaptchaHtml(null, null);

   }
   
   public static String limitString(String inputString, int limit)
   {
   	if (inputString.length()>limit)
   	{
   		return inputString.substring(0,limit-1)+"...";
   	}
   	return inputString;
   }
   
}
