package com.radsolutions.fleetbooks.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.radsolutions.fleetbooks.DTO.Notification;
import com.radsolutions.fleetbooks.datasource.DataSource;

public class NotificationManager {
	
	private static final NotificationManager singleton = new NotificationManager();
	private static final String GET_NOTIFICATIONS = null;
	private static final String GET_NOTIFICATION_BY_ID = null;
	private static final String ADD_NOTIFICATION = null;

	public NotificationManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static final NotificationManager getInstance(){
		return singleton;
	}
	
	public ArrayList<Notification> getNotifications(){
		
		ArrayList<Notification> result = new ArrayList<Notification>();
		Notification indRes;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_NOTIFICATIONS);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				indRes = new Notification();
				createNotificationFromRS(indRes, rs);
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
	
	public Notification getNotificationById(int id){

		Notification result = null;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_NOTIFICATION_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = new Notification();
				createNotificationFromRS(result, rs);
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
	

	public boolean addNotification(Notification n){
		Connection conn = null;
		int status;
		try{
			conn = DataSource.getInstance().getJDBCConnection();
			
			PreparedStatement stmt = conn.prepareStatement(ADD_NOTIFICATION);
			//int id, string type, String sender, Date date, boolean solved, boolean visible, int receiverAccountId
			stmt.setInt(1, n.getId());
			stmt.setString(2, n.getType());
			stmt.setString(3, n.getSender());
			stmt.setDate(4, n.getDate());
			stmt.setBoolean(5, n.isSolved());
			stmt.setBoolean(6, n.isVisible());
			stmt.setInt(1, n.getReceiverAccountId());
			//
			

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

	//For utility
	private void createNotificationFromRS(Notification a, ResultSet rs) throws Exception{
		a.setId(rs.getInt(1));
		a.setType(rs.getString(2));
		a.setSender(rs.getString(3));
		a.setDate(rs.getDate(4));
		a.setSolved(rs.getBoolean(5));
		a.setVisible(rs.getBoolean(6));
		a.setReceiverAccountId(rs.getInt(7));
		
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
