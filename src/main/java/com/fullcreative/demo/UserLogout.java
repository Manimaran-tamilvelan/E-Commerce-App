package com.fullcreative.demo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserLogout {

	@RequestMapping("/logout")
	public void logoutUser(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession sess = req.getSession();
		sess.removeAttribute("currentUser");
		sess.invalidate();

		// check the adminpage because of without logout shows again signin
		// change the headers
		
		sess = req.getSession();

		sess.setAttribute("message", "Logged Out!!");
		res.sendRedirect("login.jsp");

		// ModelAndView modelView = new ModelAndView();

		// modelView.setViewName("login.jsp");
		// modelView.addObject("message", "Logged Out !");
		// return modelView;

	}

}
