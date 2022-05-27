package com.revature.controllers;
import java.util.Objects;		

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpCode;
import com.revature.models.User;
import com.revature.services.AuthenticationServices;

import com.revature.services.AuthenticationServices;

import io.javalin.http.Handler;

public class AuthenticationController {
	
	ObjectMapper Mapper = new ObjectMapper();
	public void handleRegister(Context ctx) {
		
		try {
			
			String input = ctx.body();
			
			User user = Mapper.readValue(input, User.class);
			
			int id = AuthenticationServices.register(user);
			
			if(id == 0) {
				
				ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
				ctx.result("Registration unsuccessful.");
			}
			
		} catch (Exception e) {
			
			ctx.status(HttpCode.INTERNAL_SERVER_ERROR);
			
			if(!e.getMessage().isEmpty()) {
				ctx.result(e.getMessage());
			}
			
			e.printStackTrace();
		}

///////////////////////////////////////////////////////////////
	}
	AuthenticationServices as = new AuthenticationServices();
	
	public Handler loginHandler = (ctx) -> {
		String body = ctx.body();
		
		Gson gson = new Gson();
		//I recommend you make this an employee object 
		User u = gson.fromJson(body, User.class);

		if(as.login(u.getUsername(), u.getPassword()) == 1) {
			ctx.status(201);
			ctx.result("Manager Login Sucessful!");
		}
		else if(as.login(u.getUsername(), u.getPassword()) == 2) {
			ctx.status(202);
			ctx.result("Employee Login Sucessful!");
		}
		else {
		ctx.result("Login Failed!");
		ctx.status(401);
		}
	};
	
	
}