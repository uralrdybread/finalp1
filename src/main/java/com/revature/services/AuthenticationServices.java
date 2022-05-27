package com.revature.services;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

public class AuthenticationServices {

	public static int login(String username, String password) {

        try {

            User user = UserDAO.getByUsername(username);

            if(user!=null && password.equals(user.getPassword()) && user.getRole()== Role.Founder) {
                System.out.println("Manager Logged In Successfully!");
                return 1;
             
            } else if (user!=null && password.equals(user.getPassword()) && user.getRole()== Role.Member) {

                System.out.println("Employee Logged In Successfully!");
                return 2;
            } else {

                System.out.println("Username or Password Does Not Exist!");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Login Unsuccessful");
            e.printStackTrace();
            return 0;
        }
     
        
	}

public static int register(User userToBeRegistered) {

    if(UserDAO.getByUsername(userToBeRegistered.getUsername()) != null) {

        throw new NullPointerException("Username is already taken");
    }

    return UserDAO.create(userToBeRegistered);
	}
}