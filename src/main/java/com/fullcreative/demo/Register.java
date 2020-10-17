package com.fullcreative.demo;

import java.io.IOException;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Register extends HttpServlet {
	
	
	@RequestMapping("/")
	public ModelAndView home(HttpServletRequest req, HttpServletResponse res) throws IOException {

		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("index.jsp");

		return modelView;

	}
	

	@RequestMapping("/register")
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String mailID = req.getParameter("mailID");
		String dOB = req.getParameter("dOB");
		String mobileNo = req.getParameter("mobileNo");

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Demo");
		PersistenceManager pm = pmf.getPersistenceManager();
		
		//String hashed = BCrypt.hashpw(password, BCrypt.gensalt(10));


		User addUser = new User(userName, password, mailID, dOB, mobileNo);

		// Query getQ = pm.newQuery(User.class, "userName == '" + userName + "'");

		try {

			pm.currentTransaction().begin();
			
			// System.out.println(getQ.execute());

			pm.makePersistent(addUser);


			pm.currentTransaction().commit();

		}

		finally {

			if (pm.currentTransaction().isActive()) {
				System.out.println("rollback");
				pm.currentTransaction().rollback();
			}
			
			
			HttpSession sess = req.getSession();
			sess.setAttribute("src", "register");

			res.sendRedirect("welcome");

		}

	}

}
