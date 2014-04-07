package com.radsolutions.fleetbooks.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.radsolutions.fleetbooks.DTO.Equipment;
import com.radsolutions.fleetbooks.datasource.DataSource;

public class EquipmentManager {

	private static final String GET_EQUIPMENT_ORDERED_BY_TYPE = "SELECT E.*, P.name FROM equipment AS E, project as P WHERE E.projectID = P.id ORDER BY type ASC";
	private static final String GET_EQUIPMENT_BY_ID = "SELECT E.*, P.name FROM equipment AS E, project AS P WHERE E.projectID = P.id AND E.id = ?";
	private static final String GET_EQUIPMENT_IN_PROJECT = "SELECT E.*, P.name FROM equipment AS E, project AS P WHERE E.projectID = P.id AND P.id = ?";
	
	private static final String ADD_EQUIPMENT = "INSERT INTO equipment (companyid, brand, model, year, type, vin, value, usagehour, monthlycost, weeklycost, dailycost, note, isactive, isavailable, isbilling, projectid, photo) VALUES (companyid, brand, model, year, type, vin, value, usagehour, monthlycost, weeklycost, dailycost, note, isactive, isavailable, isbilling, projectid, photo);";
	
	private static final EquipmentManager singleton = new EquipmentManager();

	private EquipmentManager() {
		// TODO Auto-generated constructor stub
	}

	public static final EquipmentManager getInstance(){
		return singleton;
	}
	
	public Equipment getEquipmentById(int id){

		Equipment result = null;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_EQUIPMENT_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result =new Equipment();
				createEquipmentFromRS(result, rs);
			}
		}
		catch(Exception e){
			System.out.println("Unable to read data from data source: "+ e);
		}
		finally {
			closeConnection(conn);
		}
		return result;
	}
	
	public ArrayList<Equipment> getEquipmentOrderedByType(){

		ArrayList<Equipment> result = new ArrayList<Equipment>();
		Equipment indRes;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_EQUIPMENT_ORDERED_BY_TYPE);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				indRes = new Equipment();
				createEquipmentFromRS(indRes, rs);
				result.add(indRes);
			}
		}
		catch(Exception e){
			System.out.println("Unable to read data from data source: "+ e);
		}
		finally {
			closeConnection(conn);
		}
		return result;
	}
	
	public ArrayList<Equipment> getEquipmentInProject(int projectID){

		ArrayList<Equipment> result = new ArrayList<Equipment>();
		Equipment indRes;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_EQUIPMENT_IN_PROJECT);
			stmt.setInt(1, projectID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				indRes = new Equipment();
				this.createEquipmentFromRS(indRes, rs);
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
	
	public boolean addEquipment(Equipment eq){
		Connection conn = null;
		int status;
		try{
			conn = DataSource.getInstance().getJDBCConnection();
			
			PreparedStatement stmt = conn.prepareStatement(ADD_EQUIPMENT);
			//(companyid, brand, model, year, type, vin, value, usagehour, monthlycost, 
			//weeklycost, dailycost, note, isactive, isavailable, isbilling, projectid, 
			//photo)
			stmt.setString(1, eq.getCompanyId());
			stmt.setString(2, eq.getBrand());
			stmt.setString(3, eq.getModel());
			stmt.setInt(4, eq.getYear());
			stmt.setString(5, eq.getType());
			stmt.setString(6, eq.getVin());
			stmt.setDouble(7, eq.getValue());
			stmt.setInt(8, eq.getUsageHours());
			stmt.setDouble(9, eq.getMonthlyCost());
			stmt.setDouble(10, eq.getWeeklyCost());
			stmt.setDouble(11, eq.getDailyCost());
			stmt.setString(12, eq.getNote());
			stmt.setBoolean(13, true);
			stmt.setBoolean(14, eq.isAvailable());
			stmt.setBoolean(15, eq.isBilling());
			stmt.setInt(16, 1);

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
	
	//utils
	private void createEquipmentFromRS(Equipment e, ResultSet rs) throws Exception{
		e.setId(rs.getInt(1));
		e.setCompanyId(rs.getString(2));
		e.setBrand(rs.getString(3));
		e.setModel(rs.getString(4));
		e.setYear(rs.getInt(5));
		e.setType(rs.getString(6));
		e.setVin(rs.getString(7));
		e.setValue(rs.getDouble(8));
		e.setUsageHours(rs.getInt(9));
		e.setMonthlyCost(rs.getDouble(10));
		e.setWeeklyCost(rs.getDouble(11));
		e.setDailyCost(rs.getDouble(12));
		e.setNote(rs.getString(13));
		e.setActive(rs.getBoolean(14));
		e.setAvailable(rs.getBoolean(15));
		e.setBilling(rs.getBoolean(16));
		e.setLocation(rs.getString(19));
		e.setPictureLink(rs.getString(18));
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
