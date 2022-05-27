package com.revature.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

public class UserService {
	
	static UserDAO eDAO = new UserDAO(); //so that I can use the methods from the EmployeeDAO
	
	public static User getUserByUsername(String username) {
        return UserDAO.getByUsername(username);
    }
//////////////////////////////////////////////////

    public List<User> getAllUsers() {
        return UserDAO.getAllUsers();
    }
/////////////////////////////////////////////////////////////////////////////////////////////////
    public void UserExistsById(int id) {
    for(User user : UserDAO.getAllUsers()) {
        if(user.getId()== id) {
            System.out.println("This ID exists");
            break;
        }
    }
        System.out.println("This ID does not exist");
}
//////////////////////////////////////////////
    public List<User> getUserByRole(Role role){
        List<User> byRole = new ArrayList<>();
        for(User user : UserDAO.getAllUsers()) {
            if(user.getRole() == role) 
            {
                byRole.add(user);
            }
        }

        return byRole;
    }
/////////////////////////////////////////////////////////////////////////
    public static User getUserById(int id) {
        return UserDAO.getUserbyId(id);
    }
//////////////////////////////////////////////////////////////////////////
    public void addUser(User newEmployee) throws SQLException {


        UserDAO.create(newEmployee);
    }

    public boolean checkUserExistsById(int id) {
        return false;
    }
}
