package com.radsolutions.fleetbooks.DTO;

import java.io.Serializable;

public class Equipment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String companyId;
	private String brand;
	private String model;
	private int year;
	private String type;
	private String vin;
	private double value;
	private int usageHours;
	private double monthlyCost;
	private double weeklyCost;
	private double dailyCost;
	private String note;
	private boolean isActive;
	private boolean isAvailable;
	private boolean isBilling;
	private String location;
	private String pictureLink;

	public Equipment() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getUsageHours() {
		return usageHours;
	}

	public void setUsageHours(int usageHours) {
		this.usageHours = usageHours;
	}

	public double getMonthlyCost() {
		return monthlyCost;
	}

	public void setMonthlyCost(double monthlyCost) {
		this.monthlyCost = monthlyCost;
	}

	public double getWeeklyCost() {
		return weeklyCost;
	}

	public void setWeeklyCost(double weeklyCost) {
		this.weeklyCost = weeklyCost;
	}

	public double getDailyCost() {
		return dailyCost;
	}

	public void setDailyCost(double dailyCost) {
		this.dailyCost = dailyCost;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public boolean isBilling() {
		return isBilling;
	}

	public void setBilling(boolean isBilling) {
		this.isBilling = isBilling;
	}

	public String getPictureLink() {
		return pictureLink;
	}

	public void setPictureLink(String pictureLink) {
		this.pictureLink = pictureLink;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}