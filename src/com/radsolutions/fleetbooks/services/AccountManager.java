package com.radsolutions.fleetbooks.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.radsolutions.fleetbooks.DTO.Account;
import com.radsolutions.fleetbooks.datasource.DataSource;

public class AccountManager {

	private static final String GET_ADMIN_ACCOUNTS = "SELECT * FROM Account WHERE type = 'Administrator'";
	private static final String GET_USER_BY_EMAIL = "SELECT * FROM Account WHERE email = ?";
	private static final String ADD_ACCOUNT = "INSERT INTO account(firstname, lastname, email, password, phone, type, approved) VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	private static final AccountManager singleton = new AccountManager();

	private AccountManager(){

	}

	public static final AccountManager getInstance(){
		return singleton;
	}

	//Get
	public Account getUserByEmail(String email){

		Account result = null;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_USER_BY_EMAIL);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()){
				result = new Account();
				this.createAccountFromRS(result, rs);
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
	
	public ArrayList<Account> getAllAdministrators(){
		ArrayList<Account> result = new ArrayList<Account>();
		Account indRes;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_ADMIN_ACCOUNTS);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				indRes = new Account();
				createAccountFromRS(indRes, rs);
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

	//Add
	public boolean addAccount(Account a) {
		
		Connection conn = null;
		int status;
		try{
			conn = DataSource.getInstance().getJDBCConnection();
			conn.setAutoCommit(false);
			
			PreparedStatement stmt = conn.prepareStatement(ADD_ACCOUNT);
			stmt.setString(2, a.getFirstName());
			stmt.setString(3, a.getLastName());
			stmt.setString(4, a.getEmail());
			stmt.setString(5, a.getPassword());
			stmt.setString(6, a.getPhone());
			stmt.setString(7, a.getType());
			stmt.setBoolean(8, a.isApproved());

			status = stmt.executeUpdate();
			if (status != 1){
				throw new Exception("Error adding account");
			}
			
			//Need to add the creation of a new account request and checking its status
			
			if (status != 1){
					conn.rollback();
					conn.setAutoCommit(true);
					throw new Exception("Error creating new acount request");
			}
			else{ 
				try{
					conn.commit();
				}
				catch(Exception e){
					try{
						conn.rollback();
						conn.setAutoCommit(true);
					}
					catch(Exception e2){
					}

					throw new Exception("Unable to commit transaction.", e);
				}
				conn.setAutoCommit(true);
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

//Utils
private void createAccountFromRS(Account a, ResultSet rs) throws Exception{
	a.setId(rs.getInt(1));
	a.setFirstName(rs.getString(2));
	a.setLastName(rs.getString(3));
	a.setEmail(rs.getString(4));
	a.setPassword(rs.getString(5));
	a.setPhone(rs.getString(6));
	a.setType(rs.getString(7));
	a.setApproved(rs.getBoolean(8));
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
