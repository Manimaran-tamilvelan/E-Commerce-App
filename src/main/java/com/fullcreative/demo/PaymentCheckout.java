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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaymentCheckout {

	@RequestMapping("/checkout")
	public ModelAndView checkout(HttpServletRequest req, HttpServletResponse res) {

		String src = req.getParameter("ref");

		// System.out.println(src);
		ModelAndView modelView = new ModelAndView();

		// get current user cart rs. and send it on modelview

		HttpSession sess = req.getSession();

		String currentUser = (String) sess.getAttribute("currentUser");

		if (sess.getAttribute("currentUser") == null | src == null) {
			modelView.setViewName("index.jsp");
			return modelView;
		}

		if (src.equals("cart")) {

			int overAll = getTotalCost(currentUser);

			modelView.addObject("overAllCost", overAll);
			modelView.setViewName("checkout.jsp");

		}

		return modelView;

	}

	public int getTotalCost(String currentUser) {

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
		PersistenceManager pm = pmf.getPersistenceManager();

		Query q = pm.newQuery(UserCart.class);
		// paymentcheckout,usercart, adminaccess

		int overAll = 0;

		if (q.execute().toString().equals("[]")) {
			System.out.println("Empty");
		} else {

			List<UserCart> userCartIds = (List<UserCart>) q.execute();

			String productIds = "";
			for (int i = 0; i < userCartIds.size(); i++) {

				if (userCartIds.get(i).userId.equals(currentUser)) {
					productIds = userCartIds.get(i).ProductID;
				}

			}

			if (productIds.contains(",")) {

				String[] productid = productIds.split(",");

				Query q1 = pm.newQuery(Products.class);

				List<Products> fetchprice = (List<Products>) q1.execute();

				for (String everyProduct : productid) {

					for (int i = 0; i < fetchprice.size(); i++) {

						if (fetchprice.get(i).uuid.equals(everyProduct)) {

							int price = Integer.valueOf(fetchprice.get(i).ProductValue);

							overAll = overAll + price;
						}

					}

				}

			}

			else {

				String productid = productIds;

				Query q1 = pm.newQuery(Products.class);

				List<Products> fetchprice = (List<Products>) q1.execute();

				for (int i = 0; i < fetchprice.size(); i++) {

					if (fetchprice.get(i).uuid.equals(productid)) {

						int price = Integer.valueOf(fetchprice.get(i).ProductValue);

						overAll = overAll + price;
					}

				}

			}

		}

		return overAll;
	}

	@RequestMapping("/payment")
	public void getPayment(HttpServletRequest req, HttpServletResponse res) throws IOException {

		String getId = req.getParameter("orderId");
		String src = req.getParameter("ref");

		HttpSession sess = req.getSession();

		String currentUser = (String) sess.getAttribute("currentUser");

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
		PersistenceManager pm = pmf.getPersistenceManager();

		Query q = pm.newQuery(UserCart.class);

		List<UserCart> l = (List<UserCart>) q.execute();

		String orderedProducts = "";

		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).userId.equals(currentUser)) {
				orderedProducts = l.get(i).ProductID;
			}

		}

		UserOrders order = new UserOrders(getId, currentUser, orderedProducts, "new");

		try {
			pm.currentTransaction().begin();
			pm.makePersistent(order);
			pm.currentTransaction().commit();

		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
		}

		res.getWriter().println("success");

	}

}
