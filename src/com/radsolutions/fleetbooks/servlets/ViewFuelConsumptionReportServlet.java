package com.radsolutions.fleetbooks.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.radsolutions.fleetbooks.DTO.ExpensesReportRow;
import com.radsolutions.fleetbooks.services.ReportManager;

/**
 * Servlet implementation class ViewFuelConsumptionReportServlet
 */
@WebServlet("/ViewFuelConsumptionReportServlet")
public class ViewFuelConsumptionReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewFuelConsumptionReportServlet() {
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
		ReportManager rm = ReportManager.getInstance();
		String sDate = request.getParameter("sDate");
		String eDate = request.getParameter("eDate");
		int projectId = Integer.valueOf(request.getParameter("projectId"));
		ArrayList<ExpensesReportRow> report = rm.getProjectExpenseReport(sDate, eDate, projectId);
		
		request.setAttribute("report", report);
		
		String nextUrl = "./ExpensesAdmin.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextUrl); 
		dispatcher.forward(request, response);
	}

}
