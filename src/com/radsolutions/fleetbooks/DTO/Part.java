package com.radsolutions.fleetbooks.DTO;

public class Part {

	private int id;
	private String name;
	private String brand;
	private String number;
	private float price;
	private int changeLimitHours;
	private int lastInstalledHours;
	private int parentEquipment;
	
	public Part() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getChangeLimitHours() {
		return changeLimitHours;
	}

	public void setChangeLimitHours(int changeLimitHours) {
		this.changeLimitHours = changeLimitHours;
	}

	public int getLastInstalledHours() {
		return lastInstalledHours;
	}

	public void setLastInstalledHours(int lastInstalledHours) {
		this.lastInstalledHours = lastInstalledHours;
	}

	public int getParentEquipment() {
		return parentEquipment;
	}

	public void setParentEquipment(int parentEquipment) {
		this.parentEquipment = parentEquipment;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	
}
