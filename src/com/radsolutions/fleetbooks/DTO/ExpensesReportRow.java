package com.radsolutions.fleetbooks.DTO;

public class ExpensesReportRow {
	
	private String equipmentCompanyId;
	private int equipmentId;
	private int totalMonths;
	private float monthlyCost;
	private int totalWeeks;
	private float weeklyCost;
	private int totalDays;
	private float dailyCost;
	private float totalCost;
	
	public ExpensesReportRow() {
		
	}

	public ExpensesReportRow(int equipmentId, String equipmentCompanyId, int totalMonths,
			float monthlyCost, int totalWeeks, float weeklyCost, int totalDays,
			float dailyCost) {
		super();
		this.setEquipmentId(equipmentId);
		this.equipmentCompanyId = equipmentCompanyId;
		this.totalMonths = totalMonths;
		this.monthlyCost = monthlyCost;
		this.totalWeeks = totalWeeks;
		this.weeklyCost = weeklyCost;
		this.totalDays = totalDays;
		this.dailyCost = dailyCost;
	}

	public String getEquipmentCompanyId() {
		return equipmentCompanyId;
	}

	public void setEquipmentCompanyId(String equipmentCompanyId) {
		this.equipmentCompanyId = equipmentCompanyId;
	}

	public int getTotalMonths() {
		return totalMonths;
	}

	public void setTotalMonths(int totalMonths) {
		this.totalMonths = totalMonths;
	}

	public float getMonthlyCost() {
		return monthlyCost;
	}

	public void setMonthlyCost(float monthlyCost) {
		this.monthlyCost = monthlyCost;
	}

	public int getTotalWeeks() {
		return totalWeeks;
	}

	public void setTotalWeeks(int totalWeeks) {
		this.totalWeeks = totalWeeks;
	}

	public float getWeeklyCost() {
		return weeklyCost;
	}

	public void setWeeklyCost(float weeklyCost) {
		this.weeklyCost = weeklyCost;
	}

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}

	public float getDailyCost() {
		return dailyCost;
	}

	public void setDailyCost(float dailyCost) {
		this.dailyCost = dailyCost;
	}

	public int getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	public void calculateTotalCost() {
		this.totalCost=(totalMonths*monthlyCost + totalWeeks*weeklyCost + totalDays*dailyCost);
	}

	@Override
	public String toString() {
		return "ExpensesReportRow [equipmentCompanyId=" + equipmentCompanyId
				+ ", equipmentId=" + equipmentId + ", totalMonths="
				+ totalMonths + ", monthlyCost=" + monthlyCost
				+ ", totalWeeks=" + totalWeeks + ", weeklyCost=" + weeklyCost
				+ ", totalDays=" + totalDays + ", dailyCost=" + dailyCost
				+ ", totalCost=" + totalCost + "]";
	}
	
}
