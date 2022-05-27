package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.repositories.ReimbursementDAO;

public class ReimbursementService {
	
	public static ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
    public static UserService rService = new UserService();
    public static ArrayList<Reimbursement> reimbursements = new ArrayList<>();
    public static void clearData() {
        reimbursements.clear();
    }
    
	public static Reimbursement update(Reimbursement unprocessedReimbursment) {
		
//		User Founder = rService.getUserById(resolverID);
//
//        if(Founder.getRole() != Role.Founder) {
//            throw new RuntimeException("There was an error processing this reimbursement, please try again.");
//        }else {
//
//            unprocessedReimbursment.setResolver(resolverID);
//            unprocessedReimbursment.setStatus(updatedStatus);

            reimbursementDAO.update(unprocessedReimbursment);

         return unprocessedReimbursment;
        //}
}


public static int sumbitReimbursement (Reimbursement reimbursementToBeSubmitted) {
	User employee = rService.getUserById(reimbursementToBeSubmitted.getAuthor());

   

       
        reimbursementToBeSubmitted.setStatus(Status.Pending);


        return reimbursementDAO.create(reimbursementToBeSubmitted);
}


public static List<Reimbursement>getPendingReimbursements() {
	return ReimbursementDAO.getByStatus(Status.Pending);
		
	}
	

public static Reimbursement getReimbursementById(int id) {
	return ReimbursementDAO.getReimbursementById(id);
}

public static List<Reimbursement> getReimbursementsByAuthor(int userId) {

    List<Reimbursement> userReimbursements = new ArrayList<>();

        for(Reimbursement r : reimbursements) {
            if (r.getAuthor() == userId || r.getResolver() == userId) {
            }
        }
        return userReimbursements;
	}

public List<Reimbursement> getReimbursementByStatus(Status status) {
	// TODO Auto-generated method stub
	return reimbursementDAO.getByStatus(status);
}

}

