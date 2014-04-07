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
 * Servlet implementation class MobileRequestAccountServlet
 */
@WebServlet("/MobileRequestAccountServlet")
public class MobileRequestAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MobileRequestAccountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String accountType = request.getParameter("accountType");
			AccountManager am = AccountManager.getInstance();
			Account account = new Account();
			account.setFirstName(fname);
			account.setLastName(lname);
			account.setPhone(phone);
			account.setEmail(email);
			account.setPassword(password);
			account.setType(accountType);
			account.setApproved(false);
			boolean result = am.addAccount(account);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(String.valueOf(result));
		}
		catch(IOException e){
			response.getWriter().println(e.getMessage());
		}
	}

}