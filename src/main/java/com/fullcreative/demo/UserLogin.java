package com.fullcreative.demo;

import java.io.IOException;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jdo.Query;

@Controller
public class UserLogin {

	@RequestMapping("/login")
	public void login(HttpServletRequest req, HttpServletResponse res) throws IOException {

		String mailID = req.getParameter("mailID");
		String password = req.getParameter("password");

		HttpSession session = req.getSession();
		
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
		PersistenceManager pm = pmf.getPersistenceManager();

		if (mailID.equals("admin") && password.equals("admin@123")) {
			
			System.out.println("admin");

			session.setAttribute("currentUser", mailID + " " + password);
			session.setAttribute("src", "admin");

			// modelView.addObject("message", "Welcome Admin !");
			res.sendRedirect("welcome");

		}else {
			
			
			try {		

				
				String query = "mailID == '" + mailID + "' && password == '" + password + "'";

				Query q = pm.newQuery(User.class, query);
				String user = q.execute().toString();
	

				if (user.equals("[]")) {
					throw new EntityNotFoundException(null);
				}
				else {
		
					//String userDetails[] = user.split(",");
					//String getpassword = userDetails[1];
					//String getmailID = userDetails[2];					
					
					session.setAttribute("currentUser", mailID);
					session.setAttribute("src", "user");
					System.out.println(user);
					
					res.sendRedirect("welcome");
				}
				
				
				
			} catch (EntityNotFoundException e) {
				session.setAttribute("message", "Incorrect Credentials");
				res.sendRedirect("login.jsp");
			}
			
		}

		

	}

}
