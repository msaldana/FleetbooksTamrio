package com.radsolutions.fleetbooks.DTO;

import java.io.Serializable;
import java.sql.Date;

public class Project implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String companyID;
	private String name;
	private String location;
	private Date startDate;
	private Date endDate;
	private float cost;
	private boolean isActive;
	private boolean hasFuelTank;
	private String foreman;
	private String engineer;

	public Project() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isHasFuelTank() {
		return hasFuelTank;
	}

	public void setHasFuelTank(boolean hasFuelTank) {
		this.hasFuelTank = hasFuelTank;
	}

	public String getForeman() {
		return foreman;
	}

	public void setForeman(String foreman) {
		this.foreman = foreman;
	}

	public String getEngineer() {
		return engineer;
	}

	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}
}
