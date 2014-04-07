package com.radsolutions.fleetbooks.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.radsolutions.fleetbooks.DTO.Equipment;
import com.radsolutions.fleetbooks.services.EquipmentManager;

/**
 * Servlet implementation class MobileProjectInformationServlet
 */
@WebServlet("/MobileProjectInformationServlet")
public class MobileProjectInformationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MobileProjectInformationServlet() {
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
		try {	
			int id = Integer.valueOf(request.getParameter("id"));
			EquipmentManager em = EquipmentManager.getInstance();
			ArrayList<Equipment> equipment = em.getEquipmentInProject(id);

			Gson gson = new Gson();
			String json = gson.toJson(equipment);

			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(json);

		}
		catch (IOException e) {
			response.getWriter().println(e.getMessage());
		}
	}

}