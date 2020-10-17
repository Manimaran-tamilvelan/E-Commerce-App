package com.fullcreative.demo;


import java.io.IOException;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("hello")
public class Demo extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String userName = "helo";
		String password = "helo1";
		String mailID = "helo2";
		String dOB = "helo3";
		String mobileNo = "helo4";
		
		//PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
		//PersistenceManager pm = pmf.getPersistenceManager();

		
		 Properties properties = new Properties();

		    properties.put(
		      "javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
		    properties.put(
		      "javax.jdo.option.ConnectionURL", "appengine");
		    properties.put(
				      "datanucleus.appengine.datastoreReadConsistencys", "STRONG");
		    properties.put(
				      "javax.jdo.option.NontransactionalRead", "true");
		    properties.put(
				      "javax.jdo.option.NontransactionalWrite", "true");
		    properties.put(
		      "javax.jdo.option.RetainValues", "true");
		  
		      
		      PersistenceManagerFactory pmf =
		      JDOHelper.getPersistenceManagerFactory(properties);
		   
		   PersistenceManager pm = pmf.getPersistenceManager();
		   
		
		
		User addUser = new User(userName, password, mailID, dOB, mobileNo);
		
		//User addUser = new User(userName, password, mailID, dOB, mobileNo);

		// Query getQ = pm.newQuery(User.class, "userName == '" + userName + "'");

		try {

			pm.currentTransaction().begin();
			// System.out.println(getQuery);
			// System.out.println(getQ.execute());

			pm.makePersistent(addUser);
			
			//System.out.println("ooo");

			pm.currentTransaction().commit();

		}

		finally {

			if (pm.currentTransaction().isActive()) {
				System.out.println("rollback");
				pm.currentTransaction().rollback();
			}

		}

	}
}