package com.radsolutions.fleetbooks.tests;

import com.radsolutions.fleetbooks.DTO.Account;
import com.radsolutions.fleetbooks.DTO.Equipment;
import com.radsolutions.fleetbooks.DTO.Part;
import com.radsolutions.fleetbooks.DTO.Project;
import com.radsolutions.fleetbooks.services.EquipmentManager;
import com.radsolutions.fleetbooks.services.MailManager;
import com.radsolutions.fleetbooks.services.PartManager;
import com.radsolutions.fleetbooks.services.ProjectManager;

/**
 * Tests the functionality of all MailController's methods.
 * @author Manuel Saldana Pueyo
 *
 */
public final class MailControllerTester 
{
	/*
	 * Dummy data fields for tests.
	 */
	private Project	testProject = null;
    private Equipment testEquipment =  null;
    private Account testUser = null;
    private Part testPart = null;
    
    /**
     * Constructor of MailControllerTester. 
     */
  	public MailControllerTester()
  	{
  		testProject = ProjectManager.getInstance().getAllProjects().get(0);
  		testEquipment = EquipmentManager.getInstance().getEquipmentInProject(testProject.getId()).get(0);
  		testUser = new Account();
  		testUser.setEmail("noreply.fleetbooks@gmail.com");
  	  	testUser.setFirstName("Noreply");
  	  	testUser.setPassword("pass");
  	  	testUser.setType("General User");
  	  	testUser.setLastName("Tamrio");
  	  	testPart = PartManager.getInstance().getPartById(1);
  	}
  	
  	/**
  	 * Tests functionality of creating a new account email.
  	 */
  	public void testAccountCreation(){
  		MailManager.getInstance().sendNewAccountNotification(testUser);
  	}
  	
  	/**
  	 * Test functionality of completing new account request email.
  	 */
  	public void testAccountCompletion(){
  		MailManager.getInstance().sendNewAccountCompletionNotification(testUser);
  	}
  	
  	/**
  	 * Test functionality of completing a maintenance required for part email.
  	 */
  	public void testMaintenance(){
  		Equipment testEquipment = EquipmentManager.getInstance().
  				getEquipmentById(testPart.getParentEquipment());
  		MailManager.getInstance().sendMaintenanceNotification(testEquipment, testPart);
  	}
  	
  	/**
  	 * Test functionality of sending an email formated with an equipment request.
  	 */
  	public void testEquipmentRequest(){
  		MailManager.getInstance().sendEquipmentRequest(testEquipment, testProject, testUser);
  	}
  	
  	/**
  	 * Test functionality of sending an email formated with equipment checkout data.
  	 */
  	public void testEquipmentCheckout(){
  		MailManager.getInstance().sendEquipmentCheckoutRequest(testEquipment, 
  				testProject, testProject.getEndDate().toString());
  	}
  	/**
  	 * Test functionality of sending an email upon unit malfunction.
  	 */
  	public void testUnitMalfunction(){
  		MailManager.getInstance().sendUnitMalfunctionNotification(testEquipment, "John Doe");
  	}
  	
  	public void testPasswordRecoveryLink(){
  		MailManager.getInstance().sendPasswordRecoveryLink(testUser, "google.com");
  	}
    
	public static void main(String[] args){
		//Remove comment of desired test.
		MailControllerTester mailTester = new MailControllerTester();
		//mailTester.testAccountCompletion();
		//mailTester.testAccountCreation();
		//mailTester.testEquipmentRequest();
		//mailTester.testMaintenance();
		//mailTester.testEquipmentCheckout();
		//mailTester.testUnitMalfunction();
		mailTester.testPasswordRecoveryLink();
	}

}
