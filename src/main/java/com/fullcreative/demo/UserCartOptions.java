package com.fullcreative.demo;

import java.io.IOException;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.datanucleus.store.exceptions.NoTableManagedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserCartOptions {

	@RequestMapping("/addProductToCart")
	public void addProductToCart(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession session = req.getSession();
		String currentUser = (String) session.getAttribute("currentUser");

		String productID = req.getParameter("productID");

		if (currentUser != null) {

			// create db for cart
			/**
			 * DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			 * Query q = new Query("UserCart"); PreparedQuery pq = datastore.prepare(q);
			 * String cartList = ""; for (Entity a : pq.asIterable()) { String users =
			 * (String) a.getKey().getName(); if (users.equals(currentUser)) { cartList =
			 * (String) a.getProperty("ProductID"); } } String currentProductID = productID;
			 * if (cartList.contains(productID)) { currentProductID = cartList; } else if
			 * (!cartList.equals("")) { currentProductID = cartList + "," + productID; }
			 * 
			 * Entity e = new Entity("UserCart", currentUser); e.setProperty("ProductID",
			 * currentProductID);
			 * 
			 * // add with old data(this replace all data) datastore.put(e);
			 */

			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
			PersistenceManager pm = pmf.getPersistenceManager();

			Query q = pm.newQuery(UserCart.class);

			// System.out.println(q.execute());

			if (q.execute().toString().equals("[]")) {
				System.out.println("Empty");

				UserCart ucart = new UserCart(currentUser, productID);

				try {
					pm.currentTransaction().begin();
					pm.makePersistent(ucart);
					pm.currentTransaction().commit();
				} finally {
					if (pm.currentTransaction().isActive()) {
						pm.currentTransaction().rollback();
					}
				}

			} else {

				List<UserCart> list = (List<UserCart>) q.execute();

				String ids = "";

				// for(int )
				for (int i = 0; i < list.size(); i++) {

					if (list.get(i).userId.equals(currentUser)) {
						ids = ids + list.get(i).ProductID + ",";
					}

				}

				ids = ids + productID;

				if (ids.startsWith(",")) {
					ids = ids.replaceFirst(",", "");
				} else if (ids.contains(",,")) {
					ids = ids.replaceAll(",,", ",");
				}

				UserCart ucart = new UserCart(currentUser, ids);

				try {
					pm.currentTransaction().begin();
					pm.makePersistent(ucart);
					pm.currentTransaction().commit();
				} finally {
					if (pm.currentTransaction().isActive()) {
						pm.currentTransaction().rollback();
					}
				}

			}

			res.getWriter().print("added");

		} else {
			res.getWriter().print("not added");
		}

	}

	@RequestMapping("/deleteFromCart")
	public void deleteFromCart(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String productId = req.getParameter("id");

		HttpSession sess = req.getSession();
		String CurrentUser = (String) sess.getAttribute("currentUser");
		/**
		 * DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 * 
		 * Query q = new Query("UserCart");
		 * 
		 * PreparedQuery pq = datastore.prepare(q); String currentUserCartProducts = "";
		 * 
		 * for (Entity e : pq.asIterable()) {
		 * 
		 * if (e.getKey().getName().toString().equals(CurrentUser)) {
		 * 
		 * currentUserCartProducts = (String) e.getProperty("ProductID");
		 * 
		 * }
		 * 
		 * }
		 * 
		 * if (currentUserCartProducts.equals("")) { System.out.println("Empty");
		 * 
		 * } else if (currentUserCartProducts.contains(",")) { String[] splitproductIds
		 * = currentUserCartProducts.split(",");
		 * 
		 * for (String a : splitproductIds) { System.out.println(a); }
		 * 
		 * List<String> list = new ArrayList<String>(Arrays.asList(splitproductIds));
		 * list.remove(productId); splitproductIds = list.toArray(new String[0]);
		 * 
		 * String finalList = "";
		 * 
		 * for (String finalproductId : splitproductIds) { finalList = finalList +
		 * finalproductId + ","; }
		 * 
		 * finalList = finalList.substring(0, finalList.length() - 1);
		 * 
		 * System.out.println(finalList);
		 * 
		 * Entity e = new Entity("UserCart", CurrentUser);
		 * 
		 * e.setProperty("ProductID", finalList);
		 * 
		 * datastore.put(e);
		 * 
		 * } else {
		 * 
		 * Entity e = new Entity("UserCart", CurrentUser);
		 * 
		 * e.setProperty("ProductID", "");
		 * 
		 * datastore.put(e);
		 * 
		 * }
		 */

		if (CurrentUser != null) {

			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
			PersistenceManager pm = pmf.getPersistenceManager();

			Query q = pm.newQuery(UserCart.class);

			// System.out.println(q.execute());

			List<UserCart> list = (List<UserCart>) q.execute();

			String ids = "";

			// for(int )
			for (int i = 0; i < list.size(); i++) {

				if (list.get(i).userId.equals(CurrentUser)) {
					ids = list.get(i).ProductID;
				}

			}

			if (ids.equals(productId)) {

				ids = "";

			} else if (ids.contains(productId)) {

				if (ids.contains(productId + ",")) {

					ids = ids.replace(productId + ",", "");

				} else {
					ids = ids.replace(productId, "");
				}

			}

			if (ids.startsWith(",")) {
				ids = ids.replaceFirst(",", "");
			} else if (ids.contains(",,")) {
				ids = ids.replaceAll(",,", ",");
			}

			UserCart ucart = new UserCart(CurrentUser, ids);

			try {
				pm.currentTransaction().begin();
				pm.makePersistent(ucart);
				pm.currentTransaction().commit();
			} finally {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}
			}

		}

		res.getWriter().print("removed");

	}

	@RequestMapping("/getShippingAddress")
	public void getShippingAddress(HttpServletRequest req, HttpServletResponse res) throws IOException {

		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String address = req.getParameter("address");
		String townOrCity = req.getParameter("townOrCity");
		String pincode = req.getParameter("pincode");

		String mobileNo = req.getParameter("mobileNo");
		String mailId = req.getParameter("mailId");

		System.out.println(firstName + "-" + lastName + "-" + "-" + address + "-" + townOrCity + "-" + pincode);
		System.out.println(mobileNo + "--" + mailId);

		HttpSession sess = req.getSession();
		String currentUser = (String) sess.getAttribute("currentUser");

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
		PersistenceManager pm = pmf.getPersistenceManager();

		ShippingAddress addAddress = new ShippingAddress(currentUser, firstName, lastName, address, townOrCity, pincode,
				mobileNo, mailId);

		try {

			pm.currentTransaction().begin();

			pm.makePersistent(addAddress);

			pm.currentTransaction().commit();

		} finally {

			if (pm.currentTransaction().isActive()) {

				pm.currentTransaction().rollback();

			}

		}

		res.getWriter().print("added");

	}

	@RequestMapping("/getAddress")
	public void getAddress(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession sess = req.getSession();
		String currentUser = (String) sess.getAttribute("currentUser");

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
		PersistenceManager pm = pmf.getPersistenceManager();

		Query q = pm.newQuery(ShippingAddress.class);

		String firstName = "";
		String LastName = "";
		String address = "";
		String townOrCity = "";
		String pincode = "";

		String mobileNo = "";
		String mailId = "";

		if (q.execute().toString().equals("[]")) {
			System.out.println("Empty");

		}

		else {
			List<ShippingAddress> l = (List<ShippingAddress>) q.execute();

			for (int i = 0; i < l.size(); i++) {

				if (l.get(i).currentUser.equals(currentUser)) {

					firstName = l.get(i).firstName;
					LastName = l.get(i).lastName;
					address = l.get(i).address;
					townOrCity = l.get(i).townOrCity;
					pincode = l.get(i).pincode;

					mobileNo = l.get(i).mobileNo;
					mailId = l.get(i).mailId;

				}

			}

		}

		if (firstName.equals("") || LastName.equals("") || address.equals("") || townOrCity.equals("")
				|| pincode.equals("") || mobileNo.equals("") || mailId.equals("")) {
			res.getWriter().print("");
		}

		else {
			res.getWriter().print(firstName + "|" + LastName + "|" + address + "|" + townOrCity + "|" + pincode + "|"
					+ mobileNo + "|" + mailId);
		}

	}

	@RequestMapping("/changeStatus")
	public void changeStatus(HttpServletRequest req, HttpServletResponse res) {

		String orderID = req.getParameter("orderID");
		String status = req.getParameter("status");

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
		PersistenceManager pm = pmf.getPersistenceManager();

		Query q = pm.newQuery(UserOrders.class);

		String orders = "";
		String userMailID = "";

		if (q.execute().toString().equals("[]")) {
			System.out.println("Empty");

		} else {

			List<UserOrders> l = (List<UserOrders>) q.execute();

			UserOrders changeStatus = null;

			for (int i = 0; i < l.size(); i++) {

				if (l.get(i).id.equals(orderID)) {

					changeStatus = new UserOrders(orderID, l.get(i).userMailID, l.get(i).Orders, status);

				}

			}

			try {
				pm.currentTransaction().begin();
				pm.makePersistent(changeStatus);
				pm.currentTransaction().commit();
			} finally {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}
			}

		}

	}

}
