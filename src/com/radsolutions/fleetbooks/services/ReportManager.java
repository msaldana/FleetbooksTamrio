package com.radsolutions.fleetbooks.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.radsolutions.fleetbooks.DTO.ExpensesReportRow;
import com.radsolutions.fleetbooks.datasource.DataSource;

/**
 * Obtains all required information to generate business reports
 * @author Christian Barrientos
 *
 */
public class ReportManager {

	//Queries to be used for each report
	private static final String GET_EQUIPMENT_USAGE_REPORT = "SELECT U.*, E.companyid FROM uses AS U, equipment AS E WHERE U.equipmentid = E.id AND U.projectid = ? AND U.enddate BETWEEN ? AND ? ORDER BY U.equipmentid, U.date ASC ";
	private static final String GET_PROJECT_EXPENSES_REPORT = "SELECT U.*, E.companyid, E.dailycost, E.weeklycost, E.monthlycost FROM uses AS U, equipment AS E WHERE E.id = U.equipmentid AND U.date BETWEEN ? AND ? AND U.projectid = ? ORDER BY U.equipmentid, U.date ASC";
	private static final String GET_FUEL_CONSUMPTION_REPORT = "DROP VIEW records; CREATE VIEW records AS SELECT * FROM refuels WHERE equipmentid = ? AND date BETWEEN ? AND ?; SELECT records.*,MAX(F.date), F.price FROM records, fuelpurchase AS F WHERE F.projectid = records.projectid  AND F.date < records.date GROUP BY F.price, records.projectid, records.equipmentid, records.date, records.gallons; ";
	
	/**
	 * Amount of days until equipment usage is considered a week of use
	 */
	private int weekThreshold = 3;
	
	/**
	 * Amount of weeks until equipment usage is considered a month of use
	 */
	private int monthThreshold = 3;
	
	private static final ReportManager singleton = new ReportManager();

	private ReportManager(){

	}
	
	/**
	 * Gets ReportManger instance
	 * @return ReportManager instance
	 */
	public static final ReportManager getInstance(){
		return singleton;
	}
	
