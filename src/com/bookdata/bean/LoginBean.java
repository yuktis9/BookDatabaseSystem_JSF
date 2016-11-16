package com.bookdata.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.bookdata.db.DBConnection;

@ManagedBean
public class LoginBean {

	public String getHomePage(){
		return "homePage";
	}
	
	
	public void loginPage(){
		
		FacesContext context = FacesContext.getCurrentInstance(); 
		   try { 
		     HttpServletRequest request   = 
		                   (HttpServletRequest) context.getExternalContext().getRequest();
		     /*HttpServletResponse response = 
		                   (HttpServletResponse) context.getExternalContext().getResponse();
		     RequestDispatcher dispatcher = 
		                   request.getRequestDispatcher("/GetName");
		     dispatcher.forward(request, response);*/
		     
		     DBConnection dbConnection = new DBConnection();
		     
		     request.getSession(true).setAttribute("dbConnection", dbConnection.createDBConnection()); 
		     
		   }
		   catch (Exception e) { 
		     e.printStackTrace(); 
		   } 
		   /*finally{ 
		     context.responseComplete();  
		   } */
	}
}
