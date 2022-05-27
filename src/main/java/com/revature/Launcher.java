package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.controllers.AuthenticationController;
import com.revature.controllers.ReimbursementController;
import com.revature.controllers.UserController;
import com.revature.services.CLI_Menu_Service;
import com.revature.utilities.ConnectionFactory;

import io.javalin.Javalin;

public class Launcher {
	public static void main(String[] args) throws SQLException {
		UserController uc = new UserController();
		AuthenticationController ac = new AuthenticationController();
		ReimbursementController rc = new ReimbursementController();
		//Testing Database Connectivity - just testing whether our Connection (from ConnectionFactory) is successful
		try(Connection conn = ConnectionFactory.getConnection()){
			System.out.println("Connection Successful :)");
		} catch(SQLException e) {
			System.out.println("Connection failed");
			e.printStackTrace();
		}	

	
		//Make the menu	run, its only 2 lines of code	29-31 are the CLI menu
//		CLI_Menu_Service options = new CLI_Menu_Service();
//	    options.displayLoginMenu();
//	    options.displayMenu();
		
	
		Javalin app = Javalin.create(
			config -> {
				config.enableCorsForAllOrigins(); 
			}
		).start(4000);
		
		
	
		app.get("/employee", uc.getEmployeesHandler); //get employee list

        app.post("/employee", uc.insertEmployeesHandler); //create employee

        app.post("/login", ac.loginHandler); //login

        app.get("/status", rc.handleGetReimbursmentByStatus); //status of pending requests

        app.get("/reimbursement", rc.handleGetReimbursements); //status of ALL reimbursements approved or denied or pending

        app.get("/{id}", rc.handleGetReimbursementById); //show you the reimbursement based on id same ID

        app.post("/submit", rc.handleSubmit); //sumbit a reimbursement
        
        app.put("/approved", rc.handleApproved);
        
        app.put("/denied", rc.handleDenied);

        app.post("/process", rc.handleProcess); //approve or deny any reimbursement

        app.get("/author", rc.handleGetReimbursementByAuthor); //same as id except with author
        
        
	}
}
