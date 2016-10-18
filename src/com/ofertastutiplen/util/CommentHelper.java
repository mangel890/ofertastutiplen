package com.ofertastutiplen.util;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.ofertastutiplen.entities.Comment;

public class CommentHelper
{

	public static List<Comment> getComments(Key offer)
	{
		PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

		Query query = pm.newQuery(Comment.class);
      query.declareParameters("String myOffer");
		query.setFilter("offer==myOffer");
		query.setOrdering("date descending");
		return (List<Comment>) query.execute(offer);

	}

	public static int countComments(Key offer)
	{
		PersistenceManager pm = PMFContainer.getPMF().getPersistenceManager();

		Query query = pm.newQuery(Comment.class);
      query.declareParameters("String myOffer");
		query.setFilter("offer==myOffer");
      query.setResult("count(key)");
	   return (Integer)query.execute(offer);

	}

}
