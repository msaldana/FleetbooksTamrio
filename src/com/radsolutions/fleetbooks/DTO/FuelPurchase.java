package com.radsolutions.fleetbooks.DTO;

import java.io.Serializable;
import java.sql.Date;

public class FuelPurchase implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private Date date;
	private int gallons;
	private float price;
	private int tankProjectID;
	

	public FuelPurchase() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public int getGallons() {
		return gallons;
	}


	public void setGallons(int gallons) {
		this.gallons = gallons;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public int getTankProjectID() {
		return tankProjectID;
	}


	public void setTankProjectID(int tankProjectID) {
		this.tankProjectID = tankProjectID;
	}
	
	

}
