package com.radsolutions.fleetbooks.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.radsolutions.fleetbooks.DTO.Project;
import com.radsolutions.fleetbooks.datasource.DataSource;

public class ProjectManager {

	private static final String GET_ACTIVE_PROJECTS = "SELECT P.*, A.firstname, A.lastname "
													+ "FROM Project AS P NATURAL JOIN Manages AS M NATURAL JOIN Account AS A "
													+ "WHERE isActive = TRUE AND P.id = M.projectid";
	private static final String GET_ALL_PROJECTS = "SELECT P.*, A.firstname, A.lastname "
			 									 + "FROM Project AS P NATURAL JOIN Manages AS M NATURAL JOIN Account AS A "
			 									 + "WHERE  P.id = M.projectid";

	private static final String ADD_PROJECT = "INSERT INTO project(companyid, name, location, startdate, enddate, cost, isactive, hasfueltank, foreman) VALUES(?, ?, ?, ?, ?, ?, TRUE, ?, ?);";

	private static final String UPDATE_PROJECT = "";
	
	private static final ProjectManager singleton = new ProjectManager();

	private ProjectManager(){

	}

	public static final ProjectManager getInstance(){
		return singleton;
	}

	public ArrayList<Project> getActiveProjects(){
		ArrayList<Project> result = new ArrayList<Project>();
		Project indRes;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_ACTIVE_PROJECTS);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				indRes = new Project();
				this.createProjectFromRS(indRes, rs);
				result.add(indRes);
			}
		}
		catch(Exception e){
			System.out.println("Unable to read data from data source: "+ e);
		}
		finally {
			this.closeConnection(conn);
		}
		return result;
	}
	
	public ArrayList<Project> getAllProjects(){
		ArrayList<Project> result = new ArrayList<Project>();
		Project indRes;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_ALL_PROJECTS);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				indRes = new Project();
				this.createProjectFromRS(indRes, rs);
				result.add(indRes);
			}
		}
		catch(Exception e){
			System.out.println("Unable to read data from data source: "+ e);
		}
		finally {
			this.closeConnection(conn);
		}
		return result;
	}
	
	//add new project to DB
	public boolean addProject(Project p){
		Connection conn = null;
		int status;
		try{
			conn = DataSource.getInstance().getJDBCConnection();
			
			PreparedStatement stmt = conn.prepareStatement(ADD_PROJECT);
			//(companyid, name, location, startdate, enddate, cost, isactive, hasfueltank, foreman
			stmt.setString(1, p.getCompanyID());
			stmt.setString(2, p.getName());
			stmt.setString(3, p.getLocation());
			stmt.setDate(4, p.getStartDate());
			stmt.setDate(5, p.getEndDate());
			stmt.setFloat(6, p.getCost());
			stmt.setBoolean(8, p.isHasFuelTank());
			stmt.setString(8, p.getForeman());			
			
			status = stmt.executeUpdate();
			if (status != 1){
				return false;
			}
		}
		catch(Exception e){
			return false;
		}
		finally{
			this.closeConnection(conn);
		}
		return true;
	}
	
	private void createProjectFromRS(Project p, ResultSet rs) throws Exception{
		p.setId(rs.getInt(1));
		p.setCompanyID(rs.getString(2));
		p.setName(rs.getString(3));
		p.setLocation(rs.getString(4));
		p.setStartDate(rs.getDate(5));
		p.setEndDate(rs.getDate(6));
		p.setCost(rs.getFloat(7));
		p.setActive(rs.getBoolean(8));
		p.setHasFuelTank(rs.getBoolean(9));
		p.setForeman(rs.getString(10));
		p.setEngineer(rs.getString(11)+" "+rs.getString(12));
	}
	
	private void closeConnection(Connection c){
		if (c != null){
			try {
				c.close();
			}
			catch(Exception e2){

			}
		}
	}

}
