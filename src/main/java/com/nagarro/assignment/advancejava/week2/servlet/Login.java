package com.nagarro.assignment.advancejava.week2.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;

import com.nagarro.assignment.advancejava.week2.hibernate.*;
import com.nagarro.assignment.advancejava.week2.model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("loginuserName");
		String pass = request.getParameter("loginpass");
		
		Session session =  FactoryProvider.getFactory().openSession();
		session.beginTransaction();
		Query query = session.createQuery("from User where username=:username");
		query.setParameter("username", username);
		User user = (User)query.uniqueResult();
		session.getTransaction().commit();
		session.close();
		
		if (user==null)
		{
			response.getWriter().print("No such User Exists");
		}
		else if(user.getPass().equals(pass))
		{
			response.getWriter().print("Login successful");
			response.addCookie(new Cookie("username", username));
			response.addCookie(new Cookie("pass", pass));
		}
		else
			response.getWriter().print("Sorry, Invalid Password");
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendRedirect("Welcome");
	}
	
	
}
