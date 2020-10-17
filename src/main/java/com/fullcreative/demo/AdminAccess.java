package com.fullcreative.demo;

import java.awt.List;
import java.io.IOException;
import java.util.UUID;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminAccess {

	@RequestMapping("/fetchuserorders")
	public void getUsers(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession session = req.getSession();
		String currentUser = (String) session.getAttribute("currentUser");

		if (currentUser == null || !currentUser.equals("admin admin@123")) {

			session.setAttribute("src", "user");

			res.sendRedirect("/welcome");
		}
		
		
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
		PersistenceManager pm = pmf.getPersistenceManager();
		
		Query q = pm.newQuery(UserOrders.class);
		
		
		String result ="";
		
		if (q.execute().toString().equals("[]")) {
			System.out.println("Empty");
		}
		
		else {
			
			java.util.List<UserOrders> userOrders = (java.util.List<UserOrders>) q.execute();
			
			for(int i=0;i<userOrders.size();i++) {
				
				
				
					
					result = result.concat("<tr>");
					result = result.concat("<td>" + userOrders.get(i).id + "</td>");
					result = result.concat("<td>" + userOrders.get(i).Orders+ "</td>");
					result = result.concat("<td>" + userOrders.get(i).Status + "</td>");
					result = result.concat("<td>" + userOrders.get(i).userMailID + "</td>");
					result = result.concat("</tr>");
	
				
			}
			//System.out.println(result);
		}
		

		res.getWriter().print(result);

	}

	@RequestMapping("/addToCart")
	public void addToCart(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession session = req.getSession();
		String currentUser = (String) session.getAttribute("currentUser");

		if (currentUser == null || !currentUser.equals("admin admin@123")) {

			session.setAttribute("src", "user");

			res.sendRedirect("/welcome");
		}

		String productName = req.getParameter("productName");
		String productValue = req.getParameter("productValue");
		String productStock = req.getParameter("stockValue");

		UUID uuid = UUID.randomUUID();

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
		PersistenceManager pm = pmf.getPersistenceManager();

		Products product = new Products(uuid.toString(), productName, productValue, productStock);

		try {

			pm.currentTransaction().begin();

			// System.out.println(getQ.execute());

			pm.makePersistent(product);

			pm.currentTransaction().commit();

		}

		finally {

			if (pm.currentTransaction().isActive()) {
				System.out.println("rollback");
				pm.currentTransaction().rollback();
			}
		}
		/**
		 * DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 * Entity e = new Entity("Products", uuid.toString());
		 * e.setProperty("ProductName", productName); e.setProperty("ProductValue",
		 * productValue); e.setProperty("AvailableStock", productStock);
		 * datastore.put(e);
		 */
		// System.out.println(productName+"--"+productValue+"--"+productStock);

		res.getWriter().print("<tr><td>" + uuid.toString() + "</td><td>" + productName + "</td><td>" + productValue
				+ "</td><td>" + productStock + "</td></tr>");

	}

	@RequestMapping("/getProducts")
	public void getProducts(HttpServletRequest req, HttpServletResponse res) throws IOException {

		// DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// Query q = new Query("Products");
		// PreparedQuery pq = datastore.prepare(q);

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
		PersistenceManager pm = pmf.getPersistenceManager();

		Query q = pm.newQuery(Products.class);

		// System.out.println(q.execute());
		String result = "";

		if (q.execute().toString().equals("[]")) {
			System.out.println("Empty");
		} else {

			java.util.List<Products> results = (java.util.List<Products>) q.execute();

			System.out.println(results.size());

			for (int i = 0; i < results.size(); i++) {

				System.out.println(results.get(i).ProductName);

				result = result.concat("<tr>");
				result = result.concat("<td>" + results.get(i).uuid + "</td>");
				result = result.concat("<td>" + results.get(i).ProductName + "</td>");
				result = result.concat("<td>" + results.get(i).ProductValue + "</td>");
				result = result.concat("<td>" + results.get(i).AvailableStock + "</td>");
				result = result.concat("</tr>");

			}

			/**
			 * String result = "";
			 * 
			 * for (Entity products : pq.asIterable()) {
			 * 
			 * result = result.concat("
			 * <tr>
			 * "); result = result.concat("
			 * <td>" + products.getKey().getName() + "</td>"); result = result.concat("
			 * <td>" + products.getProperty("ProductName") + "</td>"); result =
			 * result.concat("
			 * <td>" + products.getProperty("ProductValue") + "</td>"); result =
			 * result.concat("
			 * <td>" + products.getProperty("AvailableStock") + "</td>"); result =
			 * result.concat("
			 * </tr>
			 * ");
			 * 
			 * }
			 */
			// System.out.println(productName+"--"+productValue+"--"+productStock);
		}
		res.getWriter().print(result);

	}

}
