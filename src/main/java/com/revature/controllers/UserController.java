package com.revature.controllers;
import java.util.List;

import com.google.gson.Gson;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpCode;
import com.revature.models.User;
import com.revature.services.UserService;

public class UserController {
	UserService us = new UserService();
	

	
	public void handleGetUser (Context ctx) {
		if(ctx.queryParam("Username") != null) {
			handleGetByUsername(ctx);
		} else if(ctx.queryParam("Id") != null) {
			handleGetById(ctx);
		}
	}

	public Handler getEmployeesHandler = (ctx) ->{
		
	 List<User> allUsers = us.getAllUsers();
	
	 Gson gson = new Gson();
	
	 String  JSONObject = gson.toJson(allUsers);
	 
	 ctx.result(JSONObject);
	 ctx.status(200);
		
	};
	
	public Handler insertEmployeesHandler = (ctx) ->{
		
		String body = ctx.body();
		
		Gson gson = new Gson();
		
		User employee = gson.fromJson(body, User.class);
		
		us.addUser(employee);
		
		ctx.result("Employee successfully added!");
		ctx.status(201);

	
	};
	
	public void handleGetByUsername(Context ctx) {
		
		try {
			String UserParam = ctx.pathParam("username");
			
			String user = new String (UserParam);
			
			User users = UserService.getUserByUsername(user);
		
			if(users != null) {
				ctx.status(HttpCode.OK);
				ctx.json(users);
			} else {
				ctx.status(HttpCode.BAD_REQUEST);
				ctx.result("Could not retrieve the User");
			}
		} catch(Exception e) {
			
		
			ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
			
			if(!e.getMessage().isEmpty()) {
				ctx.result(e.getMessage());
			}
			e.printStackTrace();
		}
	};
	
	
	
	
	public void handleGetById(Context ctx) {
		
		try {
			String idParam = ctx.pathParam("id");
			
			int id = Integer.parseInt(idParam);
			
			User users = UserService.getUserById(id);
		
			if(users != null) {
				ctx.status(HttpCode.OK);
				ctx.json(users);
			} else {
				ctx.status(HttpCode.BAD_REQUEST);
				ctx.result("Could not retrieve the User");
			}
			
		} catch(Exception e){ 
			ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
			
			if(!e.getMessage().isEmpty()) {
				ctx.result(e.getMessage());
			}
			e.printStackTrace();
		}
	};
	
}