package com.radsolutions.fleetbooks.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
	
	private static DataSource singleton = null;
	private String userName;
	private String password;
	private String dbName;
	
	private DataSource(String userName, String password, String dbName) 
			throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		this.userName = userName;
		this.password = password;
		this.dbName = dbName;
	}
	public static DataSource getInstance() throws ClassNotFoundException {
		if (singleton == null){
			singleton = new DataSource("postgres", "postgres", "FleetBooks");
		}
		return singleton;
	}
	public Connection getJDBCConnection() throws SQLException{
		String dbURL = "jdbc:postgresql://ec2-54-214-69-174.us-west-2.compute.amazonaws.com:5432/" + this.dbName;
		Connection dbConn = DriverManager.getConnection(dbURL, userName, password);
		return dbConn;
	}
	
	public Connection getJDBCConnectionAcceptMultipleQueries() throws SQLException{
		String dbURL = "jdbc:postgresql://ec2-54-214-69-174.us-west-2.compute.amazonaws.com:5432/" + this.dbName+"?allowMultiQueries=true";
		Connection dbConn = DriverManager.getConnection(dbURL, userName, password);
		return dbConn;
	}
	
	
}