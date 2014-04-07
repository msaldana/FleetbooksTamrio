package com.radsolutions.fleetbooks.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.radsolutions.fleetbooks.DTO.Part;
import com.radsolutions.fleetbooks.datasource.DataSource;

public class PartManager {
	
	private static final String GET_ALL_PARTS = "SELECT * FROM part";
	private static final String GET_PART_BY_ID = "SELECT * FROM part WHERE id = ?";
	
	private static final String ADD_PART = "INSERT INTO part() VALUES ()";
	
	private static final PartManager singleton = new PartManager();

	private PartManager() {
	
	}
	
	public static final PartManager getInstance(){
		return singleton;
	}
	
	public ArrayList<Part> getAllParts(){
		ArrayList<Part> result = new ArrayList<Part>();
		Part indRes;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_ALL_PARTS);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				indRes = new Part();
				createPartFromRS(indRes, rs);
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
	
	public Part getPartById(int id){

		Part result = null;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_PART_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result =new Part();
				createPartFromRS(result, rs);
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
	
	public boolean addPart(Part p){
		Connection conn = null;
		int status;
		try{
			conn = DataSource.getInstance().getJDBCConnection();
			
			PreparedStatement stmt = conn.prepareStatement(ADD_PART);
			//name, number, brand, price, changehours, installhours, equipmentid
			stmt.setString(1, p.getName());
			stmt.setString(2, p.getNumber());
			stmt.setDouble(3, p.getPrice());
			stmt.setInt(4, p.getChangeLimitHours());
			stmt.setInt(5, p.getLastInstalledHours());
			stmt.setInt(6, p.getParentEquipment());

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
	
	private void createPartFromRS(Part p, ResultSet rs) throws Exception{
		p.setId(rs.getInt(1));
		p.setName(rs.getString(2));
		p.setNumber(rs.getString(3));
		p.setBrand(rs.getString(4));
		p.setPrice(rs.getFloat(5));
		p.setChangeLimitHours(rs.getInt(6));
		p.setLastInstalledHours(rs.getInt(7));
		p.setParentEquipment(rs.getInt(8));

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
