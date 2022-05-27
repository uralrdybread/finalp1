package com.revature.controllers;

import com.revature.models.Status;	
import com.revature.repositories.ReimbursementDAO;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpCode;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;

public class ReimbursementController {
	ObjectMapper objectMapper = new ObjectMapper();
	ReimbursementService reimbursementService = new ReimbursementService();
	UserService userService = new UserService();
	ReimbursementDAO rDAO = new ReimbursementDAO();
	
	
	public void handleSubmit(Context ctx) {
	
		try {
			String input = ctx.body();
			
			Reimbursement reimbursement = objectMapper.readValue(input, Reimbursement.class);
			
			int id = ReimbursementService.sumbitReimbursement(reimbursement);
			
			if(id != 0) {
				
				ctx.status(HttpCode.CREATED);
				ctx.result(""+id);
			
			} else {
				ctx.status(HttpCode.BAD_REQUEST);
				ctx.result("Reimbursement submission was unsuccessful.");
				
			}
		} catch (Exception e) {
			
			ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
			
			if(!e.getMessage().isEmpty()) {
				ctx.result(e.getMessage());
			}
			e.printStackTrace();
		}
	}
/////////////////////////////////////////////////////////////////////////////
	public void handleProcess(Context ctx) {
		
		String authHeader = ctx.header("Current-User");
		
		if(authHeader != null) {
			
			int userId = Integer.parseInt(authHeader);
			
		try {
			String reimbursementIdInput = ctx.pathParam("id");
			
			int id = Integer.parseInt(reimbursementIdInput);
			
			String statusInput = ctx.formParam("status");
			
			Reimbursement reimbursement = reimbursementService.getReimbursementById(id);
			
			if(reimbursement != null) {
				
				Reimbursement processedReimbursement = reimbursementService.update(reimbursement);
				
				ctx.status(HttpCode.ACCEPTED);
				ctx.json(processedReimbursement);
			
			} else {
				ctx.status(HttpCode.BAD_REQUEST);
				ctx.result("Reimbursement processing was not successful");
			}
		} catch (Exception e) {
			ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
			
			if(!e.getMessage().isEmpty()) {
				ctx.result(e.getMessage());
	
	} else {
		ctx.status(HttpCode.FORBIDDEN);
		ctx.result("Missing Current User Header with ID");
			}
		}
	}
}
//////////////////////////////////////////////////////////////////////////////////////////////////		
public Handler handleGetReimbursements =(ctx) -> {
		
		List<Reimbursement> allReim = rDAO.getAllReimbursements();
				
		Gson gson = new Gson();
		String JSONObject = gson.toJson(allReim);
		
		ctx.result(JSONObject);
		ctx.status(200);
		
	};
public Handler handleGetReimbursmentByStatus=(ctx) -> { 
	

		String statusParam = ctx.body();
		
		Status status = Status.valueOf(statusParam);
		List<Reimbursement> reim = reimbursementService.getReimbursementByStatus(status); //DELETE IF NECESSARY
		Gson gson = new Gson();
		String json = gson.toJson(reim);
		
		//if(status == Status.Pending)
		if(reim != null) {
			
			ctx.result(json);
			ctx.status(HttpCode.OK);
		
//			ctx.json(reimbursementService.getPendingReimbursements());
		} else {
			ctx.status(HttpCode.OK);
			ctx.result(json);
		}


};
public Handler handleGetReimbursementByAuthor = (ctx) ->{
	int id = Integer.parseInt(ctx.pathParam("author"));
	System.out.println(id);
	
	List<Reimbursement> reimID = ReimbursementService.getReimbursementsByAuthor(id);
	
	Gson gson = new Gson();
	String JSONObject = gson.toJson(reimID);
	
		ctx.status(HttpCode.OK);
		ctx.result(JSONObject);
};

public Handler handleProcess = (ctx) ->{
	String authHeader = ctx.header("Current-User");
	
	if(authHeader != null) {
		
		int userId = Integer.parseInt(authHeader);
		
		String reimbursementIdInput = ctx.pathParam("id");
		
		int id = Integer.parseInt(reimbursementIdInput);
		
		String statusInput = ctx.formParam("status");
		
		Reimbursement reimbursement = ReimbursementService.getReimbursementById(id);
		
		if(reimbursement != null) {
			
			Reimbursement processedReimbursement = ReimbursementService.update(reimbursement);
			
			ctx.status(HttpCode.ACCEPTED);
			ctx.json(processedReimbursement);
		} else {
			ctx.status(HttpCode.ACCEPTED);
			ctx.result("Reimbursement processing was not successful");
		}
	}
};
public Handler handleSubmit = (ctx) ->{
	
	String body = ctx.body();
    Gson gson = new Gson();
    Reimbursement reimbursement = gson.fromJson(body, Reimbursement.class);
    ReimbursementService.update(reimbursement);
    String JSONObject = gson.toJson("Reimbursement processed successfully!");
    ctx.result(JSONObject);
    ctx.status(208);



		
		int id = ReimbursementService.sumbitReimbursement(reimbursement);
		
		if(id !=0) {
			ctx.status(HttpCode.CREATED);
			ctx.result("" + id);
		} else {
			ctx.status(HttpCode.BAD_REQUEST);
			ctx.result("Reimbursement submission was unsuccessful");
		}
	
};


public Handler handleGetReimbursementById = (ctx) ->{
	String body = ctx.body();
	int id = Integer.parseInt(body);
	Reimbursement reimId = ReimbursementService.getReimbursementById(id);
	
	Gson gson = new Gson();
	String JSONObject = gson.toJson(reimId);
	
	ctx.result(JSONObject);
	ctx.status(200);
};

public Handler handleApproved = (ctx) ->{
	
	String body = ctx.body();
	Gson gson = new Gson();
	Reimbursement reimbursement = gson.fromJson(body, Reimbursement.class);
	int id = reimbursement.getResolver();
	
	Reimbursement processedReimbursement = ReimbursementService.update(reimbursement);
	if(processedReimbursement != null) {
		ctx.status(HttpCode.ACCEPTED);
	
	
	} else {
			ctx.status(HttpCode.ACCEPTED);
			ctx.result("Reimbursement processing was not successful");
	}
};
	
	
	
public Handler handleDenied = (ctx) ->{
	
	String body = ctx.body();
	Gson gson = new Gson();
	Reimbursement reimbursement = gson.fromJson(body, Reimbursement.class);
	int id = reimbursement.getResolver();
	
	Reimbursement processedReimbursement = ReimbursementService.update(reimbursement);
	if(processedReimbursement != null) {
		ctx.status(HttpCode.ACCEPTED);
		
	} else {
		ctx.status(HttpCode.ACCEPTED);
		ctx.result("Reimbursement processing was not successful");
		
	}
};





public void handleGetReimbursementById(Context ctx) {

		try {
			String idParam = ctx.pathParam("id");
			
			int id = Integer.parseInt(idParam);
			
			Reimbursement reimbursement = reimbursementService.getReimbursementById(id);
		
			if(reimbursement != null) {
				ctx.status(HttpCode.OK);
				ctx.json(reimbursement);
			} else {
				ctx.status(HttpCode.BAD_REQUEST);
				ctx.result("Could not retrieve the reimbursement");
			}
			
		} catch(Exception e){ 
			ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
			
			if(!e.getMessage().isEmpty()) {
				ctx.result(e.getMessage());
			}
			e.printStackTrace();
		}
}
//////////////////////////////////////////////////////////////////////////////////////////////

public void handleGetReimbursementsByAuthor(Context ctx) {
	
	try {
		
		String idParam = ctx.queryParam("author");
		
		if(idParam != null) {
			int id = Integer.parseInt(idParam);
			
			if(userService.checkUserExistsById(id)) {
				ctx.status(HttpCode.OK);
				
				ctx.json(reimbursementService.getReimbursementsByAuthor(id));
			} else {
				ctx.status(HttpCode.NOT_FOUND);
				ctx.result("Unable to retrieve reimbursements, current user is not in the database");
			}
		} else {
			
			ctx.status(HttpCode.BAD_REQUEST);
			ctx.result("Missing Current User header");
		}
		
	} catch(Exception e) {
		
	
		ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
		
		if(!e.getMessage().isEmpty()) {
			ctx.result(e.getMessage());
		}
		e.printStackTrace();
		}
	
		}
	};