	/**
	 * Gets equipment usage hours for a specific project
	 * @param startDate first date to include in result
	 * @param endDate last date to include in result
	 * @param projectId id of project to generate result on
	 * @return ResultSet contains companyId, date of usage and hours of usage
	 */
	public ResultSet getEquipmentUsageReport(String startDate, String endDate, int projectId){
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_EQUIPMENT_USAGE_REPORT);
			stmt.setInt(1, projectId);
			stmt.setDate(2,  Date.valueOf(startDate));
			stmt.setDate(3, Date.valueOf(endDate));
			result = stmt.executeQuery();
		}
		catch(Exception e){
			System.out.println("Unable to read data from data source: "+ e);
		}
		finally {
			this.closeConnection(conn);
		}
		return result;
	}
	
	/**
	 * Gets fuel consumption for a specific equipment
	 * @param startDate first date to include in result
	 * @param endDate last date to include in result
	 * @param equipmentId id of equipment to generate result on
	 * @return ResultSet contains date, gallons used and price of the gallons
	 */
	public ResultSet getFuelConsumptionReport(String startDate, String endDate, int equipmentId){
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_FUEL_CONSUMPTION_REPORT);
			stmt.setDate(1,  Date.valueOf(startDate));
			stmt.setDate(2, Date.valueOf(endDate));
			stmt.setInt(3, equipmentId);
			result = stmt.executeQuery();
		}
		catch(Exception e){
			System.out.println("Unable to read data from data source: "+ e);
		}
		finally {
			this.closeConnection(conn);
		}
		return result;
	}

	/**
	 * Gets expenses for a specific project
	 * @param startDate first date to include in result
	 * @param endDate last date to include in result
	 * @param projectId id of project to generate result on
	 * @return ArrayList<ExpensesReportRow> ArrayList containing ExpensesReportRows which include companyId, days, weeks and months used, as well as daily, weekly and monthly cost and a total of the cost for that equipment
	 */
	public ArrayList<ExpensesReportRow> getProjectExpenseReport(String startDate, String endDate, int projectId){
		ResultSet result = null;
		Connection conn = null;
		ArrayList<ExpensesReportRow> reportRows = new ArrayList<ExpensesReportRow>();

		try {
			conn = DataSource.getInstance().getJDBCConnection();
			PreparedStatement stmt = conn.prepareStatement(GET_PROJECT_EXPENSES_REPORT);
			stmt.setDate(1, Date.valueOf(startDate));
			stmt.setDate(2, Date.valueOf(endDate));
			stmt.setInt(3, projectId);
			result = stmt.executeQuery();
			
			//Creates HashMap to store equipment information, using equipmentId as key
			HashMap<Integer, ArrayList<Integer>> equipmentHash = new HashMap<Integer, ArrayList<Integer>>();
			
			//Sets up date formatting
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
			dayFormat.applyPattern("EEEE");
			
			//Will keep track of the previous usage day for an equipment
			java.util.Date previous = null;
			
			//Stores amount of consequent days of the equipment being used
			int count=0;
			
			int previousId=-1;
			
			ArrayList<Integer> temp = new ArrayList<Integer>();
			ArrayList<Integer> ids = new ArrayList<Integer>();
			
			//Gets first record
			try {
				if(result.next()){
					//Gets all equipment information
					String date = result.getDate("date").toString();
					int equipmentId = result.getInt("equipmentid");
					int usage = result.getInt("usage");
					String companyId = result.getString("companyid");
					int dailyCost = result.getInt("dailycost");
					int weeklyCost = result.getInt("weeklycost");
					int monthlyCost = result.getInt("monthlycost");
					
					//Creates row in result with equipment information
					ExpensesReportRow row = new ExpensesReportRow(equipmentId, companyId, 0, monthlyCost, 0, weeklyCost, 0, dailyCost);
					reportRows.add(row);
					
					ids.add(equipmentId);
					
					java.util.Date current = dateFormat.parse(date);
					
					//Verifies if usage is done on a weekend
					boolean isWeekend = dayFormat.format(current).equals("Saturday") || dayFormat.format(current).equals("Sunday");
					
					previous = dateFormat.parse(result.getDate("date").toString());
					
					//If equipment is not malfunctioning, initialize count at 1
					if(result.getInt("usage")!=-1){
						count=1;
						previousId = result.getInt("equipmentid");
					}
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				//Gets all records after first one
				while(result.next()){
					//Get all equipment information
					String date = result.getDate("date").toString();
					int equipmentId = result.getInt("equipmentid");
					int usage = result.getInt("usage");
					String companyId = result.getString("companyid");
					int dailyCost = result.getInt("dailycost");
					int weeklyCost = result.getInt("weeklycost");
					int monthlyCost = result.getInt("monthlycost");
					
					
					java.util.Date current = dateFormat.parse(date);
					
					//Verifies if usage is done on a weekend
					boolean isWeekend = dayFormat.format(current).equals("Saturday") || dayFormat.format(current).equals("Sunday");
					
					//Verify if the this equipment and previous one are the same
					if(equipmentId == previousId){
						if(isWeekend){
							if(usage>0){
								count++;
							}
						}
						//Verifies if days of usage are concurrent
						else if(isConcurrent(previous, current)){
							//Verifies if equipment was malfunctioning
							if(usage!=-1){
								count++;
							}
							//Stes current date as previous one
							previous = current;
						}
						//Executes if days are not concurrent
						else{
							//Gets equipment usage information
							temp = equipmentHash.get(equipmentId);
							if(temp == null){
								temp = new ArrayList<Integer>();
							}
							//Adds count as days the equipment has been used
							temp.add(count);
							equipmentHash.put(equipmentId, temp);
							//Starts a new count
							count=1;
							previous = current;
						}
					}
					//Executes when a different equipment is being analyzed
					else{
						//Adds equipmentId to id list
						ids.add(equipmentId);
						
						//Creates new row
						ExpensesReportRow row = new ExpensesReportRow(equipmentId, companyId, 0, monthlyCost, 0, weeklyCost, 0, dailyCost);
						reportRows.add(row);
						temp = (ArrayList<Integer>) equipmentHash.get(previousId);
						if(temp == null){
							temp = new ArrayList<Integer>();
						}
						//Adds count as days of usage
						temp.add(count);
						equipmentHash.put(previousId, temp);
						if(usage!=1){
							count=1;
						}
						else{
							count=0;
						}
					}
					previousId = equipmentId;
				} 
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			//Iterates through all viewed equipment usage 
			for (int i = 0; i < ids.size(); i++) {
				ArrayList<Integer> terms = equipmentHash.get(ids.get(i));
				ExpensesReportRow row = reportRows.get(i);
				int totalDays = 0;
				int totalWeeks = 0;
				int totalMonths = 0;
				//For each term, get how many days, weeks and months will be charged
				for(int j=0; j < terms.size(); j++){
					int[] termResult = getBillingCycle(terms.get(j), totalDays, totalWeeks, totalMonths);
					totalDays = termResult[0];
					totalWeeks = termResult[1];
					totalMonths = termResult[2];
				}
				
				//Set total values to row for days, weeks and months
				row.setTotalDays(totalDays);
				row.setTotalWeeks(totalWeeks);
				row.setTotalMonths(totalMonths);
				
				//Calculates the total cost for an equipment
				row.calculateTotalCost();
				
			}
		}
		catch(Exception e){
			System.out.println("Unable to read data from data source: "+ e);
		}
		finally {
			this.closeConnection(conn);
		}
		return reportRows;
	}
	
	/**
	 * Calculates how the usage will be charged
	 * @param term consecutive days of usage
	 * @param totalDays initial count of days
	 * @param totalWeeks initial count of weeks
	 * @param totalMonths initial count of months
	 * @return int[] array containing total days, weeks and months to be charged
	 */
	private int[] getBillingCycle(int term, int totalDays, int totalWeeks, int totalMonths){
		//Divides days into weeks
		int weeks = term/5;
		//Gets remaining days
		int days = term%5;
		
		//Checks if days count as a week
		if(days >= weekThreshold){
			weeks++;
			days = 0;
		}
		
		//Divides weeks into months
		int months = weeks/4;
		//Gets remaining weeks
		weeks = weeks%4;
		
		//Checks if weeks count as a month
		if(weeks >= monthThreshold){
			months++;
			weeks = 0;
		}
		
		int[] result = {totalDays+days, totalWeeks+weeks, totalMonths+months};
		return result;
	}
	
	/**
	 * Given a workday, verifies if another date is next to the first one
	 * @param first first date 
	 * @param second second date
	 * @return boolean tells if the days are in sequence or not
	 */
	private boolean isConcurrent(java.util.Date first, java.util.Date second){
		//Sets up date formatting
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		dayFormat.applyPattern("EEEE");
		
		java.util.Date next;
		
		//If day is Friday, compares to next workday (Monday)
		if(dayFormat.format(first).equals("Friday")){
			Calendar cal = Calendar.getInstance();
	        cal.setTime(first);
	        cal.add(Calendar.DATE, 3);
	        next = cal.getTime();
		}
        
		//Calculates next day of first parameter, and compares it to second parameter
		else{
			Calendar cal = Calendar.getInstance();
	        cal.setTime(first);
	        cal.add(Calendar.DATE, 1);
	        next = cal.getTime();
		}
        if(next.equals(second)){
        	return true;
        }
        else{
        	return false;
        } 
	}
	
	/**
	 * Closes connection
	 * @param c connection to database
	 */
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
