package com.radsolutions.fleetbooks.DTO;

import java.sql.Date;

public class Notification {

	private int id;
	private String type;
	private String sender;
	private Date date;
	private boolean isSolved;
	private boolean isVisible;
	private int receiverAccountId;
	
	public Notification() {
		// TODO Auto-generated constructor stub
	}

}
