package com.radsolutions.fleetbooks.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.radsolutions.fleetbooks.DTO.Account;
import com.radsolutions.fleetbooks.services.AccountManager;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/MobileLoginServlet")
public class MobileLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MobileLoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet entered");
		//response.getOutputStream().println("Hurray !! This Servlet Works");
		System.out.println("doGet completed successfully");
		doPost(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String status = "Invalid Login";
			AccountManager am = AccountManager.getInstance();
			Account account = am.getUserByEmail(email.trim().toLowerCase());
			System.out.println(email.trim().toLowerCase());
			if(account==null){
				status = "Invalid email: "+email;
			}
			else if(password.equals(account.getPassword())){
				status="Logged in";
				System.out.println(account.getFirstName()+" "+account.getLastName());
			}
			else {
				status="Invalid password";
			}

			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(status);

		}
		catch (Exception e) {
			response.getWriter().println(e.getMessage());
		}
	}
	
/*	public static void main(String[] args){
		AccountManager am = AccountManager.getInstance();
		Account a = am.getUserByEmail("christian.barrientos@upr.edu");
		System.out.println(a.getFirstName()+" "+a.getLastName());
	}*/
}