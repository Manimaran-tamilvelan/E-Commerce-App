package com.fullcreative.demo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CheckUser {

	@RequestMapping("/welcome")
	public void redirect(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession sess = req.getSession();
		String source = (String) sess.getAttribute("src");

		// ModelAndView modelView = new ModelAndView();

		if (source == null) {
			res.sendRedirect("index.jsp");
			// modelView.setViewName("index.jsp");
		}

		else if (source.equals("register")) {

			// modelView.addObject("message", "Registered Successfully You, Can Login
			// Now!");
			// modelView.setViewName("login.jsp");

			sess.setAttribute("message", "Registered Successfully You, Can Login Now!");
			res.sendRedirect("login.jsp");

		} else if (source.equals("admin")) {

			// modelView.setViewName("adminpage.jsp");
			res.sendRedirect("adminpage.jsp");

		}

		else if (source.equals("user")) {

			// modelView.setViewName("index.jsp");
			res.sendRedirect("index.jsp");

		} else if (source.equals("err")) {
			// modelView.addObject("message", "Invalid Credentials!");
			// modelView.setViewName("login.jsp");

			sess.setAttribute("message", "Invalid Credential!");
			res.sendRedirect("login.jsp");

		}
		

		sess.removeAttribute("src");

		// return modelView;

	}

}
